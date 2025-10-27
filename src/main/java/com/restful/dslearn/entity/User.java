package com.restful.dslearn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "User")
@Table(
        name = "tb_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "tb_user_email_unique",
                        columnNames = "email"
                )
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Setter(NONE)
    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<>();

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

        User user = (User) obj;

        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
