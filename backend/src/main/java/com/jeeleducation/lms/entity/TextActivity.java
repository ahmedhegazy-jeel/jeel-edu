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
 * Text Activity entity for text-based learning content.
 */
@Entity
@DiscriminatorValue("TEXT")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TextActivity extends Activity {

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(length = 500)
    private String textAudio;
}

