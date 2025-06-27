package project;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class EmployeeManagementSystem extends JFrame {

    private JTabbedPane tabbedPane;

    public EmployeeManagementSystem() {
        setTitle("Employee Management System 2025");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Employee", new EmployeePanel());
        tabbedPane.addTab("Department", new DepartmentPanel());
        tabbedPane.addTab("Attendance", new AttendancePanel());
        tabbedPane.addTab("Payslip", new PayslipPanel());
        tabbedPane.addTab("Leave Request", new LeaveRequestPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // ***** Domain Classes *****

    public static class Employee {
        private int employeeID;
        private String firstName, lastName, birthDate, contactNumber, address, position, department;

        public Employee(int employeeID, String firstName, String lastName, String birthDate, String position, String department) {
            this.employeeID = employeeID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDate = birthDate;
            this.position = position;
            this.department = department;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmployeeInfo() {
            return "ID: " + employeeID + ", Name: " + getFullName() + ", BirthDate: " + birthDate +
                   ", Position: " + position + ", Department: " + department +
                   (contactNumber != null ? ", Contact: " + contactNumber : "") +
                   (address != null ? ", Address: " + address : "");
        }
    }

    public static class Department {
        private String name;
        private String managerName;

        public Department(String name, String managerName) {
            this.name = name;
            this.managerName = managerName;
        }

        public String getDepartmentInfo() {
            return "Department: " + name + ", Manager: " + managerName;
        }
    }

    public static class AttendanceRecord {
        private String date, timeIn, timeOut;
        private Employee employee;

        public AttendanceRecord(String date, String timeIn, String timeOut, Employee employee) {
            this.date = date;
            this.timeIn = timeIn;
            this.timeOut = timeOut;
            this.employee = employee;
        }

        public double calculateHoursWorked() {
            try {
                String[] in = timeIn.split(":");
                String[] out = timeOut.split(":");
                int inHours = Integer.parseInt(in[0]);
                int inMinutes = Integer.parseInt(in[1]);
                int outHours = Integer.parseInt(out[0]);
                int outMinutes = Integer.parseInt(out[1]);
                double worked = (outHours - inHours) + (outMinutes - inMinutes) / 60.0;
                return worked > 0 ? worked : 0;
            } catch (Exception e) {
                return 0;
            }
        }

        public boolean isLate() {
            try {
                String[] in = timeIn.split(":");
                int hour = Integer.parseInt(in[0]);
                int minute = Integer.parseInt(in[1]);
                // Late if after 9:00 AM
                return hour > 9 || (hour == 9 && minute > 0);
            } catch (Exception e) {
                return false;
            }
        }

        public String getAttendanceInfo() {
            return "Date: " + date + ", Employee: " + employee.getFullName() + ", Late: " + isLate();
        }
    }

    public static class PaySlip {
        private int paySlipID;
        private Employee employee;
        private double basicSalary = 5000, deductions = 500, netPay;
        private String periodCovered;

        public PaySlip(int paySlipID, Employee employee, String periodCovered) {
            this.paySlipID = paySlipID;
            this.employee = employee;
            this.periodCovered = periodCovered;
        }

        public void calculateNetPay() {
            netPay = basicSalary - deductions;
        }

        public String generatePaySlip() {
            calculateNetPay();
            return "Payslip ID: " + paySlipID + ", Employee: " + employee.getFullName() +
                   ", Net Pay: $" + String.format("%.2f", netPay) + ", Period: " + periodCovered;
        }
    }

    public static class LeaveRequest {
        private int requestID;
        private Employee employee;
        private String leaveType, startDate, endDate, status;

        public LeaveRequest(int requestID, Employee employee, String leaveType, String startDate, String endDate) {
            this.requestID = requestID;
            this.employee = employee;
            this.leaveType = leaveType;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = "Pending";
        }

        public void approveLeave() {
            this.status = "Approved";
        }

        public void rejectLeave() {
            this.status = "Rejected";
        }

        public long calculateLeaveDays() {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(startDate, formatter);
                LocalDate end = LocalDate.parse(endDate, formatter);
                return java.time.temporal.ChronoUnit.DAYS.between(start, end);
            } catch(Exception e) {
                return 0;
            }
        }

        public String getLeaveDetails() {
            return "Request ID: " + requestID + ", Employee: " + employee.getFullName() +
                   ", Type: " + leaveType + ", Start: " + startDate + ", End: " + endDate + ", Status: " + status;
        }
    }

    // ***** Panels (Views) *****

    class EmployeePanel extends JPanel {
        private JTextField idField, firstNameField, lastNameField, birthDateField,
                           positionField, departmentField, contactField, addressField;
        private JTextArea resultArea;
        private JButton saveButton;

        public EmployeePanel() {
            setLayout(new BorderLayout());
            JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
            formPanel.setBorder(BorderFactory.createTitledBorder("Employee Registration / Update"));
            idField = new JTextField();
            firstNameField = new JTextField();
            lastNameField = new JTextField();
            birthDateField = new JTextField("1990-01-01");
            positionField = new JTextField();
            departmentField = new JTextField();
            contactField = new JTextField();
            addressField = new JTextField();

            formPanel.add(new JLabel("Employee ID:"));
            formPanel.add(idField);
            formPanel.add(new JLabel("First Name:"));
            formPanel.add(firstNameField);
            formPanel.add(new JLabel("Last Name:"));
            formPanel.add(lastNameField);
            formPanel.add(new JLabel("Birth Date (yyyy-MM-dd):"));
            formPanel.add(birthDateField);
            formPanel.add(new JLabel("Position:"));
            formPanel.add(positionField);
            formPanel.add(new JLabel("Department:"));
            formPanel.add(departmentField);
            formPanel.add(new JLabel("Contact Number:"));
            formPanel.add(contactField);
            formPanel.add(new JLabel("Address:"));
            formPanel.add(addressField);

            saveButton = new JButton("Save / Update");
            JPanel btnPanel = new JPanel();
            btnPanel.add(saveButton);

            resultArea = new JTextArea(5, 40);
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea);

            add(formPanel, BorderLayout.NORTH);
            add(btnPanel, BorderLayout.CENTER);
            add(scrollPane, BorderLayout.SOUTH);

            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(idField.getText());
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String birthDate = birthDateField.getText();
                        String position = positionField.getText();
                        String department = departmentField.getText();
                        Employee emp = new Employee(id, firstName, lastName, birthDate, position, department);
                        emp.setContactNumber(contactField.getText());
                        emp.setAddress(addressField.getText());
                        resultArea.setText("Employee Saved:\n" + emp.getEmployeeInfo());
                    } catch (Exception ex) {
                        resultArea.setText("Error saving employee. Check your inputs.");
                    }
                }
            });
        }
    }

    class DepartmentPanel extends JPanel {
        private JComboBox<String> departmentCombo;
        private JTextField managerField;
        private JTextArea resultArea;
        private JButton showButton;

        public DepartmentPanel() {
            setLayout(new BorderLayout());
            JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            formPanel.setBorder(BorderFactory.createTitledBorder("Department Info"));
            String[] departments = {"IT", "HR", "Finance", "Marketing", "Operations", "Admin", "Sales"};
            departmentCombo = new JComboBox<>(departments);
            managerField = new JTextField();
            formPanel.add(new JLabel("Department:"));
            formPanel.add(departmentCombo);
            formPanel.add(new JLabel("Manager Name:"));
            formPanel.add(managerField);

            showButton = new JButton("Show Department Info");
            resultArea = new JTextArea(5, 40);
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea);

            add(formPanel, BorderLayout.NORTH);
            add(showButton, BorderLayout.CENTER);
            add(scrollPane, BorderLayout.SOUTH);

            showButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String dept = (String) departmentCombo.getSelectedItem();
                    String manager = managerField.getText();
                    Department department = new Department(dept, manager);
                    resultArea.setText(department.getDepartmentInfo());
                }
            });
        }
    }

    class AttendancePanel extends JPanel {
        private JTextField empIdField, timeInField, timeOutField, dateField;
        private JTextArea resultArea;
        private JButton logButton;

        public AttendancePanel() {
            setLayout(new BorderLayout());
            JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
            formPanel.setBorder(BorderFactory.createTitledBorder("Attendance Logging"));
            empIdField = new JTextField();
            dateField = new JTextField(LocalDate.now().toString());
            timeInField = new JTextField("09:00");
            timeOutField = new JTextField("17:00");
            formPanel.add(new JLabel("Employee ID:"));
            formPanel.add(empIdField);
            formPanel.add(new JLabel("Date (yyyy-MM-dd):"));
            formPanel.add(dateField);
            formPanel.add(new JLabel("Time In (HH:mm):"));
            formPanel.add(timeInField);
            formPanel.add(new JLabel("Time Out (HH:mm):"));
            formPanel.add(timeOutField);

            logButton = new JButton("Log Attendance");
            resultArea = new JTextArea(5, 40);
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea);

            add(formPanel, BorderLayout.NORTH);
            add(logButton, BorderLayout.CENTER);
            add(scrollPane, BorderLayout.SOUTH);

            logButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(empIdField.getText());
                        // For demo purposes, we create a dummy employee.
                        Employee emp = new Employee(id, "John", "Doe", "1990-01-01", "Employee", "IT");
                        AttendanceRecord attendance = new AttendanceRecord(dateField.getText(), timeInField.getText(),
                                timeOutField.getText(), emp);
                        double hours = attendance.calculateHoursWorked();
                        resultArea.setText("Attendance:\n" + attendance.getAttendanceInfo() +
                                "\nHours Worked: " + String.format("%.2f", hours));
                    } catch (Exception ex) {
                        resultArea.setText("Error logging attendance. Check your inputs.");
                    }
                }
            });
        }
    }

    class PayslipPanel extends JPanel {
        private JTextField empIdField, firstNameField, lastNameField, birthDateField, positionField, departmentField;
        private JTextArea resultArea;
        private JButton generateButton;

        public PayslipPanel() {
            setLayout(new BorderLayout());
            JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            formPanel.setBorder(BorderFactory.createTitledBorder("Payslip Generation"));
            empIdField = new JTextField();
            firstNameField = new JTextField();
            lastNameField = new JTextField();
            birthDateField = new JTextField("1990-01-01");
            positionField = new JTextField();
            departmentField = new JTextField();

            formPanel.add(new JLabel("Employee ID:"));
            formPanel.add(empIdField);
            formPanel.add(new JLabel("First Name:"));
            formPanel.add(firstNameField);
            formPanel.add(new JLabel("Last Name:"));
            formPanel.add(lastNameField);
            formPanel.add(new JLabel("Birth Date (yyyy-MM-dd):"));
            formPanel.add(birthDateField);
            formPanel.add(new JLabel("Position:"));
            formPanel.add(positionField);
            formPanel.add(new JLabel("Department:"));
            formPanel.add(departmentField);

            generateButton = new JButton("Generate Payslip");
            resultArea = new JTextArea(5, 40);
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea);

            add(formPanel, BorderLayout.NORTH);
            add(generateButton, BorderLayout.CENTER);
            add(scrollPane, BorderLayout.SOUTH);

            generateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(empIdField.getText());
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String birthDate = birthDateField.getText();
                        String position = positionField.getText();
                        String department = departmentField.getText();
                        Employee emp = new Employee(id, firstName, lastName, birthDate, position, department);
                        PaySlip payslip = new PaySlip(1, emp, "June 2025");
                        resultArea.setText("Payslip Generated:\n" + payslip.generatePaySlip() +
                                "\n" + emp.getEmployeeInfo());
                    } catch (Exception ex) {
                        resultArea.setText("Error generating payslip. Check your inputs.");
                    }
                }
            });
        }
    }

    class LeaveRequestPanel extends JPanel {
        private JTextField empIdField, leaveTypeField, startDateField, endDateField;
        private JTextArea resultArea;
        private JButton requestButton, approveButton;

        public LeaveRequestPanel() {
            setLayout(new BorderLayout());
            JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
            formPanel.setBorder(BorderFactory.createTitledBorder("Leave Request"));
            empIdField = new JTextField();
            leaveTypeField = new JTextField();
            startDateField = new JTextField(LocalDate.now().toString());
            endDateField = new JTextField(LocalDate.now().plusDays(5).toString());
            formPanel.add(new JLabel("Employee ID:"));
            formPanel.add(empIdField);
            formPanel.add(new JLabel("Leave Type:"));
            formPanel.add(leaveTypeField);
            formPanel.add(new JLabel("Start Date (yyyy-MM-dd):"));
            formPanel.add(startDateField);
            formPanel.add(new JLabel("End Date (yyyy-MM-dd):"));
            formPanel.add(endDateField);

            JPanel btnPanel = new JPanel();
            requestButton = new JButton("Request Leave");
            approveButton = new JButton("Approve Leave");
            btnPanel.add(requestButton);
            btnPanel.add(approveButton);

            resultArea = new JTextArea(5, 40);
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea);

            add(formPanel, BorderLayout.NORTH);
            add(btnPanel, BorderLayout.CENTER);
            add(scrollPane, BorderLayout.SOUTH);

            requestButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(empIdField.getText());
                        // For demo purposes, we use a dummy employee.
                        Employee emp = new Employee(id, "Jane", "Doe", "1990-01-01", "Employee", "IT");
                        LeaveRequest leave = new LeaveRequest(1, emp, leaveTypeField.getText(),
                                startDateField.getText(), endDateField.getText());
                        long days = leave.calculateLeaveDays();
                        resultArea.setText("Leave Requested:\n" + leave.getLeaveDetails() +
                                "\nLeave Days: " + days);
                    } catch (Exception ex) {
                        resultArea.setText("Error processing leave request. Check your inputs.");
                    }
                }
            });

            approveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(empIdField.getText());
                        Employee emp = new Employee(id, "Jane", "Doe", "1990-01-01", "Employee", "IT");
                        LeaveRequest leave = new LeaveRequest(1, emp, leaveTypeField.getText(),
                                startDateField.getText(), endDateField.getText());
                        leave.approveLeave();
                        long days = leave.calculateLeaveDays();
                        resultArea.setText("Leave Approved:\n" + leave.getLeaveDetails() +
                                "\nLeave Days: " + days);
                    } catch (Exception ex) {
                        resultArea.setText("Error approving leave. Check your inputs.");
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new EmployeeManagementSystem().setVisible(true);
            }
        });
    }
}
