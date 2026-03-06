package com.employee.service;

import com.employee.dto.EmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;

public interface EmployeeService {

    EmployeeResponseDTO create(EmployeeRequestDTO dto);
}
