package com.jeeleducation.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating a new school.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchoolRequest {

    @NotBlank(message = "School name is required")
    @Size(max = 200, message = "School name must not exceed 200 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private String icon;

    private String logo;

    @NotBlank(message = "Admin mobile is required")
    @Size(max = 20, message = "Mobile number must not exceed 20 characters")
    private String adminMobile;

    @NotBlank(message = "Admin email is required")
    @Email(message = "Email should be valid")
    private String adminEmail;

    @NotBlank(message = "Admin password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String adminPassword;

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
}

