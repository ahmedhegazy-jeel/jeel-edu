package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.CreateCurriculumRequest;
import com.jeeleducation.lms.dto.CurriculumDTO;
import com.jeeleducation.lms.dto.UpdateCurriculumRequest;
import com.jeeleducation.lms.entity.Curriculum;
import com.jeeleducation.lms.entity.Status;
import com.jeeleducation.lms.repository.CurriculumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for curriculum management operations.
 */
@Service
@Transactional
public class CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;

    /**
     * Create a new curriculum.
     *
     * @param request Curriculum creation request
     * @return Created curriculum DTO
     */
    public CurriculumDTO createCurriculum(CreateCurriculumRequest request) {
        Curriculum curriculum = Curriculum.builder()
                .name(request.getName())
                .description(request.getDescription())
                .icon(request.getIcon())
                .status(request.getStatus() != null ? request.getStatus() : Status.DRAFT)
                .displayOrder(request.getDisplayOrder())
                .build();

        Curriculum saved = curriculumRepository.save(curriculum);
        return toDTO(saved);
    }

    /**
     * Get curriculum by ID.
     *
     * @param id Curriculum ID
     * @return Curriculum DTO
     * @throws RuntimeException if not found
     */
    public CurriculumDTO getCurriculumById(Long id) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curriculum not found with id: " + id));
        return toDTO(curriculum);
    }

    /**
     * Get all curriculums.
     *
     * @return List of curriculum DTOs
     */
    public List<CurriculumDTO> getAllCurriculums() {
        return curriculumRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get curriculums by status.
     *
     * @param status Status to filter by
     * @return List of curriculum DTOs
     */
    public List<CurriculumDTO> getCurriculumsByStatus(Status status) {
        return curriculumRepository.findByStatusOrderByDisplayOrderAsc(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search curriculums by name.
     *
     * @param searchTerm Search term
     * @return List of matching curriculum DTOs
     */
    public List<CurriculumDTO> searchCurriculums(String searchTerm) {
        return curriculumRepository.searchByName(searchTerm).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update curriculum.
     *
     * @param id Curriculum ID
     * @param request Update request
     * @return Updated curriculum DTO
     * @throws RuntimeException if not found
     */
    public CurriculumDTO updateCurriculum(Long id, UpdateCurriculumRequest request) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curriculum not found with id: " + id));

        if (request.getName() != null) {
            curriculum.setName(request.getName());
        }
        if (request.getDescription() != null) {
            curriculum.setDescription(request.getDescription());
        }
        if (request.getIcon() != null) {
            curriculum.setIcon(request.getIcon());
        }
        if (request.getStatus() != null) {
            curriculum.setStatus(request.getStatus());
        }
        if (request.getDisplayOrder() != null) {
            curriculum.setDisplayOrder(request.getDisplayOrder());
        }

        Curriculum updated = curriculumRepository.save(curriculum);
        return toDTO(updated);
    }

    /**
     * Delete curriculum.
     *
     * @param id Curriculum ID
     * @throws RuntimeException if not found
     */
    public void deleteCurriculum(Long id) {
        if (!curriculumRepository.existsById(id)) {
            throw new RuntimeException("Curriculum not found with id: " + id);
        }
        curriculumRepository.deleteById(id);
    }

    /**
     * Convert Curriculum entity to DTO.
     *
     * @param curriculum Curriculum entity
     * @return Curriculum DTO
     */
    private CurriculumDTO toDTO(Curriculum curriculum) {
        return CurriculumDTO.builder()
                .id(curriculum.getId())
                .name(curriculum.getName())
                .description(curriculum.getDescription())
                .icon(curriculum.getIcon())
                .status(curriculum.getStatus())
                .displayOrder(curriculum.getDisplayOrder())
                .unitCount(curriculum.getUnitCount())
                .createdAt(curriculum.getCreatedAt())
                .updatedAt(curriculum.getUpdatedAt())
                .build();
    }
}

