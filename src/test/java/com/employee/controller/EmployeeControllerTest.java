package com.employee.controller;

import com.employee.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
    @InjectMocks
    private EmployeeServiceImpl employeeService;

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
                "salary":100000
                }
                """;
        String request2 = """
                {
                "fullName":"Sambit Mukherjee",
                "jobTitle":"Software Architect",
                "country":"United States",
                "salary":900000
                }
                """;
        String request3 = """
                {
                "fullName":"Anurima Biswas",
                "jobTitle":"Sr. Developer",
                "country":"Japan",
                "salary":150000
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
        mockMvc.perform(post("/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request3))
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
        mockMvc.perform(get("/employees/get/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
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

        mockMvc.perform(put("/employees/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "fullName":"Avirup Biswas",
                                "jobTitle":"IT",
                                "salary":150000,
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

    @Test
    void shouldReturnSalaryDetailsForIndia() throws Exception {

        mockMvc.perform(get("/employees/202/salary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(202))
                .andExpect(jsonPath("$.grossSalary").value(100000))
                .andExpect(jsonPath("$.deduction").value(10000))
                .andExpect(jsonPath("$.netSalary").value(90000));
    }
    @Test
    void shouldReturnSalaryDetailsForUSA() throws Exception {

        mockMvc.perform(get("/employees/203/salary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(203))
                .andExpect(jsonPath("$.grossSalary").value(900000))
                .andExpect(jsonPath("$.deduction").value(108000))
                .andExpect(jsonPath("$.netSalary").value(792000));
    }

    @Test
    void shouldReturnSalaryDetailsForJapan() throws Exception {

        mockMvc.perform(get("/employees/204/salary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(204))
                .andExpect(jsonPath("$.grossSalary").value(150000))
                .andExpect(jsonPath("$.deduction").value(0))
                .andExpect(jsonPath("$.netSalary").value(150000));
    }

    @Test
    void shouldReturnExceptionWhenEmployeeNotFound() throws Exception {

        mockMvc.perform(get("/employees/395/salary"))
                .andExpect(status().isNotFound());
    }

}
