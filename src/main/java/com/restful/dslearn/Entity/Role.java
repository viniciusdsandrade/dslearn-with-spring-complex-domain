package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash = prime * hash + ((this.id == null) ? 0 : this.id.hashCode());
        hash = prime * hash + ((this.authority == null) ? 0 : this.authority.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Role that = (Role) obj;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.authority, that.authority);

    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"id\": " + this.id + ",\n" +
                "  \"authority\": \"" + this.authority + "\"\n" +
                "}";
    }
}