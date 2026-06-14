package com.employee.service;

import com.employee.dto.CrudOperationsRequestDTO;
import com.employee.dto.CrudOperationsResponseDTO;
import com.employee.dto.SalaryDetailsResponseDTO;

import java.util.List;

public interface CrudOperationsService {

    CrudOperationsResponseDTO create(CrudOperationsRequestDTO request);
    CrudOperationsResponseDTO getEmployee(Long id);

    List<CrudOperationsResponseDTO> getAllEmployees();
    CrudOperationsResponseDTO updateEmployee(Long id, CrudOperationsRequestDTO request);
    Long deleteEmployee(Long id);
    SalaryDetailsResponseDTO findEmployeeSalary(Long id);
}
