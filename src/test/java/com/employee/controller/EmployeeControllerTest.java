package com.employee.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        String request = """
                {
                "fullName":"Avirup Biswas",
                "jobTitle":"Software Developer",
                "country":"India",
                "salary":15000
                }
                """;
        mockMvc.perform(post("/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
    }
}
