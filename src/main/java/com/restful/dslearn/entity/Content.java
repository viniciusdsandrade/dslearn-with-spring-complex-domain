package com.restful.dslearn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Content")
@Table(name = "tb_content")
public class Content extends Lesson {

    private String text;
    private String videoUri;

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

        Content content = (Content) obj;

        return getId() != null && Objects.equals(getId(), content.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
