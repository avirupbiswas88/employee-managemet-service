package com.employee.controller;

import com.employee.dto.AverageSalaryResponseDTO;
import com.employee.service.SalaryStatisticsService;
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
    @GetMapping("/average-salary/{jobTitle}")
    public ResponseEntity<?> getAverageSalary(
            @PathVariable String jobTitle) {

        return ResponseEntity.ok(salaryStatisticsService.getAverageSalary(jobTitle));
    }
}
