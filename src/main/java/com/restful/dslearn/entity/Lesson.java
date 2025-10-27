package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.NONE;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Lesson")
@Table(name = "tb_lesson")
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
                    @JoinColumn(name = "user_id"),
                    @JoinColumn(name = "offer_id")
            }
    )
    @Setter(AccessLevel.NONE)
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        Class<?> oEffectiveClass = obj instanceof HibernateProxy
                ? ((HibernateProxy) obj).getHibernateLazyInitializer().getPersistentClass()
                : obj.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Lesson lesson = (Lesson) obj;

        return getId() != null && Objects.equals(getId(), lesson.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();    }
}
