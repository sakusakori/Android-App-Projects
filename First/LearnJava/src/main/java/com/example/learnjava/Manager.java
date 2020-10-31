package com.example.learnjava;

public class Manager extends Employee {
    @Override
    public int getSalary() {
        return super.getSalary() + 1000;
    }

}
