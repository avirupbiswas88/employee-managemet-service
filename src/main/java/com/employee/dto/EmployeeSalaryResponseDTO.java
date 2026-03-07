package com.employee.dto;

import lombok.Data;

@Data
public class EmployeeSalaryResponseDTO {
    private Long id;
    private double grossSalary;
    private double deduction;
    private double netSalary;

    public EmployeeSalaryResponseDTO() {
    }

    public EmployeeSalaryResponseDTO(Long id, double grossSalary, double deduction, double netSalary) {
        this.id = id;
        this.grossSalary = grossSalary;
        this.deduction = deduction;
        this.netSalary = netSalary;
    }
}
