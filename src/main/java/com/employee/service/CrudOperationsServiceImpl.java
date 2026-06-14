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

/**
 * Service implementation responsible for handling CRUD operations
 * related to Employee management.
 *
 * This service performs:
 * - Employee creation
 * - Employee retrieval
 * - Employee updates
 * - Employee deletion
 * - Salary calculation based on country-specific tax strategies
 *
 * The service interacts with the repository layer to persist
 * employee information and applies business logic such as
 * tax deduction and net salary calculation.
 *
 * Exceptions handled:
 * - EmployeeNotFoundException
 * - EmployeeServiceGenericException
 */
@Service
public class CrudOperationsServiceImpl implements CrudOperationsService {

    private static final Logger log =
            LogManager.getLogger(CrudOperationsServiceImpl.class);
    @Autowired
    EmployeeRepository repository;

    /**
     * Creates a new employee record in the database.
     *
     * Steps performed:
     * 1. Maps request DTO to Employee entity.
     * 2. Calculates salary details (gross, net, deduction).
     * 3. Saves the employee in the database.
     * 4. Converts saved entity to response DTO.
     *
     * @param request request object containing employee information
     * @return CrudOperationsResponseDTO containing saved employee details
     * @author Avirup Biswas
     */
    @Override
    public CrudOperationsResponseDTO create(CrudOperationsRequestDTO request) {
        try {
            Employee employee = this.mapEmployeeEntityFromDto(request);
            this.calculateSalary(request, employee);
            Employee savedEmployee = repository.save(employee);
            CrudOperationsResponseDTO response = this.mapEmployeeResponseFromDatabase(savedEmployee);
            log.info("employee created with Id: {} with metadata- {}", response.getId(), response);
            return response;
        } catch (Exception ex) {
            log.error("exception occurred while transforming and saving employee data in database with exception: {}", ex.getMessage());
        }
        return null;
    }

    /**
     * Retrieves employee details by employee ID.
     *
     * @param id unique identifier of the employee
     * @return CrudOperationsResponseDTO containing employee information
     * @throws EmployeeNotFoundException if employee does not exist
     * @author Avirup Biswas
     */
    @Override
    public CrudOperationsResponseDTO getEmployee(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        log.debug("employee response from the database- {}", employee);
        return this.mapEmployeeResponseFromDatabase(employee);
    }

    /**
     * Fetches all employees from the database.
     *
     * @return list of CrudOperationsResponseDTO containing employee records
     * @author Avirup Biswas
     */
    @Override
    public List<CrudOperationsResponseDTO> getAllEmployees() {
        return repository.findAll().stream().map(this::mapEmployeeResponseFromDatabase).toList();
    }

    /**
     * Updates an existing employee record.
     *
     * Steps performed:
     * 1. Fetch employee from database.
     * 2. Update fields from request DTO.
     * 3. Recalculate salary details.
     * 4. Save updated employee.
     *
     * @param id employee identifier
     * @param request request object containing updated data
     * @return updated employee response DTO
     * @throws EmployeeNotFoundException if employee is not found
     * @author Avirup Biswas
     */
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
            CrudOperationsResponseDTO response = this.mapEmployeeResponseFromDatabase(updatedEmployee);
            log.info("employee updated with Id: {} and response- {}", response.getId(), response);
            return response;
        } catch (Exception ex) {
            log.error("exception occurred while transforming and updating employee data in database with exception: {}", ex.getMessage());
        }
        return null;
    }

    /**
     * Deletes an employee record from the database.
     *
     * @param id employee identifier
     * @return id of the deleted employee
     * @throws EmployeeNotFoundException if employee does not exist
     * @author Avirup Biswas
     */
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

    /**
     * Converts request DTO to Employee entity.
     *
     * @param dto employee request DTO
     * @return mapped Employee entity
     * @author Avirup Biswas
     */
    private Employee mapEmployeeEntityFromDto(CrudOperationsRequestDTO dto) {
        Employee employee = new Employee();
        employee.setFullName(dto.getFullName());
        employee.setJobTitle(dto.getJobTitle());
        employee.setCountry(dto.getCountry());
        return employee;
    }

    /**
     * Converts Employee entity to response DTO.
     *
     * @param emp employee entity retrieved from database
     * @return CrudOperationsResponseDTO
     * @author Avirup Biswas
     */
    private CrudOperationsResponseDTO mapEmployeeResponseFromDatabase(Employee emp) {
        CrudOperationsResponseDTO empResponse = new CrudOperationsResponseDTO();
        empResponse.setId(emp.getId());
        empResponse.setFullName(emp.getFullName());
        empResponse.setJobTitle(emp.getJobTitle());
        empResponse.setCountry(emp.getCountry());
        empResponse.setGrossSalary(emp.getGrossSalary());
        return empResponse;
    }

    /**
     * Calculates salary details for an employee using
     * country-specific tax deduction strategies.
     *
     * This method calculates:
     * - Gross salary
     * - Tax deduction
     * - Net salary
     *
     * @param request request DTO containing salary and country
     * @param employee employee entity to update salary fields
     * @throws EmployeeServiceGenericException if salary calculation fails
     * @author Avirup Biswas
     */
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

    /**
     * Retrieves salary details of an employee.
     *
     * @param id employee identifier
     * @return SalaryDetailsResponseDTO containing gross salary,
     * net salary and deduction
     * @throws EmployeeNotFoundException if employee does not exist
     * @author Avirup Biswas
     */
    @Override
    public SalaryDetailsResponseDTO findEmployeeSalary(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        log.info("response from database: {}",employee);
        return this.mapEmployeeSalaryResponseFromDatabase(employee);
    }

    /**
     * Converts Employee entity to salary response DTO.
     *
     * @param emp employee entity
     * @return SalaryDetailsResponseDTO containing salary details
     * @author Avirup Biswas
     */
    private SalaryDetailsResponseDTO mapEmployeeSalaryResponseFromDatabase(Employee emp) {
        SalaryDetailsResponseDTO empSalaryResponse = new SalaryDetailsResponseDTO();
        empSalaryResponse.setId(emp.getId());
        empSalaryResponse.setGrossSalary(emp.getGrossSalary());
        empSalaryResponse.setNetSalary(emp.getNetSalary());
        empSalaryResponse.setDeduction(emp.getDeduction());
        return empSalaryResponse;
    }
}
