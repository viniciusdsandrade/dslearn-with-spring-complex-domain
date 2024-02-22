package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Resource")
@Table(name = "tb_resource",
        schema = "db_dslearn")
public class Resource {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Integer position;
    private String imgUri;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @OneToMany
    @JoinColumn(name = "resource")
    private List<Section> section = new ArrayList<>();


    public Resource(Long id,
                    String title,
                    String description,
                    Integer position,
                    String imgUri,
                    ResourceType type,
                    Offer offer) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.position = position;
        this.imgUri = imgUri;
        this.type = type;
        this.offer = offer;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Resource that = (Resource) obj;

        return Objects.equals(this.id, that.id);
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
                + "\"imgUri\": \"" + this.imgUri + "\",\n"
                + "\"type\": \"" + this.type + "\",\n"
                + "\"offer\": " + this.offer + "\n"
                + "}";
    }
}
