package com.employee.dto;

import lombok.Data;

@Data
public class CrudOperationsResponseDTO {
    private Long id;
    private String fullName;
    private String jobTitle;
    private String country;
    private Double grossSalary;

    @Override
    public String toString() {
        return "EmployeeResponseDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", country='" + country + '\'' +
                ", grossSalary=" + grossSalary +
                '}';
    }
}
