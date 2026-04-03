package com.restful.dslearn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record OfferRequestDTO(
        @NotBlank String edition,
        @NotNull Instant startMoment,
        @NotNull Instant endMoment,
        @NotNull Long courseId
) {
}
