package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(5, "Lily", 20, Gender.FEMALE, 8000));
        employees.add(new Employee(6, "Tom", 21, Gender.MALE, 9000));
        employees.add(new Employee(7, "Jacky", 19, Gender.MALE, 7000));
        //command +d
    }

    public List<Employee> getAll() {
        return employees;
    }

}
