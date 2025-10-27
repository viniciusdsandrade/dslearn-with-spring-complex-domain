package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.*;

import static lombok.AccessLevel.NONE;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "Enrollment")
@Table(name = "tb_enrollment")
public class Enrollment {

    @EmbeddedId
    private EnrollmentPK id = new EnrollmentPK();

    private Boolean available;
    private Boolean onlyUpdate;

    @CreationTimestamp
    private LocalDateTime enrollMoment;

    @UpdateTimestamp
    private LocalDateTime refundMoment;

    @OneToMany(mappedBy = "enrollment")
    private List<Deliver> deliveries = new ArrayList<>();

    @ManyToMany(mappedBy = "enrollmentsDone")
    @Setter(NONE)
    private Set<Lesson> lessonsDone = new HashSet<>();

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

        Enrollment that = (Enrollment) obj;

        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();    }
}
