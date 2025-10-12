package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.CreateUnitRequest;
import com.jeeleducation.lms.dto.UnitDTO;
import com.jeeleducation.lms.entity.Curriculum;
import com.jeeleducation.lms.entity.Status;
import com.jeeleducation.lms.entity.Unit;
import com.jeeleducation.lms.repository.CurriculumRepository;
import com.jeeleducation.lms.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for unit management operations.
 */
@Service
@Transactional
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    /**
     * Create a new unit.
     *
     * @param request Unit creation request
     * @return Created unit DTO
     * @throws RuntimeException if curriculum not found
     */
    public UnitDTO createUnit(CreateUnitRequest request) {
        Curriculum curriculum = curriculumRepository.findById(request.getCurriculumId())
                .orElseThrow(() -> new RuntimeException("Curriculum not found with id: " + request.getCurriculumId()));

        Unit unit = Unit.builder()
                .name(request.getName())
                .description(request.getDescription())
                .audioName(request.getAudioName())
                .icon(request.getIcon())
                .status(request.getStatus() != null ? request.getStatus() : Status.DRAFT)
                .displayOrder(request.getDisplayOrder())
                .curriculum(curriculum)
                .build();

        Unit saved = unitRepository.save(unit);
        return toDTO(saved);
    }

    /**
     * Get unit by ID.
     *
     * @param id Unit ID
     * @return Unit DTO
     * @throws RuntimeException if not found
     */
    public UnitDTO getUnitById(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + id));
        return toDTO(unit);
    }

    /**
     * Get all units.
     *
     * @return List of unit DTOs
     */
    public List<UnitDTO> getAllUnits() {
        return unitRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get units by curriculum ID.
     *
     * @param curriculumId Curriculum ID
     * @return List of unit DTOs
     */
    public List<UnitDTO> getUnitsByCurriculumId(Long curriculumId) {
        return unitRepository.findByCurriculumIdOrderByDisplayOrderAsc(curriculumId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get units by curriculum ID and status.
     *
     * @param curriculumId Curriculum ID
     * @param status Status to filter by
     * @return List of unit DTOs
     */
    public List<UnitDTO> getUnitsByCurriculumIdAndStatus(Long curriculumId, Status status) {
        return unitRepository.findByCurriculumIdAndStatusOrderByDisplayOrderAsc(curriculumId, status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get units by status.
     *
     * @param status Status to filter by
     * @return List of unit DTOs
     */
    public List<UnitDTO> getUnitsByStatus(Status status) {
        return unitRepository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search units by name within a curriculum.
     *
     * @param curriculumId Curriculum ID
     * @param searchTerm Search term
     * @return List of matching unit DTOs
     */
    public List<UnitDTO> searchUnitsInCurriculum(Long curriculumId, String searchTerm) {
        return unitRepository.searchByNameInCurriculum(curriculumId, searchTerm).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update unit.
     *
     * @param id Unit ID
     * @param request Update request
     * @return Updated unit DTO
     * @throws RuntimeException if not found
     */
    public UnitDTO updateUnit(Long id, CreateUnitRequest request) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found with id: " + id));

        if (request.getName() != null) {
            unit.setName(request.getName());
        }
        if (request.getDescription() != null) {
            unit.setDescription(request.getDescription());
        }
        if (request.getAudioName() != null) {
            unit.setAudioName(request.getAudioName());
        }
        if (request.getIcon() != null) {
            unit.setIcon(request.getIcon());
        }
        if (request.getStatus() != null) {
            unit.setStatus(request.getStatus());
        }
        if (request.getDisplayOrder() != null) {
            unit.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getCurriculumId() != null && !request.getCurriculumId().equals(unit.getCurriculum().getId())) {
            Curriculum curriculum = curriculumRepository.findById(request.getCurriculumId())
                    .orElseThrow(() -> new RuntimeException("Curriculum not found with id: " + request.getCurriculumId()));
            unit.setCurriculum(curriculum);
        }

        Unit updated = unitRepository.save(unit);
        return toDTO(updated);
    }

    /**
     * Delete unit.
     *
     * @param id Unit ID
     * @throws RuntimeException if not found
     */
    public void deleteUnit(Long id) {
        if (!unitRepository.existsById(id)) {
            throw new RuntimeException("Unit not found with id: " + id);
        }
        unitRepository.deleteById(id);
    }

    /**
     * Convert Unit entity to DTO.
     *
     * @param unit Unit entity
     * @return Unit DTO
     */
    private UnitDTO toDTO(Unit unit) {
        return UnitDTO.builder()
                .id(unit.getId())
                .name(unit.getName())
                .description(unit.getDescription())
                .audioName(unit.getAudioName())
                .icon(unit.getIcon())
                .status(unit.getStatus())
                .displayOrder(unit.getDisplayOrder())
                .curriculumId(unit.getCurriculum().getId())
                .curriculumName(unit.getCurriculum().getName())
                .lessonCount(unit.getLessonCount())
                .createdAt(unit.getCreatedAt())
                .updatedAt(unit.getUpdatedAt())
                .build();
    }
}

