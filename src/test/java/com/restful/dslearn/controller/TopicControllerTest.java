package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.TopicDTO;
import com.restful.dslearn.dto.TopicRequestDTO;
import com.restful.dslearn.service.TopicService;
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

@WebMvcTest(TopicController.class)
class TopicControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private TopicService topicService;

    @Test
    void shouldReturnTopics() throws Exception {
        when(topicService.findAll()).thenReturn(List.of(
                new TopicDTO(1L, "Question", "body", null, 1L, 1L, 1L, null)));

        mockMvc.perform(get("/api/topics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Question")));
    }

    @Test
    void shouldReturnNotFoundWhenTopicMissing() throws Exception {
        when(topicService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/topics/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateTopic() throws Exception {
        TopicRequestDTO request = new TopicRequestDTO("New Q", "body", 1L, 1L, 1L, null);
        when(topicService.create(eq(request)))
                .thenReturn(new TopicDTO(2L, "New Q", "body", null, 1L, 1L, 1L, null));

        mockMvc.perform(post("/api/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Q")));
    }

    @Test
    void shouldRejectInvalidTopic() throws Exception {
        mockMvc.perform(post("/api/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"body\":\"no title\"}"))
                .andExpect(status().isBadRequest());
    }
}
