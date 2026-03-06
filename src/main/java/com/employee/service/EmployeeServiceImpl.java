package com.employee.service;

import com.employee.dto.EmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;
import com.employee.entity.Employee;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log =
            LogManager.getLogger(EmployeeService.class);
    @Autowired
    EmployeeRepository repository;

    @Override
    public EmployeeResponseDTO create(EmployeeRequestDTO dto) {
        try {
            Employee employee = this.mapEmployeeEntityFromDto(dto);
            Employee savedEmployee = repository.save(employee);
            EmployeeResponseDTO response = this.mapEmployeeResponseFromDatabaseResponse(savedEmployee);
            log.info("employee created with Id: {}",response.getId());
            return response;
        } catch (Exception ex) {
            log.info("exception occurred while transforming and saving employee data in database with exception: {}", ex.getMessage());
        }

        return null;
    }

    @Override
    public EmployeeResponseDTO getEmployee(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return this.mapEmployeeResponseFromDatabaseResponse(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return repository.findAll().stream().map(this::mapEmployeeResponseFromDatabaseResponse).toList();
    }

    private Employee mapEmployeeEntityFromDto(EmployeeRequestDTO dto) {
        Employee employee = new Employee();
        employee.setFullName(dto.getFullName());
        employee.setJobTitle(dto.getJobTitle());
        employee.setCountry(dto.getCountry());
        employee.setSalary(dto.getSalary());
        return employee;
    }

    private EmployeeResponseDTO mapEmployeeResponseFromDatabaseResponse(Employee emp) {
        EmployeeResponseDTO empResponse = new EmployeeResponseDTO();
        empResponse.setId(emp.getId());
        empResponse.setFullName(emp.getFullName());
        empResponse.setJobTitle(emp.getJobTitle());
        empResponse.setCountry(emp.getCountry());
        empResponse.setSalary(emp.getSalary());
        return empResponse;
    }
}
