package com.restful.dslearn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Content")
@Table(
        name = "tb_content",
        schema = "db_dslearn"
)
public class Content extends Lesson {

    private String text;
    private String videoUri;
}
