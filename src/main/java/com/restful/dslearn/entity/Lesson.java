package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.NONE;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Lesson")
@Table(
        name = "tb_lesson",
        schema = "db_dslearn"
)
@Inheritance(strategy = JOINED)
public abstract class Lesson {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "lesson")
    @Setter(NONE)
    private List<Deliver> deliveries = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tb_lessons_done",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id",  referencedColumnName = "user_id"),
                    @JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
            }
    )
    @Setter(NONE)
    private Set<Enrollment> enrollmentsDone = new HashSet<>();

    public Lesson(
            Long id,
            String title,
            Integer position,
            Section section
    ) {
        super();
        this.id = id;
        this.title = title;
        this.position = position;
        this.section = section;
    }
}
