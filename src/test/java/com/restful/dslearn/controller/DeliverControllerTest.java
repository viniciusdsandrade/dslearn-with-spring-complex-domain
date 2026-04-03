package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.DeliverDTO;
import com.restful.dslearn.dto.DeliverRequestDTO;
import com.restful.dslearn.entity.DeliverStatus;
import com.restful.dslearn.service.DeliverService;
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

@WebMvcTest(DeliverController.class)
class DeliverControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private DeliverService deliverService;

    @Test
    void shouldReturnDelivers() throws Exception {
        when(deliverService.findAll()).thenReturn(List.of(
                new DeliverDTO(1L, "gist.com/1", "Good", 8, DeliverStatus.ACCEPTED, 1L, 1L, 2L)));

        mockMvc.perform(get("/api/delivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is("ACCEPTED")));
    }

    @Test
    void shouldReturnNotFoundWhenDeliverMissing() throws Exception {
        when(deliverService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/delivers/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateDeliver() throws Exception {
        DeliverRequestDTO request = new DeliverRequestDTO(
                "gist.com/2", "feedback", 7, DeliverStatus.PENDING, 1L, 1L, 2L);
        when(deliverService.create(eq(request)))
                .thenReturn(new DeliverDTO(2L, "gist.com/2", "feedback", 7, DeliverStatus.PENDING, 1L, 1L, 2L));

        mockMvc.perform(post("/api/delivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void shouldRejectInvalidDeliver() throws Exception {
        mockMvc.perform(post("/api/delivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"uri\":\"gist.com\"}"))
                .andExpect(status().isBadRequest());
    }
}
