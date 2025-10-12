package com.jeeleducation.lms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Homework Activity entity for homework assignments.
 */
@Entity
@DiscriminatorValue("HOMEWORK")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkActivity extends Activity {

    @Column
    private LocalDateTime dueDate;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @OneToMany(mappedBy = "homework", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HomeworkFile> files = new ArrayList<>();

    /**
     * Add file to homework.
     *
     * @param file File to add
     */
    public void addFile(HomeworkFile file) {
        if (files == null) {
            files = new ArrayList<>();
        }
        files.add(file);
        file.setHomework(this);
    }
}

