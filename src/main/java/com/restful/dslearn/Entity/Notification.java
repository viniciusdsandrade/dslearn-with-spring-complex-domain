package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Notification")
@Table(name = "notification",
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

        hash *= prime + ((id == null) ? 0 : id.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Notification notification = (Notification) o;

        return Objects.equals(this.id, notification.id);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", moment=" + moment +
                ", read=" + reading +
                ", route='" + route + '\'' +
                ", user=" + user +
                '}';
    }
}