package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Topic")
@Table(
        name = "tb_topic",
        schema = "db_dslearn"
)
public class Topic {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    private LocalDateTime moment;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "reply_id")
    private Reply answer;

    @OneToMany(mappedBy = "topic")
    private List<Reply> replies = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tb_topics_likes",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Setter(NONE)
    private Set<User> likes = new HashSet<>();

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

        Topic topic = (Topic) obj;

        return getId() != null && Objects.equals(getId(), topic.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();    }
}
