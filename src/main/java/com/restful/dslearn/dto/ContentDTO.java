package com.restful.dslearn.dto;

public record ContentDTO(
        Long id,
        String title,
        Integer position,
        Long sectionId,
        String text,
        String videoUri
) {
}
