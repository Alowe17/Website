package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.EmployeeDto;
import com.example.Web_Service.model.entity.Employee;
import com.example.Web_Service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> getListEmployeeDto () {
        List<Employee> list = employeeRepository.findAll();

        if (list.isEmpty()) {
            return List.of();
        }

        List<EmployeeDto> getListEmployeeDto = list.stream()
                .map (employee -> new EmployeeDto(
                        employee.getName(),
                        employee.getUsername(),
                        employee.getEmail(),
                        employee.getPhone(),
                        employee.getRole(),
                        employee.getSalary(),
                        employee.getBonus()
                ))
                .toList();

        return getListEmployeeDto;
    }
}