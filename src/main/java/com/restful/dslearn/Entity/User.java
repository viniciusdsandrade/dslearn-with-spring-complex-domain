package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "tb_user",
        schema = "db_dslearn",
        uniqueConstraints = {
                @UniqueConstraint(name = "tb_user_email_unique",
                        columnNames = "email")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Setter(AccessLevel.NONE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<>();

    public User(Long id,
                String name,
                String email,
                String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash = prime * hash + ((this.id == null) ? 0 : this.id.hashCode());
        hash = prime * hash + ((this.email == null) ? 0 : this.email.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        User other = (User) obj;

        return this.id.equals(other.id) &&
                this.email.equals(other.email);
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"id\": " + this.id + ",\n" +
                "  \"name\": \"" + this.name + "\",\n" +
                "  \"email\": \"" + this.email + "\",\n" +
                "  \"password\": \"" + this.password + "\",\n" +
                "  \"roles\": " + this.roles + ",\n" +
                "  \"notifications\": " + this.notifications + "\n" +
                "}";
    }
}