package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Reply")
@Table(name = "tb_reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    private LocalDateTime moment;

    @OneToMany(mappedBy = "answer")
    @Setter(AccessLevel.NONE)
    private Set<Topic> topics = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "tb_replies_likes",
            joinColumns = @JoinColumn(name = "reply_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Setter(AccessLevel.NONE)
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

        Reply reply = (Reply) obj;

        return getId() != null && Objects.equals(getId(), reply.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}