package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.CreateSchoolRequest;
import com.jeeleducation.lms.dto.MessageResponse;
import com.jeeleducation.lms.dto.SchoolDTO;
import com.jeeleducation.lms.dto.UpdateSchoolRequest;
import com.jeeleducation.lms.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
 * REST controller for school management operations.
 * Accessible by SUPER_ADMIN role.
 */
@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    /**
     * Create new school.
     * Creates both the school entity and a school admin user.
     *
     * @param request School creation request
     * @return Created school DTO
     */
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<SchoolDTO> createSchool(@Valid @RequestBody CreateSchoolRequest request) {
        SchoolDTO created = schoolService.createSchool(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get all schools.
     *
     * @return List of all schools
     */
    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<SchoolDTO>> getAllSchools() {
        List<SchoolDTO> schools = schoolService.getAllSchools();
        return ResponseEntity.ok(schools);
    }

    /**
     * Get school by ID.
     *
     * @param id School ID
     * @return School DTO
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<SchoolDTO> getSchoolById(@PathVariable Long id) {
        SchoolDTO school = schoolService.getSchoolById(id);
        return ResponseEntity.ok(school);
    }

    /**
     * Get all active schools.
     *
     * @return List of active schools
     */
    @GetMapping("/active")
    public ResponseEntity<List<SchoolDTO>> getActiveSchools() {
        List<SchoolDTO> schools = schoolService.getActiveSchools();
        return ResponseEntity.ok(schools);
    }

    /**
     * Get schools by status.
     *
     * @param isActive Active status
     * @return List of schools with specified status
     */
    @GetMapping("/status")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<SchoolDTO>> getSchoolsByStatus(@RequestParam Boolean isActive) {
        List<SchoolDTO> schools = schoolService.getSchoolsByStatus(isActive);
        return ResponseEntity.ok(schools);
    }

    /**
     * Get schools by city.
     *
     * @param city City name
     * @return List of schools in city
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<SchoolDTO>> getSchoolsByCity(@PathVariable String city) {
        List<SchoolDTO> schools = schoolService.getSchoolsByCity(city);
        return ResponseEntity.ok(schools);
    }

    /**
     * Get schools by country.
     *
     * @param country Country name
     * @return List of schools in country
     */
    @GetMapping("/country/{country}")
    public ResponseEntity<List<SchoolDTO>> getSchoolsByCountry(@PathVariable String country) {
        List<SchoolDTO> schools = schoolService.getSchoolsByCountry(country);
        return ResponseEntity.ok(schools);
    }

    /**
     * Search schools by name.
     *
     * @param searchTerm Search term
     * @return List of matching schools
     */
    @GetMapping("/search")
    public ResponseEntity<List<SchoolDTO>> searchSchools(@RequestParam String searchTerm) {
        List<SchoolDTO> schools = schoolService.searchSchools(searchTerm);
        return ResponseEntity.ok(schools);
    }

    /**
     * Get schools with available capacity.
     *
     * @return List of schools with capacity
     */
    @GetMapping("/capacity/available")
    public ResponseEntity<List<SchoolDTO>> getSchoolsWithCapacity() {
        List<SchoolDTO> schools = schoolService.getSchoolsWithCapacity();
        return ResponseEntity.ok(schools);
    }

    /**
     * Get school statistics.
     *
     * @return School statistics
     */
    @GetMapping("/stats")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Map<String, Object>> getSchoolStats() {
        Map<String, Object> stats = Map.of(
                "totalSchools", schoolService.getAllSchools().size(),
                "activeSchools", schoolService.getActiveSchoolCount(),
                "totalStudents", schoolService.getTotalStudentCount()
        );
        return ResponseEntity.ok(stats);
    }

    /**
     * Update school information.
     *
     * @param id School ID
     * @param request Update request
     * @return Updated school DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<SchoolDTO> updateSchool(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSchoolRequest request) {
        SchoolDTO updated = schoolService.updateSchool(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Activate school.
     *
     * @param id School ID
     * @return Success message
     */
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> activateSchool(@PathVariable Long id) {
        schoolService.activateSchool(id);
        return ResponseEntity.ok(new MessageResponse("School activated successfully"));
    }

    /**
     * Deactivate school.
     *
     * @param id School ID
     * @return Success message
     */
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> deactivateSchool(@PathVariable Long id) {
        schoolService.deactivateSchool(id);
        return ResponseEntity.ok(new MessageResponse("School deactivated successfully"));
    }

    /**
     * Delete school.
     *
     * @param id School ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok(new MessageResponse("School deleted successfully"));
    }
}

