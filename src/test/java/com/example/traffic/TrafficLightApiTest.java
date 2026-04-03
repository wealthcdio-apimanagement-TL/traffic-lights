package com.example.traffic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TrafficLightApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void change_state_and_get_current_state() throws Exception {
        String payload = "{"NORTH":"GREEN","SOUTH":"GREEN","EAST":"RED","WEST":"RED"}";

        mockMvc.perform(post("/api/traffic/state")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/traffic/state"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lights.NORTH").value("GREEN"));
    }

    @Test
    void conflicting_green_should_fail() throws Exception {
        String payload = "{"NORTH":"GREEN","EAST":"GREEN"}";

        mockMvc.perform(post("/api/traffic/state")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void pause_and_resume_should_work() throws Exception {
        mockMvc.perform(post("/api/traffic/pause")).andExpect(status().isOk());
        mockMvc.perform(post("/api/traffic/resume")).andExpect(status().isOk());
    }
}
