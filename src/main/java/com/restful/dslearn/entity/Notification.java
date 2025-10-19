package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import static jakarta.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Notification")
@Table(
        name = "tb_notification",
        schema = "db_dslearn"
)
public class Notification {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String text;

    @CreationTimestamp
    private LocalDateTime moment;
    private Boolean reading;
    private String route;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}