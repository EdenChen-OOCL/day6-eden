package com.oocl.springbootemployee.model;

public class Employee {
    private Integer id;
    private String name;
    private Integer age;
    private Gender gender;
    private double salary;

    //alt + insert
    //command + n  > generate
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

    public void setId(int id) {
        this.id = id;
    }
}
