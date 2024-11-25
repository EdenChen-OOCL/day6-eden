package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getAll() {

        return employeeRepository.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id) {
        return employeeRepository.getEmployeeById(id);
    }


    @GetMapping(params = {"gender"})
    public List<Employee> getByGender(@RequestParam Gender gender) {

        return employeeRepository.findByGender(gender);
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeRepository.update(employee);
    }
}
