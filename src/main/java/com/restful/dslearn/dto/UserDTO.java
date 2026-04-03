package com.restful.dslearn.dto;

import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        List<Long> roleIds
) {
}
