package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.CreateUnitRequest;
import com.jeeleducation.lms.dto.MessageResponse;
import com.jeeleducation.lms.dto.UnitDTO;
import com.jeeleducation.lms.entity.Status;
import com.jeeleducation.lms.service.UnitService;
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
 * REST controller for unit management operations.
 */
@RestController
@RequestMapping("/units")
public class UnitController {

    @Autowired
    private UnitService unitService;

    /**
     * Create new unit.
     *
     * @param request Unit creation request
     * @return Created unit DTO
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<UnitDTO> createUnit(@Valid @RequestBody CreateUnitRequest request) {
        UnitDTO created = unitService.createUnit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get all units.
     *
     * @return List of all units
     */
    @GetMapping
    public ResponseEntity<List<UnitDTO>> getAllUnits() {
        List<UnitDTO> units = unitService.getAllUnits();
        return ResponseEntity.ok(units);
    }

    /**
     * Get unit by ID.
     *
     * @param id Unit ID
     * @return Unit DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<UnitDTO> getUnitById(@PathVariable Long id) {
        UnitDTO unit = unitService.getUnitById(id);
        return ResponseEntity.ok(unit);
    }

    /**
     * Get units by curriculum ID.
     *
     * @param curriculumId Curriculum ID
     * @return List of units in curriculum
     */
    @GetMapping("/curriculum/{curriculumId}")
    public ResponseEntity<List<UnitDTO>> getUnitsByCurriculum(@PathVariable Long curriculumId) {
        List<UnitDTO> units = unitService.getUnitsByCurriculumId(curriculumId);
        return ResponseEntity.ok(units);
    }

    /**
     * Get units by curriculum ID and status.
     *
     * @param curriculumId Curriculum ID
     * @param status Status to filter by
     * @return List of units matching criteria
     */
    @GetMapping("/curriculum/{curriculumId}/status/{status}")
    public ResponseEntity<List<UnitDTO>> getUnitsByCurriculumAndStatus(
            @PathVariable Long curriculumId,
            @PathVariable Status status) {
        List<UnitDTO> units = unitService.getUnitsByCurriculumIdAndStatus(curriculumId, status);
        return ResponseEntity.ok(units);
    }

    /**
     * Get units by status.
     *
     * @param status Status to filter by
     * @return List of units with specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<UnitDTO>> getUnitsByStatus(@PathVariable Status status) {
        List<UnitDTO> units = unitService.getUnitsByStatus(status);
        return ResponseEntity.ok(units);
    }

    /**
     * Search units by name within a curriculum.
     *
     * @param curriculumId Curriculum ID
     * @param searchTerm Search term
     * @return List of matching units
     */
    @GetMapping("/curriculum/{curriculumId}/search")
    public ResponseEntity<List<UnitDTO>> searchUnitsInCurriculum(
            @PathVariable Long curriculumId,
            @RequestParam String searchTerm) {
        List<UnitDTO> units = unitService.searchUnitsInCurriculum(curriculumId, searchTerm);
        return ResponseEntity.ok(units);
    }

    /**
     * Update unit.
     *
     * @param id Unit ID
     * @param request Update request
     * @return Updated unit DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<UnitDTO> updateUnit(
            @PathVariable Long id,
            @Valid @RequestBody CreateUnitRequest request) {
        UnitDTO updated = unitService.updateUnit(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete unit.
     *
     * @param id Unit ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        return ResponseEntity.ok(new MessageResponse("Unit deleted successfully"));
    }
}

