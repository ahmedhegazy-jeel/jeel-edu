package com.jeeleducation.lms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * HomeworkFile entity representing uploaded files for homework activities.
 */
@Entity
@Table(name = "homework_files")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "File name is required")
    @Column(nullable = false, length = 255)
    private String fileName;

    @NotBlank(message = "File URL is required")
    @Column(nullable = false, length = 1000)
    private String fileUrl;

    @Column(length = 100)
    private String fileType;

    @Column
    private Long fileSize;

    @Column
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_id", nullable = false)
    @JsonIgnore
    private HomeworkActivity homework;
}

