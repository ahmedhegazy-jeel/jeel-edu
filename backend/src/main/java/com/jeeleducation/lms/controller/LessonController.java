package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.CreateLessonRequest;
import com.jeeleducation.lms.dto.LessonDTO;
import com.jeeleducation.lms.dto.MessageResponse;
import com.jeeleducation.lms.entity.Status;
import com.jeeleducation.lms.service.LessonService;
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

/**
 * REST controller for lesson management operations.
 */
@RestController
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    /**
     * Create new lesson.
     *
     * @param request Lesson creation request
     * @return Created lesson DTO
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<LessonDTO> createLesson(@Valid @RequestBody CreateLessonRequest request) {
        LessonDTO created = lessonService.createLesson(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get all lessons.
     *
     * @return List of all lessons
     */
    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        List<LessonDTO> lessons = lessonService.getAllLessons();
        return ResponseEntity.ok(lessons);
    }

    /**
     * Get lesson by ID.
     *
     * @param id Lesson ID
     * @return Lesson DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        LessonDTO lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(lesson);
    }

    /**
     * Get lessons by unit ID.
     *
     * @param unitId Unit ID
     * @return List of lessons in unit
     */
    @GetMapping("/unit/{unitId}")
    public ResponseEntity<List<LessonDTO>> getLessonsByUnit(@PathVariable Long unitId) {
        List<LessonDTO> lessons = lessonService.getLessonsByUnitId(unitId);
        return ResponseEntity.ok(lessons);
    }

    /**
     * Get lessons by unit ID and status.
     *
     * @param unitId Unit ID
     * @param status Status to filter by
     * @return List of lessons matching criteria
     */
    @GetMapping("/unit/{unitId}/status/{status}")
    public ResponseEntity<List<LessonDTO>> getLessonsByUnitAndStatus(
            @PathVariable Long unitId,
            @PathVariable Status status) {
        List<LessonDTO> lessons = lessonService.getLessonsByUnitIdAndStatus(unitId, status);
        return ResponseEntity.ok(lessons);
    }

    /**
     * Get lessons by status.
     *
     * @param status Status to filter by
     * @return List of lessons with specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LessonDTO>> getLessonsByStatus(@PathVariable Status status) {
        List<LessonDTO> lessons = lessonService.getLessonsByStatus(status);
        return ResponseEntity.ok(lessons);
    }

    /**
     * Search lessons by name within a unit.
     *
     * @param unitId Unit ID
     * @param searchTerm Search term
     * @return List of matching lessons
     */
    @GetMapping("/unit/{unitId}/search")
    public ResponseEntity<List<LessonDTO>> searchLessonsInUnit(
            @PathVariable Long unitId,
            @RequestParam String searchTerm) {
        List<LessonDTO> lessons = lessonService.searchLessonsInUnit(unitId, searchTerm);
        return ResponseEntity.ok(lessons);
    }

    /**
     * Update lesson.
     *
     * @param id Lesson ID
     * @param request Update request
     * @return Updated lesson DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<LessonDTO> updateLesson(
            @PathVariable Long id,
            @Valid @RequestBody CreateLessonRequest request) {
        LessonDTO updated = lessonService.updateLesson(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete lesson.
     *
     * @param id Lesson ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok(new MessageResponse("Lesson deleted successfully"));
    }
}

