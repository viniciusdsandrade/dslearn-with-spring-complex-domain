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
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Notification")
@Table(name = "tb_notification",
        schema = "db_dslearn")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

    @CreationTimestamp
    private LocalDateTime moment;
    private Boolean reading;
    private String route;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((this.id == null) ? 0 : this.id.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"id\": " + this.id + ",\n" +
                "  \"text\": \"" + this.text + "\",\n" +
                "  \"moment\": \"" + this.moment + "\",\n" +
                "  \"reading\": " + this.reading + ",\n" +
                "  \"route\": \"" + this.route + "\",\n" +
                "  \"user\": " + this.user + "\n" +
                "}";
    }
}