package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Role")
@Table(name = "tb_role",
        schema = "db_dslearn")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash = prime * hash + ((id == null) ? 0 : id.hashCode());
        hash = prime * hash + ((authority == null) ? 0 : authority.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Role other = (Role) obj;

        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.authority, other.authority);

    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}