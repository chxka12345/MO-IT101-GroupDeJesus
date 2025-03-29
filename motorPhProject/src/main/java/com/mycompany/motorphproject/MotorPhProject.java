/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.motorphproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

class Employee {
    private String empId;
    private String name;
    private String birthday;
    private double hourlyRate;
    private int hoursWorked;
    private double sssContribution;    

    public Employee(String empId, String name, String birthday, double hourlyRate, double sssContribution) {
        this.empId = empId;
        this.name = name;
        this.birthday = birthday;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = 0;
        this.sssContribution = sssContribution;
        
    }

    public void logHours(int hours) {
        this.hoursWorked = hours;
    }

    public double calculateGrossSalary() {
        return hoursWorked * hourlyRate;
    }

    public double calculateDeductions(double grossSalary) {
        double sss = sssContribution;
        double philhealth = grossSalary * 0.03;
        double pagibig = calculatepagibig(grossSalary);
        return sss + philhealth + pagibig;
    }
    public double calculatepagibig(double grossSalary){
        double pagibig = 0;
        if(grossSalary > 1500){
            pagibig = grossSalary * 0.02;
        }else{
            pagibig = grossSalary * 0.01;
        }
        return pagibig;
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
    public double calculationTax(double netSalary){
        double tax = 0;
        if(netSalary < 20832){
            tax = 0;
        }else if(netSalary > 20832 && netSalary < 33333){
            tax = (netSalary * 0.2);
        }else if(netSalary > 33333 && netSalary < 66667){
            tax = (netSalary * .25) + 2500;
        }else if(netSalary > 66667 && netSalary < 166667){
            tax = (netSalary * 0.3) + 10833;
        }else if(netSalary > 166667 && netSalary < 666667){
            tax = (netSalary * 0.32) + 40833.33;
        }else{
            tax = (netSalary * 0.35) + 200833.33;
        }
        return tax;
    }

    public String getDetails() {
        return "===========================\n" +
                "         EMPLOYEE PAYSLIP\n" +
                "===========================\n" +
                "Employee ID: " + empId + "\n" +
               "Name: " + name + "\n" +
               "Birthday: " + birthday + "\n" +
               "Hourly Rate: PHP " + String.format("%.2f", hourlyRate) + "\n" +
               "Total Hours Worked: " + hoursWorked + " hrs\n" + 
                "-------------------------------------------------\n" +
                "Gross Weekly Salary: PHP " + String.format("%.2f", calculateGrossSalary()) + "\n" +
                "-------------------------------------------------\n" +
                "Deductions:\n" +
                "   SSS Contribution: " + String.format("%.2f", sssContribution) + "\n" + 
                "   PhilHealth Contribution: " + String.format("%.2f", calculateGrossSalary() * 0.03) + "\n" +
                "   Pag-ibig Contribution: " + String.format("%.2f", calculatepagibig(calculateGrossSalary()))+ "\n" +
                "   Withholding Tax: " + String.format("%.2f", calculationTax(calculateNetSalary())) + "\n\n" + 
                "Total Deductions: " + String.format("%.2f", (sssContribution +(calculateGrossSalary() * 0.03)+calculatepagibig(calculateGrossSalary())+calculationTax(calculateNetSalary()))) + "\n" + 
                "-------------------------------------------------\n" +
               "Net Weekly Salary: PHP " + String.format("%.2f", calculateNetSalary() - calculationTax(calculateNetSalary())) + "\n" + 
                "===========================\n" +
                "=======End of Payslip========\n" +
                "===========================\n";
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
            empDropdown.addItem(emp.getDetails().split("\n")[4]);
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
    public static double calculateSSSContribution(double compensation) {
    double[][] sssTable = {
        {0, 3249.99, 135.00}, {3250, 3749.99, 157.50}, {3750, 4249.99, 180.00},
        {4250, 4749.99, 202.50}, {4750, 5249.99, 225.00}, {5250, 5749.99, 247.50},
        {5750, 6249.99, 270.00}, {6250, 6749.99, 292.50}, {6750, 7249.99, 315.00},
        {7250, 7749.99, 337.50}, {7750, 8249.99, 360.00}, {8250, 8749.99, 382.50},
        {8750, 9249.99, 405.00}, {9250, 9749.99, 427.50}, {9750, 10249.99, 450.00},
        {10250, 10749.99, 472.50}, {10750, 11249.99, 495.00}, {11250, 11749.99, 517.50},
        {11750, 12249.99, 540.00}, {12250, 12749.99, 562.50}, {12750, 13249.99, 585.00},
        {13250, 13749.99, 607.50}, {13750, 14249.99, 630.00}, {14250, 14749.99, 652.50},
        {14750, 15249.99, 675.00}, {15250, 15749.99, 697.50}, {15750, 16249.99, 720.00},
        {16250, 16749.99, 742.50}, {16750, 17249.99, 765.00}, {17250, 17749.99, 787.50},
        {17750, 18249.99, 810.00}, {18250, 18749.99, 832.50}, {18750, 19249.99, 855.00},
        {19250, 19749.99, 877.50}, {19750, 20249.99, 900.00}, {20250, 20749.99, 922.50},
        {20750, 21249.99, 945.00}, {21250, 21749.99, 967.50}, {21750, 22249.99, 990.00},
        {22250, 22749.99, 1012.50}, {22750, 23249.99, 1035.00}, {23250, 23749.99, 1057.50},
        {23750, 24249.99, 1080.00}, {24250, 24749.99, 1102.50}, {24750, Double.MAX_VALUE, 1125.00}
    };
    
    for (double[] range : sssTable) {
        if (compensation >= range[0] && compensation <= range[1]) {
            return range[2];
        }
    }
    return 0;
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
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Handles quoted commas

                if (values.length < 19) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                try {
                    String empId = values[0].trim();
                    String fullName = values[2].trim() + " " + values[1].trim();
                    String birthday = values[3].trim();
                    double hourlyRate = Double.parseDouble(values[18].trim().replace(",", "").replace("\"", ""));
                    double compensation = Double.parseDouble(values[18].trim().replace(",", "").replace("\"", ""));
                    double sssContribution = calculateSSSContribution(compensation);

                    employees.add(new Employee(empId, fullName, birthday, hourlyRate, sssContribution));
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
