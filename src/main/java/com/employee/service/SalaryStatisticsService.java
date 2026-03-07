package com.employee.service;

import com.employee.dto.EmployeeStatsByCountryResponseDTO;

public interface SalaryStatisticsService {
    EmployeeStatsByCountryResponseDTO getSalaryStats(String country);
}
