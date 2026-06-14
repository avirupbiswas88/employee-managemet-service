package com.employee.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SalaryStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnSalaryStats() throws Exception {

        mockMvc.perform(get("/employees/stats/salary/India"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("India"))
                .andExpect(jsonPath("$.responseMessage").value("country specific employee salary stats found"));
        ;
    }

    @Test
    void shouldThrowExceptionWhenCountryHasNoEmployees() throws Exception {
        mockMvc.perform(get("/employees/stats/salary/France"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("France"))
                .andExpect(jsonPath("$.responseMessage").value("country specific employee salary or employee stats not found"));
    }

    @Test
    void shouldReturnAverageSalary() throws Exception {
        mockMvc.perform(
                        get("/employees/stats/average-salary/Software Developer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobTitle").value("Software Developer"));
    }

    @Test
    void shouldReturnException_JobTitleNotFound() throws Exception {
        mockMvc.perform(
                        get("/employees/stats/average-salary/Principle Engineer"))
                .andExpect(status().isNotFound());
    }

}
