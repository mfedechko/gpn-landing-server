package com.gpn.leads;

import com.gpn.leads.repository.LeadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LeadEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LeadRepository leadRepository;

    @BeforeEach
    void setUp() {
        leadRepository.deleteAll();
    }

    @Test
    void postLead_validPayload_returns201() throws Exception {
        mockMvc.perform(post("/api/leads")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "name": "Іван Петренко", "phone": "+380685798545" }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.message", containsString("Дякуємо")));
    }

    @Test
    void postLead_missingName_returns400() throws Exception {
        mockMvc.perform(post("/api/leads")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "phone": "+380685798545" }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("name")));
    }

    @Test
    void postLead_invalidPhone_returns400() throws Exception {
        mockMvc.perform(post("/api/leads")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "name": "Іван", "phone": "not-a-phone" }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getLeads_returnsListNewestFirst() throws Exception {
        // Seed two leads
        mockMvc.perform(post("/api/leads")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "name": "Аліна", "phone": "+380501234567" }
                        """));
        mockMvc.perform(post("/api/leads")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "name": "Богдан", "phone": "+380677654321" }
                        """));

        mockMvc.perform(get("/api/leads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Богдан")));
    }
}
