package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.CreateCurriculumRequest;
import com.jeeleducation.lms.dto.CurriculumDTO;
import com.jeeleducation.lms.dto.MessageResponse;
import com.jeeleducation.lms.dto.UpdateCurriculumRequest;
import com.jeeleducation.lms.entity.Status;
import com.jeeleducation.lms.service.CurriculumService;
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
 * REST controller for curriculum management operations.
 * Accessible by SUPER_ADMIN and SCHOOL_ADMIN roles.
 */
@RestController
@RequestMapping("/api/curriculums")
public class CurriculumController {

    @Autowired
    private CurriculumService curriculumService;

    /**
     * Create new curriculum.
     *
     * @param request Curriculum creation request
     * @return Created curriculum DTO
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<CurriculumDTO> createCurriculum(@Valid @RequestBody CreateCurriculumRequest request) {
        CurriculumDTO created = curriculumService.createCurriculum(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get all curriculums.
     *
     * @return List of all curriculums
     */
    @GetMapping
    public ResponseEntity<List<CurriculumDTO>> getAllCurriculums() {
        List<CurriculumDTO> curriculums = curriculumService.getAllCurriculums();
        return ResponseEntity.ok(curriculums);
    }

    /**
     * Get curriculum by ID.
     *
     * @param id Curriculum ID
     * @return Curriculum DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CurriculumDTO> getCurriculumById(@PathVariable Long id) {
        CurriculumDTO curriculum = curriculumService.getCurriculumById(id);
        return ResponseEntity.ok(curriculum);
    }

    /**
     * Get curriculums by status.
     *
     * @param status Status to filter by
     * @return List of curriculums with specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CurriculumDTO>> getCurriculumsByStatus(@PathVariable Status status) {
        List<CurriculumDTO> curriculums = curriculumService.getCurriculumsByStatus(status);
        return ResponseEntity.ok(curriculums);
    }

    /**
     * Search curriculums by name.
     *
     * @param searchTerm Search term
     * @return List of matching curriculums
     */
    @GetMapping("/search")
    public ResponseEntity<List<CurriculumDTO>> searchCurriculums(@RequestParam String searchTerm) {
        List<CurriculumDTO> curriculums = curriculumService.searchCurriculums(searchTerm);
        return ResponseEntity.ok(curriculums);
    }

    /**
     * Update curriculum.
     *
     * @param id Curriculum ID
     * @param request Update request
     * @return Updated curriculum DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<CurriculumDTO> updateCurriculum(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCurriculumRequest request) {
        CurriculumDTO updated = curriculumService.updateCurriculum(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete curriculum.
     *
     * @param id Curriculum ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> deleteCurriculum(@PathVariable Long id) {
        curriculumService.deleteCurriculum(id);
        return ResponseEntity.ok(new MessageResponse("Curriculum deleted successfully"));
    }
}

