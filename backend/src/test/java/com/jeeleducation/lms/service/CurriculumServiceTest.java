package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.CreateCurriculumRequest;
import com.jeeleducation.lms.dto.CurriculumDTO;
import com.jeeleducation.lms.dto.UpdateCurriculumRequest;
import com.jeeleducation.lms.entity.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for CurriculumService.
 */
@SpringBootTest
@Transactional
class CurriculumServiceTest {

    @Autowired
    private CurriculumService curriculumService;

    @Test
    void testCreateCurriculum() {
        CreateCurriculumRequest request = CreateCurriculumRequest.builder()
                .name("Test Curriculum")
                .description("Test description")
                .status(Status.DRAFT)
                .displayOrder(1)
                .build();

        CurriculumDTO created = curriculumService.createCurriculum(request);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Test Curriculum", created.getName());
        assertEquals(Status.DRAFT, created.getStatus());
        assertEquals(0, created.getUnitCount());
    }

    @Test
    void testGetCurriculumById() {
        CreateCurriculumRequest request = CreateCurriculumRequest.builder()
                .name("Test Curriculum")
                .status(Status.DRAFT)
                .build();
        CurriculumDTO created = curriculumService.createCurriculum(request);

        CurriculumDTO retrieved = curriculumService.getCurriculumById(created.getId());

        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals("Test Curriculum", retrieved.getName());
    }

    @Test
    void testUpdateCurriculum() {
        CreateCurriculumRequest createRequest = CreateCurriculumRequest.builder()
                .name("Original Name")
                .status(Status.DRAFT)
                .build();
        CurriculumDTO created = curriculumService.createCurriculum(createRequest);

        UpdateCurriculumRequest updateRequest = UpdateCurriculumRequest.builder()
                .name("Updated Name")
                .status(Status.PUBLISHED)
                .build();
        CurriculumDTO updated = curriculumService.updateCurriculum(created.getId(), updateRequest);

        assertEquals("Updated Name", updated.getName());
        assertEquals(Status.PUBLISHED, updated.getStatus());
    }

    @Test
    void testGetCurriculumsByStatus() {
        CreateCurriculumRequest draftRequest = CreateCurriculumRequest.builder()
                .name("Draft Curriculum")
                .status(Status.DRAFT)
                .build();
        curriculumService.createCurriculum(draftRequest);

        CreateCurriculumRequest publishedRequest = CreateCurriculumRequest.builder()
                .name("Published Curriculum")
                .status(Status.PUBLISHED)
                .build();
        curriculumService.createCurriculum(publishedRequest);

        List<CurriculumDTO> draftCurriculums = curriculumService.getCurriculumsByStatus(Status.DRAFT);
        List<CurriculumDTO> publishedCurriculums = curriculumService.getCurriculumsByStatus(Status.PUBLISHED);

        assertNotNull(draftCurriculums);
        assertNotNull(publishedCurriculums);
        assertEquals(1, draftCurriculums.stream()
                .filter(c -> c.getName().equals("Draft Curriculum")).count());
        assertEquals(1, publishedCurriculums.stream()
                .filter(c -> c.getName().equals("Published Curriculum")).count());
    }

    @Test
    void testDeleteCurriculum() {
        CreateCurriculumRequest request = CreateCurriculumRequest.builder()
                .name("To Delete")
                .status(Status.DRAFT)
                .build();
        CurriculumDTO created = curriculumService.createCurriculum(request);

        curriculumService.deleteCurriculum(created.getId());

        assertThrows(RuntimeException.class, () -> curriculumService.getCurriculumById(created.getId()));
    }

    @Test
    void testSearchCurriculums() {
        CreateCurriculumRequest request = CreateCurriculumRequest.builder()
                .name("Arabic Language Curriculum")
                .status(Status.DRAFT)
                .build();
        curriculumService.createCurriculum(request);

        List<CurriculumDTO> results = curriculumService.searchCurriculums("Arabic");

        assertNotNull(results);
        assertEquals(1, results.stream()
                .filter(c -> c.getName().contains("Arabic")).count());
    }
}

