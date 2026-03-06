package com.employee.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="employees")
@Data
public class Employee {

    @Id
    private Long id;
    @Column(nullable=false)
    private String fullName;
    @Column(nullable=false)
    private String jobTitle;
    @Column(nullable=false)
    private String country;
    @Column(nullable=false)
    private Double salary;
}
