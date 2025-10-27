package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Deliver")
@Table(name = "tb_deliver")
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

        Deliver deliver = (Deliver) obj;

        return getId() != null && Objects.equals(getId(), deliver.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}