package com.employee.service;

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
}
