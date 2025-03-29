/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.motorphproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

class Employee {
    private String empId;
    private String name;
    private String birthday;
    private double hourlyRate;
    private int hoursWorked;

    public Employee(String empId, String name, String birthday, double hourlyRate) {
        this.empId = empId;
        this.name = name;
        this.birthday = birthday;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = 0;
    }

    public void logHours(int hours) {
        this.hoursWorked = hours;
    }

    public double calculateGrossSalary() {
        return hoursWorked * hourlyRate;
    }

    public double calculateDeductions(double grossSalary) {
        double sss = grossSalary * 0.045;
        double philhealth = grossSalary * 0.03;
        double pagibig = 100;
        double tax = calculateTax(grossSalary);
        return sss + philhealth + pagibig + tax;
    }

    private double calculateTax(double grossSalary) {
        if (grossSalary <= 20833) {
            return 0;
        } else if (grossSalary < 33333) {
            return (grossSalary - 20833) * 0.20;
        } else if (grossSalary < 66667) {
            return 2500 + (grossSalary - 33333) * 0.25;
        } else if (grossSalary < 166667) {
            return 10833 + (grossSalary - 66667) * 0.30;
        } else if (grossSalary < 666667) {
            return 40833.33 + (grossSalary - 166667) * 0.32;
        } else {
            return 200833.33 + (grossSalary - 666667) * 0.35;
        }
    }

    public double calculateNetSalary() {
        double gross = calculateGrossSalary();
        return gross - calculateDeductions(gross);
    }

    public String getDetails() {
        return "Employee ID: " + empId + "\n" +
               "Name: " + name + "\n" +
               "Birthday: " + birthday + "\n" +
               "Hourly Rate: PHP " + String.format("%.2f", hourlyRate) + "\n" +
               "Total Hours Worked: " + hoursWorked + " hrs\n" +
               "Gross Weekly Salary: PHP " + String.format("%.2f", calculateGrossSalary()) + "\n" +
               "Net Weekly Salary: PHP " + String.format("%.2f", calculateNetSalary());
    }
}

public class MotorPhProject {
    public static void main(String[] args) {
        List<Employee> employees = readCSV("data.csv");

        JFrame frame = new JFrame("Employee Payroll System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 2));
        JComboBox<String> empDropdown = new JComboBox<>();
        JTextField hoursField = new JTextField();
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JButton calculateButton = new JButton("Calculate Salary");
        JScrollPane scrollPane = new JScrollPane(outputArea);

        for (Employee emp : employees) {
            empDropdown.addItem(emp.getDetails().split("\n")[1]);
        }

        panel.add(new JLabel("Select Employee:"));
        panel.add(empDropdown);
        panel.add(new JLabel("Hours Worked:"));
        panel.add(hoursField);
        panel.add(calculateButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        calculateButton.addActionListener(e -> {
            try {
                int selectedIndex = empDropdown.getSelectedIndex();
                int hours = Integer.parseInt(hoursField.getText().trim());
                employees.get(selectedIndex).logHours(hours);
                outputArea.setText(employees.get(selectedIndex).getDetails());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for hours worked.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    public static List<Employee> readCSV(String fileName) {
        List<Employee> employees = new ArrayList<>();
        
        File file = new File(fileName);

        System.out.println("Looking for file at: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                System.out.println("Reading line: " + line);

                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Handles quoted commas

                if (values.length < 19) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                try {
                    String empId = values[0].trim();
                    String fullName = values[2].trim() + " " + values[1].trim();
                    String birthday = values[3].trim();
                    double hourlyRate = Double.parseDouble(values[18].trim().replace(",", "").replace("\"", "")); // Fix comma & quotes

                    employees.add(new Employee(empId, fullName, birthday, hourlyRate));
                } catch (NumberFormatException ex) {
                    System.out.println("Error parsing hourly rate in line: " + line);
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return employees;
    }
}
