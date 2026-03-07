package com.employee.service;

import com.employee.dto.AverageSalaryResponseDTO;
import com.employee.dto.EmployeeStatsByCountryResponseDTO;
import com.employee.entity.Employee;
import com.employee.exception.EmployeeServiceGenericException;
import com.employee.exception.JobTitleNotFoundException;
import com.employee.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Service implementation responsible for calculating employee
 * salary statistics and analytics.
 *
 * <p>This service provides business logic to compute:
 * <ul>
 *     <li>Salary statistics by country (min, max, average)</li>
 *     <li>Average salary by job title</li>
 * </ul>
 *
 * <p>The service interacts with the repository layer to retrieve
 * employee data and performs statistical calculations using
 * Java Stream APIs.
 *
 * <p>Exceptions handled:
 * <ul>
 *     <li>EmployeeServiceGenericException</li>
 *     <li>JobTitleNotFoundException</li>
 * </ul>
 */
@Service
public class SalaryStatisticsServiceImpl implements SalaryStatisticsService {
    private static final Logger log =
            LogManager.getLogger(SalaryStatisticsServiceImpl.class);
    private static final String SUCCESS_MESSAGE = "country specific employee salary stats found";
    private static final String FAILURE_MESSAGE = "country specific employee salary or employee stats not found";
    @Autowired
    EmployeeRepository repository;

    /**
     * Retrieves salary statistics for employees belonging to a specific country.
     *
     * <p>This method performs the following steps:
     * <ol>
     *     <li>Fetch employees by country from the repository</li>
     *     <li>Calculate salary statistics using {@link DoubleSummaryStatistics}</li>
     *     <li>Return minimum, maximum, and average salary values</li>
     * </ol>
     *
     * @param country the country for which salary statistics are required
     * @return {@link EmployeeStatsByCountryResponseDTO} containing salary statistics
     *
     * @throws EmployeeServiceGenericException
     * if an error occurs while processing salary statistics
     * @author Avirup Biswas
     */
    @Override
    public EmployeeStatsByCountryResponseDTO getSalaryStats(String country) {
        try {
            log.info("Fetching salary stats for country={}", country);
            EmployeeStatsByCountryResponseDTO response = new EmployeeStatsByCountryResponseDTO();

            List<Employee> employeesByCountry = repository.findByCountryIgnoreCase(country);

            if (employeesByCountry.isEmpty()) {
                log.error("No employees found for country={}", country);
                response.setCountry(country);
                response.setResponseMessage(FAILURE_MESSAGE);
                return response;
                //throw new EmployeeServiceGenericException("No employees found for country: " + country);
            }

            DoubleSummaryStatistics stats =
                    employeesByCountry.stream()
                            .mapToDouble(Employee::getNetSalary)
                            .summaryStatistics();

            log.debug("Salary statistics calculated for country={} with stats- {}", country, stats);

            return new EmployeeStatsByCountryResponseDTO(
                    country,
                    stats.getMin(),
                    stats.getMax(),
                    stats.getAverage(),
                    SUCCESS_MESSAGE
            );
        } catch (Exception e) {
            log.info("error while processing employee stats by country with exception: {}", e.getMessage());
            throw new EmployeeServiceGenericException("error while processing employee stats by country with exception");
        }
    }

    /**
     * Calculates the average salary of employees for a given job title.
     *
     * <p>The method retrieves aggregated salary data directly
     * from the repository using a database-level query.
     *
     * <p>Processing steps:
     * <ol>
     *     <li>Validate job title input</li>
     *     <li>Fetch average salary using repository query</li>
     *     <li>Return formatted response DTO</li>
     * </ol>
     *
     * @param jobTitle the job title used to calculate the average salary
     *
     * @return {@link AverageSalaryResponseDTO} containing the job title
     * and its calculated average salary
     *
     * @throws IllegalArgumentException
     * if job title is null or empty
     *
     * @throws JobTitleNotFoundException
     * if no employees exist for the provided job title
     *
     * @author Avirup Biswas
     */
    @Override
    public AverageSalaryResponseDTO getAverageSalary(String jobTitle) {

        log.info("Fetching average salary for jobTitle= {}", jobTitle);

        if (jobTitle == null || jobTitle.isBlank()) {
            log.error("Invalid job title provided");
            throw new IllegalArgumentException("job title cannot be empty");
        }

        Double avgSalary = repository.findAverageSalaryByJobTitle(jobTitle);

        if (avgSalary == null) {

            log.warn("No employees found for jobTitle={}", jobTitle);
            throw new JobTitleNotFoundException(
                    "No employees found for job title: " + jobTitle
            );
        }

        log.debug("Average salary calculated = {}", avgSalary);

        return new AverageSalaryResponseDTO(
                jobTitle,
                avgSalary,
                "average salary for job title found"
        );
    }
}
