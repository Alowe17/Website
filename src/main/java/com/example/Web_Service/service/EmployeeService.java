package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.adminDto.CreateNewEmployeeRequestDto;
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

    public List<CreateNewEmployeeRequestDto.EmployeeDto> getListEmployeeDto () {
        List<Employee> list = employeeRepository.findAll();

        if (list.isEmpty()) {
            return List.of();
        }

        List<CreateNewEmployeeRequestDto.EmployeeDto> getListEmployeeDto = list.stream()
                .map (employee -> new CreateNewEmployeeRequestDto.EmployeeDto(
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

    public String validator (String username, String email, String phone) {
        if (employeeRepository.findByUsername(username).isPresent()) {
            return "NPC с никнеймом " + username + " уже существует!";
        }

        if (employeeRepository.findByEmail(email).isPresent()) {
            return "NPC с такой почтой " + email + " уже существует!";
        }

        if (employeeRepository.findByPhone(phone).isPresent()) {
            return "NPC с таким номером " + phone + " уже существует!";
        }

        return null;
    }

    public String createNewNpc (Employee employeeNew) {
        Employee employee = employeeRepository.save(employeeNew);

        return null;
    }

    public Employee getEmployee (String username) {
        return employeeRepository.findByUsername(username).orElse(null);
    }

    public String updateEmployeeData (Employee employee) {
        employeeRepository.save(employee);

        return null;
    }
}