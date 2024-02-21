package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Lesson")
@Table(name = "tb_lesson",
        schema = "db_dslearn")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "lesson")
    @Setter(AccessLevel.NONE)
    private List<Deliver> deliveries = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tb_lessons_done",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id"),
                    @JoinColumn(name = "offer_id")
            }
    )
    @Setter(AccessLevel.NONE)
    private Set<Enrollment> enrollmentsDone = new HashSet<>();

    public Lesson(Long id, String title, Integer position, Section section) {
        this.id = id;
        this.title = title;
        this.position = position;
        this.section = section;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash = prime * hash + ((id == null) ? 0 : id.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Lesson other = (Lesson) obj;

        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", position=" + position +
                ", section=" + section +
                '}';
    }
}
