package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.ActivityProgressDTO;
import com.jeeleducation.lms.dto.MessageResponse;
import com.jeeleducation.lms.dto.ProgressSummaryDTO;
import com.jeeleducation.lms.dto.QuizAttemptDTO;
import com.jeeleducation.lms.dto.StudentProgressDTO;
import com.jeeleducation.lms.security.UserDetailsImpl;
import com.jeeleducation.lms.service.ProgressTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST controller for progress tracking operations.
 */
@RestController
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressTrackingService progressService;

    /**
     * Enroll student in curriculum.
     *
     * @param studentId Student ID
     * @param request Request with curriculumId
     * @return Created progress DTO
     */
    @PostMapping("/enroll/{studentId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN', 'TEACHER')")
    public ResponseEntity<StudentProgressDTO> enrollStudent(
            @PathVariable Long studentId,
            @RequestBody Map<String, Long> request) {
        Long curriculumId = request.get("curriculumId");
        StudentProgressDTO progress = progressService.enrollStudentInCurriculum(studentId, curriculumId);
        return ResponseEntity.status(HttpStatus.CREATED).body(progress);
    }

    /**
     * Get current student's progress summary.
     *
     * @param authentication Authentication object
     * @return Progress summary DTO
     */
    @GetMapping("/my-summary")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ProgressSummaryDTO> getMyProgressSummary(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ProgressSummaryDTO summary = progressService.getProgressSummary(userDetails.getId());
        return ResponseEntity.ok(summary);
    }

    /**
     * Get progress summary for a student.
     *
     * @param studentId Student ID
     * @return Progress summary DTO
     */
    @GetMapping("/student/{studentId}/summary")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN', 'TEACHER', 'PARENT')")
    public ResponseEntity<ProgressSummaryDTO> getStudentProgressSummary(@PathVariable Long studentId) {
        ProgressSummaryDTO summary = progressService.getProgressSummary(studentId);
        return ResponseEntity.ok(summary);
    }

    /**
     * Get student progress for a curriculum.
     *
     * @param studentId Student ID
     * @param curriculumId Curriculum ID
     * @return Progress DTO
     */
    @GetMapping("/student/{studentId}/curriculum/{curriculumId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN', 'TEACHER', 'PARENT', 'STUDENT')")
    public ResponseEntity<StudentProgressDTO> getStudentCurriculumProgress(
            @PathVariable Long studentId,
            @PathVariable Long curriculumId) {
        StudentProgressDTO progress = progressService.getStudentProgress(studentId, curriculumId);
        return ResponseEntity.ok(progress);
    }

    /**
     * Get all progress for a student.
     *
     * @param studentId Student ID
     * @return List of progress DTOs
     */
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN', 'TEACHER', 'PARENT')")
    public ResponseEntity<List<StudentProgressDTO>> getStudentProgress(@PathVariable Long studentId) {
        List<StudentProgressDTO> progressList = progressService.getStudentProgressList(studentId);
        return ResponseEntity.ok(progressList);
    }

    /**
     * Get current student's progress list.
     *
     * @param authentication Authentication object
     * @return List of progress DTOs
     */
    @GetMapping("/my-progress")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<StudentProgressDTO>> getMyProgress(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<StudentProgressDTO> progressList = progressService.getStudentProgressList(userDetails.getId());
        return ResponseEntity.ok(progressList);
    }

    /**
     * Start activity (track when student begins).
     *
     * @param authentication Authentication object
     * @param activityId Activity ID
     * @return Activity progress DTO
     */
    @PostMapping("/activity/{activityId}/start")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ActivityProgressDTO> startActivity(
            Authentication authentication,
            @PathVariable Long activityId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ActivityProgressDTO progress = progressService.startActivity(userDetails.getId(), activityId);
        return ResponseEntity.ok(progress);
    }

    /**
     * Complete activity.
     *
     * @param authentication Authentication object
     * @param activityId Activity ID
     * @param request Request with earnedPoints and passed
     * @return Updated activity progress DTO
     */
    @PostMapping("/activity/{activityId}/complete")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ActivityProgressDTO> completeActivity(
            Authentication authentication,
            @PathVariable Long activityId,
            @RequestBody Map<String, Object> request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        Integer earnedPoints = request.get("earnedPoints") != null ? 
                (Integer) request.get("earnedPoints") : 0;
        Boolean passed = request.get("passed") != null ? 
                (Boolean) request.get("passed") : true;

        ActivityProgressDTO progress = progressService.completeActivity(
                userDetails.getId(), activityId, earnedPoints, passed);
        return ResponseEntity.ok(progress);
    }

    /**
     * Get activity progress for current student.
     *
     * @param authentication Authentication object
     * @param activityId Activity ID
     * @return Activity progress DTO
     */
    @GetMapping("/activity/{activityId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ActivityProgressDTO> getMyActivityProgress(
            Authentication authentication,
            @PathVariable Long activityId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ActivityProgressDTO progress = progressService.getActivityProgress(userDetails.getId(), activityId);
        return ResponseEntity.ok(progress);
    }

    /**
     * Submit quiz attempt.
     *
     * @param authentication Authentication object
     * @param quizId Quiz ID
     * @param request Request with correctAnswers and timeSpent
     * @return Quiz attempt DTO
     */
    @PostMapping("/quiz/{quizId}/submit")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<QuizAttemptDTO> submitQuiz(
            Authentication authentication,
            @PathVariable Long quizId,
            @RequestBody Map<String, Integer> request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        Integer correctAnswers = request.get("correctAnswers");
        Integer timeSpent = request.get("timeSpent");

        QuizAttemptDTO attempt = progressService.submitQuizAttempt(
                userDetails.getId(), quizId, correctAnswers, timeSpent);
        return ResponseEntity.status(HttpStatus.CREATED).body(attempt);
    }

    /**
     * Get quiz attempts for current student.
     *
     * @param authentication Authentication object
     * @param quizId Quiz ID
     * @return List of quiz attempt DTOs
     */
    @GetMapping("/quiz/{quizId}/attempts")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<QuizAttemptDTO>> getMyQuizAttempts(
            Authentication authentication,
            @PathVariable Long quizId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<QuizAttemptDTO> attempts = progressService.getQuizAttempts(userDetails.getId(), quizId);
        return ResponseEntity.ok(attempts);
    }

    /**
     * Get quiz attempts for a student (admin/teacher/parent view).
     *
     * @param studentId Student ID
     * @param quizId Quiz ID
     * @return List of quiz attempt DTOs
     */
    @GetMapping("/student/{studentId}/quiz/{quizId}/attempts")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN', 'TEACHER', 'PARENT')")
    public ResponseEntity<List<QuizAttemptDTO>> getStudentQuizAttempts(
            @PathVariable Long studentId,
            @PathVariable Long quizId) {
        List<QuizAttemptDTO> attempts = progressService.getQuizAttempts(studentId, quizId);
        return ResponseEntity.ok(attempts);
    }
}

