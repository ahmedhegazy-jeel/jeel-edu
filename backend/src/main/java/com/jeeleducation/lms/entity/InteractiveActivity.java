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
 * Interactive Activity entity for external interactive content.
 */
@Entity
@DiscriminatorValue("INTERACTIVE")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InteractiveActivity extends Activity {

    @Column(length = 1000)
    private String externalActivityUrl;
}

