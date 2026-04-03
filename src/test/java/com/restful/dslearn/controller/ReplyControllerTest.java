package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.ReplyDTO;
import com.restful.dslearn.dto.ReplyRequestDTO;
import com.restful.dslearn.service.ReplyService;
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

@WebMvcTest(ReplyController.class)
class ReplyControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ReplyService replyService;

    @Test
    void shouldReturnReplies() throws Exception {
        when(replyService.findAll()).thenReturn(List.of(
                new ReplyDTO(1L, "Answer text", null, 1L, 1L)));

        mockMvc.perform(get("/api/replies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].body", is("Answer text")));
    }

    @Test
    void shouldReturnNotFoundWhenReplyMissing() throws Exception {
        when(replyService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/replies/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateReply() throws Exception {
        ReplyRequestDTO request = new ReplyRequestDTO("My reply", 1L, 1L);
        when(replyService.create(eq(request)))
                .thenReturn(new ReplyDTO(2L, "My reply", null, 1L, 1L));

        mockMvc.perform(post("/api/replies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body", is("My reply")));
    }

    @Test
    void shouldRejectInvalidReply() throws Exception {
        mockMvc.perform(post("/api/replies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"body\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
