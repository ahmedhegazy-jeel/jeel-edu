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
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BookPage entity representing a page in a book activity.
 */
@Entity
@Table(name = "book_pages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Page number must be at least 1")
    @Column(nullable = false)
    private Integer pageNumber;

    @Column(length = 1000)
    private String image;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(length = 500)
    private String audioGeneratedByAi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnore
    private BookActivity book;
}

