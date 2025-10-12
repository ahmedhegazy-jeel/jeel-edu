package com.jeeleducation.lms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Quiz Activity entity for assessment quizzes.
 */
@Entity
@DiscriminatorValue("QUIZ")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QuizActivity extends Activity {

    @Min(value = 0, message = "Percentage to pass must be at least 0")
    @Max(value = 100, message = "Percentage to pass cannot exceed 100")
    @Column
    private Integer percentageToPass;

    @Min(value = 1, message = "At least 1 attempt must be allowed")
    @Column
    private Integer attemptNumber;

    @Column
    private Integer numberOfQuestions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<Question> questions = new ArrayList<>();

    /**
     * Add question to quiz.
     *
     * @param question Question to add
     */
    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
        question.setQuiz(this);
    }
}

