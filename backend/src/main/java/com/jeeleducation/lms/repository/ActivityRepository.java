package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.Activity;
import com.jeeleducation.lms.entity.ActivityType;
import com.jeeleducation.lms.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Activity entity.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    /**
     * Find activities by lesson ID.
     *
     * @param lessonId Lesson ID
     * @return List of activities in lesson
     */
    List<Activity> findByLessonIdOrderByDisplayOrderAsc(Long lessonId);

    /**
     * Find activities by lesson ID and status.
     *
     * @param lessonId Lesson ID
     * @param status Status to filter by
     * @return List of activities matching criteria
     */
    List<Activity> findByLessonIdAndStatusOrderByDisplayOrderAsc(Long lessonId, Status status);

    /**
     * Find activities by type.
     *
     * @param activityType Activity type
     * @return List of activities of specified type
     */
    List<Activity> findByActivityType(ActivityType activityType);

    /**
     * Find activities by lesson ID and type.
     *
     * @param lessonId Lesson ID
     * @param activityType Activity type
     * @return List of activities matching criteria
     */
    List<Activity> findByLessonIdAndActivityType(Long lessonId, ActivityType activityType);

    /**
     * Count activities by lesson ID.
     *
     * @param lessonId Lesson ID
     * @return Number of activities in lesson
     */
    long countByLessonId(Long lessonId);

    /**
     * Count activities by type.
     *
     * @param activityType Activity type
     * @return Number of activities of specified type
     */
    long countByActivityType(ActivityType activityType);

    /**
     * Find activities by tag.
     *
     * @param tag Tag to filter by
     * @return List of activities with specified tag
     */
    List<Activity> findByTag(String tag);

    /**
     * Search activities by title within a lesson.
     *
     * @param lessonId Lesson ID
     * @param searchTerm Search term
     * @return List of matching activities
     */
    @Query("SELECT a FROM Activity a WHERE a.lesson.id = :lessonId AND LOWER(a.titleName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Activity> searchByTitleInLesson(@Param("lessonId") Long lessonId, @Param("searchTerm") String searchTerm);
}

