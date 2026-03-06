package com.employee.dto;

import lombok.Data;

@Data
public class EmployeeRequestDTO {

    private String fullName;
    private String jobTitle;
    private String country;
    private Double salary;
}
