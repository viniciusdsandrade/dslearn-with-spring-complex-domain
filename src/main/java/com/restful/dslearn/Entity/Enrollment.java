package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "Enrollment")
@Table(name = "tb_enrollment",
        schema = "db_dslearn")
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
    @Setter(AccessLevel.NONE)
    private Set<Lesson> lessonsDone = new HashSet<>();

    public Enrollment(User user,
                      Offer offer,
                      LocalDateTime enrollMoment,
                      LocalDateTime refundMoment,
                      Boolean available,
                      Boolean onlyUpdate) {
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

        hash *= prime + ((this.id == null) ? 0 : this.id.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Enrollment that = (Enrollment) obj;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public String toString() {
        return "{\n"
                + "\"id\": " + this.id + ",\n"
                + "\"enrollMoment\": \"" + this.enrollMoment + "\",\n"
                + "\"refundMoment\": \"" + this.refundMoment + "\",\n"
                + "\"available\": " + this.available + ",\n"
                + "\"onlyUpdate\": " + this.onlyUpdate + "\n"
                + "}";
    }
}
