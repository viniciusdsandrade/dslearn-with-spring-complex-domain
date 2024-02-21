package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Topic")
@Table(name = "tb_topic",
        schema = "db_dslearn")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany
    @JoinTable(
            name = "tb_topics_likes",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Setter(lombok.AccessLevel.NONE)
    private Set<User> likes = new HashSet<>();

    @OneToMany(mappedBy = "topic")
    private List<Reply> replies = new ArrayList<>();

    public Topic(Long id, String title, String body, LocalDateTime moment, User author, Offer offer, Lesson lesson, Reply answer) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.moment = moment;
        this.author = author;
        this.offer = offer;
        this.lesson = lesson;
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Topic that = (Topic) o;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 7;

        hash *= prime + ((this.id == null) ? 0 : this.id.hashCode());

        return hash;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", moment=" + moment +
                ", author=" + author +
                ", offer=" + offer +
                ", lesson=" + lesson +
                ", answer=" + answer +
                ", likes=" + likes +
                ", replies=" + replies +
                '}';
    }
}