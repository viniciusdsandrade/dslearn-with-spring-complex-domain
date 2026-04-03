package com.restful.dslearn.dto;

import java.time.LocalDateTime;

public record TopicDTO(
        Long id,
        String title,
        String body,
        LocalDateTime moment,
        Long authorId,
        Long offerId,
        Long lessonId,
        Long answerId
) {
}
