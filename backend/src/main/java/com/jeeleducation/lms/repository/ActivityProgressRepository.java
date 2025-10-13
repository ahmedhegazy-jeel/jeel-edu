package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.ActivityProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ActivityProgress entity.
 */
@Repository
public interface ActivityProgressRepository extends JpaRepository<ActivityProgress, Long> {

    /**
     * Find progress by student ID and activity ID.
     *
     * @param studentId Student ID
     * @param activityId Activity ID
     * @return Optional containing progress if found
     */
    Optional<ActivityProgress> findByStudentIdAndActivityId(Long studentId, Long activityId);

    /**
     * Find all activity progress for a student.
     *
     * @param studentId Student ID
     * @return List of activity progress
     */
    List<ActivityProgress> findByStudentId(Long studentId);

    /**
     * Find activity progress by student and lesson.
     *
     * @param studentId Student ID
     * @param lessonId Lesson ID
     * @return List of activity progress
     */
    @Query("SELECT ap FROM ActivityProgress ap WHERE ap.student.id = :studentId AND ap.activity.lesson.id = :lessonId")
    List<ActivityProgress> findByStudentIdAndLessonId(@Param("studentId") Long studentId, @Param("lessonId") Long lessonId);

    /**
     * Find completed activities for a student.
     *
     * @param studentId Student ID
     * @return List of completed activity progress
     */
    List<ActivityProgress> findByStudentIdAndIsCompletedTrue(Long studentId);

    /**
     * Count completed activities for a student in a lesson.
     *
     * @param studentId Student ID
     * @param lessonId Lesson ID
     * @return Number of completed activities
     */
    @Query("SELECT COUNT(ap) FROM ActivityProgress ap WHERE ap.student.id = :studentId AND ap.activity.lesson.id = :lessonId AND ap.isCompleted = true")
    long countCompletedByStudentAndLesson(@Param("studentId") Long studentId, @Param("lessonId") Long lessonId);

    /**
     * Get average score for a student.
     *
     * @param studentId Student ID
     * @return Average score percentage
     */
    @Query("SELECT AVG(ap.scorePercentage) FROM ActivityProgress ap WHERE ap.student.id = :studentId AND ap.scorePercentage IS NOT NULL")
    Double getAverageScoreByStudent(@Param("studentId") Long studentId);

    /**
     * Get total earned points for a student.
     *
     * @param studentId Student ID
     * @return Total earned points
     */
    @Query("SELECT COALESCE(SUM(ap.earnedPoints), 0) FROM ActivityProgress ap WHERE ap.student.id = :studentId")
    Integer getTotalEarnedPointsByStudent(@Param("studentId") Long studentId);
}

