package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.ActivityDTO;
import com.jeeleducation.lms.dto.CreateActivityRequest;
import com.jeeleducation.lms.entity.Activity;
import com.jeeleducation.lms.entity.ActivityType;
import com.jeeleducation.lms.entity.Lesson;
import com.jeeleducation.lms.entity.Status;
import com.jeeleducation.lms.repository.ActivityRepository;
import com.jeeleducation.lms.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for activity management operations.
 */
@Service
@Transactional
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private LessonRepository lessonRepository;

    /**
     * Create a new activity.
     *
     * @param request Activity creation request
     * @return Created activity DTO
     * @throws RuntimeException if lesson not found
     */
    public ActivityDTO createActivity(CreateActivityRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + request.getLessonId()));

        Activity activity = Activity.builder()
                .titleName(request.getTitleName())
                .titleAudioName(request.getTitleAudioName())
                .description(request.getDescription())
                .icon(request.getIcon())
                .activityType(request.getActivityType())
                .status(request.getStatus() != null ? request.getStatus() : Status.DRAFT)
                .points(request.getPoints())
                .tag(request.getTag())
                .topic(request.getTopic())
                .displayOrder(request.getDisplayOrder())
                .lesson(lesson)
                .build();

        Activity saved = activityRepository.save(activity);
        return toDTO(saved);
    }

    /**
     * Get activity by ID.
     *
     * @param id Activity ID
     * @return Activity DTO
     * @throws RuntimeException if not found
     */
    public ActivityDTO getActivityById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
        return toDTO(activity);
    }

    /**
     * Get all activities.
     *
     * @return List of activity DTOs
     */
    public List<ActivityDTO> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get activities by lesson ID.
     *
     * @param lessonId Lesson ID
     * @return List of activity DTOs
     */
    public List<ActivityDTO> getActivitiesByLessonId(Long lessonId) {
        return activityRepository.findByLessonIdOrderByDisplayOrderAsc(lessonId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get activities by lesson ID and status.
     *
     * @param lessonId Lesson ID
     * @param status Status to filter by
     * @return List of activity DTOs
     */
    public List<ActivityDTO> getActivitiesByLessonIdAndStatus(Long lessonId, Status status) {
        return activityRepository.findByLessonIdAndStatusOrderByDisplayOrderAsc(lessonId, status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get activities by type.
     *
     * @param activityType Activity type
     * @return List of activity DTOs
     */
    public List<ActivityDTO> getActivitiesByType(ActivityType activityType) {
        return activityRepository.findByActivityType(activityType).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get activities by lesson ID and type.
     *
     * @param lessonId Lesson ID
     * @param activityType Activity type
     * @return List of activity DTOs
     */
    public List<ActivityDTO> getActivitiesByLessonIdAndType(Long lessonId, ActivityType activityType) {
        return activityRepository.findByLessonIdAndActivityType(lessonId, activityType).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get activities by tag.
     *
     * @param tag Tag to filter by
     * @return List of activity DTOs
     */
    public List<ActivityDTO> getActivitiesByTag(String tag) {
        return activityRepository.findByTag(tag).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search activities by title within a lesson.
     *
     * @param lessonId Lesson ID
     * @param searchTerm Search term
     * @return List of matching activity DTOs
     */
    public List<ActivityDTO> searchActivitiesInLesson(Long lessonId, String searchTerm) {
        return activityRepository.searchByTitleInLesson(lessonId, searchTerm).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update activity.
     *
     * @param id Activity ID
     * @param request Update request
     * @return Updated activity DTO
     * @throws RuntimeException if not found
     */
    public ActivityDTO updateActivity(Long id, CreateActivityRequest request) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));

        if (request.getTitleName() != null) {
            activity.setTitleName(request.getTitleName());
        }
        if (request.getTitleAudioName() != null) {
            activity.setTitleAudioName(request.getTitleAudioName());
        }
        if (request.getDescription() != null) {
            activity.setDescription(request.getDescription());
        }
        if (request.getIcon() != null) {
            activity.setIcon(request.getIcon());
        }
        if (request.getActivityType() != null) {
            activity.setActivityType(request.getActivityType());
        }
        if (request.getStatus() != null) {
            activity.setStatus(request.getStatus());
        }
        if (request.getPoints() != null) {
            activity.setPoints(request.getPoints());
        }
        if (request.getTag() != null) {
            activity.setTag(request.getTag());
        }
        if (request.getTopic() != null) {
            activity.setTopic(request.getTopic());
        }
        if (request.getDisplayOrder() != null) {
            activity.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getLessonId() != null && !request.getLessonId().equals(activity.getLesson().getId())) {
            Lesson lesson = lessonRepository.findById(request.getLessonId())
                    .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + request.getLessonId()));
            activity.setLesson(lesson);
        }

        Activity updated = activityRepository.save(activity);
        return toDTO(updated);
    }

    /**
     * Delete activity.
     *
     * @param id Activity ID
     * @throws RuntimeException if not found
     */
    public void deleteActivity(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new RuntimeException("Activity not found with id: " + id);
        }
        activityRepository.deleteById(id);
    }

    /**
     * Get activity count by type.
     *
     * @param activityType Activity type
     * @return Count of activities
     */
    public long countActivitiesByType(ActivityType activityType) {
        return activityRepository.countByActivityType(activityType);
    }

    /**
     * Convert Activity entity to DTO.
     *
     * @param activity Activity entity
     * @return Activity DTO
     */
    private ActivityDTO toDTO(Activity activity) {
        Map<String, Object> typeSpecificData = new HashMap<>();
        // Note: Type-specific data would need to be populated based on activity type
        // For now, returning basic DTO structure

        return ActivityDTO.builder()
                .id(activity.getId())
                .titleName(activity.getTitleName())
                .titleAudioName(activity.getTitleAudioName())
                .description(activity.getDescription())
                .icon(activity.getIcon())
                .activityType(activity.getActivityType())
                .status(activity.getStatus())
                .points(activity.getPoints())
                .tag(activity.getTag())
                .topic(activity.getTopic())
                .displayOrder(activity.getDisplayOrder())
                .lessonId(activity.getLesson().getId())
                .lessonName(activity.getLesson().getName())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .typeSpecificData(typeSpecificData)
                .build();
    }
}

