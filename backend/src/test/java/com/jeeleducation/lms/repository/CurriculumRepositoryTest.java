package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.Curriculum;
import com.jeeleducation.lms.entity.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for CurriculumRepository.
 */
@DataJpaTest
class CurriculumRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Test
    void testFindByStatus() {
        Curriculum draft = Curriculum.builder()
                .name("Draft Curriculum")
                .status(Status.DRAFT)
                .build();
        entityManager.persist(draft);

        Curriculum published = Curriculum.builder()
                .name("Published Curriculum")
                .status(Status.ACTIVE)
                .build();
        entityManager.persist(published);
        entityManager.flush();

        List<Curriculum> draftList = curriculumRepository.findByStatus(Status.DRAFT);
        List<Curriculum> publishedList = curriculumRepository.findByStatus(Status.ACTIVE);

        assertTrue(draftList.stream().anyMatch(c -> c.getName().equals("Draft Curriculum")));
        assertTrue(publishedList.stream().anyMatch(c -> c.getName().equals("Published Curriculum")));
    }

    @Test
    void testSearchByName() {
        Curriculum curriculum = Curriculum.builder()
                .name("Arabic Language Curriculum")
                .status(Status.ACTIVE)
                .build();
        entityManager.persist(curriculum);
        entityManager.flush();

        List<Curriculum> results = curriculumRepository.searchByName("Arabic");

        assertTrue(results.stream().anyMatch(c -> c.getName().contains("Arabic")));
    }

    @Test
    void testCountByStatus() {
        Curriculum draft1 = Curriculum.builder()
                .name("Draft 1")
                .status(Status.DRAFT)
                .build();
        entityManager.persist(draft1);

        Curriculum draft2 = Curriculum.builder()
                .name("Draft 2")
                .status(Status.DRAFT)
                .build();
        entityManager.persist(draft2);
        entityManager.flush();

        long count = curriculumRepository.countByStatus(Status.DRAFT);

        assertTrue(count >= 2);
    }
}

