package com.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

@Data
public class EmployeeRequestDTO {

    @NotBlank
    private String fullName;
    @NotBlank
    private String jobTitle;
    @NotBlank
    private String country;
    @Positive(message = "salary should be greater than zero")
    private Double salary;
}
