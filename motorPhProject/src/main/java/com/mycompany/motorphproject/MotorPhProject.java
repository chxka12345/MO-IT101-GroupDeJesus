/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.motorphproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MotorPhProject {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
class Employee {
    private String empId;
    private String name;
    private String birthday;
    private double hourlyRate;
    private ArrayList<Integer> workHours;

    public Employee(String empId, String name, String birthday, double hourlyRate) {
        this.empId = empId;
        this.name = name;
        this.birthday = birthday;
        this.hourlyRate = hourlyRate;
        this.workHours = new ArrayList<>();
    }

    public void logHours(int hours) {
        if (hours >= 0 && hours <= 24) {
            workHours.add(hours);
        } else {
            System.out.println("Invalid hours. Must be between 0 and 24.");
        }
    }

    public int totalHoursWorked() {
        return workHours.stream().mapToInt(Integer::intValue).sum();
    }

    public double calculateGrossSalary() {
        return totalHoursWorked() * hourlyRate;
    }

    public double calculateNetSalary() {
        double gross = calculateGrossSalary();
        double sss = gross * 0.045;
        double philhealth = gross * 0.03;
        double pagibig = 100;
        double tax = gross * 0.10;
        double totalDeductions = sss + philhealth + pagibig + tax;
        return gross - totalDeductions;
    }

    public String getDetails() {
        return "Employee ID: " + empId + "\n" +
               "Name: " + name + "\n" +
               "Birthday: " + birthday + "\n" +
               "Hourly Rate: PHP " + String.format("%.2f", hourlyRate) + "\n" +
               "Total Hours Worked: " + totalHoursWorked() + " hrs\n" +
               "Gross Weekly Salary: PHP " + String.format("%.2f", calculateGrossSalary()) + "\n" +
               "Net Weekly Salary: PHP " + String.format("%.2f", calculateNetSalary());
    }
}