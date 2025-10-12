package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.CreateLessonRequest;
import com.jeeleducation.lms.dto.LessonDTO;
import com.jeeleducation.lms.entity.Lesson;
import com.jeeleducation.lms.entity.Status;
import com.jeeleducation.lms.entity.Unit;
import com.jeeleducation.lms.repository.LessonRepository;
import com.jeeleducation.lms.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for lesson management operations.
 */
@Service
@Transactional
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UnitRepository unitRepository;

    /**
     * Create a new lesson.
     *
     * @param request Lesson creation request
     * @return Created lesson DTO
     * @throws RuntimeException if unit not found
     */
    public LessonDTO createLesson(CreateLessonRequest request) {
        Unit unit = unitRepository.findById(request.getUnitId())
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + request.getUnitId()));

        Lesson lesson = Lesson.builder()
                .name(request.getName())
                .description(request.getDescription())
                .audioName(request.getAudioName())
                .icon(request.getIcon())
                .status(request.getStatus() != null ? request.getStatus() : Status.DRAFT)
                .displayOrder(request.getDisplayOrder())
                .unit(unit)
                .build();

        Lesson saved = lessonRepository.save(lesson);
        return toDTO(saved);
    }

    /**
     * Get lesson by ID.
     *
     * @param id Lesson ID
     * @return Lesson DTO
     * @throws RuntimeException if not found
     */
    public LessonDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + id));
        return toDTO(lesson);
    }

    /**
     * Get all lessons.
     *
     * @return List of lesson DTOs
     */
    public List<LessonDTO> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get lessons by unit ID.
     *
     * @param unitId Unit ID
     * @return List of lesson DTOs
     */
    public List<LessonDTO> getLessonsByUnitId(Long unitId) {
        return lessonRepository.findByUnitIdOrderByDisplayOrderAsc(unitId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get lessons by unit ID and status.
     *
     * @param unitId Unit ID
     * @param status Status to filter by
     * @return List of lesson DTOs
     */
    public List<LessonDTO> getLessonsByUnitIdAndStatus(Long unitId, Status status) {
        return lessonRepository.findByUnitIdAndStatusOrderByDisplayOrderAsc(unitId, status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get lessons by status.
     *
     * @param status Status to filter by
     * @return List of lesson DTOs
     */
    public List<LessonDTO> getLessonsByStatus(Status status) {
        return lessonRepository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search lessons by name within a unit.
     *
     * @param unitId Unit ID
     * @param searchTerm Search term
     * @return List of matching lesson DTOs
     */
    public List<LessonDTO> searchLessonsInUnit(Long unitId, String searchTerm) {
        return lessonRepository.searchByNameInUnit(unitId, searchTerm).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update lesson.
     *
     * @param id Lesson ID
     * @param request Update request
     * @return Updated lesson DTO
     * @throws RuntimeException if not found
     */
    public LessonDTO updateLesson(Long id, CreateLessonRequest request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + id));

        if (request.getName() != null) {
            lesson.setName(request.getName());
        }
        if (request.getDescription() != null) {
            lesson.setDescription(request.getDescription());
        }
        if (request.getAudioName() != null) {
            lesson.setAudioName(request.getAudioName());
        }
        if (request.getIcon() != null) {
            lesson.setIcon(request.getIcon());
        }
        if (request.getStatus() != null) {
            lesson.setStatus(request.getStatus());
        }
        if (request.getDisplayOrder() != null) {
            lesson.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getUnitId() != null && !request.getUnitId().equals(lesson.getUnit().getId())) {
            Unit unit = unitRepository.findById(request.getUnitId())
                    .orElseThrow(() -> new RuntimeException("Unit not found with id: " + request.getUnitId()));
            lesson.setUnit(unit);
        }

        Lesson updated = lessonRepository.save(lesson);
        return toDTO(updated);
    }

    /**
     * Delete lesson.
     *
     * @param id Lesson ID
     * @throws RuntimeException if not found
     */
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new RuntimeException("Lesson not found with id: " + id);
        }
        lessonRepository.deleteById(id);
    }

    /**
     * Convert Lesson entity to DTO.
     *
     * @param lesson Lesson entity
     * @return Lesson DTO
     */
    private LessonDTO toDTO(Lesson lesson) {
        return LessonDTO.builder()
                .id(lesson.getId())
                .name(lesson.getName())
                .description(lesson.getDescription())
                .audioName(lesson.getAudioName())
                .icon(lesson.getIcon())
                .status(lesson.getStatus())
                .displayOrder(lesson.getDisplayOrder())
                .unitId(lesson.getUnit().getId())
                .unitName(lesson.getUnit().getName())
                .activityCount(lesson.getActivityCount())
                .createdAt(lesson.getCreatedAt())
                .updatedAt(lesson.getUpdatedAt())
                .build();
    }
}

