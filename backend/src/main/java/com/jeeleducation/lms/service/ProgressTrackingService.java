package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.ActivityProgressDTO;
import com.jeeleducation.lms.dto.ProgressSummaryDTO;
import com.jeeleducation.lms.dto.QuizAttemptDTO;
import com.jeeleducation.lms.dto.StudentProgressDTO;
import com.jeeleducation.lms.entity.Activity;
import com.jeeleducation.lms.entity.ActivityProgress;
import com.jeeleducation.lms.entity.Curriculum;
import com.jeeleducation.lms.entity.QuizActivity;
import com.jeeleducation.lms.entity.QuizAttempt;
import com.jeeleducation.lms.entity.StudentProgress;
import com.jeeleducation.lms.entity.User;
import com.jeeleducation.lms.repository.ActivityProgressRepository;
import com.jeeleducation.lms.repository.ActivityRepository;
import com.jeeleducation.lms.repository.CurriculumRepository;
import com.jeeleducation.lms.repository.QuizAttemptRepository;
import com.jeeleducation.lms.repository.StudentProgressRepository;
import com.jeeleducation.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for progress tracking operations.
 */
@Service
@Transactional
public class ProgressTrackingService {

    @Autowired
    private StudentProgressRepository studentProgressRepository;

    @Autowired
    private ActivityProgressRepository activityProgressRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Enroll student in curriculum (initialize progress tracking).
     *
     * @param studentId Student ID
     * @param curriculumId Curriculum ID
     * @return Created progress DTO
     * @throws RuntimeException if student or curriculum not found or already enrolled
     */
    public StudentProgressDTO enrollStudentInCurriculum(Long studentId, Long curriculumId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        Curriculum curriculum = curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new RuntimeException("Curriculum not found with id: " + curriculumId));

        // Check if already enrolled
        if (studentProgressRepository.findByStudentIdAndCurriculumId(studentId, curriculumId).isPresent()) {
            throw new RuntimeException("Student is already enrolled in this curriculum");
        }

        // Calculate totals
        int totalUnits = curriculum.getUnitCount();
        int totalLessons = curriculum.getUnits().stream()
                .mapToInt(unit -> unit.getLessonCount())
                .sum();
        int totalActivities = curriculum.getUnits().stream()
                .flatMap(unit -> unit.getLessons().stream())
                .mapToInt(lesson -> lesson.getActivityCount())
                .sum();
        int totalPoints = curriculum.getUnits().stream()
                .flatMap(unit -> unit.getLessons().stream())
                .flatMap(lesson -> lesson.getActivities().stream())
                .mapToInt(activity -> activity.getPoints() != null ? activity.getPoints() : 0)
                .sum();

        StudentProgress progress = StudentProgress.builder()
                .student(student)
                .curriculum(curriculum)
                .totalUnits(totalUnits)
                .completedUnits(0)
                .totalLessons(totalLessons)
                .completedLessons(0)
                .totalActivities(totalActivities)
                .completedActivities(0)
                .totalPoints(totalPoints)
                .earnedPoints(0)
                .completionPercentage(0.0)
                .startedAt(LocalDateTime.now())
                .build();

        StudentProgress saved = studentProgressRepository.save(progress);
        return toStudentProgressDTO(saved);
    }

    /**
     * Get student progress for a curriculum.
     *
     * @param studentId Student ID
     * @param curriculumId Curriculum ID
     * @return Progress DTO
     * @throws RuntimeException if not found
     */
    public StudentProgressDTO getStudentProgress(Long studentId, Long curriculumId) {
        StudentProgress progress = studentProgressRepository.findByStudentIdAndCurriculumId(studentId, curriculumId)
                .orElseThrow(() -> new RuntimeException("Progress not found for student and curriculum"));
        return toStudentProgressDTO(progress);
    }

    /**
     * Get all progress for a student.
     *
     * @param studentId Student ID
     * @return List of progress DTOs
     */
    public List<StudentProgressDTO> getStudentProgressList(Long studentId) {
        return studentProgressRepository.findByStudentId(studentId).stream()
                .map(this::toStudentProgressDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get progress summary for a student.
     *
     * @param studentId Student ID
     * @return Progress summary DTO
     */
    public ProgressSummaryDTO getProgressSummary(Long studentId) {
        List<StudentProgressDTO> progressList = getStudentProgressList(studentId);
        
        int completed = (int) progressList.stream().filter(p -> p.getIsCompleted()).count();
        int inProgress = (int) progressList.stream()
                .filter(p -> !p.getIsCompleted() && p.getStartedAt() != null)
                .count();

        Integer totalActivitiesCompleted = activityProgressRepository
                .findByStudentIdAndIsCompletedTrue(studentId).size();
        
        Integer totalPointsEarned = activityProgressRepository.getTotalEarnedPointsByStudent(studentId);
        
        Double averageScore = activityProgressRepository.getAverageScoreByStudent(studentId);
        
        int totalQuizzes = quizAttemptRepository.findByStudentId(studentId).size();
        int passedQuizzes = quizAttemptRepository.findByStudentIdAndPassedTrue(studentId).size();

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return ProgressSummaryDTO.builder()
                .studentId(studentId)
                .studentName(student.getFullName())
                .totalCurriculums(progressList.size())
                .completedCurriculums(completed)
                .inProgressCurriculums(inProgress)
                .totalActivitiesCompleted(totalActivitiesCompleted)
                .totalPointsEarned(totalPointsEarned != null ? totalPointsEarned : 0)
                .averageScore(averageScore != null ? averageScore : 0.0)
                .totalQuizzesTaken(totalQuizzes)
                .totalQuizzesPassed(passedQuizzes)
                .curriculumProgress(progressList)
                .build();
    }

    /**
     * Start activity (track when student begins).
     *
     * @param studentId Student ID
     * @param activityId Activity ID
     * @return Activity progress DTO
     */
    public ActivityProgressDTO startActivity(Long studentId, Long activityId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        // Check if already started
        Optional<ActivityProgress> existing = activityProgressRepository
                .findByStudentIdAndActivityId(studentId, activityId);
        
        if (existing.isPresent()) {
            ActivityProgress progress = existing.get();
            progress.setLastAccessedAt(LocalDateTime.now());
            ActivityProgress updated = activityProgressRepository.save(progress);
            return toActivityProgressDTO(updated);
        }

        ActivityProgress progress = ActivityProgress.builder()
                .student(student)
                .activity(activity)
                .isCompleted(false)
                .isPassed(false)
                .attemptCount(0)
                .startedAt(LocalDateTime.now())
                .lastAccessedAt(LocalDateTime.now())
                .timeSpentSeconds(0)
                .build();

        ActivityProgress saved = activityProgressRepository.save(progress);
        return toActivityProgressDTO(saved);
    }

    /**
     * Complete activity.
     *
     * @param studentId Student ID
     * @param activityId Activity ID
     * @param earnedPoints Points earned
     * @param passed Whether student passed
     * @return Updated activity progress DTO
     */
    public ActivityProgressDTO completeActivity(Long studentId, Long activityId, 
                                                Integer earnedPoints, Boolean passed) {
        ActivityProgress progress = activityProgressRepository
                .findByStudentIdAndActivityId(studentId, activityId)
                .orElseThrow(() -> new RuntimeException("Activity progress not found"));

        progress.markCompleted(passed != null ? passed : true);
        progress.setEarnedPoints(earnedPoints);
        
        ActivityProgress updated = activityProgressRepository.save(progress);
        
        // Update curriculum progress
        updateCurriculumProgress(studentId, updated.getActivity().getLesson().getUnit().getCurriculum().getId());
        
        return toActivityProgressDTO(updated);
    }

    /**
     * Submit quiz attempt.
     *
     * @param studentId Student ID
     * @param quizId Quiz ID
     * @param correctAnswers Number of correct answers
     * @param timeSpent Time spent in seconds
     * @return Quiz attempt DTO
     */
    public QuizAttemptDTO submitQuizAttempt(Long studentId, Long quizId, 
                                           Integer correctAnswers, Integer timeSpent) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        QuizActivity quiz = (QuizActivity) activityRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        int attemptNumber = (int) quizAttemptRepository.countByStudentIdAndQuizId(studentId, quizId) + 1;
        int totalQuestions = quiz.getNumberOfQuestions() != null ? quiz.getNumberOfQuestions() : 0;

        QuizAttempt attempt = QuizAttempt.builder()
                .student(student)
                .quiz(quiz)
                .attemptNumber(attemptNumber)
                .totalQuestions(totalQuestions)
                .correctAnswers(correctAnswers)
                .incorrectAnswers(totalQuestions - correctAnswers)
                .timeSpentSeconds(timeSpent)
                .startedAt(LocalDateTime.now().minusSeconds(timeSpent != null ? timeSpent : 0))
                .completedAt(LocalDateTime.now())
                .build();

        attempt.calculateScore(quiz.getPoints());
        attempt.checkPassed(quiz.getPercentageToPass());

        QuizAttempt saved = quizAttemptRepository.save(attempt);

        // Update activity progress
        updateActivityProgressFromQuiz(studentId, quizId, saved.getPassed(), saved.getScore());

        return toQuizAttemptDTO(saved);
    }

    /**
     * Get quiz attempts for a student.
     *
     * @param studentId Student ID
     * @param quizId Quiz ID
     * @return List of quiz attempt DTOs
     */
    public List<QuizAttemptDTO> getQuizAttempts(Long studentId, Long quizId) {
        return quizAttemptRepository.findByStudentIdAndQuizIdOrderByAttemptNumberDesc(studentId, quizId).stream()
                .map(this::toQuizAttemptDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get activity progress for student.
     *
     * @param studentId Student ID
     * @param activityId Activity ID
     * @return Activity progress DTO
     */
    public ActivityProgressDTO getActivityProgress(Long studentId, Long activityId) {
        ActivityProgress progress = activityProgressRepository
                .findByStudentIdAndActivityId(studentId, activityId)
                .orElseThrow(() -> new RuntimeException("Activity progress not found"));
        return toActivityProgressDTO(progress);
    }

    /**
     * Update curriculum progress based on activity completion.
     *
     * @param studentId Student ID
     * @param curriculumId Curriculum ID
     */
    private void updateCurriculumProgress(Long studentId, Long curriculumId) {
        StudentProgress progress = studentProgressRepository
                .findByStudentIdAndCurriculumId(studentId, curriculumId)
                .orElse(null);

        if (progress == null) {
            return;
        }

        // Count completed activities
        Long completedCount = activityProgressRepository.findByStudentId(studentId).stream()
                .filter(ap -> ap.getIsCompleted() && 
                       ap.getActivity().getLesson().getUnit().getCurriculum().getId().equals(curriculumId))
                .count();

        progress.setCompletedActivities(completedCount.intValue());

        // Calculate points
        Integer earnedPoints = activityProgressRepository.findByStudentId(studentId).stream()
                .filter(ap -> ap.getIsCompleted() && 
                       ap.getActivity().getLesson().getUnit().getCurriculum().getId().equals(curriculumId))
                .mapToInt(ap -> ap.getEarnedPoints() != null ? ap.getEarnedPoints() : 0)
                .sum();

        progress.setEarnedPoints(earnedPoints);
        progress.updateCompletionPercentage();

        // Check if completed
        if (progress.getCompletedActivities().equals(progress.getTotalActivities()) && 
            progress.getCompletedAt() == null) {
            progress.setCompletedAt(LocalDateTime.now());
        }

        studentProgressRepository.save(progress);
    }

    /**
     * Update activity progress from quiz attempt.
     *
     * @param studentId Student ID
     * @param quizId Quiz ID
     * @param passed Whether passed
     * @param score Score earned
     */
    private void updateActivityProgressFromQuiz(Long studentId, Long quizId, Boolean passed, Integer score) {
        ActivityProgress progress = activityProgressRepository
                .findByStudentIdAndActivityId(studentId, quizId)
                .orElse(null);

        if (progress == null) {
            // Create new progress
            Activity activity = activityRepository.findById(quizId)
                    .orElseThrow(() -> new RuntimeException("Activity not found"));
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            progress = ActivityProgress.builder()
                    .student(student)
                    .activity(activity)
                    .startedAt(LocalDateTime.now())
                    .build();
        }

        progress.setAttemptCount((progress.getAttemptCount() != null ? progress.getAttemptCount() : 0) + 1);
        progress.setCurrentScore(score);
        progress.setEarnedPoints(passed ? score : 0);
        progress.calculateScorePercentage();

        if (passed) {
            progress.markCompleted(true);
        }

        activityProgressRepository.save(progress);
    }

    /**
     * Convert StudentProgress to DTO.
     */
    private StudentProgressDTO toStudentProgressDTO(StudentProgress progress) {
        return StudentProgressDTO.builder()
                .id(progress.getId())
                .studentId(progress.getStudent().getId())
                .studentName(progress.getStudent().getFullName())
                .curriculumId(progress.getCurriculum().getId())
                .curriculumName(progress.getCurriculum().getName())
                .totalUnits(progress.getTotalUnits())
                .completedUnits(progress.getCompletedUnits())
                .totalLessons(progress.getTotalLessons())
                .completedLessons(progress.getCompletedLessons())
                .totalActivities(progress.getTotalActivities())
                .completedActivities(progress.getCompletedActivities())
                .totalPoints(progress.getTotalPoints())
                .earnedPoints(progress.getEarnedPoints())
                .completionPercentage(progress.getCompletionPercentage())
                .startedAt(progress.getStartedAt())
                .completedAt(progress.getCompletedAt())
                .isCompleted(progress.isCompleted())
                .createdAt(progress.getCreatedAt())
                .updatedAt(progress.getUpdatedAt())
                .build();
    }

    /**
     * Convert ActivityProgress to DTO.
     */
    private ActivityProgressDTO toActivityProgressDTO(ActivityProgress progress) {
        return ActivityProgressDTO.builder()
                .id(progress.getId())
                .studentId(progress.getStudent().getId())
                .studentName(progress.getStudent().getFullName())
                .activityId(progress.getActivity().getId())
                .activityTitle(progress.getActivity().getTitleName())
                .activityType(progress.getActivity().getActivityType().name())
                .isCompleted(progress.getIsCompleted())
                .isPassed(progress.getIsPassed())
                .earnedPoints(progress.getEarnedPoints())
                .attemptCount(progress.getAttemptCount())
                .maxScore(progress.getMaxScore())
                .currentScore(progress.getCurrentScore())
                .scorePercentage(progress.getScorePercentage())
                .startedAt(progress.getStartedAt())
                .completedAt(progress.getCompletedAt())
                .lastAccessedAt(progress.getLastAccessedAt())
                .timeSpentSeconds(progress.getTimeSpentSeconds())
                .createdAt(progress.getCreatedAt())
                .updatedAt(progress.getUpdatedAt())
                .build();
    }

    /**
     * Convert QuizAttempt to DTO.
     */
    private QuizAttemptDTO toQuizAttemptDTO(QuizAttempt attempt) {
        return QuizAttemptDTO.builder()
                .id(attempt.getId())
                .studentId(attempt.getStudent().getId())
                .studentName(attempt.getStudent().getFullName())
                .quizId(attempt.getQuiz().getId())
                .quizTitle(attempt.getQuiz().getTitleName())
                .attemptNumber(attempt.getAttemptNumber())
                .totalQuestions(attempt.getTotalQuestions())
                .correctAnswers(attempt.getCorrectAnswers())
                .incorrectAnswers(attempt.getIncorrectAnswers())
                .score(attempt.getScore())
                .percentage(attempt.getPercentage())
                .passed(attempt.getPassed())
                .timeSpentSeconds(attempt.getTimeSpentSeconds())
                .startedAt(attempt.getStartedAt())
                .completedAt(attempt.getCompletedAt())
                .createdAt(attempt.getCreatedAt())
                .build();
    }
}

