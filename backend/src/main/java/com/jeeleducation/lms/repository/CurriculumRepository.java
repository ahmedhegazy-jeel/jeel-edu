package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.Curriculum;
import com.jeeleducation.lms.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Curriculum entity.
 */
@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    /**
     * Find curriculums by status.
     *
     * @param status Status to filter by
     * @return List of curriculums with specified status
     */
    List<Curriculum> findByStatus(Status status);

    /**
     * Find curriculums by status ordered by display order.
     *
     * @param status Status to filter by
     * @return List of curriculums ordered by display order
     */
    List<Curriculum> findByStatusOrderByDisplayOrderAsc(Status status);

    /**
     * Find all curriculums ordered by display order.
     *
     * @return List of all curriculums ordered by display order
     */
    List<Curriculum> findAllByOrderByDisplayOrderAsc();

    /**
     * Search curriculums by name.
     *
     * @param searchTerm Search term
     * @return List of matching curriculums
     */
    @Query("SELECT c FROM Curriculum c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Curriculum> searchByName(@Param("searchTerm") String searchTerm);

    /**
     * Count curriculums by status.
     *
     * @param status Status to count
     * @return Number of curriculums with specified status
     */
    long countByStatus(Status status);
}

