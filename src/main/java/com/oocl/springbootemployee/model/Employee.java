package com.oocl.springbootemployee.model;

import java.util.Objects;

public class Employee {
    private Integer id;
    private String name;
    private Integer age;
    private Gender gender;
    private double salary;

    public Employee(Integer id, String name, Integer age, Gender gender, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return Double.compare(salary, employee.salary) == 0 && Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(age,
                employee.age) && gender == employee.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, salary);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
