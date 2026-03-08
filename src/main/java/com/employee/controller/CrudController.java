package com.employee.controller;

import com.employee.dto.CrudOperationsRequestDTO;
import com.employee.service.CrudOperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Employee CRUD API", description = "Employee CRUD operations")
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
    @Operation(
            summary = "Create Employee",
            description = "Creates a new employee and calculates salary deductions"
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody CrudOperationsRequestDTO request) {
        return new ResponseEntity<>(crudOperationsService.create(request), HttpStatus.CREATED);
    }

    /**
     * Retrieves all employees.
     *
     * @return list of employees
     */
    @Operation(
            summary = "Get All Employees",
            description = "Retrieve a list of all employees stored in the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully")
    })
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
    @Operation(
            summary = "Get Employee by ID",
            description = "Fetch employee details for a given employee ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
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
    @Operation(
            summary = "Update Employee",
            description = "Updates employee information and recalculates salary details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid update request")
    })
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
    @Operation(
            summary = "Delete Employee",
            description = "Deletes an employee record from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
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
    @Operation(
            summary = "Get Employee Salary Details",
            description = "Returns salary breakdown including gross salary, deductions and net salary"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salary details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}/salary")
    public ResponseEntity<?> findSalaryById(@PathVariable Long id) {
        return ResponseEntity.ok(crudOperationsService.findEmployeeSalary(id));
    }
}
