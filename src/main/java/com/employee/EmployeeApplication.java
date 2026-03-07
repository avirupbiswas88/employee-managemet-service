package com.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class EmployeeApplication{
    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }
}