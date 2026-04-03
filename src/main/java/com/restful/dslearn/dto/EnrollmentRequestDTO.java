package com.restful.dslearn.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequestDTO(
        @NotNull Long userId,
        @NotNull Long offerId,
        Boolean available,
        Boolean onlyUpdate
) {
}
