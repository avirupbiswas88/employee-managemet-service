package com.employee.repository;

import com.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT * FROM employees WHERE LOWER(country) = LOWER(:country)", nativeQuery = true)
    List<Employee> findByCountryIgnoreCase(@Param("country") String country);

    @Query(
            value = "SELECT AVG(net_salary) FROM employees WHERE LOWER(job_title) = LOWER(:jobTitle)",
            nativeQuery = true
    )
    Double findAverageSalaryByJobTitle(@Param("jobTitle") String jobTitle);
}
