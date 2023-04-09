package com.example.employeevolot.Repository;

import com.example.employeevolot.Models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    public List<Employee> findByNameContaining(String name);
    Employee findByUsername(String username);
    Employee findByName(String name);
}
