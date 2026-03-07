package com.employee.dto;

import lombok.Data;

@Data
public class SalaryDetailsResponseDTO {
    private Long id;
    private double grossSalary;
    private double deduction;
    private double netSalary;

    public SalaryDetailsResponseDTO() {
    }

    public SalaryDetailsResponseDTO(Long id, double grossSalary, double deduction, double netSalary) {
        this.id = id;
        this.grossSalary = grossSalary;
        this.deduction = deduction;
        this.netSalary = netSalary;
    }
}
