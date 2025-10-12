package com.jeeleducation.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for School entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTO {

    private Long id;
    private String name;
    private String description;
    private String icon;
    private String logo;
    private String adminMobile;
    private String adminEmail;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String phoneNumber;
    private String faxNumber;
    private String website;
    private Boolean isActive;
    private Integer studentCapacity;
    private Integer currentStudentCount;
    private Integer availableCapacity;
    private Double capacityUtilization;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

