package com.employee.controller;

import com.employee.dto.CrudOperationsRequestDTO;
import com.employee.service.CrudOperationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class CrudController {

    @Autowired
    CrudOperationsService crudOperationsService;

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody CrudOperationsRequestDTO request) {
        return new ResponseEntity<>(crudOperationsService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllEmployee() {
        return new ResponseEntity<>(crudOperationsService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAllEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(crudOperationsService.getEmployee(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id,
                                            @RequestBody CrudOperationsRequestDTO employee) {
        return ResponseEntity.ok(crudOperationsService.updateEmployee(id, employee));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        crudOperationsService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted");
    }

    @GetMapping("/{id}/salary")
    public ResponseEntity<?> findSalaryById(@PathVariable Long id) {
        return ResponseEntity.ok(crudOperationsService.findEmployeeSalary(id));
    }
}
