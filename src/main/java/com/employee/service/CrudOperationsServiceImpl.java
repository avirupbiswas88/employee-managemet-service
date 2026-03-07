package com.employee.service;

import com.employee.dto.CrudOperationsRequestDTO;
import com.employee.dto.CrudOperationsResponseDTO;
import com.employee.dto.SalaryDetailsResponseDTO;
import com.employee.entity.Employee;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.exception.EmployeeServiceGenericException;
import com.employee.repository.EmployeeRepository;
import com.employee.util.CountryTaxStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudOperationsServiceImpl implements CrudOperationsService {

    private static final Logger log =
            LogManager.getLogger(CrudOperationsServiceImpl.class);
    @Autowired
    EmployeeRepository repository;

    @Override
    public CrudOperationsResponseDTO create(CrudOperationsRequestDTO request) {
        try {
            Employee employee = this.mapEmployeeEntityFromDto(request);
            this.calculateSalary(request, employee);
            Employee savedEmployee = repository.save(employee);
            CrudOperationsResponseDTO response = this.mapEmployeeResponseFromDatabaseResponse(savedEmployee);
            log.info("employee created with Id: {} with metadata- {}", response.getId(), response);
            return response;
        } catch (Exception ex) {
            log.error("exception occurred while transforming and saving employee data in database with exception: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public CrudOperationsResponseDTO getEmployee(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        log.debug("employee response from the database- {}", employee);
        return this.mapEmployeeResponseFromDatabaseResponse(employee);
    }

    @Override
    public List<CrudOperationsResponseDTO> getAllEmployees() {
        return repository.findAll().stream().map(this::mapEmployeeResponseFromDatabaseResponse).toList();
    }

    @Override
    public CrudOperationsResponseDTO updateEmployee(Long id, CrudOperationsRequestDTO request) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        try {
            employee.setFullName(request.getFullName());
            employee.setJobTitle(request.getJobTitle());
            employee.setCountry(request.getCountry());
            this.calculateSalary(request, employee);
            Employee updatedEmployee = repository.save(employee);
            CrudOperationsResponseDTO response = this.mapEmployeeResponseFromDatabaseResponse(updatedEmployee);
            log.info("employee updated with Id: {} and response- {}", response.getId(), response);
            return response;
        } catch (Exception ex) {
            log.error("exception occurred while transforming and updating employee data in database with exception: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public Long deleteEmployee(Long id) {
        try {
            if (!repository.existsById(id)) {
                throw new EmployeeNotFoundException(id);
            }
            repository.deleteById(id);
            return id;
        } catch (Exception ex) {
            log.error("exception occurred while deleting employee data in database with exception: {}", ex.getMessage());
        }
        return null;
    }

    private Employee mapEmployeeEntityFromDto(CrudOperationsRequestDTO dto) {
        Employee employee = new Employee();
        employee.setFullName(dto.getFullName());
        employee.setJobTitle(dto.getJobTitle());
        employee.setCountry(dto.getCountry());
        return employee;
    }

    private CrudOperationsResponseDTO mapEmployeeResponseFromDatabaseResponse(Employee emp) {
        CrudOperationsResponseDTO empResponse = new CrudOperationsResponseDTO();
        empResponse.setId(emp.getId());
        empResponse.setFullName(emp.getFullName());
        empResponse.setJobTitle(emp.getJobTitle());
        empResponse.setCountry(emp.getCountry());
        empResponse.setGrossSalary(emp.getGrossSalary());
        return empResponse;
    }

    private void calculateSalary(CrudOperationsRequestDTO request, Employee employee) {
        double grossSalary = request.getSalary();
        try {
            CountryTaxStrategy strategy =
                    CountryTaxStrategy.fromCountry(request.getCountry());
            double deduction = strategy.calculateDeduction(grossSalary);
            double netSalary = grossSalary - deduction;
            employee.setGrossSalary(grossSalary);
            employee.setNetSalary(netSalary);
            employee.setDeduction(deduction);
        } catch (Exception e) {
            log.error("exception while calculating employee salary");
            throw new EmployeeServiceGenericException("exception while calculating employee salary with exception- " + e.getMessage());
        }
    }
    @Override
    public SalaryDetailsResponseDTO findEmployeeSalary(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        log.info("response from database: {}",employee);
        return this.mapEmployeeSalaryResponseFromDatabase(employee);
    }
    private SalaryDetailsResponseDTO mapEmployeeSalaryResponseFromDatabase(Employee emp) {
        SalaryDetailsResponseDTO empSalaryResponse = new SalaryDetailsResponseDTO();
        empSalaryResponse.setId(emp.getId());
        empSalaryResponse.setGrossSalary(emp.getGrossSalary());
        empSalaryResponse.setNetSalary(emp.getNetSalary());
        empSalaryResponse.setDeduction(emp.getDeduction());
        return empSalaryResponse;
    }
}
