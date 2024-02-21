package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Enrollment")
@Table(name = "tb_enrollment",
        schema = "db_dslearn")
public class Enrollment {

    @EmbeddedId
    private EnrollmentPK id = new EnrollmentPK();

    private boolean available;
    private boolean onlyUpdate;

    @CreationTimestamp
    private LocalDateTime enrollMoment;

    @UpdateTimestamp
    private LocalDateTime refundMoment;

    @OneToMany(mappedBy = "enrollment")
    private List<Deliver> deliveries = new ArrayList<>();

    @ManyToMany(mappedBy = "enrollmentsDone")
    @Setter(AccessLevel.NONE)
    private Set<Lesson> lessonsDone = new HashSet<>();

    public Enrollment(User user, Offer offer, LocalDateTime enrollMoment, LocalDateTime refundMoment, boolean available, boolean onlyUpdate) {
        id.setUser(user);
        id.setOffer(offer);
        this.enrollMoment = enrollMoment;
        this.refundMoment = refundMoment;
        this.available = available;
        this.onlyUpdate = onlyUpdate;
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
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Enrollment other = (Enrollment) obj;

        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", enrollMoment=" + enrollMoment +
                ", refundMoment=" + refundMoment +
                ", available=" + available +
                ", onlyUpdate=" + onlyUpdate +
                '}';
    }
}
