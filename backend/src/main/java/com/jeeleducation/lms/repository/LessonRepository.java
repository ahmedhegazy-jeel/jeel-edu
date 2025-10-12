package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.Lesson;
import com.jeeleducation.lms.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Lesson entity.
 */
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    /**
     * Find lessons by unit ID.
     *
     * @param unitId Unit ID
     * @return List of lessons in unit
     */
    List<Lesson> findByUnitIdOrderByDisplayOrderAsc(Long unitId);

    /**
     * Find lessons by unit ID and status.
     *
     * @param unitId Unit ID
     * @param status Status to filter by
     * @return List of lessons matching criteria
     */
    List<Lesson> findByUnitIdAndStatusOrderByDisplayOrderAsc(Long unitId, Status status);

    /**
     * Find lessons by status.
     *
     * @param status Status to filter by
     * @return List of lessons with specified status
     */
    List<Lesson> findByStatus(Status status);

    /**
     * Count lessons by unit ID.
     *
     * @param unitId Unit ID
     * @return Number of lessons in unit
     */
    long countByUnitId(Long unitId);

    /**
     * Search lessons by name within a unit.
     *
     * @param unitId Unit ID
     * @param searchTerm Search term
     * @return List of matching lessons
     */
    @Query("SELECT l FROM Lesson l WHERE l.unit.id = :unitId AND LOWER(l.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Lesson> searchByNameInUnit(@Param("unitId") Long unitId, @Param("searchTerm") String searchTerm);
}

