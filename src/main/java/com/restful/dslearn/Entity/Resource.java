package com.restful.dslearn.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Resource")
@Table(name = "tb_resource",
        schema = "db_dslearn")
public class Resource {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String link;
    private Integer position;
    private String imgUri;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

}
