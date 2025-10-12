package com.jeeleducation.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * PDF Activity entity for PDF document-based learning content.
 */
@Entity
@DiscriminatorValue("PDF")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PdfActivity extends Activity {

    @Column(length = 1000)
    private String pdfUrl;

    @Column(length = 500)
    private String audioOfPdf;
}

