package com.employee.controller;

import com.employee.dto.EmployeeRequestDTO;
import com.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRequestDTO request) {
        return new ResponseEntity<>(employeeService.create(request),HttpStatus.CREATED);
    }
    @GetMapping("/getall")
    public ResponseEntity<?> getAllEmployee() {
        return  new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAllEmployee(@PathVariable Long id) {
        return  new ResponseEntity<>(employeeService.getEmployee(id), HttpStatus.OK);
    }
}
