package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.CurriculumAnalyticsDTO;
import com.jeeleducation.lms.dto.StudentPerformanceDTO;
import com.jeeleducation.lms.dto.StudentProgressDTO;
import com.jeeleducation.lms.dto.SystemStatsDTO;
import com.jeeleducation.lms.entity.ActivityType;
import com.jeeleducation.lms.entity.Curriculum;
import com.jeeleducation.lms.entity.Role;
import com.jeeleducation.lms.entity.Status;
import com.jeeleducation.lms.entity.User;
import com.jeeleducation.lms.repository.ActivityProgressRepository;
import com.jeeleducation.lms.repository.ActivityRepository;
import com.jeeleducation.lms.repository.CurriculumRepository;
import com.jeeleducation.lms.repository.LessonRepository;
import com.jeeleducation.lms.repository.QuizAttemptRepository;
import com.jeeleducation.lms.repository.SchoolRepository;
import com.jeeleducation.lms.repository.StudentProgressRepository;
import com.jeeleducation.lms.repository.UnitRepository;
import com.jeeleducation.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for admin panel operations.
 */
@Service
@Transactional(readOnly = true)
public class AdminPanelService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private StudentProgressRepository studentProgressRepository;

    @Autowired
    private ActivityProgressRepository activityProgressRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    /**
     * Get system-wide statistics.
     *
     * @return System stats DTO
     */
    public SystemStatsDTO getSystemStats() {
        // User statistics
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.findByIsActiveTrue().size();
        long totalStudents = userRepository.countByRole(Role.STUDENT);
        long totalTeachers = userRepository.countByRole(Role.TEACHER);
        long totalParents = userRepository.countByRole(Role.PARENT);
        long totalSchoolAdmins = userRepository.countByRole(Role.SCHOOL_ADMIN);
        long totalSuperAdmins = userRepository.countByRole(Role.SUPER_ADMIN);

        // School statistics
        long totalSchools = schoolRepository.count();
        long activeSchools = schoolRepository.countByIsActiveTrue();
        Long totalStudentsEnrolled = schoolRepository.getTotalStudentCount();

        // Curriculum statistics
        long totalCurriculums = curriculumRepository.count();
        long publishedCurriculums = curriculumRepository.countByStatus(Status.PUBLISHED);
        long draftCurriculums = curriculumRepository.countByStatus(Status.DRAFT);
        long totalUnits = unitRepository.count();
        long totalLessons = lessonRepository.count();
        long totalActivities = activityRepository.count();

        // Activity type breakdown
        Map<String, Long> activitiesByType = new HashMap<>();
        for (ActivityType type : ActivityType.values()) {
            activitiesByType.put(type.name(), activityRepository.countByActivityType(type));
        }

        // Progress statistics
        long totalEnrollments = studentProgressRepository.count();
        long completedCurriculums = studentProgressRepository.findAll().stream()
                .filter(sp -> sp.getCompletedAt() != null)
                .count();
        
        Double averageCompletion = studentProgressRepository.findAll().stream()
                .mapToDouble(sp -> sp.getCompletionPercentage())
                .average()
                .orElse(0.0);

        long totalQuizzes = quizAttemptRepository.count();
        Double averageQuizScore = quizAttemptRepository.findAll().stream()
                .mapToDouble(qa -> qa.getPercentage())
                .average()
                .orElse(0.0);

        return SystemStatsDTO.builder()
                .totalUsers(totalUsers)
                .activeUsers(activeUsers)
                .totalStudents(totalStudents)
                .totalTeachers(totalTeachers)
                .totalParents(totalParents)
                .totalSchoolAdmins(totalSchoolAdmins)
                .totalSuperAdmins(totalSuperAdmins)
                .totalSchools(totalSchools)
                .activeSchools(activeSchools)
                .totalStudentsEnrolled(totalStudentsEnrolled != null ? totalStudentsEnrolled : 0L)
                .totalCurriculums(totalCurriculums)
                .publishedCurriculums(publishedCurriculums)
                .draftCurriculums(draftCurriculums)
                .totalUnits(totalUnits)
                .totalLessons(totalLessons)
                .totalActivities(totalActivities)
                .activitiesByType(activitiesByType)
                .totalEnrollments(totalEnrollments)
                .completedCurriculums(completedCurriculums)
                .averageCompletionRate(averageCompletion)
                .totalQuizzesTaken(totalQuizzes)
                .averageQuizScore(averageQuizScore)
                .systemStatus("HEALTHY")
                .build();
    }

    /**
     * Get curriculum analytics.
     *
     * @param curriculumId Curriculum ID
     * @return Curriculum analytics DTO
     */
    public CurriculumAnalyticsDTO getCurriculumAnalytics(Long curriculumId) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new RuntimeException("Curriculum not found"));

        long enrolled = studentProgressRepository.countByCurriculumId(curriculumId);
        long completed = studentProgressRepository.findByCurriculumId(curriculumId).stream()
                .filter(sp -> sp.getCompletedAt() != null)
                .count();

        Double avgCompletion = studentProgressRepository.getAverageCompletionByCurriculum(curriculumId);

        // Calculate total points
        int totalPoints = curriculum.getUnits().stream()
                .flatMap(unit -> unit.getLessons().stream())
                .flatMap(lesson -> lesson.getActivities().stream())
                .mapToInt(activity -> activity.getPoints() != null ? activity.getPoints() : 0)
                .sum();

        // Count quizzes
        int totalQuizzes = (int) curriculum.getUnits().stream()
                .flatMap(unit -> unit.getLessons().stream())
                .flatMap(lesson -> lesson.getActivities().stream())
                .filter(activity -> activity.getActivityType() == ActivityType.QUIZ)
                .count();

        return CurriculumAnalyticsDTO.builder()
                .curriculumId(curriculum.getId())
                .curriculumName(curriculum.getName())
                .status(curriculum.getStatus().name())
                .totalUnits(curriculum.getUnitCount())
                .totalLessons(curriculum.getUnits().stream().mapToInt(u -> u.getLessonCount()).sum())
                .totalActivities(curriculum.getUnits().stream()
                        .flatMap(u -> u.getLessons().stream())
                        .mapToInt(l -> l.getActivityCount())
                        .sum())
                .totalPoints(totalPoints)
                .enrolledStudents(enrolled)
                .completedStudents(completed)
                .averageCompletionRate(avgCompletion != null ? avgCompletion : 0.0)
                .totalQuizzes(totalQuizzes)
                .build();
    }

    /**
     * Get top performing students.
     *
     * @param limit Number of students to return
     * @return List of student performance DTOs
     */
    public List<StudentPerformanceDTO> getTopPerformingStudents(int limit) {
        List<User> students = userRepository.findByRole(Role.STUDENT);
        
        return students.stream()
                .map(this::buildStudentPerformance)
                .sorted((a, b) -> Double.compare(b.getAverageQuizScore(), a.getAverageQuizScore()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Get student performance details.
     *
     * @param studentId Student ID
     * @return Student performance DTO
     */
    public StudentPerformanceDTO getStudentPerformance(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return buildStudentPerformance(student);
    }

    /**
     * Get all students performance.
     *
     * @return List of all student performance DTOs
     */
    public List<StudentPerformanceDTO> getAllStudentsPerformance() {
        List<User> students = userRepository.findByRole(Role.STUDENT);
        return students.stream()
                .map(this::buildStudentPerformance)
                .collect(Collectors.toList());
    }

    /**
     * Get curriculum leaderboard (top students in a curriculum).
     *
     * @param curriculumId Curriculum ID
     * @param limit Number of students
     * @return List of student progress DTOs
     */
    public List<StudentPerformanceDTO> getCurriculumLeaderboard(Long curriculumId, int limit) {
        return studentProgressRepository.findByCurriculumId(curriculumId).stream()
                .map(sp -> buildStudentPerformance(sp.getStudent()))
                .sorted((a, b) -> Integer.compare(b.getTotalPointsEarned(), a.getTotalPointsEarned()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Build student performance DTO.
     *
     * @param student Student user
     * @return Student performance DTO
     */
    private StudentPerformanceDTO buildStudentPerformance(User student) {
        List<StudentProgressDTO> progressList = studentProgressRepository.findByStudentId(student.getId())
                .stream()
                .map(sp -> StudentProgressDTO.builder()
                        .completionPercentage(sp.getCompletionPercentage())
                        .build())
                .collect(Collectors.toList());

        int enrolled = progressList.size();
        int completed = (int) studentProgressRepository.findCompletedByStudentId(student.getId()).size();

        Integer totalPoints = activityProgressRepository.getTotalEarnedPointsByStudent(student.getId());
        Integer totalActivities = activityProgressRepository.findByStudentIdAndIsCompletedTrue(student.getId()).size();

        Double avgCompletion = progressList.stream()
                .mapToDouble(StudentProgressDTO::getCompletionPercentage)
                .average()
                .orElse(0.0);

        Double avgQuizScore = quizAttemptRepository.getAverageScoreByStudent(student.getId());
        
        int totalQuizzes = quizAttemptRepository.findByStudentId(student.getId()).size();
        int passedQuizzes = quizAttemptRepository.findByStudentIdAndPassedTrue(student.getId()).size();

        // Determine performance level
        String performanceLevel = "Needs Improvement";
        if (avgQuizScore != null) {
            if (avgQuizScore >= 90) {
                performanceLevel = "Excellent";
            } else if (avgQuizScore >= 75) {
                performanceLevel = "Good";
            } else if (avgQuizScore >= 60) {
                performanceLevel = "Average";
            }
        }

        return StudentPerformanceDTO.builder()
                .studentId(student.getId())
                .studentName(student.getFullName())
                .studentEmail(student.getEmail())
                .enrolledCurriculums(enrolled)
                .completedCurriculums(completed)
                .totalActivitiesCompleted(totalActivities)
                .totalPointsEarned(totalPoints != null ? totalPoints : 0)
                .averageCompletionRate(avgCompletion)
                .averageQuizScore(avgQuizScore != null ? avgQuizScore : 0.0)
                .totalQuizzesTaken(totalQuizzes)
                .totalQuizzesPassed(passedQuizzes)
                .lastActiveAt(student.getLastLoginAt())
                .performanceLevel(performanceLevel)
                .build();
    }
}

