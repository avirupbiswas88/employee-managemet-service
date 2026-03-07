package com.employee.controller;

import com.employee.dto.EmployeeSalaryStatsResponseDTO;
import com.employee.service.SalaryStatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SalaryStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SalaryStatisticsService service;

    @Test
    void shouldReturnSalaryStats() throws Exception {

        EmployeeSalaryStatsResponseDTO response =
                new EmployeeSalaryStatsResponseDTO("India", 45000, 90000, 67500);
        mockMvc.perform(get("/employees/salary-stats/India"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("India"))
                .andExpect(jsonPath("$.responseMessage").value("country specific employee salary stats found"));
    }

    @Test
    void shouldThrowExceptionWhenCountryHasNoEmployees() throws Exception {
        EmployeeSalaryStatsResponseDTO response =
                new EmployeeSalaryStatsResponseDTO("France", 45000, 90000, 67500);
        mockMvc.perform(get("/employees/salary-stats/France"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.country").value("Japan"))
                .andExpect(jsonPath("$.responseMessage").value("country specific employee salary or employee stats not found"));
    }

}
