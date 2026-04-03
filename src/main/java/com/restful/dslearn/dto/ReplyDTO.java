package com.restful.dslearn.dto;

import java.time.LocalDateTime;

public record ReplyDTO(
        Long id,
        String body,
        LocalDateTime moment,
        Long topicId,
        Long authorId
) {
}
