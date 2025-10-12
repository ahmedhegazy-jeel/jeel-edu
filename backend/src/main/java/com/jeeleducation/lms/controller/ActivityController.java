package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.ActivityDTO;
import com.jeeleducation.lms.dto.CreateActivityRequest;
import com.jeeleducation.lms.dto.MessageResponse;
import com.jeeleducation.lms.entity.ActivityType;
import com.jeeleducation.lms.entity.Status;
import com.jeeleducation.lms.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST controller for activity management operations.
 */
@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * Create new activity.
     *
     * @param request Activity creation request
     * @return Created activity DTO
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody CreateActivityRequest request) {
        ActivityDTO created = activityService.createActivity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get all activities.
     *
     * @return List of all activities
     */
    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<ActivityDTO> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    /**
     * Get activity by ID.
     *
     * @param id Activity ID
     * @return Activity DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
        ActivityDTO activity = activityService.getActivityById(id);
        return ResponseEntity.ok(activity);
    }

    /**
     * Get activities by lesson ID.
     *
     * @param lessonId Lesson ID
     * @return List of activities in lesson
     */
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<ActivityDTO>> getActivitiesByLesson(@PathVariable Long lessonId) {
        List<ActivityDTO> activities = activityService.getActivitiesByLessonId(lessonId);
        return ResponseEntity.ok(activities);
    }

    /**
     * Get activities by lesson ID and status.
     *
     * @param lessonId Lesson ID
     * @param status Status to filter by
     * @return List of activities matching criteria
     */
    @GetMapping("/lesson/{lessonId}/status/{status}")
    public ResponseEntity<List<ActivityDTO>> getActivitiesByLessonAndStatus(
            @PathVariable Long lessonId,
            @PathVariable Status status) {
        List<ActivityDTO> activities = activityService.getActivitiesByLessonIdAndStatus(lessonId, status);
        return ResponseEntity.ok(activities);
    }

    /**
     * Get activities by type.
     *
     * @param type Activity type
     * @return List of activities of specified type
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ActivityDTO>> getActivitiesByType(@PathVariable ActivityType type) {
        List<ActivityDTO> activities = activityService.getActivitiesByType(type);
        return ResponseEntity.ok(activities);
    }

    /**
     * Get activities by lesson ID and type.
     *
     * @param lessonId Lesson ID
     * @param type Activity type
     * @return List of activities matching criteria
     */
    @GetMapping("/lesson/{lessonId}/type/{type}")
    public ResponseEntity<List<ActivityDTO>> getActivitiesByLessonAndType(
            @PathVariable Long lessonId,
            @PathVariable ActivityType type) {
        List<ActivityDTO> activities = activityService.getActivitiesByLessonIdAndType(lessonId, type);
        return ResponseEntity.ok(activities);
    }

    /**
     * Get activities by tag.
     *
     * @param tag Tag to filter by
     * @return List of activities with specified tag
     */
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<ActivityDTO>> getActivitiesByTag(@PathVariable String tag) {
        List<ActivityDTO> activities = activityService.getActivitiesByTag(tag);
        return ResponseEntity.ok(activities);
    }

    /**
     * Search activities by title within a lesson.
     *
     * @param lessonId Lesson ID
     * @param searchTerm Search term
     * @return List of matching activities
     */
    @GetMapping("/lesson/{lessonId}/search")
    public ResponseEntity<List<ActivityDTO>> searchActivitiesInLesson(
            @PathVariable Long lessonId,
            @RequestParam String searchTerm) {
        List<ActivityDTO> activities = activityService.searchActivitiesInLesson(lessonId, searchTerm);
        return ResponseEntity.ok(activities);
    }

    /**
     * Get activity count by type.
     *
     * @param type Activity type
     * @return Activity count
     */
    @GetMapping("/type/{type}/count")
    public ResponseEntity<Map<String, Long>> countActivitiesByType(@PathVariable ActivityType type) {
        long count = activityService.countActivitiesByType(type);
        return ResponseEntity.ok(Map.of("count", count));
    }

    /**
     * Update activity.
     *
     * @param id Activity ID
     * @param request Update request
     * @return Updated activity DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<ActivityDTO> updateActivity(
            @PathVariable Long id,
            @Valid @RequestBody CreateActivityRequest request) {
        ActivityDTO updated = activityService.updateActivity(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete activity.
     *
     * @param id Activity ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.ok(new MessageResponse("Activity deleted successfully"));
    }
}

