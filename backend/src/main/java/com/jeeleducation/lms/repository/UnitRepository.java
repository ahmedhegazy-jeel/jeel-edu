package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.Status;
import com.jeeleducation.lms.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Unit entity.
 */
@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    /**
     * Find units by curriculum ID.
     *
     * @param curriculumId Curriculum ID
     * @return List of units in curriculum
     */
    List<Unit> findByCurriculumIdOrderByDisplayOrderAsc(Long curriculumId);

    /**
     * Find units by curriculum ID and status.
     *
     * @param curriculumId Curriculum ID
     * @param status Status to filter by
     * @return List of units matching criteria
     */
    List<Unit> findByCurriculumIdAndStatusOrderByDisplayOrderAsc(Long curriculumId, Status status);

    /**
     * Find units by status.
     *
     * @param status Status to filter by
     * @return List of units with specified status
     */
    List<Unit> findByStatus(Status status);

    /**
     * Count units by curriculum ID.
     *
     * @param curriculumId Curriculum ID
     * @return Number of units in curriculum
     */
    long countByCurriculumId(Long curriculumId);

    /**
     * Search units by name within a curriculum.
     *
     * @param curriculumId Curriculum ID
     * @param searchTerm Search term
     * @return List of matching units
     */
    @Query("SELECT u FROM Unit u WHERE u.curriculum.id = :curriculumId AND LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Unit> searchByNameInCurriculum(@Param("curriculumId") Long curriculumId, @Param("searchTerm") String searchTerm);
}

