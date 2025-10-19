package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Resource")
@Table(
        name = "tb_resource",
        schema = "db_dslearn"
)
public class Resource {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Integer position;
    private String imgUri;

    @Enumerated(STRING)
    private ResourceType type;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @OneToMany(mappedBy = "resource")
    private List<Section> sections = new ArrayList<>();
}
