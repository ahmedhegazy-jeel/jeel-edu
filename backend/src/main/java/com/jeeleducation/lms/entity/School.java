package com.jeeleducation.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * School entity representing educational institutions in the system.
 */
@Entity
@Table(name = "schools")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "School name is required")
    @Size(max = 200, message = "School name must not exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    @Column(length = 500)
    private String icon;

    @Column(length = 500)
    private String logo;

    @NotBlank(message = "Admin mobile is required")
    @Size(max = 20, message = "Mobile number must not exceed 20 characters")
    @Column(nullable = false, length = 20)
    private String adminMobile;

    @NotBlank(message = "Admin email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true, length = 100)
    private String adminEmail;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    @Column(length = 500)
    private String address;

    @Size(max = 100, message = "City must not exceed 100 characters")
    @Column(length = 100)
    private String city;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    @Column(length = 100)
    private String country;

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    @Column(length = 20)
    private String postalCode;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private String faxNumber;

    @Column(length = 500)
    private String website;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = true;

    @Column
    private Integer studentCapacity;

    @Column
    private Integer currentStudentCount;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Check if school has capacity for more students.
     *
     * @return true if capacity available, false otherwise
     */
    public boolean hasCapacity() {
        if (studentCapacity == null || currentStudentCount == null) {
            return true;
        }
        return currentStudentCount < studentCapacity;
    }

    /**
     * Get available capacity.
     *
     * @return Number of available student slots
     */
    public Integer getAvailableCapacity() {
        if (studentCapacity == null || currentStudentCount == null) {
            return null;
        }
        return studentCapacity - currentStudentCount;
    }

    /**
     * Get capacity utilization percentage.
     *
     * @return Percentage of capacity used
     */
    public Double getCapacityUtilization() {
        if (studentCapacity == null || currentStudentCount == null || studentCapacity == 0) {
            return 0.0;
        }
        return (currentStudentCount.doubleValue() / studentCapacity.doubleValue()) * 100;
    }
}

