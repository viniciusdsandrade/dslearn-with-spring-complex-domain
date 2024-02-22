package com.restful.dslearn.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_enrollment",
        schema = "db_dslearn")
@Embeddable
public class EnrollmentPK implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    public EnrollmentPK(User user, Offer offer) {
        super();
        this.user = user;
        this.offer = offer;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((this.user == null) ? 0 : this.user.hashCode());
        hash *= prime + ((this.offer == null) ? 0 : this.offer.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        EnrollmentPK that = (EnrollmentPK) obj;

        return Objects.equals(this.user, that.user) &&
                Objects.equals(this.offer, that.offer);
    }

    @Override
    public String toString() {
        return "{\n"
                + "\"user\": " + this.user + ",\n"
                + "\"offer\": " + this.offer + "\n"
                + "}";
    }
}
