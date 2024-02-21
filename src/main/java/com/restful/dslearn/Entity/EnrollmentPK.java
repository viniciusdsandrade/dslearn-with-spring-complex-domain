package com.restful.dslearn.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
@Table(name = "tb_enrollment",
        schema = "db_dslearn")
public class EnrollmentPK implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((user == null) ? 0 : user.hashCode());
        hash *= prime + ((offer == null) ? 0 : offer.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        EnrollmentPK other = (EnrollmentPK) obj;

        return Objects.equals(this.user, other.user) &&
                Objects.equals(this.offer, other.offer);
    }

    @Override
    public String toString() {
        return "EnrollmentPK{" +
                "user=" + user +
                ", offer=" + offer +
                '}';
    }
}
