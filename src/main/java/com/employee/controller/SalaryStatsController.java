package com.employee.controller;

import com.employee.service.SalaryStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller responsible for providing salary analytics
 * and statistical insights for employees.
 *
 * <p>This controller exposes APIs to:
 * <ul>
 *     <li>Retrieve salary statistics by country</li>
 *     <li>Retrieve average salary by job title</li>
 * </ul>
 *
 * <p>The controller delegates business logic to the
 * {@link SalaryStatisticsService} service layer.
 * @author Avirup Biswas
 */
@Tag(
        name = "Employee Salary Statistics API",
        description = "APIs for retrieving salary analytics such as salary statistics by country and average salary by job title"
)
@RestController
@RequestMapping("/employees/stats")
public class SalaryStatsController {

    @Autowired
    SalaryStatisticsService salaryStatisticsService;

    /**
     * Returns minimum, maximum and average net salary for a country.
     *
     * @param country employee country
     * @return salary statistics
     */
    @Operation(
            summary = "Get Salary Statistics by Country",
            description = "Returns minimum salary, maximum salary and average salary for employees belonging to a given country"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salary statistics successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "No employees found for the given country"),
            @ApiResponse(responseCode = "500", description = "Internal server error while calculating salary statistics")
    })
    @GetMapping("/salary/{country}")
    public ResponseEntity<?> getSalaryStats(@PathVariable String country) {
        return ResponseEntity.ok(salaryStatisticsService.getSalaryStats(country));
    }

    /**
     * Returns average salary for employees with given job title.
     *
     * @param jobTitle employee job title
     * @return average salary statistics
     */
    @Operation(
            summary = "Get Average Salary by Job Title",
            description = "Returns the average salary of employees who have the specified job title"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Average salary successfully calculated"),
            @ApiResponse(responseCode = "400", description = "Invalid job title provided"),
            @ApiResponse(responseCode = "404", description = "No employees found for the given job title")
    })
    @GetMapping("/average-salary/{jobTitle}")
    public ResponseEntity<?> getAverageSalary(
            @PathVariable String jobTitle) {
        return ResponseEntity.ok(salaryStatisticsService.getAverageSalary(jobTitle));
    }
}
