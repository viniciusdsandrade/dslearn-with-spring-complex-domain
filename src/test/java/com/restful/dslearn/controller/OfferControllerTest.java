package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.OfferDTO;
import com.restful.dslearn.dto.OfferRequestDTO;
import com.restful.dslearn.service.OfferService;
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

@WebMvcTest(OfferController.class)
class OfferControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private OfferService offerService;

    private final Instant start = Instant.parse("2025-01-10T09:00:00Z");
    private final Instant end = Instant.parse("2025-03-30T18:00:00Z");

    @Test
    void shouldReturnOffers() throws Exception {
        when(offerService.findAll()).thenReturn(List.of(
                new OfferDTO(1L, "2025.1", start, end, 1L)));

        mockMvc.perform(get("/api/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].edition", is("2025.1")));
    }

    @Test
    void shouldReturnNotFoundWhenOfferMissing() throws Exception {
        when(offerService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/offers/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateOffer() throws Exception {
        OfferRequestDTO request = new OfferRequestDTO("2025.2", start, end, 1L);
        when(offerService.create(eq(request)))
                .thenReturn(new OfferDTO(2L, "2025.2", start, end, 1L));

        mockMvc.perform(post("/api/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    void shouldRejectInvalidOffer() throws Exception {
        mockMvc.perform(post("/api/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"edition\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
