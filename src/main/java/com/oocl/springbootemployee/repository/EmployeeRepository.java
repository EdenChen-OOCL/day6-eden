package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(0, "Lily", 20, Gender.FEMALE, 8000));
        employees.add(new Employee(1, "Tom", 21, Gender.MALE, 9000));
        employees.add(new Employee(2, "Jacky", 19, Gender.MALE, 7000));
        //command +d
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee getEmployeeById(Integer id) {
        return employees.stream().
                filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Employee save(Employee employee) {
        employee.setId(employees.size());
        employees.add(employee);
        return employee;
    }

    public List<Employee> findByGender(Gender gender) {
        return employees.stream()
                .filter(employee -> Objects.equals(gender, employee.getGender()))
                .collect(Collectors.toList());
    }

    public Employee update(Employee updateEmployee) {
        Employee targetEmployee = employees.stream()
                .filter(employee -> Objects.equals(updateEmployee.getId(), employee.getId()))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(targetEmployee)) {
            return null;
        }
        targetEmployee.setAge(updateEmployee.getAge());
        targetEmployee.setSalary(updateEmployee.getSalary());
        return targetEmployee;
    }
}
