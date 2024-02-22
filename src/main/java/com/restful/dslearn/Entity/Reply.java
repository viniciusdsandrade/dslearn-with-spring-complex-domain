package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Reply")
@Table(name = "tb_reply",
        schema = "db_dslearn")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinTable(name = "tb_replies_likes",
            joinColumns = @JoinColumn(name = "reply_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Setter(AccessLevel.NONE)
    private Set<User> likes = new HashSet<>();

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((this.id == null) ? 0 : this.id.hashCode());

        if (hash < 0) hash = -hash;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Reply that = (Reply) obj;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public String toString() {
        return "{\n"
                + "\"id\": " + this.id + ",\n"
                + "\"body\": \"" + this.body + "\",\n"
                + "\"moment\": \"" + this.moment + "\",\n"
                + "\"topic\": " + this.topic + ",\n"
                + "\"author\": " + this.author + ",\n"
                + "\"likes\": " + this.likes + "\n"
                + "}";
    }
}