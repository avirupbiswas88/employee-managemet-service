package com.employee.dto;

import jakarta.validation.constraints.NotBlank;
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
    private Double salary;
}
