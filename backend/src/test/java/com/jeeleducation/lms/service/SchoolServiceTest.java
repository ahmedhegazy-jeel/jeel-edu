package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.CreateSchoolRequest;
import com.jeeleducation.lms.dto.SchoolDTO;
import com.jeeleducation.lms.repository.SchoolRepository;
import com.jeeleducation.lms.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for SchoolService.
 */
@SpringBootTest
@Transactional
class SchoolServiceTest {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateSchool() {
        CreateSchoolRequest request = CreateSchoolRequest.builder()
                .name("Test School")
                .adminMobile("+201234567890")
                .adminEmail("admin@testschool.com")
                .adminPassword("password123")
                .city("Cairo")
                .country("Egypt")
                .studentCapacity(500)
                .build();

        SchoolDTO created = schoolService.createSchool(request);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Test School", created.getName());
        assertEquals("admin@testschool.com", created.getAdminEmail());
        assertEquals(500, created.getStudentCapacity());
        assertEquals(0, created.getCurrentStudentCount());
        assertTrue(created.getIsActive());

        // Verify SCHOOL_ADMIN user was created
        assertTrue(userRepository.findByEmail("admin@testschool.com").isPresent());
    }

    @Test
    void testCapacityCalculations() {
        CreateSchoolRequest request = CreateSchoolRequest.builder()
                .name("Capacity Test School")
                .adminMobile("+201234567890")
                .adminEmail("capacity@testschool.com")
                .adminPassword("password123")
                .studentCapacity(100)
                .build();

        SchoolDTO created = schoolService.createSchool(request);

        assertEquals(100, created.getAvailableCapacity());
        assertEquals(0.0, created.getCapacityUtilization());
    }

    @Test
    void testGetSchoolsByCity() {
        CreateSchoolRequest request = CreateSchoolRequest.builder()
                .name("Cairo School")
                .adminMobile("+201234567890")
                .adminEmail("cairo@testschool.com")
                .adminPassword("password123")
                .city("Cairo")
                .country("Egypt")
                .build();

        schoolService.createSchool(request);

        var schools = schoolService.getSchoolsByCity("Cairo");

        assertNotNull(schools);
        assertTrue(schools.stream().anyMatch(s -> s.getName().equals("Cairo School")));
    }
}

