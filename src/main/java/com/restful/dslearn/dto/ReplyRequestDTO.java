package com.restful.dslearn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReplyRequestDTO(
        @NotBlank String body,
        @NotNull Long topicId,
        @NotNull Long authorId
) {
}
