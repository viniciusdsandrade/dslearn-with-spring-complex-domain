package com.restful.dslearn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationRequestDTO(
        @NotBlank String text,
        Boolean reading,
        String route,
        @NotNull Long userId
) {
}
