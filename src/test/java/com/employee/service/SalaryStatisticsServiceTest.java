package com.employee.service;

import com.employee.dto.AverageSalaryResponseDTO;
import com.employee.exception.JobTitleNotFoundException;
import com.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalaryStatisticsServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private SalaryStatisticsServiceImpl service;

    @Test
    void shouldReturnAverageSalary() {
        when(repository.findAverageSalaryByJobTitle("Software Developer"))
                .thenReturn(80000.0);
        var response = service.getAverageSalary("Software Developer");

        assertEquals(80000.0, response.getAverageSalary());
    }

    @Test
    void shouldThrowExceptionWhenJobTitleNotFound() {

        when(repository.findAverageSalaryByJobTitle("Principle Engineer"))
                .thenReturn(null);

        assertThrows(JobTitleNotFoundException.class,
                () -> service.getAverageSalary("Principle Engineer"));
    }
    @Test
    void shouldThrowExceptionWhenEmptyJobTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getAverageSalary(""));
    }
    @Test
    void shouldHandleCaseInsensitiveJobTitle() {
        when(repository.findAverageSalaryByJobTitle("Software Architect"))
                .thenReturn(240000.98);

        AverageSalaryResponseDTO response =
                service.getAverageSalary("Software Architect");

        assertEquals(240000.98, response.getAverageSalary());
    }
    @Test
    void shouldHandleLargeSalaryDataset() {

        when(repository.findAverageSalaryByJobTitle("Engineer"))
                .thenReturn(8500000.7564);

        AverageSalaryResponseDTO response =
                service.getAverageSalary("Engineer");

        assertEquals(8500000.7564, response.getAverageSalary());
    }
}
