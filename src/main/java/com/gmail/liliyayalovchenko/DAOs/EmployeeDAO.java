package com.gmail.liliyayalovchenko.DAOs;

import com.gmail.liliyayalovchenko.domainModel.Employee;

import java.util.List;

public interface EmployeeDAO {

    public void save(Employee employee);

    public Employee getById(Long id);

    public List<Employee> findAll();

    public Employee findByName(String firstName, String secondName);

    public void removeEmployee(String firstName, String secondName);
}
