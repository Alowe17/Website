package com.example.Web_Service.repository;

import com.example.Web_Service.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT u FROM Employee u")
    List<Employee> findAllEmployees();
}
