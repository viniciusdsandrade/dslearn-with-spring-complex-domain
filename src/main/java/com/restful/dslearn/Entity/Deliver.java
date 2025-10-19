package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uri;
    private String feedback;
    private Integer correctCount;

    @Enumerated(EnumType.STRING)
    private DeliverStatus status;

    @CreationTimestamp
    private LocalDateTime moment;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id",  referencedColumnName = "user_id"),
            @JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
    })
    private Enrollment enrollment;
}