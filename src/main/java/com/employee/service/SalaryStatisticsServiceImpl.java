package com.employee.service;

import com.employee.dto.EmployeeStatsByCountryResponseDTO;
import com.employee.entity.Employee;
import com.employee.exception.EmployeeServiceGenericException;
import com.employee.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class SalaryStatisticsServiceImpl implements SalaryStatisticsService {
    private static final Logger log =
            LogManager.getLogger(SalaryStatisticsServiceImpl.class);
    private static final String SUCCESS_MESSAGE = "country specific employee salary stats found";
    private static final String FAILURE_MESSAGE = "country specific employee salary or employee stats not found";
    @Autowired
    EmployeeRepository repository;

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
}
