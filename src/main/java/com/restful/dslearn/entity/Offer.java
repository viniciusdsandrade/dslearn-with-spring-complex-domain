package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Offer")
@Table(name = "tb_offer",
        schema = "db_dslearn")
public class Offer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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

    public Offer(
            Long id,
            String edition,
            Instant startMoment,
            Instant endMoment,
            Course course
    ) {
        super();
        this.id = id;
        this.edition = edition;
        this.startMoment = startMoment;
        this.endMoment = endMoment;
        this.course = course;
    }
}