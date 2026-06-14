package com.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CrudOperationsRequestDTO {

    @NotBlank
    private String fullName;
    @NotBlank
    private String jobTitle;
    @NotBlank
    private String country;
    @NotNull
    @Positive(message = "salary should be greater than zero")
    private Double salary;
}
