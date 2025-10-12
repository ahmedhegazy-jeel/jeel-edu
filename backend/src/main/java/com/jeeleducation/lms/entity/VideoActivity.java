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
 * Video Activity entity for video-based learning content.
 */
@Entity
@DiscriminatorValue("VIDEO")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VideoActivity extends Activity {

    @Column(length = 1000)
    private String videoWithMusic;

    @Column(length = 1000)
    private String videoWithoutMusic;
}

