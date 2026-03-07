package com.employee.controller;

import com.employee.dto.CrudOperationsRequestDTO;
import com.employee.service.CrudOperationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller responsible for managing CRUD operations
 * related to Employee entities.
 *
 * <p>This controller exposes endpoints for:
 * <ul>
 *     <li>Creating employees</li>
 *     <li>Retrieving employee details</li>
 *     <li>Updating employee records</li>
 *     <li>Deleting employees</li>
 *     <li>Fetching salary details</li>
 * </ul>
 *
 * <p>All requests are routed through the service layer
 * where business logic and salary calculations are applied.
 * @author Avirup Biswas
 */
@RestController
@RequestMapping("/employees")
public class CrudController {

    @Autowired
    CrudOperationsService crudOperationsService;

    /**
     * Creates a new employee record.
     *
     * @param request employee request payload
     * @return created employee response
     */
    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody CrudOperationsRequestDTO request) {
        return new ResponseEntity<>(crudOperationsService.create(request), HttpStatus.CREATED);
    }

    /**
     * Retrieves all employees.
     *
     * @return list of employees
     */
    @GetMapping("/getall")
    public ResponseEntity<?> getAllEmployee() {
        return new ResponseEntity<>(crudOperationsService.getAllEmployees(), HttpStatus.OK);
    }

    /**
     * Retrieves employee details by ID.
     *
     * @param id employee identifier
     * @return employee details
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAllEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(crudOperationsService.getEmployee(id), HttpStatus.OK);
    }

    /**
     * Updates employee details.
     *
     * @param id employee identifier
     * @param request updated employee data
     * @return updated employee details
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id,
                                            @RequestBody CrudOperationsRequestDTO employee) {
        return ResponseEntity.ok(crudOperationsService.updateEmployee(id, employee));
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id employee identifier
     * @return deleted employee ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        crudOperationsService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted");
    }

    /**
     * Retrieves salary details for an employee.
     *
     * @param id employee identifier
     * @return salary information including gross, net, and deductions
     */
    @GetMapping("/{id}/salary")
    public ResponseEntity<?> findSalaryById(@PathVariable Long id) {
        return ResponseEntity.ok(crudOperationsService.findEmployeeSalary(id));
    }
}
