package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for School entity.
 */
@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    /**
     * Find school by admin email.
     *
     * @param adminEmail Admin email
     * @return Optional containing school if found
     */
    Optional<School> findByAdminEmail(String adminEmail);

    /**
     * Check if admin email exists.
     *
     * @param adminEmail Admin email
     * @return true if exists, false otherwise
     */
    boolean existsByAdminEmail(String adminEmail);

    /**
     * Find all active schools.
     *
     * @return List of active schools
     */
    List<School> findByIsActiveTrue();

    /**
     * Find all inactive schools.
     *
     * @return List of inactive schools
     */
    List<School> findByIsActiveFalse();

    /**
     * Find schools by active status.
     *
     * @param isActive Active status
     * @return List of schools with specified status
     */
    List<School> findByIsActive(Boolean isActive);

    /**
     * Find schools by city.
     *
     * @param city City name
     * @return List of schools in city
     */
    List<School> findByCity(String city);

    /**
     * Find schools by country.
     *
     * @param country Country name
     * @return List of schools in country
     */
    List<School> findByCountry(String country);

    /**
     * Search schools by name.
     *
     * @param searchTerm Search term
     * @return List of matching schools
     */
    @Query("SELECT s FROM School s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<School> searchByName(@Param("searchTerm") String searchTerm);

    /**
     * Find schools with available capacity.
     *
     * @return List of schools with capacity
     */
    @Query("SELECT s FROM School s WHERE s.isActive = true AND " +
           "(s.studentCapacity IS NULL OR s.currentStudentCount IS NULL OR " +
           "s.currentStudentCount < s.studentCapacity)")
    List<School> findSchoolsWithCapacity();

    /**
     * Count active schools.
     *
     * @return Number of active schools
     */
    long countByIsActiveTrue();

    /**
     * Get total student count across all schools.
     *
     * @return Total student count
     */
    @Query("SELECT COALESCE(SUM(s.currentStudentCount), 0) FROM School s WHERE s.isActive = true")
    Long getTotalStudentCount();
}

