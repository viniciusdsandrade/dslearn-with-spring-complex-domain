package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Reply")
@Table(name = "tb_reply",
        schema = "db_dslearn")
public class Reply {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    private LocalDateTime moment;

    @OneToMany(mappedBy = "answer")
    @Setter(NONE)
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
    @Setter(NONE)
    private Set<User> likes = new HashSet<>();
}