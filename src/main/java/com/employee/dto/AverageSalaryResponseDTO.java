package com.employee.dto;

import lombok.Data;

@Data
public class AverageSalaryResponseDTO {
    private String jobTitle;
    private Double averageSalary;
    private String responseMessage;

    public AverageSalaryResponseDTO() {
    }

    public AverageSalaryResponseDTO(
            String jobTitle,
            Double averageSalary,
            String responseMessage) {

        this.jobTitle = jobTitle;
        this.averageSalary = averageSalary;
        this.responseMessage = responseMessage;
    }
}
