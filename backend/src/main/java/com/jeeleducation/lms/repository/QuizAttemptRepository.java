package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for QuizAttempt entity.
 */
@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    /**
     * Find all attempts for a student and quiz.
     *
     * @param studentId Student ID
     * @param quizId Quiz ID
     * @return List of quiz attempts
     */
    List<QuizAttempt> findByStudentIdAndQuizIdOrderByAttemptNumberDesc(Long studentId, Long quizId);

    /**
     * Find all attempts for a student.
     *
     * @param studentId Student ID
     * @return List of quiz attempts
     */
    List<QuizAttempt> findByStudentId(Long studentId);

    /**
     * Find best attempt for a student and quiz.
     *
     * @param studentId Student ID
     * @param quizId Quiz ID
     * @return Optional containing best attempt
     */
    @Query("SELECT qa FROM QuizAttempt qa WHERE qa.student.id = :studentId AND qa.quiz.id = :quizId ORDER BY qa.percentage DESC")
    Optional<QuizAttempt> findBestAttempt(@Param("studentId") Long studentId, @Param("quizId") Long quizId);

    /**
     * Find latest attempt for a student and quiz.
     *
     * @param studentId Student ID
     * @param quizId Quiz ID
     * @return Optional containing latest attempt
     */
    @Query("SELECT qa FROM QuizAttempt qa WHERE qa.student.id = :studentId AND qa.quiz.id = :quizId ORDER BY qa.attemptNumber DESC")
    Optional<QuizAttempt> findLatestAttempt(@Param("studentId") Long studentId, @Param("quizId") Long quizId);

    /**
     * Count attempts for a student and quiz.
     *
     * @param studentId Student ID
     * @param quizId Quiz ID
     * @return Number of attempts
     */
    long countByStudentIdAndQuizId(Long studentId, Long quizId);

    /**
     * Find passed attempts for a student.
     *
     * @param studentId Student ID
     * @return List of passed attempts
     */
    List<QuizAttempt> findByStudentIdAndPassedTrue(Long studentId);

    /**
     * Get average quiz score for a student.
     *
     * @param studentId Student ID
     * @return Average percentage
     */
    @Query("SELECT AVG(qa.percentage) FROM QuizAttempt qa WHERE qa.student.id = :studentId")
    Double getAverageScoreByStudent(@Param("studentId") Long studentId);
}

