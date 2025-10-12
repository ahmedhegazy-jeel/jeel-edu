package com.jeeleducation.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating a school.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSchoolRequest {

    @Size(max = 200, message = "School name must not exceed 200 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private String icon;

    private String logo;

    @Size(max = 20, message = "Mobile number must not exceed 20 characters")
    private String adminMobile;

    @Email(message = "Email should be valid")
    private String adminEmail;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    @Size(max = 20, message = "Fax number must not exceed 20 characters")
    private String faxNumber;

    private String website;

    private Integer studentCapacity;

    private Integer currentStudentCount;

    private Boolean isActive;
}

