package com.employee.service;

import com.employee.dto.EmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;
import com.employee.dto.EmployeeSalaryResponseDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO create(EmployeeRequestDTO request);
    EmployeeResponseDTO getEmployee(Long id);

    List<EmployeeResponseDTO> getAllEmployees();
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request);
    Long deleteEmployee(Long id);
    public EmployeeSalaryResponseDTO findEmployeeSalary(Long id);
}
