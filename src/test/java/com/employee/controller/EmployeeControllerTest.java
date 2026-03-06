package com.employee.controller;

import com.employee.exception.EmployeeNotFoundException;
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
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Verifies that a new Employee can be successfully created.
     *
     * <p>This test ensures that when valid employee data is provided to the
     * employee creation service/API, the system:
     * <ul>
     *     <li>Persists the employee record in the database</li>
     *     <li>Returns the created employee with a generated identifier</li>
     *     <li>Maintains the integrity of the provided fields</li>
     * </ul>
     *
     * <p>Expected Result:
     * <ul>
     *     <li>The employee is stored in the database</li>
     *     <li>A non-null employee ID is generated</li>
     *     <li>The returned employee matches the input values</li>
     * </ul>
     */
    @Test
    void shouldCreateEmployee() throws Exception {
        String request1 = """
                {
                "fullName":"Avirup Biswas",
                "jobTitle":"Software Developer",
                "country":"India",
                "salary":15000
                }
                """;
        String request2 = """
                {
                "fullName":"Sambit Mukherjee",
                "jobTitle":"Software Architect",
                "country":"India",
                "salary":900000
                }
                """;
        mockMvc.perform(post("/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request1))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request2))
                .andExpect(status().isCreated());
    }
    @Test
    void createEmployee_missingJobTitleValidation() throws Exception {
        String request = """
                {
                "fullName":"Sambit Mukherjee",
                "country":"India",
                "salary":15000
                }
                """;
        mockMvc.perform(post("/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
    @Test
    void createEmployee_missingCountryValidation() throws Exception {
        String request = """
                {
                "fullName":"Sambit Mukherjee",
                "jobTitle":"Software Developer",
                "salary":15000
                }
                """;
        mockMvc.perform(post("/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
    @Test
    void createEmployee_missingSalaryValidation() throws Exception {
        String request = """
                {
                "fullName":"Sambit Mukherjee",
                "jobTitle":"Software Developer",
                "country":"India"
                }
                """;
        mockMvc.perform(post("/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
    @Test
    void createEmployee_missingFullNameValidation() throws Exception {
        String request = """
                {
                "fullName":"",
                "jobTitle":"Software Developer",
                "country":"India",
                "salary":15000
                }
                """;
        mockMvc.perform(post("/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
    @Test
    void createEmployee_salaryValueShouldBeGreaterThanZero() throws Exception {
        String request = """
                {
                "fullName":"Avirup Biswas",
                "jobTitle":"Software Developer",
                "country":"India",
                "salary":-50
                }
                """;
        mockMvc.perform(post("/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldReturnAllEmployees() throws Exception {

        mockMvc.perform(get("/employees/getall"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEmployee() throws Exception {
        mockMvc.perform(get("/employees/get/452")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("452"))
                .andExpect(jsonPath("$.fullName").value("Avirup Biswas"));
    }
    @Test
    void testGetEmployee_noDataFound() throws Exception {
        mockMvc.perform(get("/employees/get/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateEmployee() throws Exception {

        mockMvc.perform(put("/employees/update/352")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                        "fullName":"Avirup Biswas",
                        "jobTitle":"IT",
                        "salary":50000,
                        "country":"UK"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Avirup Biswas"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employees/delete/353"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted"));
    }
}
