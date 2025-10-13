package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.StudentProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for StudentProgress entity.
 */
@Repository
public interface StudentProgressRepository extends JpaRepository<StudentProgress, Long> {

    /**
     * Find progress by student ID and curriculum ID.
     *
     * @param studentId Student ID
     * @param curriculumId Curriculum ID
     * @return Optional containing progress if found
     */
    Optional<StudentProgress> findByStudentIdAndCurriculumId(Long studentId, Long curriculumId);

    /**
     * Find all progress for a student.
     *
     * @param studentId Student ID
     * @return List of student progress
     */
    List<StudentProgress> findByStudentId(Long studentId);

    /**
     * Find all progress for a curriculum.
     *
     * @param curriculumId Curriculum ID
     * @return List of student progress
     */
    List<StudentProgress> findByCurriculumId(Long curriculumId);

    /**
     * Find completed curriculums for a student.
     *
     * @param studentId Student ID
     * @return List of completed progress
     */
    @Query("SELECT sp FROM StudentProgress sp WHERE sp.student.id = :studentId AND sp.completedAt IS NOT NULL")
    List<StudentProgress> findCompletedByStudentId(@Param("studentId") Long studentId);

    /**
     * Find in-progress curriculums for a student.
     *
     * @param studentId Student ID
     * @return List of in-progress
     */
    @Query("SELECT sp FROM StudentProgress sp WHERE sp.student.id = :studentId AND sp.startedAt IS NOT NULL AND sp.completedAt IS NULL")
    List<StudentProgress> findInProgressByStudentId(@Param("studentId") Long studentId);

    /**
     * Get average completion percentage for a curriculum.
     *
     * @param curriculumId Curriculum ID
     * @return Average completion percentage
     */
    @Query("SELECT AVG(sp.completionPercentage) FROM StudentProgress sp WHERE sp.curriculum.id = :curriculumId")
    Double getAverageCompletionByCurriculum(@Param("curriculumId") Long curriculumId);

    /**
     * Count students enrolled in curriculum.
     *
     * @param curriculumId Curriculum ID
     * @return Number of students
     */
    long countByCurriculumId(Long curriculumId);
}

