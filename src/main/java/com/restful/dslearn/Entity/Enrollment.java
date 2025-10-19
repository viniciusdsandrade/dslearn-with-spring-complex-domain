package com.restful.dslearn.Entity;

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

    @JoinTable(
            name = "tb_lessons_done",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "offer_id", referencedColumnName = "offer_id"),
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
            }
    )
    @Setter(NONE)
    private Set<Enrollment> enrollmentsDone = new HashSet<>();
}
