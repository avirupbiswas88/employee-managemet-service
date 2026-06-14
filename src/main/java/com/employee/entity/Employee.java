package com.employee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Entity
@Table(name="employees")
@Data
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable=false)
    private String fullName;
    @Column(nullable=false)
    private String jobTitle;
    @Column(nullable=false)
    private String country;
    @Column(nullable=false)
    @Positive(message = "gross salary should be greater than zero")
    private Double grossSalary;
    @Column(nullable=false)
    private Double deduction;
    @Column(nullable=false)
    @Positive(message = "net salary should be greater than zero")
    private Double netSalary;
}
