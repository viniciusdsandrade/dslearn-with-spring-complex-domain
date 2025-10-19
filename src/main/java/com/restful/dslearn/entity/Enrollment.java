package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

import static lombok.AccessLevel.NONE;

@EqualsAndHashCode(callSuper = false)
@Setter
@Getter
@NoArgsConstructor
@Entity(name = "Enrollment")
@Table(
        name = "tb_enrollment",
        schema = "db_dslearn"
)
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
}
