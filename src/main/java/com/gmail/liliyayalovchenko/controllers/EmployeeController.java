package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.dao.EmployeeDAO;
import com.gmail.liliyayalovchenko.domain.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeController {

    private EmployeeDAO employeeDAO;

    @Transactional
    public void createEmployee(Employee employee) {
        Set<Employee> employeeSet = new HashSet<>(employeeDAO.findAll());

        if (!employeeSet.contains(employee)) {
            employeeDAO.save(employee);
        } else {
            System.out.println("Employee already exist!");
        }

    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    @Transactional
    public Employee getEmployeeByName(String firstName, String secondName) {
        return employeeDAO.findByName(firstName, secondName);
    }

    @Transactional
    public void deleteEmployee(String firstName, String secondName) {
        employeeDAO.removeEmployee(firstName, secondName);
    }

    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
}
