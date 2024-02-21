package com.restful.dslearn.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tb_task",
        schema = "db_dslearn")
public class Task extends Lesson {

    private String description;
    private Integer questionCount;
    private Integer approvalCount;
    private Double weight;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant dueDate;

    public Task(Long id,
                String title,
                Integer position,
                Section section,
                String description,
                Integer questionCount,
                Integer approvalCount,
                Double weight,
                Instant dueDate) {

        super(id, title, position, section);
        this.description = description;
        this.questionCount = questionCount;
        this.approvalCount = approvalCount;
        this.weight = weight;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", questionCount=" + questionCount +
                ", approvalCount=" + approvalCount +
                ", weight=" + weight +
                ", dueDate=" + dueDate +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = super.hashCode();

        hash *= prime + ((description == null) ? 0 : description.hashCode());
        hash *= prime + ((questionCount == null) ? 0 : questionCount.hashCode());
        hash *= prime + ((approvalCount == null) ? 0 : approvalCount.hashCode());
        hash *= prime + ((weight == null) ? 0 : weight.hashCode());
        hash *= prime + ((dueDate == null) ? 0 : dueDate.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return Objects.equals(description, task.description) &&
                Objects.equals(questionCount, task.questionCount) &&
                Objects.equals(approvalCount, task.approvalCount) &&
                Objects.equals(weight, task.weight) &&
                Objects.equals(dueDate, task.dueDate);
    }
}