package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Resource")
@Table(name = "tb_resource")
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

    @OneToMany
    @JoinColumn(name = "resource")
    private List<Section> section = new ArrayList<>();

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        Class<?> oEffectiveClass = obj instanceof HibernateProxy
                ? ((HibernateProxy) obj).getHibernateLazyInitializer().getPersistentClass()
                : obj.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Resource resource = (Resource) obj;

        return getId() != null && Objects.equals(getId(), resource.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();    }
}
