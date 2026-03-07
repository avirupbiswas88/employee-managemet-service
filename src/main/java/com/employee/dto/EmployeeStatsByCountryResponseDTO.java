package com.employee.dto;

import lombok.Data;

@Data
public class EmployeeStatsByCountryResponseDTO {
    private String country;
    private double minNetSalary;
    private double maxNetSalary;
    private double averageNetSalary;
    private String responseMessage;

    public EmployeeStatsByCountryResponseDTO() {
    }

    public EmployeeStatsByCountryResponseDTO(String country, double minNetSalary, double maxNetSalary, double averageNetSalary, String responseMessage) {
        this.country = country;
        this.minNetSalary = minNetSalary;
        this.maxNetSalary = maxNetSalary;
        this.averageNetSalary = averageNetSalary;
        this.responseMessage = responseMessage;
    }
}
