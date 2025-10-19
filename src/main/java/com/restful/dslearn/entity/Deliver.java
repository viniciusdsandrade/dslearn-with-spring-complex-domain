package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Deliver")
@Table(name = "tb_deliver",
        schema = "db_dslearn")
public class Deliver {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String uri;
    private String feedback;
    private Integer correctCount;

    @Enumerated(STRING)
    private DeliverStatus status;

    @CreationTimestamp
    private LocalDateTime moment;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id"),
            @JoinColumn(name = "offer_id")
    })
    private Enrollment enrollment;
}