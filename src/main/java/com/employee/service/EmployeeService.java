package com.employee.service;

import com.employee.dto.EmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO create(EmployeeRequestDTO dto);
    EmployeeResponseDTO getEmployee(Long id);

    List<EmployeeResponseDTO> getAllEmployees();
}
