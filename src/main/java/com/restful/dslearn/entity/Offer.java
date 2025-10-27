package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Offer")
@Table(
        name = "tb_offer",
        schema = "db_dslearn"
)
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

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        Class<?> oEffectiveClass = obj instanceof HibernateProxy
                ? ((HibernateProxy) obj).getHibernateLazyInitializer().getPersistentClass()
                : obj.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Offer offer = (Offer) obj;

        return getId() != null && Objects.equals(getId(), offer.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();    }
}
