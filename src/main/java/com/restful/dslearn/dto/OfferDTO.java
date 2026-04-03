package com.restful.dslearn.dto;

import java.time.Instant;

public record OfferDTO(
        Long id,
        String edition,
        Instant startMoment,
        Instant endMoment,
        Long courseId
) {
}
