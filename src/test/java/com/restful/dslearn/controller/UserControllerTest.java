package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.UserDTO;
import com.restful.dslearn.dto.UserRequestDTO;
import com.restful.dslearn.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private UserService userService;

    @Test
    void shouldReturnUsers() throws Exception {
        when(userService.findAll()).thenReturn(List.of(
                new UserDTO(1L, "Alice", "alice@test.com", List.of(1L))));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Alice")));
    }

    @Test
    void shouldReturnNotFoundWhenUserMissing() throws Exception {
        when(userService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserRequestDTO request = new UserRequestDTO("Bob", "bob@test.com", "pass123", List.of(1L));
        when(userService.create(eq(request)))
                .thenReturn(new UserDTO(2L, "Bob", "bob@test.com", List.of(1L)));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Bob")));
    }

    @Test
    void shouldRejectInvalidUser() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
