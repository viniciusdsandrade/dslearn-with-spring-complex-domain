package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Section")
@Table(name = "tb_section",
        schema = "db_dslearn")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Integer position;
    private String imgUri;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "prerequisite_id")
    private Section prerequisite;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Section other = (Section) obj;

        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((this.id == null) ? 0 : this.id.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public String toString() {
        return "{\n"
                + "\"id\": " + this.id + ",\n"
                + "\"title\": \"" + this.title + "\",\n"
                + "\"description\": \"" + this.description + "\",\n"
                + "\"position\": " + this.position + ",\n"
                + "\"imgUri\": \"" + this.imgUri + "\"\n"
                + "}";
    }
}
