package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.CreateSchoolRequest;
import com.jeeleducation.lms.dto.CreateUserRequest;
import com.jeeleducation.lms.dto.SchoolDTO;
import com.jeeleducation.lms.dto.UpdateSchoolRequest;
import com.jeeleducation.lms.dto.UserDTO;
import com.jeeleducation.lms.entity.Role;
import com.jeeleducation.lms.entity.School;
import com.jeeleducation.lms.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for school management operations.
 */
@Service
@Transactional
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserService userService;

    /**
     * Create a new school and its admin user.
     *
     * @param request School creation request
     * @return Created school DTO
     * @throws RuntimeException if admin email already exists
     */
    public SchoolDTO createSchool(CreateSchoolRequest request) {
        // Check if admin email exists
        if (schoolRepository.existsByAdminEmail(request.getAdminEmail())) {
            throw new RuntimeException("Admin email is already in use");
        }

        // Create school entity
        School school = School.builder()
                .name(request.getName())
                .description(request.getDescription())
                .icon(request.getIcon())
                .logo(request.getLogo())
                .adminMobile(request.getAdminMobile())
                .adminEmail(request.getAdminEmail())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .phoneNumber(request.getPhoneNumber())
                .faxNumber(request.getFaxNumber())
                .website(request.getWebsite())
                .studentCapacity(request.getStudentCapacity())
                .currentStudentCount(0)
                .isActive(true)
                .build();

        School savedSchool = schoolRepository.save(school);

        // Create school admin user
        CreateUserRequest adminUserRequest = CreateUserRequest.builder()
                .username(generateUsernameFromEmail(request.getAdminEmail()))
                .email(request.getAdminEmail())
                .password(request.getAdminPassword())
                .firstName(request.getName())
                .lastName("Admin")
                .mobile(request.getAdminMobile())
                .role(Role.SCHOOL_ADMIN)
                .bio("School administrator for " + request.getName())
                .build();

        try {
            userService.createUser(adminUserRequest);
        } catch (RuntimeException e) {
            // If user creation fails, rollback school creation
            schoolRepository.delete(savedSchool);
            throw new RuntimeException("Failed to create school admin user: " + e.getMessage());
        }

        return toDTO(savedSchool);
    }

    /**
     * Get school by ID.
     *
     * @param id School ID
     * @return School DTO
     * @throws RuntimeException if not found
     */
    public SchoolDTO getSchoolById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + id));
        return toDTO(school);
    }

    /**
     * Get all schools.
     *
     * @return List of school DTOs
     */
    public List<SchoolDTO> getAllSchools() {
        return schoolRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all active schools.
     *
     * @return List of active school DTOs
     */
    public List<SchoolDTO> getActiveSchools() {
        return schoolRepository.findByIsActiveTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get schools by active status.
     *
     * @param isActive Active status
     * @return List of schools with specified status
     */
    public List<SchoolDTO> getSchoolsByStatus(Boolean isActive) {
        return schoolRepository.findByIsActive(isActive).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get schools by city.
     *
     * @param city City name
     * @return List of schools in city
     */
    public List<SchoolDTO> getSchoolsByCity(String city) {
        return schoolRepository.findByCity(city).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get schools by country.
     *
     * @param country Country name
     * @return List of schools in country
     */
    public List<SchoolDTO> getSchoolsByCountry(String country) {
        return schoolRepository.findByCountry(country).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search schools by name.
     *
     * @param searchTerm Search term
     * @return List of matching school DTOs
     */
    public List<SchoolDTO> searchSchools(String searchTerm) {
        return schoolRepository.searchByName(searchTerm).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get schools with available capacity.
     *
     * @return List of schools with capacity
     */
    public List<SchoolDTO> getSchoolsWithCapacity() {
        return schoolRepository.findSchoolsWithCapacity().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update school information.
     *
     * @param id School ID
     * @param request Update request
     * @return Updated school DTO
     * @throws RuntimeException if school not found or email already exists
     */
    public SchoolDTO updateSchool(Long id, UpdateSchoolRequest request) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + id));

        // Check if email is being changed and if it's already in use
        if (request.getAdminEmail() != null && !request.getAdminEmail().equals(school.getAdminEmail())) {
            if (schoolRepository.existsByAdminEmail(request.getAdminEmail())) {
                throw new RuntimeException("Admin email is already in use");
            }
            school.setAdminEmail(request.getAdminEmail());
        }

        // Update fields if provided
        if (request.getName() != null) {
            school.setName(request.getName());
        }
        if (request.getDescription() != null) {
            school.setDescription(request.getDescription());
        }
        if (request.getIcon() != null) {
            school.setIcon(request.getIcon());
        }
        if (request.getLogo() != null) {
            school.setLogo(request.getLogo());
        }
        if (request.getAdminMobile() != null) {
            school.setAdminMobile(request.getAdminMobile());
        }
        if (request.getAddress() != null) {
            school.setAddress(request.getAddress());
        }
        if (request.getCity() != null) {
            school.setCity(request.getCity());
        }
        if (request.getCountry() != null) {
            school.setCountry(request.getCountry());
        }
        if (request.getPostalCode() != null) {
            school.setPostalCode(request.getPostalCode());
        }
        if (request.getPhoneNumber() != null) {
            school.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getFaxNumber() != null) {
            school.setFaxNumber(request.getFaxNumber());
        }
        if (request.getWebsite() != null) {
            school.setWebsite(request.getWebsite());
        }
        if (request.getStudentCapacity() != null) {
            school.setStudentCapacity(request.getStudentCapacity());
        }
        if (request.getCurrentStudentCount() != null) {
            school.setCurrentStudentCount(request.getCurrentStudentCount());
        }
        if (request.getIsActive() != null) {
            school.setIsActive(request.getIsActive());
        }

        School updated = schoolRepository.save(school);
        return toDTO(updated);
    }

    /**
     * Activate school.
     *
     * @param id School ID
     * @throws RuntimeException if school not found
     */
    public void activateSchool(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + id));
        school.setIsActive(true);
        schoolRepository.save(school);
    }

    /**
     * Deactivate school.
     *
     * @param id School ID
     * @throws RuntimeException if school not found
     */
    public void deactivateSchool(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + id));
        school.setIsActive(false);
        schoolRepository.save(school);
    }

    /**
     * Delete school.
     *
     * @param id School ID
     * @throws RuntimeException if school not found
     */
    public void deleteSchool(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new RuntimeException("School not found with id: " + id);
        }
        schoolRepository.deleteById(id);
    }

    /**
     * Get total active school count.
     *
     * @return Number of active schools
     */
    public long getActiveSchoolCount() {
        return schoolRepository.countByIsActiveTrue();
    }

    /**
     * Get total student count across all schools.
     *
     * @return Total student count
     */
    public Long getTotalStudentCount() {
        return schoolRepository.getTotalStudentCount();
    }

    /**
     * Generate username from email.
     *
     * @param email Email address
     * @return Generated username
     */
    private String generateUsernameFromEmail(String email) {
        return email.substring(0, email.indexOf('@')).replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Convert School entity to DTO.
     *
     * @param school School entity
     * @return School DTO
     */
    private SchoolDTO toDTO(School school) {
        return SchoolDTO.builder()
                .id(school.getId())
                .name(school.getName())
                .description(school.getDescription())
                .icon(school.getIcon())
                .logo(school.getLogo())
                .adminMobile(school.getAdminMobile())
                .adminEmail(school.getAdminEmail())
                .address(school.getAddress())
                .city(school.getCity())
                .country(school.getCountry())
                .postalCode(school.getPostalCode())
                .phoneNumber(school.getPhoneNumber())
                .faxNumber(school.getFaxNumber())
                .website(school.getWebsite())
                .isActive(school.getIsActive())
                .studentCapacity(school.getStudentCapacity())
                .currentStudentCount(school.getCurrentStudentCount())
                .availableCapacity(school.getAvailableCapacity())
                .capacityUtilization(school.getCapacityUtilization())
                .createdAt(school.getCreatedAt())
                .updatedAt(school.getUpdatedAt())
                .build();
    }
}

