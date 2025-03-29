import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
        int total = 0;
        for (int hours : workHours) {
            total += hours;
        }
        return total;
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

public class PayrollSystemGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee Payroll System");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(8, 2));

        JLabel empIdLabel = new JLabel("Employee ID:");
        JTextField empIdField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel birthLabel = new JLabel("Birthday:");
        JTextField birthField = new JTextField();
        JLabel rateLabel = new JLabel("Hourly Rate:");
        JTextField rateField = new JTextField();
        JLabel hoursLabel = new JLabel("Hours Worked:");
        JTextField hoursField = new JTextField();
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JButton calculateButton = new JButton("Calculate Salary");

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empId = empIdField.getText();
                String name = nameField.getText();
                String birthday = birthField.getText();
                double hourlyRate = Double.parseDouble(rateField.getText());
                int hoursWorked = Integer.parseInt(hoursField.getText());

                Employee emp = new Employee(empId, name, birthday, hourlyRate);
                emp.logHours(hoursWorked);
                outputArea.setText(emp.getDetails());
            }
        });

        frame.add(empIdLabel);
        frame.add(empIdField);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(birthLabel);
        frame.add(birthField);
        frame.add(rateLabel);
        frame.add(rateField);
        frame.add(hoursLabel);
        frame.add(hoursField);
        frame.add(calculateButton);
        frame.add(outputArea);

        frame.setVisible(true);
    }
}
