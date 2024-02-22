package com.restful.dslearn.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Content")
@Table(name = "tb_content",
        schema = "db_dslearn")
public class Content extends Lesson {

    private String text;
    private String videoUri;

    public Content(Long id,
                   String title,
                   Integer position,
                   Section section,
                   String text,
                   String videoUri) {
        super(id, title, position, section);
        this.text = text;
        this.videoUri = videoUri;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((this.getId() == null) ? 0 : this.getId().hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Content that = (Content) obj;

        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"text\": \"" + this.text + "\",\n" +
                "  \"videoUri\": \"" + this.videoUri + "\"\n" +
                "}";
    }
}