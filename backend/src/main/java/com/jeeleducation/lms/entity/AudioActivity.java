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
 * Audio Activity entity for audio-based learning content.
 */
@Entity
@DiscriminatorValue("AUDIO")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AudioActivity extends Activity {

    @Column(length = 1000)
    private String audioWithMusic;

    @Column(length = 1000)
    private String audioWithoutMusic;
}

