package com.restful.dslearn.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Task")
@Table(name = "tb_task",
        schema = "db_dslearn")
public class Task extends Lesson {

    private String description;
    private Integer questionCount;
    private Integer approvalCount;
    private Double weight;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant dueDate;

    public Task(
            Long id,
            String title,
            Integer position,
            Section section,
            String description,
            Integer questionCount,
            Integer approvalCount,
            Double weight,
            Instant dueDate
    ) {

        super(id, title, position, section);
        this.description = description;
        this.questionCount = questionCount;
        this.approvalCount = approvalCount;
        this.weight = weight;
        this.dueDate = dueDate;
    }
}