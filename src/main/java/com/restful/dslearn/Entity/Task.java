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
    public int hashCode() {
        final int prime = 31;
        int hash = super.hashCode();

        hash *= prime + ((this.description == null) ? 0 : this.description.hashCode());
        hash *= prime + ((this.questionCount == null) ? 0 : this.questionCount.hashCode());
        hash *= prime + ((this.approvalCount == null) ? 0 : this.approvalCount.hashCode());
        hash *= prime + ((this.weight == null) ? 0 : this.weight.hashCode());
        hash *= prime + ((this.dueDate == null) ? 0 : this.dueDate.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return Objects.equals(this.description, task.description) &&
                Objects.equals(this.questionCount, task.questionCount) &&
                Objects.equals(this.approvalCount, task.approvalCount) &&
                Objects.equals(this.weight, task.weight) &&
                Objects.equals(this.dueDate, task.dueDate);
    }

    @Override
    public String toString() {
        return "{\n"
                + "\"description\": \"" + this.description + "\",\n"
                + "\"questionCount\": " + this.questionCount + ",\n"
                + "\"approvalCount\": " + this.approvalCount + ",\n"
                + "\"weight\": " + this.weight + ",\n"
                + "\"dueDate\": \"" + this.dueDate + "\"\n"
                + "}";
    }
}