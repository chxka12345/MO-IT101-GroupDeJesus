package com.mycompany.project;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.io.*;

/**
 * MotorPH Employee Management System
 * Enhanced version with comprehensive View and Create Record functionalities
 * Features: Employee Management, Department Management, Attendance Tracking, 
 * Payslip Generation, Leave Request Management with data persistence
 */
public class Project extends JFrame {

    private JTabbedPane tabbedPane;
    private EmployeeDataManager dataManager;

    public Project() {
        setTitle("MotorPH Employee Management System 2025");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dataManager = new EmployeeDataManager();
        initUI();
    }

    private void initUI() {
        // Set Look and Feel with custom colors
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            // Custom color scheme
            UIManager.put("control", new Color(240, 240, 240));
            UIManager.put("info", new Color(245, 245, 245));
            UIManager.put("nimbusBase", new Color(51, 98, 140));
            UIManager.put("nimbusAlertYellow", new Color(255, 220, 35));
            UIManager.put("nimbusDisabledText", new Color(142, 143, 145));
            UIManager.put("nimbusFocus", new Color(115, 164, 209));
            UIManager.put("nimbusGreen", new Color(176, 179, 50));
            UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
            UIManager.put("nimbusLightBackground", new Color(255, 255, 255));
            UIManager.put("nimbusOrange", new Color(191, 98, 4));
            UIManager.put("nimbusRed", new Color(169, 46, 34));
            UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new Color(57, 105, 138));
            UIManager.put("text", new Color(0, 0, 0));
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create custom header panel
        JPanel headerPanel = createHeaderPanel();
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.setBackground(new Color(245, 245, 245));
        tabbedPane.setTabPlacement(JTabbedPane.TOP);

        // Add tabs with better styling
        tabbedPane.addTab("👥 Employees", new EmployeePanel());
        tabbedPane.addTab("🏢 Departments", new DepartmentPanel());
        tabbedPane.addTab("⏰ Attendance", new AttendancePanel());
        tabbedPane.addTab("💰 Payroll", new PayslipPanel());
        tabbedPane.addTab("📋 Leave Requests", new LeaveRequestPanel());

        // Main layout
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        
        // Add menu bar
        setJMenuBar(createMenuBar());
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(51, 98, 140));
        headerPanel.setPreferredSize(new Dimension(0, 80));
        
        // Title section
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(51, 98, 140));
        
        JLabel titleLabel = new JLabel("MotorPH Employee Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Comprehensive HR Management Solution");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitleLabel.setForeground(new Color(200, 220, 240));
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(51, 98, 140));
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);
        
        titlePanel.add(textPanel);
        
        // Stats section
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statsPanel.setBackground(new Color(51, 98, 140));
        
        JLabel empCountLabel = new JLabel("Employees: " + dataManager.getEmployees().size());
        empCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        empCountLabel.setForeground(Color.WHITE);
        empCountLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel deptCountLabel = new JLabel("Departments: " + dataManager.getDepartments().size());
        deptCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deptCountLabel.setForeground(Color.WHITE);
        deptCountLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        statsPanel.add(empCountLabel);
        statsPanel.add(deptCountLabel);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(statsPanel, BorderLayout.EAST);
        
        return headerPanel;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportItem = new JMenuItem("Export Data");
        JMenuItem importItem = new JMenuItem("Import Data");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        exportItem.addActionListener(e -> dataManager.exportData());
        importItem.addActionListener(e -> dataManager.importData());
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(exportItem);
        fileMenu.add(importItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        return menuBar;
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "MotorPH Employee Management System\n" +
            "Version 2.0\n" +
            "Developed for comprehensive employee management\n" +
            "© 2025 MotorPH Corporation",
            "About",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // ***** Data Management Class *****
    class EmployeeDataManager {
        private List<Employee> employees;
        private List<Department> departments;
        private List<AttendanceRecord> attendanceRecords;
        private List<PaySlip> paySlips;
        private List<LeaveRequest> leaveRequests;

        public EmployeeDataManager() {
            employees = new ArrayList<>();
            departments = new ArrayList<>();
            attendanceRecords = new ArrayList<>();
            paySlips = new ArrayList<>();
            leaveRequests = new ArrayList<>();
            loadSampleData();
        }

        private void loadSampleData() {
            // Sample departments
            departments.add(new Department("IT", "John Smith"));
            departments.add(new Department("HR", "Sarah Johnson"));
            departments.add(new Department("Finance", "Michael Brown"));
            
            // Sample employees
            employees.add(new Employee(1001, "Alice", "Williams", "1990-05-15", "Software Developer", "IT"));
            employees.add(new Employee(1002, "Bob", "Davis", "1988-08-22", "HR Manager", "HR"));
            employees.add(new Employee(1003, "Carol", "Miller", "1992-12-10", "Accountant", "Finance"));
        }

        public void addEmployee(Employee employee) { employees.add(employee); }
        public void addDepartment(Department department) { departments.add(department); }
        public void addAttendanceRecord(AttendanceRecord record) { attendanceRecords.add(record); }
        public void addPaySlip(PaySlip paySlip) { paySlips.add(paySlip); }
        public void addLeaveRequest(LeaveRequest request) { leaveRequests.add(request); }

        public List<Employee> getEmployees() { return employees; }
        public List<Department> getDepartments() { return departments; }
        public List<AttendanceRecord> getAttendanceRecords() { return attendanceRecords; }
        public List<PaySlip> getPaySlips() { return paySlips; }
        public List<LeaveRequest> getLeaveRequests() { return leaveRequests; }

        public Employee findEmployeeById(int id) {
            return employees.stream().filter(emp -> emp.getEmployeeID() == id).findFirst().orElse(null);
        }

        public void exportData() {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter("motorph_data.txt"));
                writer.println("=== MotorPH Employee Data Export ===");
                writer.println("Export Date: " + LocalDateTime.now());
                writer.println("\n--- EMPLOYEES ---");
                for (Employee emp : employees) {
                    writer.println(emp.getEmployeeInfo());
                }
                writer.println("\n--- DEPARTMENTS ---");
                for (Department dept : departments) {
                    writer.println(dept.getDepartmentInfo());
                }
                writer.close();
                JOptionPane.showMessageDialog(null, "Data exported successfully to motorph_data.txt");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Export failed: " + e.getMessage());
            }
        }

        public void importData() {
            JOptionPane.showMessageDialog(null, "Import functionality will be implemented in future versions");
        }
    }

    // ***** Enhanced Domain Classes *****

    public static class Employee {
        private int employeeID;
        private String firstName, lastName, birthDate, contactNumber, address, position, department;
        private double salary;
        private LocalDateTime dateHired;

        public Employee(int employeeID, String firstName, String lastName, String birthDate, String position, String department) {
            this.employeeID = employeeID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDate = birthDate;
            this.position = position;
            this.department = department;
            this.salary = 25000.0; // Default salary
            this.dateHired = LocalDateTime.now();
        }

        // Getters and Setters
        public int getEmployeeID() { return employeeID; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getBirthDate() { return birthDate; }
        public String getPosition() { return position; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
        
        public void setSalary(double salary) { this.salary = salary; }
        public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
        public void setAddress(String address) { this.address = address; }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        public String getEmployeeInfo() {
            return String.format("ID: %d | Name: %s | Birth: %s | Position: %s | Department: %s | Salary: $%.2f%s%s",
                employeeID, getFullName(), birthDate, position, department, salary,
                (contactNumber != null ? " | Contact: " + contactNumber : ""),
                (address != null ? " | Address: " + address : ""));
        }

        public Object[] toTableRow() {
            return new Object[]{employeeID, getFullName(), position, department, 
                               String.format("$%.2f", salary), contactNumber != null ? contactNumber : "N/A"};
        }
    }

    public static class Department {
        private String name;
        private String managerName;
        private int employeeCount;
        private double budget;

        public Department(String name, String managerName) {
            this.name = name;
            this.managerName = managerName;
            this.employeeCount = 0;
            this.budget = 500000.0; // Default budget
        }

        public String getName() { return name; }
        public String getManagerName() { return managerName; }
        public int getEmployeeCount() { return employeeCount; }
        public double getBudget() { return budget; }
        
        public void setEmployeeCount(int count) { this.employeeCount = count; }
        public void setBudget(double budget) { this.budget = budget; }

        public String getDepartmentInfo() {
            return String.format("Department: %s | Manager: %s | Employees: %d | Budget: $%.2f",
                name, managerName, employeeCount, budget);
        }

        public Object[] toTableRow() {
            return new Object[]{name, managerName, employeeCount, String.format("$%.2f", budget)};
        }
    }

    public static class AttendanceRecord {
        private String date, timeIn, timeOut;
        private Employee employee;
        private boolean isLate;
        private double hoursWorked;

        public AttendanceRecord(String date, String timeIn, String timeOut, Employee employee) {
            this.date = date;
            this.timeIn = timeIn;
            this.timeOut = timeOut;
            this.employee = employee;
            this.hoursWorked = calculateHoursWorked();
            this.isLate = isLate();
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
                return hour > 9 || (hour == 9 && minute > 0);
            } catch (Exception e) {
                return false;
            }
        }

        public String getAttendanceInfo() {
            return String.format("Date: %s | Employee: %s | Hours: %.2f | Late: %s",
                date, employee.getFullName(), hoursWorked, isLate ? "Yes" : "No");
        }

        public Object[] toTableRow() {
            return new Object[]{date, employee.getFullName(), timeIn, timeOut, 
                               String.format("%.2f hrs", hoursWorked), isLate ? "Late" : "On Time"};
        }
    }

    public static class PaySlip {
        private int paySlipID;
        private Employee employee;
        private double basicSalary, allowances, deductions, netPay;
        private String periodCovered;
        private LocalDate generatedDate;

        public PaySlip(int paySlipID, Employee employee, String periodCovered) {
            this.paySlipID = paySlipID;
            this.employee = employee;
            this.periodCovered = periodCovered;
            this.basicSalary = employee.getSalary();
            this.allowances = basicSalary * 0.1; // 10% allowances
            this.deductions = basicSalary * 0.15; // 15% deductions (tax, insurance, etc.)
            this.generatedDate = LocalDate.now();
            calculateNetPay();
        }

        public void calculateNetPay() {
            netPay = basicSalary + allowances - deductions;
        }

        public String generatePaySlip() {
            return String.format("PaySlip ID: %d | Employee: %s | Period: %s | Net Pay: $%.2f | Generated: %s",
                paySlipID, employee.getFullName(), periodCovered, netPay, generatedDate);
        }

        public Object[] toTableRow() {
            return new Object[]{paySlipID, employee.getFullName(), periodCovered, 
                               String.format("$%.2f", basicSalary), String.format("$%.2f", netPay)};
        }

        public String getDetailedPaySlip() {
            return String.format(
                "=== MOTORPH PAYSLIP ===\n" +
                "Employee: %s (ID: %d)\n" +
                "Position: %s\n" +
                "Department: %s\n" +
                "Pay Period: %s\n" +
                "Generated: %s\n\n" +
                "EARNINGS:\n" +
                "Basic Salary: $%.2f\n" +
                "Allowances: $%.2f\n" +
                "Gross Pay: $%.2f\n\n" +
                "DEDUCTIONS:\n" +
                "Tax & Insurance: $%.2f\n\n" +
                "NET PAY: $%.2f",
                employee.getFullName(), employee.getEmployeeID(),
                employee.getPosition(), employee.getDepartment(),
                periodCovered, generatedDate,
                basicSalary, allowances, basicSalary + allowances,
                deductions, netPay);
        }
    }

    public static class LeaveRequest {
        private int requestID;
        private Employee employee;
        private String leaveType, startDate, endDate, reason, status;
        private LocalDateTime submittedDate;

        public LeaveRequest(int requestID, Employee employee, String leaveType, String startDate, String endDate, String reason) {
            this.requestID = requestID;
            this.employee = employee;
            this.leaveType = leaveType;
            this.startDate = startDate;
            this.endDate = endDate;
            this.reason = reason;
            this.status = "Pending";
            this.submittedDate = LocalDateTime.now();
        }

        public void approveLeave() { this.status = "Approved"; }
        public void rejectLeave() { this.status = "Rejected"; }
        public String getStatus() { return status; }

        public long calculateLeaveDays() {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(startDate, formatter);
                LocalDate end = LocalDate.parse(endDate, formatter);
                return java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1;
            } catch(Exception e) {
                return 1;
            }
        }

        public String getLeaveDetails() {
            return String.format("Request ID: %d | Employee: %s | Type: %s | Duration: %d days | Status: %s",
                requestID, employee.getFullName(), leaveType, calculateLeaveDays(), status);
        }

        public Object[] toTableRow() {
            return new Object[]{requestID, employee.getFullName(), leaveType, startDate, endDate, 
                               calculateLeaveDays() + " days", status};
        }
    }

    // ***** Enhanced UI Panels *****

    class EmployeePanel extends JPanel {
        private JTextField idField, firstNameField, lastNameField, birthDateField,
                           positionField, departmentField, contactField, addressField, salaryField;
        private JTable employeeTable;
        private DefaultTableModel tableModel;
        private JTextArea resultArea;

        public EmployeePanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Create form panel
            JPanel formPanel = createEmployeeForm();
            
            // Create table panel
            JPanel tablePanel = createEmployeeTable();
            
            // Create enhanced status panel
            JPanel statusMainPanel = new JPanel(new BorderLayout());
            statusMainPanel.setBackground(Color.WHITE);
            
            JPanel statusHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            statusHeaderPanel.setBackground(new Color(76, 175, 80));
            statusHeaderPanel.setPreferredSize(new Dimension(0, 35));
            
            JLabel statusHeaderLabel = new JLabel("System Status");
            statusHeaderLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            statusHeaderLabel.setForeground(Color.WHITE);
            statusHeaderPanel.add(statusHeaderLabel);
            
            resultArea = new JTextArea(3, 40);
            resultArea.setEditable(false);
            resultArea.setBackground(new Color(248, 249, 250));
            resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            resultArea.setText("Ready to manage employee records. Select an employee from the table to edit or create a new one.");
            
            JScrollPane resultScroll = new JScrollPane(resultArea);
            resultScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
            
            statusMainPanel.add(statusHeaderPanel, BorderLayout.NORTH);
            statusMainPanel.add(resultScroll, BorderLayout.CENTER);
            
            // Layout with proper sizing
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePanel);
            splitPane.setDividerLocation(450);
            splitPane.setResizeWeight(0.4);
            splitPane.setBorder(null);
            
            add(splitPane, BorderLayout.CENTER);
            add(statusMainPanel, BorderLayout.SOUTH);
            
            refreshEmployeeTable();
        }

        private JPanel createEmployeeForm() {
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(Color.WHITE);
            
            // Header section
            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            headerPanel.setBackground(new Color(51, 98, 140));
            headerPanel.setPreferredSize(new Dimension(0, 50));
            
            JLabel headerLabel = new JLabel("Employee Registration");
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            headerLabel.setForeground(Color.WHITE);
            headerPanel.add(headerLabel);
            
            // Form section
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.WEST;

            // Initialize styled fields
            idField = createStyledTextField(15);
            firstNameField = createStyledTextField(15);
            lastNameField = createStyledTextField(15);
            birthDateField = createStyledTextField("1990-01-01", 15);
            positionField = createStyledTextField(15);
            departmentField = createStyledTextField(15);
            contactField = createStyledTextField(15);
            addressField = createStyledTextField(15);
            salaryField = createStyledTextField("25000.00", 15);

            // Add components with styled labels
            gbc.gridx = 0; gbc.gridy = 0; formPanel.add(createStyledLabel("Employee ID:"), gbc);
            gbc.gridx = 1; formPanel.add(idField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1; formPanel.add(createStyledLabel("First Name:"), gbc);
            gbc.gridx = 1; formPanel.add(firstNameField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2; formPanel.add(createStyledLabel("Last Name:"), gbc);
            gbc.gridx = 1; formPanel.add(lastNameField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 3; formPanel.add(createStyledLabel("Birth Date:"), gbc);
            gbc.gridx = 1; formPanel.add(birthDateField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 4; formPanel.add(createStyledLabel("Position:"), gbc);
            gbc.gridx = 1; formPanel.add(positionField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 5; formPanel.add(createStyledLabel("Department:"), gbc);
            gbc.gridx = 1; formPanel.add(departmentField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 6; formPanel.add(createStyledLabel("Salary ($):"), gbc);
            gbc.gridx = 1; formPanel.add(salaryField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 7; formPanel.add(createStyledLabel("Contact:"), gbc);
            gbc.gridx = 1; formPanel.add(contactField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 8; formPanel.add(createStyledLabel("Address:"), gbc);
            gbc.gridx = 1; formPanel.add(addressField, gbc);

            // Buttons section
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            
            JButton saveButton = createStyledButton("Save Employee", new Color(76, 175, 80));
            JButton clearButton = createStyledButton("Clear Form", new Color(255, 152, 0));
            JButton deleteButton = createStyledButton("Delete Selected", new Color(244, 67, 54));
            
            saveButton.addActionListener(this::saveEmployee);
            clearButton.addActionListener(e -> clearForm());
            deleteButton.addActionListener(this::deleteEmployee);
            
            buttonPanel.add(saveButton);
            buttonPanel.add(clearButton);
            buttonPanel.add(deleteButton);
            
            gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
            formPanel.add(buttonPanel, gbc);

            mainPanel.add(headerPanel, BorderLayout.NORTH);
            mainPanel.add(formPanel, BorderLayout.CENTER);
            
            return mainPanel;
        }
        
        private JLabel createStyledLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setForeground(new Color(51, 51, 51));
            return label;
        }
        
        private JTextField createStyledTextField(int columns) {
            return createStyledTextField("", columns);
        }
        
        private JTextField createStyledTextField(String text, int columns) {
            JTextField field = new JTextField(text, columns);
            field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
            field.setBackground(Color.WHITE);
            return field;
        }
        
        private JButton createStyledButton(String text, Color bgColor) {
            JButton button = new JButton(text);
            button.setFont(new Font("Segoe UI", Font.BOLD, 11));
            button.setBackground(bgColor);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Hover effect
            button.addMouseListener(new MouseAdapter() {
                Color originalColor = bgColor;
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(originalColor.darker());
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(originalColor);
                }
            });
            
            return button;
        }

        private JPanel createEmployeeTable() {
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(Color.WHITE);
            
            // Header section
            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            headerPanel.setBackground(new Color(51, 98, 140));
            headerPanel.setPreferredSize(new Dimension(0, 50));
            
            JLabel headerLabel = new JLabel("Employee Records (" + dataManager.getEmployees().size() + " employees)");
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            headerLabel.setForeground(Color.WHITE);
            headerPanel.add(headerLabel);
            
            // Search panel
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            searchPanel.setBackground(Color.WHITE);
            searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JLabel searchLabel = new JLabel("Search:");
            searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            JTextField searchField = createStyledTextField(15);
            searchField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    filterEmployeeTable(searchField.getText());
                }
            });
            
            searchPanel.add(searchLabel);
            searchPanel.add(searchField);
            
            // Table section
            String[] columns = {"ID", "Full Name", "Position", "Department", "Salary", "Contact"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            employeeTable = new JTable(tableModel);
            employeeTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            employeeTable.setRowHeight(25);
            employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            employeeTable.setGridColor(new Color(230, 230, 230));
            employeeTable.setSelectionBackground(new Color(184, 207, 229));
            employeeTable.setSelectionForeground(Color.BLACK);
            
            // Header styling
            employeeTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            employeeTable.getTableHeader().setBackground(new Color(240, 240, 240));
            employeeTable.getTableHeader().setForeground(new Color(51, 51, 51));
            employeeTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(51, 98, 140)));
            
            employeeTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedEmployee();
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(employeeTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
            scrollPane.getViewport().setBackground(Color.WHITE);
            
            mainPanel.add(headerPanel, BorderLayout.NORTH);
            mainPanel.add(searchPanel, BorderLayout.CENTER);
            mainPanel.add(scrollPane, BorderLayout.SOUTH);
            
            return mainPanel;
        }
        
        private void filterEmployeeTable(String searchText) {
            if (searchText.trim().isEmpty()) {
                refreshEmployeeTable();
                return;
            }
            
            tableModel.setRowCount(0);
            String search = searchText.toLowerCase();
            for (Employee emp : dataManager.getEmployees()) {
                if (emp.getFullName().toLowerCase().contains(search) ||
                    emp.getPosition().toLowerCase().contains(search) ||
                    emp.getDepartment().toLowerCase().contains(search) ||
                    String.valueOf(emp.getEmployeeID()).contains(search)) {
                    tableModel.addRow(emp.toTableRow());
                }
            }
        }

        private void saveEmployee(ActionEvent e) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String birthDate = birthDateField.getText().trim();
                String position = positionField.getText().trim();
                String department = departmentField.getText().trim();
                double salary = Double.parseDouble(salaryField.getText().trim());

                if (firstName.isEmpty() || lastName.isEmpty() || position.isEmpty() || department.isEmpty()) {
                    throw new IllegalArgumentException("Please fill in all required fields");
                }

                // Check if employee ID already exists
                Employee existingEmp = dataManager.findEmployeeById(id);
                if (existingEmp != null) {
                    int choice = JOptionPane.showConfirmDialog(this,
                        "Employee ID " + id + " already exists. Do you want to update this employee?",
                        "Employee Exists",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (choice == JOptionPane.YES_OPTION) {
                        // Update existing employee
                        dataManager.getEmployees().remove(existingEmp);
                    } else {
                        resultArea.setText("Operation cancelled. Employee ID " + id + " already exists.");
                        return;
                    }
                }

                Employee emp = new Employee(id, firstName, lastName, birthDate, position, department);
                emp.setSalary(salary);
                emp.setContactNumber(contactField.getText().trim());
                emp.setAddress(addressField.getText().trim());

                dataManager.addEmployee(emp);
                refreshEmployeeTable();
                clearForm();
                resultArea.setText("SUCCESS: Employee " + emp.getFullName() + " has been saved with ID " + id);
            } catch (NumberFormatException ex) {
                resultArea.setText("ERROR: Please enter valid numbers for Employee ID and Salary");
            } catch (Exception ex) {
                resultArea.setText("ERROR: " + ex.getMessage());
            }
        }

        private void deleteEmployee(ActionEvent e) {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this employee?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dataManager.getEmployees().remove(selectedRow);
                    refreshEmployeeTable();
                    clearForm();
                    resultArea.setText("✅ Employee deleted successfully");
                }
            } else {
                resultArea.setText("❌ Please select an employee to delete");
            }
        }

        private void clearForm() {
            idField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            birthDateField.setText("1990-01-01");
            positionField.setText("");
            departmentField.setText("");
            salaryField.setText("25000.00");
            contactField.setText("");
            addressField.setText("");
        }

        private void loadSelectedEmployee() {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                Employee emp = dataManager.getEmployees().get(selectedRow);
                idField.setText(String.valueOf(emp.getEmployeeID()));
                firstNameField.setText(emp.getFirstName());
                lastNameField.setText(emp.getLastName());
                birthDateField.setText(emp.getBirthDate());
                positionField.setText(emp.getPosition());
                departmentField.setText(emp.getDepartment());
                salaryField.setText(String.valueOf(emp.getSalary()));
                contactField.setText(emp.contactNumber != null ? emp.contactNumber : "");
                addressField.setText(emp.address != null ? emp.address : "");
            }
        }

        private void refreshEmployeeTable() {
            tableModel.setRowCount(0);
            for (Employee emp : dataManager.getEmployees()) {
                tableModel.addRow(emp.toTableRow());
            }
        }
    }

    class DepartmentPanel extends JPanel {
        private JComboBox<String> departmentCombo;
        private JTextField managerField, budgetField;
        private JTable departmentTable;
        private DefaultTableModel tableModel;
        private JTextArea resultArea;

        public DepartmentPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JPanel formPanel = createDepartmentForm();
            JPanel tablePanel = createDepartmentTable();
            
            resultArea = new JTextArea(3, 40);
            resultArea.setEditable(false);
            resultArea.setBackground(new Color(245, 245, 245));
            resultArea.setBorder(BorderFactory.createTitledBorder("Status"));
            JScrollPane resultScroll = new JScrollPane(resultArea);
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePanel);
            splitPane.setDividerLocation(400);
            
            add(splitPane, BorderLayout.CENTER);
            add(resultScroll, BorderLayout.SOUTH);
            
            refreshDepartmentTable();
        }

        private JPanel createDepartmentForm() {
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Department Management"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            String[] departments = {"IT", "HR", "Finance", "Marketing", "Operations", "Admin", "Sales"};
            departmentCombo = new JComboBox<>(departments);
            managerField = new JTextField(20);
            budgetField = new JTextField("500000.00", 20);

            gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Department:"), gbc);
            gbc.gridx = 1; panel.add(departmentCombo, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Manager Name:"), gbc);
            gbc.gridx = 1; panel.add(managerField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Budget ($):"), gbc);
            gbc.gridx = 1; panel.add(budgetField, gbc);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton saveButton = new JButton("💾 Save Department");
            JButton showInfoButton = new JButton("📊 Show Info");
            
            saveButton.addActionListener(this::saveDepartment);
            showInfoButton.addActionListener(this::showDepartmentInfo);
            
            buttonPanel.add(saveButton);
            buttonPanel.add(showInfoButton);
            
            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
            panel.add(buttonPanel, gbc);

            return panel;
        }

        private JPanel createDepartmentTable() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Department Records"));
            
            String[] columns = {"Department", "Manager", "Employees", "Budget"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            departmentTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(departmentTable);
            panel.add(scrollPane, BorderLayout.CENTER);
            
            return panel;
        }

        private void saveDepartment(ActionEvent e) {
            try {
                String deptName = (String) departmentCombo.getSelectedItem();
                String manager = managerField.getText().trim();
                double budget = Double.parseDouble(budgetField.getText().trim());

                if (manager.isEmpty()) {
                    throw new IllegalArgumentException("Manager name is required");
                }

                Department dept = new Department(deptName, manager);
                dept.setBudget(budget);
                
                // Count employees in this department
                long empCount = dataManager.getEmployees().stream()
                    .filter(emp -> emp.getDepartment().equals(deptName))
                    .count();
                dept.setEmployeeCount((int) empCount);

                dataManager.addDepartment(dept);
                refreshDepartmentTable();
                resultArea.setText("✅ Department saved successfully: " + deptName);
            } catch (Exception ex) {
                resultArea.setText("❌ Error saving department: " + ex.getMessage());
            }
        }

        private void showDepartmentInfo(ActionEvent e) {
            String deptName = (String) departmentCombo.getSelectedItem();
            String manager = managerField.getText().trim();
            
            if (!manager.isEmpty()) {
                Department dept = new Department(deptName, manager);
                long empCount = dataManager.getEmployees().stream()
                    .filter(emp -> emp.getDepartment().equals(deptName))
                    .count();
                dept.setEmployeeCount((int) empCount);
                
                resultArea.setText("📊 " + dept.getDepartmentInfo());
            } else {
                resultArea.setText("❌ Please enter manager name first");
            }
        }

        private void refreshDepartmentTable() {
            tableModel.setRowCount(0);
            for (Department dept : dataManager.getDepartments()) {
                tableModel.addRow(dept.toTableRow());
            }
        }
    }

    class AttendancePanel extends JPanel {
        private JTextField empIdField, timeInField, timeOutField, dateField;
        private JTable attendanceTable;
        private DefaultTableModel tableModel;
        private JTextArea resultArea;

        public AttendancePanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JPanel formPanel = createAttendanceForm();
            JPanel tablePanel = createAttendanceTable();
            
            resultArea = new JTextArea(3, 40);
            resultArea.setEditable(false);
            resultArea.setBackground(new Color(245, 245, 245));
            resultArea.setBorder(BorderFactory.createTitledBorder("Status"));
            JScrollPane resultScroll = new JScrollPane(resultArea);
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePanel);
            splitPane.setDividerLocation(400);
            
            add(splitPane, BorderLayout.CENTER);
            add(resultScroll, BorderLayout.SOUTH);
            
            refreshAttendanceTable();
        }

        private JPanel createAttendanceForm() {
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Attendance Logging"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            empIdField = new JTextField(15);
            dateField = new JTextField(LocalDate.now().toString(), 15);
            timeInField = new JTextField("09:00", 15);
            timeOutField = new JTextField("17:00", 15);

            gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Employee ID:"), gbc);
            gbc.gridx = 1; panel.add(empIdField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Date:"), gbc);
            gbc.gridx = 1; panel.add(dateField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Time In:"), gbc);
            gbc.gridx = 1; panel.add(timeInField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Time Out:"), gbc);
            gbc.gridx = 1; panel.add(timeOutField, gbc);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton logButton = new JButton("⏰ Log Attendance");
            JButton quickTimeInButton = new JButton("🕘 Quick Time In");
            JButton quickTimeOutButton = new JButton("🕔 Quick Time Out");
            
            logButton.addActionListener(this::logAttendance);
            quickTimeInButton.addActionListener(e -> {
                timeInField.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            });
            quickTimeOutButton.addActionListener(e -> {
                timeOutField.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            });
            
            buttonPanel.add(logButton);
            buttonPanel.add(quickTimeInButton);
            buttonPanel.add(quickTimeOutButton);
            
            gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
            panel.add(buttonPanel, gbc);

            return panel;
        }

        private JPanel createAttendanceTable() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Attendance Records"));
            
            String[] columns = {"Date", "Employee", "Time In", "Time Out", "Hours", "Status"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            attendanceTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(attendanceTable);
            panel.add(scrollPane, BorderLayout.CENTER);
            
            return panel;
        }

        private void logAttendance(ActionEvent e) {
            try {
                int id = Integer.parseInt(empIdField.getText().trim());
                Employee emp = dataManager.findEmployeeById(id);
                
                if (emp == null) {
                    throw new IllegalArgumentException("Employee not found with ID: " + id);
                }

                AttendanceRecord attendance = new AttendanceRecord(
                    dateField.getText().trim(),
                    timeInField.getText().trim(),
                    timeOutField.getText().trim(),
                    emp
                );

                dataManager.addAttendanceRecord(attendance);
                refreshAttendanceTable();
                
                resultArea.setText(String.format("✅ Attendance logged for %s - Hours: %.2f %s",
                    emp.getFullName(), attendance.calculateHoursWorked(),
                    attendance.isLate() ? "(LATE)" : ""));
            } catch (Exception ex) {
                resultArea.setText("❌ Error logging attendance: " + ex.getMessage());
            }
        }

        private void refreshAttendanceTable() {
            tableModel.setRowCount(0);
            for (AttendanceRecord record : dataManager.getAttendanceRecords()) {
                tableModel.addRow(record.toTableRow());
            }
        }
    }

    class PayslipPanel extends JPanel {
        private JTextField empIdField, periodField;
        private JTable payslipTable;
        private DefaultTableModel tableModel;
        private JTextArea payslipPreview;

        public PayslipPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.add(createPayslipForm(), BorderLayout.WEST);
            topPanel.add(createPayslipPreview(), BorderLayout.CENTER);
            
            JPanel tablePanel = createPayslipTable();
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, tablePanel);
            splitPane.setDividerLocation(300);
            
            add(splitPane, BorderLayout.CENTER);
            
            refreshPayslipTable();
        }

        private JPanel createPayslipForm() {
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Generate Payslip"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            empIdField = new JTextField(15);
            periodField = new JTextField(LocalDate.now().getMonth() + " " + LocalDate.now().getYear(), 15);

            gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Employee ID:"), gbc);
            gbc.gridx = 1; panel.add(empIdField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Pay Period:"), gbc);
            gbc.gridx = 1; panel.add(periodField, gbc);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton generateButton = new JButton("💰 Generate Payslip");
            JButton previewButton = new JButton("👁️ Preview");
            
            generateButton.addActionListener(this::generatePayslip);
            previewButton.addActionListener(this::previewPayslip);
            
            buttonPanel.add(generateButton);
            buttonPanel.add(previewButton);
            
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            panel.add(buttonPanel, gbc);

            return panel;
        }

        private JPanel createPayslipPreview() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Payslip Preview"));
            
            payslipPreview = new JTextArea(12, 30);
            payslipPreview.setEditable(false);
            payslipPreview.setFont(new Font("Monospaced", Font.PLAIN, 12));
            payslipPreview.setBackground(new Color(250, 250, 250));
            
            JScrollPane scrollPane = new JScrollPane(payslipPreview);
            panel.add(scrollPane, BorderLayout.CENTER);
            
            return panel;
        }

        private JPanel createPayslipTable() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Generated Payslips"));
            
            String[] columns = {"Payslip ID", "Employee", "Period", "Basic Salary", "Net Pay"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            payslipTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(payslipTable);
            panel.add(scrollPane, BorderLayout.CENTER);
            
            return panel;
        }

        private void generatePayslip(ActionEvent e) {
            try {
                int empId = Integer.parseInt(empIdField.getText().trim());
                Employee emp = dataManager.findEmployeeById(empId);
                
                if (emp == null) {
                    throw new IllegalArgumentException("Employee not found with ID: " + empId);
                }

                int payslipId = dataManager.getPaySlips().size() + 1;
                PaySlip payslip = new PaySlip(payslipId, emp, periodField.getText().trim());
                
                dataManager.addPaySlip(payslip);
                refreshPayslipTable();
                
                payslipPreview.setText(payslip.getDetailedPaySlip());
                
                JOptionPane.showMessageDialog(this,
                    "✅ Payslip generated successfully for " + emp.getFullName(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error generating payslip: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        private void previewPayslip(ActionEvent e) {
            try {
                int empId = Integer.parseInt(empIdField.getText().trim());
                Employee emp = dataManager.findEmployeeById(empId);
                
                if (emp == null) {
                    throw new IllegalArgumentException("Employee not found with ID: " + empId);
                }

                PaySlip payslip = new PaySlip(0, emp, periodField.getText().trim());
                payslipPreview.setText(payslip.getDetailedPaySlip());
            } catch (Exception ex) {
                payslipPreview.setText("❌ Error creating preview: " + ex.getMessage());
            }
        }

        private void refreshPayslipTable() {
            tableModel.setRowCount(0);
            for (PaySlip payslip : dataManager.getPaySlips()) {
                tableModel.addRow(payslip.toTableRow());
            }
        }
    }

    class LeaveRequestPanel extends JPanel {
        private JTextField empIdField, requestIdField, startDateField, endDateField;
        private JComboBox<String> leaveTypeCombo;
        private JTextArea reasonArea;
        private JTable leaveTable;
        private DefaultTableModel tableModel;
        private JTextArea resultArea;

        public LeaveRequestPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JPanel formPanel = createLeaveRequestForm();
            JPanel tablePanel = createLeaveRequestTable();
            
            resultArea = new JTextArea(3, 40);
            resultArea.setEditable(false);
            resultArea.setBackground(new Color(245, 245, 245));
            resultArea.setBorder(BorderFactory.createTitledBorder("Status"));
            JScrollPane resultScroll = new JScrollPane(resultArea);
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePanel);
            splitPane.setDividerLocation(450);
            
            add(splitPane, BorderLayout.CENTER);
            add(resultScroll, BorderLayout.SOUTH);
            
            refreshLeaveRequestTable();
        }

        private JPanel createLeaveRequestForm() {
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Leave Request Management"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            requestIdField = new JTextField(15);
            empIdField = new JTextField(15);
            String[] leaveTypes = {"Annual", "Sick", "Personal", "Emergency", "Maternity", "Paternity"};
            leaveTypeCombo = new JComboBox<>(leaveTypes);
            startDateField = new JTextField(LocalDate.now().toString(), 15);
            endDateField = new JTextField(LocalDate.now().plusDays(3).toString(), 15);
            reasonArea = new JTextArea(3, 15);
            reasonArea.setBorder(BorderFactory.createLoweredBevelBorder());

            gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Request ID:"), gbc);
            gbc.gridx = 1; panel.add(requestIdField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Employee ID:"), gbc);
            gbc.gridx = 1; panel.add(empIdField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Leave Type:"), gbc);
            gbc.gridx = 1; panel.add(leaveTypeCombo, gbc);
            
            gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Start Date:"), gbc);
            gbc.gridx = 1; panel.add(startDateField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("End Date:"), gbc);
            gbc.gridx = 1; panel.add(endDateField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Reason:"), gbc);
            gbc.gridx = 1; panel.add(new JScrollPane(reasonArea), gbc);

            JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            JButton requestButton = new JButton("📋 Submit Request");
            JButton approveButton = new JButton("✅ Approve");
            JButton rejectButton = new JButton("❌ Reject");
            JButton clearButton = new JButton("🗑️ Clear");
            
            requestButton.addActionListener(this::submitLeaveRequest);
            approveButton.addActionListener(e -> updateLeaveStatus("Approved"));
            rejectButton.addActionListener(e -> updateLeaveStatus("Rejected"));
            clearButton.addActionListener(e -> clearForm());
            
            buttonPanel.add(requestButton);
            buttonPanel.add(approveButton);
            buttonPanel.add(rejectButton);
            buttonPanel.add(clearButton);
            
            gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
            panel.add(buttonPanel, gbc);

            return panel;
        }

        private JPanel createLeaveRequestTable() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Leave Request Records"));
            
            String[] columns = {"Request ID", "Employee", "Type", "Start Date", "End Date", "Duration", "Status"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            leaveTable = new JTable(tableModel);
            leaveTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            leaveTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedLeaveRequest();
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(leaveTable);
            panel.add(scrollPane, BorderLayout.CENTER);
            
            return panel;
        }

        private void submitLeaveRequest(ActionEvent e) {
            try {
                int requestId = Integer.parseInt(requestIdField.getText().trim());
                int empId = Integer.parseInt(empIdField.getText().trim());
                Employee emp = dataManager.findEmployeeById(empId);
                
                if (emp == null) {
                    throw new IllegalArgumentException("Employee not found with ID: " + empId);
                }

                String leaveType = (String) leaveTypeCombo.getSelectedItem();
                String startDate = startDateField.getText().trim();
                String endDate = endDateField.getText().trim();
                String reason = reasonArea.getText().trim();

                LeaveRequest request = new LeaveRequest(requestId, emp, leaveType, startDate, endDate, reason);
                dataManager.addLeaveRequest(request);
                refreshLeaveRequestTable();
                clearForm();
                
                resultArea.setText(String.format("✅ Leave request submitted for %s - %d days",
                    emp.getFullName(), request.calculateLeaveDays()));
            } catch (Exception ex) {
                resultArea.setText("❌ Error submitting leave request: " + ex.getMessage());
            }
        }

        private void updateLeaveStatus(String status) {
            int selectedRow = leaveTable.getSelectedRow();
            if (selectedRow >= 0) {
                LeaveRequest request = dataManager.getLeaveRequests().get(selectedRow);
                if (status.equals("Approved")) {
                    request.approveLeave();
                } else {
                    request.rejectLeave();
                }
                refreshLeaveRequestTable();
                resultArea.setText("✅ Leave request " + status.toLowerCase() + " for " + 
                    request.employee.getFullName());
            } else {
                resultArea.setText("❌ Please select a leave request to update");
            }
        }

        private void loadSelectedLeaveRequest() {
            int selectedRow = leaveTable.getSelectedRow();
            if (selectedRow >= 0) {
                LeaveRequest request = dataManager.getLeaveRequests().get(selectedRow);
                requestIdField.setText(String.valueOf(request.requestID));
                empIdField.setText(String.valueOf(request.employee.getEmployeeID()));
                leaveTypeCombo.setSelectedItem(request.leaveType);
                startDateField.setText(request.startDate);
                endDateField.setText(request.endDate);
                reasonArea.setText(request.reason != null ? request.reason : "");
            }
        }

        private void clearForm() {
            requestIdField.setText("");
            empIdField.setText("");
            leaveTypeCombo.setSelectedIndex(0);
            startDateField.setText(LocalDate.now().toString());
            endDateField.setText(LocalDate.now().plusDays(3).toString());
            reasonArea.setText("");
        }

        private void refreshLeaveRequestTable() {
            tableModel.setRowCount(0);
            for (LeaveRequest request : dataManager.getLeaveRequests()) {
                tableModel.addRow(request.toTableRow());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Project().setVisible(true);
        });
    }
}
