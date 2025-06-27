package com.mycompany.project;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class Project extends JFrame {
    // Modern Color Scheme (matching web app)
    private static final Color PRIMARY_COLOR = new Color(79, 70, 229);      // Indigo
    private static final Color PRIMARY_HOVER = new Color(67, 56, 202);      // Darker Indigo
    private static final Color SECONDARY_COLOR = new Color(249, 250, 251);  // Light Gray
    private static final Color ACCENT_COLOR = new Color(34, 197, 94);       // Green
    private static final Color DANGER_COLOR = new Color(239, 68, 68);       // Red
    private static final Color WARNING_COLOR = new Color(245, 158, 11);     // Amber
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);        // Dark Gray
    private static final Color TEXT_SECONDARY = new Color(107, 114, 128);   // Medium Gray
    private static final Color BORDER_COLOR = new Color(229, 231, 235);     // Light Border
    private static final Color CARD_BG = Color.WHITE;
    
    // Data Storage
    private DataManager dataManager;
    private JTabbedPane tabbedPane;
    private JLabel timeLabel;
    private Timer clockTimer;
    
    public Project() {
        dataManager = new DataManager();
        initializeComponents();
        setupUI();
        startClock();
        setVisible(true);
    }
    
    private void initializeComponents() {
        setTitle("MotorPH Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setupUI() {
        // Header Panel
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Main Content with Tabs
        tabbedPane = createTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        
        // Status Bar
        add(createStatusBar(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD_BG);
        header.setBorder(new CompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(16, 24, 16, 24)
        ));
        header.setPreferredSize(new Dimension(0, 80));
        
        // Left side - Logo and title
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setBackground(CARD_BG);
        
        // Logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(PRIMARY_COLOR);
        logoPanel.setPreferredSize(new Dimension(40, 40));
        logoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        JLabel logoLabel = new JLabel("M", SwingConstants.CENTER);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoPanel.add(logoLabel);
        
        // Title
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(CARD_BG);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        
        JLabel titleLabel = new JLabel("MotorPH");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        JLabel subtitleLabel = new JLabel("Employee Management System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        
        leftPanel.add(logoPanel);
        leftPanel.add(titlePanel);
        
        // Right side - Time and user info
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        rightPanel.setBackground(CARD_BG);
        
        // Time display
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timeLabel.setForeground(TEXT_SECONDARY);
        
        // User avatar
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        userPanel.setBackground(CARD_BG);
        
        JPanel avatar = new JPanel();
        avatar.setBackground(SECONDARY_COLOR);
        avatar.setPreferredSize(new Dimension(32, 32));
        avatar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
        JLabel avatarLabel = new JLabel("A", SwingConstants.CENTER);
        avatarLabel.setForeground(TEXT_SECONDARY);
        avatarLabel.setFont(new Font("Arial", Font.BOLD, 12));
        avatar.add(avatarLabel);
        
        JLabel userLabel = new JLabel("Admin User");
        userLabel.setFont(new Font("Arial", Font.MEDIUM, 14));
        userLabel.setForeground(TEXT_PRIMARY);
        
        userPanel.add(avatar);
        userPanel.add(userLabel);
        
        rightPanel.add(timeLabel);
        rightPanel.add(userPanel);
        
        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);
        
        return header;
    }
    
    private JTabbedPane createTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.PLAIN, 14));
        tabs.setBackground(SECONDARY_COLOR);
        
        // Add tabs with modern styling
        tabs.addTab("👥 Employee", new EmployeePanel());
        tabs.addTab("🏢 Department", new DepartmentPanel());
        tabs.addTab("🕒 Attendance", new AttendancePanel());
        tabs.addTab("💰 Payslip", new PayslipPanel());
        tabs.addTab("📋 Leave Request", new LeaveRequestPanel());
        
        return tabs;
    }
    
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(SECONDARY_COLOR);
        statusBar.setBorder(new CompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        statusBar.setPreferredSize(new Dimension(0, 32));
        
        JLabel statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(TEXT_SECONDARY);
        
        statusBar.add(statusLabel, BorderLayout.WEST);
        
        return statusBar;
    }
    
    private void startClock() {
        clockTimer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            String timeString = now.format(DateTimeFormatter.ofPattern("h:mm a"));
            timeLabel.setText("🕒 " + timeString);
        });
        clockTimer.start();
    }
    
    // Modern Button Factory
    private JButton createModernButton(String text, Color bgColor, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.MEDIUM, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        if (listener != null) {
            button.addActionListener(listener);
        }
        
        return button;
    }
    
    // Modern Text Field Factory
    private JTextField createModernTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }
    
    // Modern Table Factory
    private JTable createModernTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(40);
        table.setSelectionBackground(PRIMARY_COLOR.brighter());
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(BORDER_COLOR);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(SECONDARY_COLOR);
        header.setForeground(TEXT_PRIMARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        
        return table;
    }
    
    // Employee Panel
    class EmployeePanel extends JPanel {
        private JTextField idField, empIdField, nameField, positionField, departmentField, salaryField, searchField;
        private JTable employeeTable;
        private DefaultTableModel tableModel;
        private JTextArea statusArea;
        
        public EmployeePanel() {
            setLayout(new BorderLayout(16, 16));
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Create main content
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPane.setLeftComponent(createEmployeeForm());
            splitPane.setRightComponent(createEmployeeTable());
            splitPane.setDividerLocation(500);
            splitPane.setBorder(null);
            
            add(splitPane, BorderLayout.CENTER);
            add(createEmployeeControls(), BorderLayout.SOUTH);
            
            refreshEmployeeTable();
        }
        
        private JPanel createEmployeeForm() {
            JPanel formCard = new JPanel(new BorderLayout());
            formCard.setBackground(CARD_BG);
            formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
            
            // Form header
            JLabel headerLabel = new JLabel("Employee Information");
            headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
            headerLabel.setForeground(TEXT_PRIMARY);
            headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            
            // Form fields
            JPanel fieldsPanel = new JPanel(new GridBagLayout());
            fieldsPanel.setBackground(CARD_BG);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 0, 8, 16);
            gbc.anchor = GridBagConstraints.WEST;
            
            // Initialize fields
            idField = createModernTextField(15);
            empIdField = createModernTextField(15);
            nameField = createModernTextField(15);
            positionField = createModernTextField(15);
            departmentField = createModernTextField(15);
            salaryField = createModernTextField(15);
            
            // Add form fields
            String[] labels = {"ID:", "Employee ID:", "Full Name:", "Position:", "Department:", "Salary:"};
            JTextField[] fields = {idField, empIdField, nameField, positionField, departmentField, salaryField};
            
            for (int i = 0; i < labels.length; i++) {
                gbc.gridx = 0; gbc.gridy = i;
                JLabel label = new JLabel(labels[i]);
                label.setFont(new Font("Arial", Font.MEDIUM, 14));
                label.setForeground(TEXT_PRIMARY);
                fieldsPanel.add(label, gbc);
                
                gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
                fieldsPanel.add(fields[i], gbc);
                gbc.fill = GridBagConstraints.NONE;
            }
            
            // Action buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 16));
            buttonPanel.setBackground(CARD_BG);
            
            buttonPanel.add(createModernButton("💾 Save", ACCENT_COLOR, this::saveEmployee));
            buttonPanel.add(createModernButton("✏️ Update", WARNING_COLOR, this::updateEmployee));
            buttonPanel.add(createModernButton("🗑️ Delete", DANGER_COLOR, this::deleteEmployee));
            buttonPanel.add(createModernButton("🗞️ Clear", TEXT_SECONDARY, this::clearEmployeeForm));
            
            formCard.add(headerLabel, BorderLayout.NORTH);
            formCard.add(fieldsPanel, BorderLayout.CENTER);
            formCard.add(buttonPanel, BorderLayout.SOUTH);
            
            return formCard;
        }
        
        private JPanel createEmployeeTable() {
            JPanel tableCard = new JPanel(new BorderLayout());
            tableCard.setBackground(CARD_BG);
            tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
            
            // Table header with search
            JPanel tableHeader = new JPanel(new BorderLayout());
            tableHeader.setBackground(CARD_BG);
            
            JLabel tableTitle = new JLabel("Employee Records");
            tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
            tableTitle.setForeground(TEXT_PRIMARY);
            
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            searchPanel.setBackground(CARD_BG);
            searchField = createModernTextField(20);
            searchField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    searchEmployees();
                }
            });
            
            JLabel searchLabel = new JLabel("🔍 Search:");
            searchLabel.setFont(new Font("Arial", Font.MEDIUM, 14));
            searchLabel.setForeground(TEXT_SECONDARY);
            
            searchPanel.add(searchLabel);
            searchPanel.add(searchField);
            
            tableHeader.add(tableTitle, BorderLayout.WEST);
            tableHeader.add(searchPanel, BorderLayout.EAST);
            tableHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
            
            // Table
            String[] columns = {"ID", "Emp ID", "Name", "Position", "Department", "Salary"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            employeeTable = createModernTable(tableModel);
            employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            employeeTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedEmployee();
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(employeeTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
            
            tableCard.add(tableHeader, BorderLayout.NORTH);
            tableCard.add(scrollPane, BorderLayout.CENTER);
            
            return tableCard;
        }
        
        private JPanel createEmployeeControls() {
            JPanel controlsPanel = new JPanel(new BorderLayout());
            controlsPanel.setBackground(SECONDARY_COLOR);
            controlsPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
            
            // CSV Controls
            JPanel csvPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            csvPanel.setBackground(SECONDARY_COLOR);
            
            csvPanel.add(createModernButton("📤 Export CSV", PRIMARY_COLOR, this::exportToCSV));
            csvPanel.add(createModernButton("📥 Import CSV", PRIMARY_COLOR, this::importFromCSV));
            
            // Status area
            statusArea = new JTextArea(2, 50);
            statusArea.setEditable(false);
            statusArea.setBackground(SECONDARY_COLOR);
            statusArea.setForeground(TEXT_SECONDARY);
            statusArea.setFont(new Font("Arial", Font.PLAIN, 12));
            statusArea.setBorder(BorderFactory.createTitledBorder("Status"));
            
            controlsPanel.add(csvPanel, BorderLayout.WEST);
            controlsPanel.add(statusArea, BorderLayout.CENTER);
            
            return controlsPanel;
        }
        
        private void saveEmployee(ActionEvent e) {
            try {
                if (empIdField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Employee ID and Name are required");
                }
                
                Employee employee = new Employee(
                    dataManager.getNextEmployeeId(),
                    Integer.parseInt(empIdField.getText().trim()),
                    nameField.getText().trim(),
                    positionField.getText().trim(),
                    departmentField.getText().trim(),
                    Double.parseDouble(salaryField.getText().trim())
                );
                
                dataManager.addEmployee(employee);
                refreshEmployeeTable();
                clearEmployeeForm(null);
                statusArea.setText("Employee saved successfully: " + employee.getFullName());
            } catch (Exception ex) {
                statusArea.setText("Error saving employee: " + ex.getMessage());
            }
        }
        
        private void updateEmployee(ActionEvent e) {
            try {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow < 0) {
                    throw new IllegalArgumentException("Please select an employee to update");
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                Employee employee = new Employee(
                    id,
                    Integer.parseInt(empIdField.getText().trim()),
                    nameField.getText().trim(),
                    positionField.getText().trim(),
                    departmentField.getText().trim(),
                    Double.parseDouble(salaryField.getText().trim())
                );
                
                dataManager.updateEmployee(id, employee);
                refreshEmployeeTable();
                clearEmployeeForm(null);
                statusArea.setText("Employee updated successfully: " + employee.getFullName());
            } catch (Exception ex) {
                statusArea.setText("Error updating employee: " + ex.getMessage());
            }
        }
        
        private void deleteEmployee(ActionEvent e) {
            try {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow < 0) {
                    throw new IllegalArgumentException("Please select an employee to delete");
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                Employee employee = dataManager.findEmployeeById(id);
                
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete employee: " + employee.getFullName() + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dataManager.deleteEmployee(id);
                    refreshEmployeeTable();
                    clearEmployeeForm(null);
                    statusArea.setText("Employee deleted successfully");
                }
            } catch (Exception ex) {
                statusArea.setText("Error deleting employee: " + ex.getMessage());
            }
        }
        
        private void clearEmployeeForm(ActionEvent e) {
            idField.setText("");
            empIdField.setText("");
            nameField.setText("");
            positionField.setText("");
            departmentField.setText("");
            salaryField.setText("");
            statusArea.setText("Form cleared");
        }
        
        private void loadSelectedEmployee() {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
                idField.setText(model.getValueAt(selectedRow, 0).toString());
                empIdField.setText(model.getValueAt(selectedRow, 1).toString());
                nameField.setText(model.getValueAt(selectedRow, 2).toString());
                positionField.setText(model.getValueAt(selectedRow, 3).toString());
                departmentField.setText(model.getValueAt(selectedRow, 4).toString());
                salaryField.setText(model.getValueAt(selectedRow, 5).toString().replace("₱", "").replace(",", ""));
            }
        }
        
        private void searchEmployees() {
            String searchTerm = searchField.getText().toLowerCase().trim();
            if (searchTerm.isEmpty()) {
                refreshEmployeeTable();
                return;
            }
            
            tableModel.setRowCount(0);
            for (Employee emp : dataManager.getEmployees()) {
                if (emp.getFullName().toLowerCase().contains(searchTerm) ||
                    emp.getPosition().toLowerCase().contains(searchTerm) ||
                    emp.getDepartment().toLowerCase().contains(searchTerm) ||
                    String.valueOf(emp.getEmployeeID()).contains(searchTerm)) {
                    tableModel.addRow(emp.toTableRow());
                }
            }
        }
        
        private void refreshEmployeeTable() {
            tableModel.setRowCount(0);
            for (Employee emp : dataManager.getEmployees()) {
                tableModel.addRow(emp.toTableRow());
            }
        }
        
        private void exportToCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Export Employees to CSV");
                fileChooser.setSelectedFile(new File("employees.csv"));
                
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                        // Write header
                        writer.println("ID,EmployeeID,Name,Position,Department,Salary");
                        
                        // Write data
                        for (Employee emp : dataManager.getEmployees()) {
                            writer.printf("%d,%d,\"%s\",\"%s\",\"%s\",%.2f%n",
                                emp.getId(), emp.getEmployeeID(), emp.getFullName(),
                                emp.getPosition(), emp.getDepartment(), emp.getSalary());
                        }
                    }
                    statusArea.setText("Employees exported to CSV successfully: " + file.getName());
                }
            } catch (Exception ex) {
                statusArea.setText("Error exporting to CSV: " + ex.getMessage());
            }
        }
        
        private void importFromCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Import Employees from CSV");
                
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    int importCount = 0;
                    
                    try (Scanner scanner = new Scanner(file)) {
                        // Skip header
                        if (scanner.hasNextLine()) {
                            scanner.nextLine();
                        }
                        
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            String[] parts = line.split(",");
                            
                            if (parts.length >= 6) {
                                Employee employee = new Employee(
                                    dataManager.getNextEmployeeId(),
                                    Integer.parseInt(parts[1]),
                                    parts[2].replace("\"", ""),
                                    parts[3].replace("\"", ""),
                                    parts[4].replace("\"", ""),
                                    Double.parseDouble(parts[5])
                                );
                                dataManager.addEmployee(employee);
                                importCount++;
                            }
                        }
                    }
                    
                    refreshEmployeeTable();
                    statusArea.setText("Imported " + importCount + " employees from CSV successfully");
                }
            } catch (Exception ex) {
                statusArea.setText("Error importing from CSV: " + ex.getMessage());
            }
        }
    }
    
    // Data Models
    class Employee {
        int id, employeeID;
        String fullName, position, department;
        double salary;
        
        public Employee(int id, int employeeID, String fullName, String position, String department, double salary) {
            this.id = id;
            this.employeeID = employeeID;
            this.fullName = fullName;
            this.position = position;
            this.department = department;
            this.salary = salary;
        }
        
        public int getId() { return id; }
        public int getEmployeeID() { return employeeID; }
        public String getFullName() { return fullName; }
        public String getPosition() { return position; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
        
        public Object[] toTableRow() {
            return new Object[]{id, employeeID, fullName, position, department, String.format("₱%.2f", salary)};
        }
    }
    
    class Department extends JPanel {
        // Department panel implementation would go here
        public Department() {
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JLabel label = new JLabel("Department Management - Coming Soon");
            label.setFont(new Font("Arial", Font.BOLD, 18));
            label.setForeground(TEXT_PRIMARY);
            add(label);
        }
    }
    
    class DepartmentPanel extends JPanel {
        public DepartmentPanel() {
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JLabel label = new JLabel("Department Management - Available in Full Version");
            label.setFont(new Font("Arial", Font.BOLD, 18));
            label.setForeground(TEXT_PRIMARY);
            add(label);
        }
    }
    
    class AttendancePanel extends JPanel {
        public AttendancePanel() {
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JLabel label = new JLabel("Attendance Management - Available in Full Version");
            label.setFont(new Font("Arial", Font.BOLD, 18));
            label.setForeground(TEXT_PRIMARY);
            add(label);
        }
    }
    
    class PayslipPanel extends JPanel {
        public PayslipPanel() {
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JLabel label = new JLabel("Payslip Management - Available in Full Version");
            label.setFont(new Font("Arial", Font.BOLD, 18));
            label.setForeground(TEXT_PRIMARY);
            add(label);
        }
    }
    
    class LeaveRequestPanel extends JPanel {
        public LeaveRequestPanel() {
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JLabel label = new JLabel("Leave Request Management - Available in Full Version");
            label.setFont(new Font("Arial", Font.BOLD, 18));
            label.setForeground(TEXT_PRIMARY);
            add(label);
        }
    }
    
    // Data Manager
    class DataManager {
        private List<Employee> employees = new ArrayList<>();
        private int nextEmployeeId = 1;
        
        public DataManager() {
            // Initialize with sample data
            initializeSampleData();
        }
        
        private void initializeSampleData() {
            // Sample Employees
            employees.add(new Employee(nextEmployeeId++, 1001, "Juan Dela Cruz", "Software Developer", "Information Technology", 50000.00));
            employees.add(new Employee(nextEmployeeId++, 1002, "Maria Santos", "HR Manager", "Human Resources", 60000.00));
            employees.add(new Employee(nextEmployeeId++, 1003, "Pedro Garcia", "Accountant", "Finance", 45000.00));
            employees.add(new Employee(nextEmployeeId++, 1004, "Ana Rodriguez", "Operations Supervisor", "Operations", 55000.00));
            employees.add(new Employee(nextEmployeeId++, 1005, "Luis Martinez", "System Administrator", "Information Technology", 52000.00));
        }
        
        // Employee methods
        public List<Employee> getEmployees() { return employees; }
        public int getNextEmployeeId() { return nextEmployeeId++; }
        public void addEmployee(Employee employee) { employees.add(employee); }
        public void updateEmployee(int id, Employee employee) {
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getId() == id) {
                    employees.set(i, employee);
                    break;
                }
            }
        }
        public void deleteEmployee(int id) {
            employees.removeIf(emp -> emp.getId() == id);
        }
        public Employee findEmployeeById(int id) {
            return employees.stream().filter(emp -> emp.getId() == id).findFirst().orElse(null);
        }
        public Employee findEmployeeByEmployeeId(int employeeId) {
            return employees.stream().filter(emp -> emp.getEmployeeID() == employeeId).findFirst().orElse(null);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for modern appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Project();
        });
    }
}