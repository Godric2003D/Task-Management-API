package com.debayan.taskapi.Task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.debayan.taskapi.Task.dto.TaskRequestDto;
import com.debayan.taskapi.Task.enums.Priority;
import com.debayan.taskapi.Task.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTask_Success() throws Exception {
        TaskRequestDto dto = new TaskRequestDto();
        dto.setTitle("Complete Spring Boot Assignment");
        dto.setDescription("Build a task management API");
        dto.setStatus(TaskStatus.PENDING);
        dto.setPriority(Priority.HIGH);
        dto.setDueDate(LocalDate.of(2024, 2, 15));

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Complete Spring Boot Assignment"));
    }

    @Test
    void testCreateTask_ValidationError() throws Exception {
        TaskRequestDto dto = new TaskRequestDto();
        dto.setTitle("Hi"); // too short
        dto.setDescription("");
        // Missing status and priority

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void testGetTask_NotFound() throws Exception {
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id 999"));
    }
}
