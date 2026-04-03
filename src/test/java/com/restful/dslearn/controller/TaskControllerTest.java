package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.TaskDTO;
import com.restful.dslearn.dto.TaskRequestDTO;
import com.restful.dslearn.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private TaskService taskService;

    private final Instant dueDate = Instant.parse("2025-03-10T23:59:59Z");

    @Test
    void shouldReturnTasks() throws Exception {
        when(taskService.findAll()).thenReturn(List.of(
                new TaskDTO(1L, "Quiz", 1, 1L, "desc", 10, 7, 1.0, dueDate)));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Quiz")));
    }

    @Test
    void shouldReturnNotFoundWhenTaskMissing() throws Exception {
        when(taskService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateTask() throws Exception {
        TaskRequestDTO request = new TaskRequestDTO("New Task", 1, 1L, "desc", 5, 3, 2.0, dueDate);
        when(taskService.create(eq(request)))
                .thenReturn(new TaskDTO(2L, "New Task", 1, 1L, "desc", 5, 3, 2.0, dueDate));

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Task")));
    }

    @Test
    void shouldRejectInvalidTask() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"no title\"}"))
                .andExpect(status().isBadRequest());
    }
}
