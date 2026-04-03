package com.restful.dslearn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicRequestDTO(
        @NotBlank String title,
        String body,
        @NotNull Long authorId,
        @NotNull Long offerId,
        @NotNull Long lessonId,
        Long answerId
) {
}
