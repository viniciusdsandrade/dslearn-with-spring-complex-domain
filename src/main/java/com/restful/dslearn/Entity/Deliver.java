package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Deliver")
@Table(name = "tb_deliver",
        schema = "db_dslearn")
public class Deliver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uri;
    private String feedback;
    private Integer correctCount;

    @Enumerated(EnumType.STRING)
    private DeliverStatus status;

    @CreationTimestamp
    private LocalDateTime moment;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id"),
            @JoinColumn(name = "offer_id")
    })
    private Enrollment enrollment;

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((this.id == null) ? 0 : this.id.hashCode());

        if (hash < 0) hash = -hash;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Deliver other = (Deliver) obj;

        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"id\": " + this.id + ",\n" +
                "  \"uri\": \"" + this.uri + "\",\n" +
                "  \"moment\": \"" + this.moment + "\",\n" +
                "  \"status\": " + this.status + ",\n" +
                "  \"feedback\": \"" + this.feedback + "\",\n" +
                "  \"correctCount\": " + this.correctCount + ",\n" +
                "  \"deliverStatus\": " + this.status + ",\n" +
                "  \"enrollment\": " + this.enrollment + ",\n" +
                "  \"lesson\": " + this.lesson + "\n" +
                "}";
    }
}