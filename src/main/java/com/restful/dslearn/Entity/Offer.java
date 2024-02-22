package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Offer")
@Table(name = "tb_offer",
        schema = "db_dslearn")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String edition;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant startMoment;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant endMoment;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "offer")
    private List<Resource> resources = new ArrayList<>();

    public Offer(Long id,
                 String edition,
                 Instant startMoment,
                 Instant endMoment,
                 Course course) {
        super();
        this.id = id;
        this.edition = edition;
        this.startMoment = startMoment;
        this.endMoment = endMoment;
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Offer that = (Offer) o;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((id == null) ? 0 : id.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public String toString() {
        return "{\n"
                + "\"id\": " + this.id + ",\n"
                + "\"course\": " + this.course + "\n"
                + "}";
    }
}