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

/**
 * MotorPH Employee Management System - Professional Edition
 * Complete CRUD Operations with CSV Import/Export for all modules
 * Modern professional design with advanced styling
 *
 * UI Revamp Notes:
 * - Refined Header: Adjusted dimensions and padding to ensure all elements are visible and well-aligned.
 * - Standardized Buttons: Removed custom paintComponent in createProfessionalButton.
 * Now relies on standard Swing rendering with refined borders, backgrounds, and hover effects for better consistency and reliability.
 * - Increased Status Area Height: Adjusted the preferred size of the system messages area.
 * - Global Spacing: Fine-tuned GridBagLayout insets and component paddings across all panels for improved visual balance and reduced clutter.
 * - Font Adjustments: Minor font size tweaks for better readability in compact layouts.
 * - Overall visual coherence and responsiveness improved.
 * - BUTTON VISIBILITY & COLOR: Buttons now feature a neutral background (white) with primary text and a subtle border.
 * Hover effects apply a light gray background and a distinct primary color border to enhance visibility and user interaction feedback.
 */
public class Project extends JFrame {
    
    // Enhanced Professional Color Palette - Modern Design System
    private static final Color PRIMARY_COLOR = new Color(67, 56, 202);    // Deep Indigo
    private static final Color PRIMARY_DARK = new Color(49, 46, 129);     // Dark Indigo
    private static final Color PRIMARY_LIGHT = new Color(165, 180, 252);   // Light Indigo
    private static final Color SECONDARY_COLOR = new Color(248, 250, 252);  // Cool Gray - Used for main background
    private static final Color ACCENT_COLOR = new Color(16, 185, 129);    // Emerald Green
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94);      // Green
    private static final Color DANGER_COLOR = new Color(239, 68, 68);      // Red
    private static final Color WARNING_COLOR = new Color(245, 158, 11);    // Amber
    private static final Color INFO_COLOR = new Color(59, 130, 246);        // Blue
    private static final Color TEXT_PRIMARY = new Color(15, 23, 42);      // Slate
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);   // Slate Gray
    private static final Color BORDER_COLOR = new Color(226, 232, 240);    // Light Border
    private static final Color CARD_BG = Color.WHITE; // Background for form/table cards
    private static final Color HOVER_COLOR = new Color(241, 245, 249);    // Light Hover
    private static final Color GRADIENT_START = new Color(99, 102, 241);    // Indigo
    private static final Color GRADIENT_END = new Color(139, 92, 246);      // Purple
    
    // Module-specific accent colors for consistent theming
    private static final Color EMPLOYEE_COLOR = new Color(79, 70, 229);    // Indigo - for employees
    private static final Color DEPARTMENT_COLOR = new Color(59, 130, 246);  // Blue - for departments
    private static final Color ATTENDANCE_COLOR = new Color(245, 158, 11);  // Amber - for attendance
    private static final Color PAYSLIP_COLOR = new Color(34, 197, 94);      // Green - for payslips
    private static final Color LEAVE_COLOR = new Color(239, 68, 68);      // Red - for leave requests
    
    // Data Storage
    private DataManager dataManager;
    private JTabbedPane tabbedPane;
    private JLabel timeLabel;
    private javax.swing.Timer clockTimer;
    
    public Project() {
        dataManager = new DataManager();
        initializeComponents();
        setupUI();
        startClock();
        setVisible(true);
    }
    
    private void initializeComponents() {
        setTitle("MotorPH Employee Management System 2025");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set to full screen automatically with proper minimum size
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 768)); // Adjusted minimum size for better content display
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Set modern look and feel with custom properties
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            customizeUIDefaults();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void customizeUIDefaults() {
        // Custom UI properties for modern appearance
        UIManager.put("TabbedPane.contentAreaColor", SECONDARY_COLOR);
        UIManager.put("TabbedPane.selected", PRIMARY_LIGHT);
        UIManager.put("TabbedPane.background", SECONDARY_COLOR);
        UIManager.put("Table.gridColor", BORDER_COLOR);
        UIManager.put("Table.background", CARD_BG);
        UIManager.put("Table.selectionBackground", PRIMARY_LIGHT);
        UIManager.put("Table.selectionForeground", Color.WHITE);
        UIManager.put("Button.focus", PRIMARY_COLOR);
        UIManager.put("TextField.selectionBackground", PRIMARY_LIGHT);
        UIManager.put("TextArea.selectionBackground", PRIMARY_LIGHT);
        UIManager.put("TextArea.selectionForeground", Color.WHITE);
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 12)); // Default label font
    }
    
    private void setupUI() {
        // Header Panel with gradient
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Main Content with Professional Tabs
        tabbedPane = createProfessionalTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        
        // Professional Status Bar
        add(createStatusBar(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create beautiful gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, GRADIENT_START,
                    getWidth(), getHeight(), GRADIENT_END
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add subtle overlay pattern
                g2d.setColor(new Color(255, 255, 255, 10));
                for (int i = 0; i < getWidth(); i += 40) {
                    g2d.drawLine(i, 0, i + 20, getHeight());
                }
            }
        };
        header.setPreferredSize(new Dimension(0, 80)); // Slightly increased header height
        header.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); // Adjusted padding
        
        // Left side - Enhanced company branding with icon
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        
        // Modern logo design with icon
        JPanel logoContainer = new JPanel(new BorderLayout());
        logoContainer.setOpaque(false);
        
        // Company icon/logo placeholder
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw modern company icon
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(2, 2, 36, 36, 8, 8); // Slightly larger icon again
                g2d.setColor(PRIMARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Slightly larger font
                g2d.drawString("M", 12, 26);
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(40, 40)); // Slightly larger icon size
        
        JPanel textContainer = new JPanel(new BorderLayout());
        textContainer.setOpaque(false);
        textContainer.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0)); // Adjusted padding
        
        JLabel companyLabel = new JLabel("MotorPH");
        companyLabel.setFont(new Font("Segoe UI", Font.BOLD, 26)); // Slightly larger font
        companyLabel.setForeground(Color.WHITE);
        
        JLabel systemLabel = new JLabel("Employee Management System • Professional Edition");
        systemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Keep small
        systemLabel.setForeground(new Color(255, 255, 255, 200));
        systemLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        
        textContainer.add(companyLabel, BorderLayout.NORTH);
        textContainer.add(systemLabel, BorderLayout.CENTER);
        
        JPanel logoGroup = new JPanel(new BorderLayout());
        logoGroup.setOpaque(false);
        logoGroup.add(iconPanel, BorderLayout.WEST);
        logoGroup.add(textContainer, BorderLayout.CENTER);
        
        leftPanel.add(logoGroup);
        
        // Right side - Enhanced info display with better styling
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0)); // Restore flow gap
        rightPanel.setOpaque(false);
        
        // Enhanced time display
        JPanel timeContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Semi-transparent background
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12); // Slightly larger rounded corners
                
                // Border highlight
                g2d.setColor(new Color(255, 255, 255, 60));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
            }
        };
        timeContainer.setOpaque(false);
        timeContainer.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18)); // Restore padding
        timeContainer.setPreferredSize(new Dimension(140, 45)); // Restore size
        
        JLabel timePrefix = new JLabel("CURRENT TIME");
        timePrefix.setFont(new Font("Segoe UI", Font.BOLD, 9)); // Restore font size
        timePrefix.setForeground(new Color(255, 255, 255, 180));
        timePrefix.setHorizontalAlignment(SwingConstants.CENTER);
        
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Restore font size
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        timeContainer.add(timePrefix, BorderLayout.NORTH);
        timeContainer.add(timeLabel, BorderLayout.CENTER);
        
        // Enhanced user info
        JPanel userContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Semi-transparent background
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12); // Slightly larger rounded corners
                
                // Border highlight
                g2d.setColor(new Color(255, 255, 255, 60));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
            }
        };
        userContainer.setOpaque(false);
        userContainer.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18)); // Restore padding
        userContainer.setPreferredSize(new Dimension(130, 45)); // Restore size
        
        JLabel userPrefix = new JLabel("LOGGED IN AS");
        userPrefix.setFont(new Font("Segoe UI", Font.BOLD, 9)); // Restore font size
        userPrefix.setForeground(new Color(255, 255, 255, 180));
        userPrefix.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel userLabel = new JLabel("Administrator");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 15)); // Restore font size
        userLabel.setForeground(Color.WHITE);
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        userContainer.add(userPrefix, BorderLayout.NORTH);
        userContainer.add(userLabel, BorderLayout.CENTER);
        
        rightPanel.add(timeContainer);
        rightPanel.add(userContainer);
        
        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);
        
        return header;
    }
    
    private JTabbedPane createProfessionalTabbedPane() {
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Restored original tab font size
        tabs.setBackground(SECONDARY_COLOR);
        tabs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Restored original padding
        
        // Add tabs with professional styling and consistent colored indicators
        addProfessionalTab(tabs, "EMPLOYEES", new EmployeePanel(), EMPLOYEE_COLOR);
        addProfessionalTab(tabs, "DEPARTMENTS", new DepartmentPanel(), DEPARTMENT_COLOR);
        addProfessionalTab(tabs, "ATTENDANCE", new AttendancePanel(), ATTENDANCE_COLOR);
        addProfessionalTab(tabs, "PAYSLIPS", new PayslipPanel(), PAYSLIP_COLOR);
        addProfessionalTab(tabs, "LEAVE REQUESTS", new LeaveRequestPanel(), LEAVE_COLOR);
        
        return tabs;
    }
    
    private void addProfessionalTab(JTabbedPane tabs, String title, JPanel panel, Color indicatorColor) {
        // Create tab with color indicator
        JPanel tabPanel = new JPanel(new BorderLayout());
        tabPanel.setOpaque(false);
        
        // Color indicator
        JPanel indicator = createColoredPanel(indicatorColor, 4, 20); // Restored original indicator size
        indicator.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Restored original tab title font size
        titleLabel.setForeground(TEXT_PRIMARY);
        
        tabPanel.add(indicator, BorderLayout.WEST);
        tabPanel.add(titleLabel, BorderLayout.CENTER);
        
        tabs.addTab(null, panel);
        tabs.setTabComponentAt(tabs.getTabCount() - 1, tabPanel);
    }
    
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(248, 250, 252));
        statusBar.setBorder(new CompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(12, 20, 12, 20) // Restored original padding
        ));
        statusBar.setPreferredSize(new Dimension(0, 40)); // Restored original height
        
        JLabel statusLabel = new JLabel("System Status: All modules operational");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Restored original font size
        statusLabel.setForeground(TEXT_SECONDARY);
        
        // Status indicator
        JPanel statusIndicator = createColoredPanel(SUCCESS_COLOR, 8, 8); // Restored original indicator size
        statusIndicator.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Restored original padding
        
        JPanel leftStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftStatus.setOpaque(false);
        leftStatus.add(statusIndicator);
        leftStatus.add(statusLabel);
        
        JLabel versionInfo = new JLabel("Version 2.0.1 | Build 2025.06.27");
        versionInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11)); // Restored original font size
        versionInfo.setForeground(TEXT_SECONDARY);
        
        statusBar.add(leftStatus, BorderLayout.WEST);
        statusBar.add(versionInfo, BorderLayout.EAST);
        
        return statusBar;
    }
    
    private void startClock() {
        clockTimer = new javax.swing.Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            String timeString = now.format(DateTimeFormatter.ofPattern("h:mm:ss a"));
            timeLabel.setText(timeString);
        });
        clockTimer.start();
    }
    
    // Utility method to create colored panels
    private JPanel createColoredPanel(Color color, int width, int height) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }
    
    /**
     * Creates a professional-looking button with neutral colors and dynamic hover effects.
     * This version uses standard Swing rendering and custom borders for a cleaner look
     * and avoids overriding paintComponent to prevent rendering issues.
     * Buttons now have a white background, dark text, and a subtle border that
     * becomes more prominent and primary-colored on hover.
     */
    private JButton createProfessionalButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(TEXT_PRIMARY); // Default text color
        button.setBackground(CARD_BG); // Default background color (white/light)
        
        // Use a custom border with rounded corners for the desired aesthetic
        button.setBorder(new LineBorder(BORDER_COLOR, 1, true) { // Default border color
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getLineColor()); // Uses the color set by LineBorder constructor
                g2d.drawRoundRect(x, y, width - 1, height - 1, 8, 8); // Draw rounded rectangle border
                g2d.dispose();
            }
        });
        
        button.setFocusPainted(false);
        button.setContentAreaFilled(true); // Ensure background color is painted
        button.setOpaque(true); // Ensure the background is always drawn
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Professional hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR); // Light hover color
                button.setBorder(new LineBorder(PRIMARY_COLOR, 2, true) { // Thicker, primary color border on hover
                    @Override
                    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2d.setColor(getLineColor());
                        g2d.drawRoundRect(x, y, width - 1, height - 1, 8, 8);
                        g2d.dispose();
                    }
                });
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARD_BG); // Revert to default background
                button.setBorder(new LineBorder(BORDER_COLOR, 1, true) { // Revert to thin, default border
                    @Override
                    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2d.setColor(getLineColor());
                        g2d.drawRoundRect(x, y, width - 1, height - 1, 8, 8);
                        g2d.dispose();
                    }
                });
            }
        });
        
        if (listener != null) {
            button.addActionListener(listener);
        }
        
        return button;
    }
    
    // Professional Text Field Factory
    private JTextField createProfessionalTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15)); // Slightly larger font for better readability
        field.setPreferredSize(new Dimension(280, 40)); // Slightly larger preferred height
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Fixed height
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12) // Adjusted padding
        ));
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_PRIMARY);
        field.setHorizontalAlignment(JTextField.LEFT);
        
        // Focus effects
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                    BorderFactory.createEmptyBorder(9, 11, 9, 11) // Adjust padding for thicker border
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
            }
        });
        
        return field;
    }
    
    // Professional Table Factory
    private JTable createProfessionalTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Restored original table font size
        table.setRowHeight(40); // Slightly increased row height for better spacing
        table.setSelectionBackground(PRIMARY_LIGHT);
        table.setSelectionForeground(TEXT_PRIMARY); // Changed to TEXT_PRIMARY for better contrast on light selection
        table.setGridColor(BORDER_COLOR);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setBackground(CARD_BG);
        
        // Professional header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Restored original header font size
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(TEXT_PRIMARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR)); // Bottom border only
        header.setPreferredSize(new Dimension(0, 45)); // Slightly increased header height
        
        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(249, 250, 251));
                    }
                } else {
                    // Ensure selected row foreground is readable
                    c.setForeground(TEXT_PRIMARY); // Use text primary on selected row for better contrast
                }
                
                setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); // Restored original padding
                return c;
            }
        });
        
        return table;
    }
    
    // Employee Panel with Professional Design
    class EmployeePanel extends JPanel {
        private JTextField idField, empIdField, nameField, positionField, departmentField, salaryField, searchField;
        private JTable employeeTable;
        private DefaultTableModel tableModel;
        private JTextArea statusArea;
        
        public EmployeePanel() {
            setLayout(new BorderLayout(15, 15)); // Increased outer padding
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Increased outer padding
            
            // Create responsive split layout with scroll panels
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            
            // Left Form Panel
            JPanel formPanelWrapper = createEmployeeForm();
            JScrollPane formScrollPane = new JScrollPane(formPanelWrapper);
            formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            formScrollPane.setBorder(null);
            formScrollPane.getViewport().setBackground(SECONDARY_COLOR);
            formScrollPane.setMinimumSize(new Dimension(400, 0)); // Adjusted minimum width for the form
            
            // Right Table Panel
            JPanel tablePanelWrapper = createEmployeeTable();
            
            splitPane.setLeftComponent(formScrollPane);
            splitPane.setRightComponent(tablePanelWrapper);
            splitPane.setDividerLocation(400); // Adjusted initial divider location
            splitPane.setResizeWeight(0.0); // Form side fixed, table takes all extra space
            splitPane.setBorder(null);
            splitPane.setBackground(SECONDARY_COLOR);
            splitPane.setOneTouchExpandable(true);
            splitPane.setContinuousLayout(true);
            
            add(splitPane, BorderLayout.CENTER);
            add(createEmployeeControls(), BorderLayout.SOUTH);
            
            refreshEmployeeTable();
        }
        
        private JPanel createEmployeeForm() {
            JPanel formCard = new JPanel(new GridBagLayout()) { // Changed to GridBagLayout
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Subtle gradient background
                    GradientPaint gradient = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, getHeight(), new Color(252, 252, 254)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Increased padding
            ));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 0, 12, 0); // Adjusted padding between components
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = GridBagConstraints.REMAINDER; // Each component takes full width
            
            // Enhanced header with consistent styling
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setOpaque(false);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Adjusted bottom padding
            
            JPanel colorAccent = createColoredPanel(EMPLOYEE_COLOR, 5, 35); // Restored original accent size
            
            JLabel headerLabel = new JLabel("EMPLOYEE INFORMATION");
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Restored original font size
            headerLabel.setForeground(TEXT_PRIMARY);
            headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            headerPanel.add(colorAccent, BorderLayout.WEST);
            headerPanel.add(headerLabel, BorderLayout.CENTER);
            
            gbc.gridy = 0;
            formCard.add(headerPanel, gbc);
            
            // Initialize fields with consistent styling
            idField = createProfessionalTextField(25); // Restored original columns
            idField.setEditable(false);
            idField.setBackground(new Color(248, 250, 252));
            empIdField = createProfessionalTextField(25);
            nameField = createProfessionalTextField(25);
            positionField = createProfessionalTextField(25);
            departmentField = createProfessionalTextField(25);
            salaryField = createProfessionalTextField(25);
            
            // Consistent labels across all modules
            String[] labels = {"INTERNAL ID:", "EMPLOYEE ID:", "FULL NAME:", "POSITION:", "DEPARTMENT:", "MONTHLY SALARY:"};
            JTextField[] fields = {idField, empIdField, nameField, positionField, departmentField, salaryField};
            
            for (int i = 0; i < labels.length; i++) {
                gbc.gridy = i * 2 + 1; // Label row
                JLabel label = new JLabel(labels[i]);
                label.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Restored original font size
                label.setForeground(TEXT_SECONDARY);
                label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // Adjusted padding below label
                formCard.add(label, gbc);
                
                gbc.gridy = i * 2 + 2; // Field row
                gbc.insets = new Insets(0, 0, 18, 0); // Adjusted padding after field
                formCard.add(fields[i], gbc);
                gbc.insets = new Insets(0, 0, 12, 0); // Reset padding for next label
            }
            
            // Consistent action buttons
            JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Restored original gaps
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0)); // Adjusted padding above buttons
            
            buttonPanel.add(createProfessionalButton("CREATE EMPLOYEE", this::saveEmployee));
            buttonPanel.add(createProfessionalButton("UPDATE EMPLOYEE", this::updateEmployee));
            buttonPanel.add(createProfessionalButton("DELETE EMPLOYEE", this::deleteEmployee));
            buttonPanel.add(createProfessionalButton("CLEAR FORM", this::clearEmployeeForm));
            
            gbc.gridy = labels.length * 2 + 1; // Position buttons after all fields
            gbc.insets = new Insets(20, 0, 0, 0); // Adjusted top padding for button panel
            formCard.add(buttonPanel, gbc);
            
            return formCard;
        }
        
        private JPanel createEmployeeTable() {
            JPanel tableCard = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Subtle gradient background
                    GradientPaint gradient = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, getHeight(), new Color(252, 252, 254)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Increased padding
            ));
            
            // Enhanced table header with consistent styling
            JPanel tableHeader = new JPanel(new BorderLayout());
            tableHeader.setOpaque(false);
            tableHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Adjusted bottom padding
            
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setOpaque(false);
            
            JPanel colorAccent = createColoredPanel(EMPLOYEE_COLOR, 5, 35); // Restored original accent size
            
            JLabel tableTitle = new JLabel("EMPLOYEE RECORDS");
            tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Restored original font size
            tableTitle.setForeground(TEXT_PRIMARY);
            tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            titlePanel.add(colorAccent, BorderLayout.WEST);
            titlePanel.add(tableTitle, BorderLayout.CENTER);
            
            // Enhanced search panel
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // Restored original horizontal gap
            searchPanel.setOpaque(false);
            
            searchField = createProfessionalTextField(20); // Restored original columns for search
            searchField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    searchEmployees();
                }
            });
            searchField.setPreferredSize(new Dimension(250, 38)); // Slightly smaller for search
            searchField.setMaximumSize(new Dimension(300, 38)); // Slightly smaller for search
            
            JLabel searchLabel = new JLabel("SEARCH RECORDS:");
            searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Restored original font size
            searchLabel.setForeground(TEXT_SECONDARY);
            searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8)); // Adjusted gap
            
            searchPanel.add(searchLabel);
            searchPanel.add(searchField);
            
            tableHeader.add(titlePanel, BorderLayout.WEST);
            tableHeader.add(searchPanel, BorderLayout.EAST);
            
            // Professional table
            String[] columns = {"ID", "Employee ID", "Full Name", "Position", "Department", "Monthly Salary"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            employeeTable = createProfessionalTable(tableModel);
            employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            employeeTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedEmployee();
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(employeeTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
            scrollPane.getViewport().setBackground(Color.WHITE);
            
            tableCard.add(tableHeader, BorderLayout.NORTH);
            tableCard.add(scrollPane, BorderLayout.CENTER);
            
            return tableCard;
        }
        
        private JPanel createEmployeeControls() {
            JPanel controlsPanel = new JPanel(new GridBagLayout()); // Using GridBagLayout
            controlsPanel.setBackground(SECONDARY_COLOR);
            controlsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Restored original top padding
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Restored original padding around components
            gbc.fill = GridBagConstraints.BOTH;
            
            // Professional CSV controls
            JPanel csvPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0)); // Restored original gap
            csvPanel.setOpaque(false);
            
            csvPanel.add(createProfessionalButton("EXPORT TO CSV", this::exportToCSV));
            csvPanel.add(createProfessionalButton("IMPORT FROM CSV", this::importFromCSV));
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.3; // Give CSV panel some space
            gbc.anchor = GridBagConstraints.WEST;
            controlsPanel.add(csvPanel, gbc);
            
            // Professional status area
            statusArea = new JTextArea(3, 60); // Restored original rows and columns
            statusArea.setEditable(false);
            statusArea.setBackground(CARD_BG);
            statusArea.setForeground(TEXT_SECONDARY);
            statusArea.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Restored original font size
            statusArea.setLineWrap(true);
            statusArea.setWrapStyleWord(true);
            statusArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDER_COLOR),
                    "System Messages", TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 12), TEXT_PRIMARY), // Restored original font size for title
                BorderFactory.createEmptyBorder(10, 15, 10, 15) // Restored original padding
            ));
            
            JScrollPane statusScrollPane = new JScrollPane(statusArea);
            statusScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            statusScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            statusScrollPane.setPreferredSize(new Dimension(300, 80)); // Restored original preferred size
            statusScrollPane.setBorder(null);
            
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 0.7; // Status area takes remaining space
            gbc.anchor = GridBagConstraints.EAST;
            controlsPanel.add(statusScrollPane, gbc);
            
            return controlsPanel;
        }
        
        // Employee CRUD Operations
        private void saveEmployee(ActionEvent e) {
            try {
                if (empIdField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                    showErrorMessage("Employee ID and Name are required fields.");
                    return;
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
                statusArea.setText("SUCCESS: Employee record created successfully for " + employee.getFullName() +
                    "\nEmployee ID: " + employee.getEmployeeID() + " | Position: " + employee.getPosition());
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid number format for Employee ID or Monthly Salary.\nPlease ensure these fields contain only digits.");
            } catch (Exception ex) {
                showErrorMessage("Failed to create employee record.\nReason: " + ex.getMessage() +
                    "\nPlease check all required fields and try again.");
            }
        }
        
        private void updateEmployee(ActionEvent e) {
            try {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select an employee record from the table to update.");
                    return;
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                 if (empIdField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                    showErrorMessage("Employee ID and Name are required fields for update.");
                    return;
                }
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
                statusArea.setText("SUCCESS: Employee record updated successfully for " + employee.getFullName() +
                    "\nUpdated information has been saved to the system.");
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid number format for Employee ID or Monthly Salary.\nPlease ensure these fields contain only digits.");
            } catch (Exception ex) {
                showErrorMessage("Failed to update employee record.\nReason: " + ex.getMessage() +
                    "\nPlease verify the selected record and try again.");
            }
        }
        
        private void deleteEmployee(ActionEvent e) {
            try {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select an employee record from the table to delete.");
                    return;
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                Employee employee = dataManager.findEmployeeById(id);
                
                // Custom confirmation dialog for deletion
                int confirm = showConfirmDialog("Confirm Employee Deletion", 
                                                "Are you sure you want to permanently delete the employee record for:\n\n" +
                                                "Name: " + employee.getFullName() + "\n" +
                                                "Employee ID: " + employee.getEmployeeID() + "\n" +
                                                "Position: " + employee.getPosition() + "\n\n" +
                                                "This action cannot be undone.",
                                                JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dataManager.deleteEmployee(id);
                    refreshEmployeeTable();
                    clearEmployeeForm(null);
                    statusArea.setText("SUCCESS: Employee record deleted successfully\n" +
                        "Removed: " + employee.getFullName() + " (ID: " + employee.getEmployeeID() + ")");
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to delete employee record.\nReason: " + ex.getMessage());
            }
        }
        
        private void clearEmployeeForm(ActionEvent e) {
            idField.setText("");
            empIdField.setText("");
            nameField.setText("");
            positionField.setText("");
            departmentField.setText("");
            salaryField.setText("");
            statusArea.setText("INFO: Employee form has been cleared\nReady for new employee entry.");
            employeeTable.clearSelection(); // Clear table selection when form is cleared
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
                statusArea.setText("INFO: Loaded employee record for editing\n" +
                    "Selected: " + model.getValueAt(selectedRow, 2).toString());
            }
        }
        
        private void searchEmployees() {
            String searchTerm = searchField.getText().toLowerCase().trim();
            
            tableModel.setRowCount(0); // Clear existing table rows
            int matchCount = 0;
            
            for (Employee emp : dataManager.getEmployees()) {
                if (emp.getFullName().toLowerCase().contains(searchTerm) ||
                    emp.getPosition().toLowerCase().contains(searchTerm) ||
                    emp.getDepartment().toLowerCase().contains(searchTerm) ||
                    String.valueOf(emp.getEmployeeID()).contains(searchTerm) ||
                    String.valueOf(emp.getId()).contains(searchTerm)) { // Also search by internal ID
                    tableModel.addRow(emp.toTableRow());
                    matchCount++;
                }
            }
            statusArea.setText("SEARCH: Found " + matchCount + " employee records matching '" + searchTerm + "'\n" +
                "Use the search field to filter results or clear to show all records.");
            if (searchTerm.isEmpty()) {
                statusArea.setText("INFO: Employee search cleared. Showing all records.");
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
                fileChooser.setDialogTitle("Export Employee Records to CSV File");
                fileChooser.setSelectedFile(new File("MotorPH_Employees_" +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv"));
                
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                        // Write header with metadata
                        writer.println("# MotorPH Employee Management System");
                        writer.println("# Export Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        writer.println("# Total Records: " + dataManager.getEmployees().size());
                        writer.println("#");
                        writer.println("ID,EmployeeID,FullName,Position,Department,MonthlySalary");
                        
                        // Write data
                        for (Employee emp : dataManager.getEmployees()) {
                            writer.printf("%d,%d,\"%s\",\"%s\",\"%s\",%.2f%n",
                                emp.getId(), emp.getEmployeeID(), emp.getFullName(),
                                emp.getPosition(), emp.getDepartment(), emp.getSalary());
                        }
                    }
                    statusArea.setText("SUCCESS: Employee records exported successfully\n" +
                        "File: " + file.getName() + " | Records: " + dataManager.getEmployees().size() +
                        " | Location: " + file.getAbsolutePath());
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to export employee records to CSV.\nReason: " + ex.getMessage());
            }
        }
        
        private void importFromCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Import Employee Records from CSV File");
                
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    int importCount = 0;
                    int errorCount = 0;
                    
                    try (Scanner scanner = new Scanner(file)) {
                        // Skip comments and header
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            if (!line.startsWith("#") && !line.startsWith("ID,")) {
                                break;
                            }
                        }
                        
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by comma outside quotes
                            
                            try {
                                if (parts.length >= 6) {
                                    // Trim quotes from string parts
                                    Employee employee = new Employee(
                                        dataManager.getNextEmployeeId(),
                                        Integer.parseInt(parts[1].trim()),
                                        parts[2].trim().replace("\"", ""),
                                        parts[3].trim().replace("\"", ""),
                                        parts[4].trim().replace("\"", ""),
                                        Double.parseDouble(parts[5].trim())
                                    );
                                    dataManager.addEmployee(employee);
                                    importCount++;
                                } else {
                                    errorCount++;
                                }
                            } catch (Exception lineEx) {
                                errorCount++;
                            }
                        }
                    }
                    
                    refreshEmployeeTable();
                    statusArea.setText("SUCCESS: CSV import completed\n" +
                        "Imported: " + importCount + " records | Errors: " + errorCount +
                        " | Source: " + file.getName());
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to import employee records from CSV.\nReason: " + ex.getMessage());
            }
        }
    }
    
    // Department Panel with Full CRUD Operations and Professional Design
    class DepartmentPanel extends JPanel {
        private JTextField idField, nameField, managerField, locationField, searchField;
        private JTable departmentTable;
        private DefaultTableModel tableModel;
        private JTextArea statusArea;
        
        public DepartmentPanel() {
            setLayout(new BorderLayout(15, 15)); // Increased outer padding
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Increased outer padding
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            
            // Left Form Panel
            JPanel formPanelWrapper = createDepartmentForm();
            JScrollPane formScrollPane = new JScrollPane(formPanelWrapper);
            formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            formScrollPane.setBorder(null);
            formScrollPane.getViewport().setBackground(SECONDARY_COLOR);
            formScrollPane.setMinimumSize(new Dimension(400, 0)); // Adjusted minimum width
            
            // Right Table Panel
            JPanel tablePanelWrapper = createDepartmentTable();
            
            splitPane.setLeftComponent(formScrollPane);
            splitPane.setRightComponent(tablePanelWrapper);
            splitPane.setDividerLocation(400); // Adjusted initial divider location
            splitPane.setResizeWeight(0.0); // Form side fixed, table takes all extra space
            splitPane.setBorder(null);
            splitPane.setBackground(SECONDARY_COLOR);
            splitPane.setOneTouchExpandable(true);
            splitPane.setContinuousLayout(true);
            
            add(splitPane, BorderLayout.CENTER);
            add(createDepartmentControls(), BorderLayout.SOUTH);
            
            refreshDepartmentTable();
        }
        
        private JPanel createDepartmentForm() {
            JPanel formCard = new JPanel(new GridBagLayout()) { // Changed to GridBagLayout
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Subtle gradient background
                    GradientPaint gradient = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, getHeight(), new Color(252, 252, 254)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Increased padding
            ));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 0, 12, 0); // Adjusted padding between components
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = GridBagConstraints.REMAINDER; // Each component takes full width
            
            // Enhanced header with consistent styling
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setOpaque(false);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Adjusted bottom padding
            
            JPanel colorAccent = createColoredPanel(DEPARTMENT_COLOR, 5, 35); // Restored original accent size
            
            JLabel headerLabel = new JLabel("DEPARTMENT INFORMATION");
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Restored original font size
            headerLabel.setForeground(TEXT_PRIMARY);
            headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            headerPanel.add(colorAccent, BorderLayout.WEST);
            headerPanel.add(headerLabel, BorderLayout.CENTER);
            
            gbc.gridy = 0;
            formCard.add(headerPanel, gbc);
            
            // Initialize fields with consistent styling
            idField = createProfessionalTextField(25);
            idField.setEditable(false);
            idField.setBackground(new Color(248, 250, 252));
            nameField = createProfessionalTextField(25);
            managerField = createProfessionalTextField(25);
            locationField = createProfessionalTextField(25);
            
            // Consistent labels across all modules
            String[] labels = {"DEPARTMENT ID:", "DEPARTMENT NAME:", "MANAGER:", "LOCATION:"};
            JTextField[] fields = {idField, nameField, managerField, locationField};
            
            for (int i = 0; i < labels.length; i++) {
                gbc.gridy = i * 2 + 1; // Label row
                JLabel label = new JLabel(labels[i]);
                label.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Restored original font size
                label.setForeground(TEXT_SECONDARY);
                label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // Adjusted padding below label
                formCard.add(label, gbc);
                
                gbc.gridy = i * 2 + 2; // Field row
                gbc.insets = new Insets(0, 0, 18, 0); // Adjusted padding after field
                formCard.add(fields[i], gbc);
                gbc.insets = new Insets(0, 0, 12, 0); // Reset padding for next label
            }
            
            // Consistent action buttons
            JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Restored original gaps
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0)); // Adjusted padding above buttons
            
            buttonPanel.add(createProfessionalButton("CREATE DEPARTMENT", this::saveDepartment));
            buttonPanel.add(createProfessionalButton("UPDATE DEPARTMENT", this::updateDepartment));
            buttonPanel.add(createProfessionalButton("DELETE DEPARTMENT", this::deleteDepartment));
            buttonPanel.add(createProfessionalButton("CLEAR FORM", this::clearDepartmentForm));
            
            gbc.gridy = labels.length * 2 + 1; // Position buttons after all fields
            gbc.insets = new Insets(20, 0, 0, 0); // Adjusted top padding for button panel
            formCard.add(buttonPanel, gbc);
            
            return formCard;
        }
        
        private JPanel createDepartmentTable() {
            JPanel tableCard = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Subtle gradient background
                    GradientPaint gradient = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, getHeight(), new Color(252, 252, 254)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Increased padding
            ));
            
            // Enhanced table header with consistent styling
            JPanel tableHeader = new JPanel(new BorderLayout());
            tableHeader.setOpaque(false);
            tableHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Adjusted bottom padding
            
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setOpaque(false);
            
            JPanel colorAccent = createColoredPanel(DEPARTMENT_COLOR, 5, 35); // Restored original accent size
            
            JLabel tableTitle = new JLabel("DEPARTMENT RECORDS");
            tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Restored original font size
            tableTitle.setForeground(TEXT_PRIMARY);
            tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            titlePanel.add(colorAccent, BorderLayout.WEST);
            titlePanel.add(tableTitle, BorderLayout.CENTER);
            
            // Enhanced search panel
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            searchPanel.setOpaque(false);
            
            searchField = createProfessionalTextField(20);
            searchField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    searchDepartments();
                }
            });
            searchField.setPreferredSize(new Dimension(250, 38));
            searchField.setMaximumSize(new Dimension(300, 38));
            
            JLabel searchLabel = new JLabel("SEARCH RECORDS:");
            searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            searchLabel.setForeground(TEXT_SECONDARY);
            searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
            
            searchPanel.add(searchLabel);
            searchPanel.add(searchField);
            
            tableHeader.add(titlePanel, BorderLayout.WEST);
            tableHeader.add(searchPanel, BorderLayout.EAST);
            
            String[] columns = {"ID", "Department Name", "Manager", "Location"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            departmentTable = createProfessionalTable(tableModel);
            departmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            departmentTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedDepartment();
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(departmentTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
            scrollPane.getViewport().setBackground(Color.WHITE);
            
            tableCard.add(tableHeader, BorderLayout.NORTH);
            tableCard.add(scrollPane, BorderLayout.CENTER);
            
            return tableCard;
        }
        
        private JPanel createDepartmentControls() {
            JPanel controlsPanel = new JPanel(new GridBagLayout());
            controlsPanel.setBackground(SECONDARY_COLOR);
            controlsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.BOTH;
            
            JPanel csvPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
            csvPanel.setOpaque(false);
            
            csvPanel.add(createProfessionalButton("EXPORT TO CSV", this::exportToCSV));
            csvPanel.add(createProfessionalButton("IMPORT FROM CSV", this::importFromCSV));
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.3;
            gbc.anchor = GridBagConstraints.WEST;
            controlsPanel.add(csvPanel, gbc);
            
            statusArea = new JTextArea(3, 60);
            statusArea.setEditable(false);
            statusArea.setBackground(CARD_BG);
            statusArea.setForeground(TEXT_SECONDARY);
            statusArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            statusArea.setLineWrap(true);
            statusArea.setWrapStyleWord(true);
            statusArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDER_COLOR),
                    "System Messages", TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 12), TEXT_PRIMARY),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            
            JScrollPane statusScrollPane = new JScrollPane(statusArea);
            statusScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            statusScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            statusScrollPane.setPreferredSize(new Dimension(300, 80));
            statusScrollPane.setBorder(null);
            
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 0.7;
            gbc.anchor = GridBagConstraints.EAST;
            controlsPanel.add(statusScrollPane, gbc);
            
            return controlsPanel;
        }
        
        private void saveDepartment(ActionEvent e) {
            try {
                if (nameField.getText().trim().isEmpty()) {
                    showErrorMessage("Department name is required.");
                    return;
                }
                
                Department department = new Department(
                    dataManager.getNextDepartmentId(),
                    nameField.getText().trim(),
                    managerField.getText().trim(),
                    locationField.getText().trim()
                );
                
                dataManager.addDepartment(department);
                refreshDepartmentTable();
                clearDepartmentForm(null);
                statusArea.setText("SUCCESS: Department created successfully\nDepartment: " + department.getName() +
                    " | Manager: " + department.getManager() + " | Location: " + department.getLocation());
            } catch (Exception ex) {
                showErrorMessage("Failed to create department.\nReason: " + ex.getMessage());
            }
        }
        
        private void updateDepartment(ActionEvent e) {
            try {
                int selectedRow = departmentTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select a department to update.");
                    return;
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                if (nameField.getText().trim().isEmpty()) {
                    showErrorMessage("Department name is required for update.");
                    return;
                }
                Department department = new Department(
                    id,
                    nameField.getText().trim(),
                    managerField.getText().trim(),
                    locationField.getText().trim()
                );
                
                dataManager.updateDepartment(id, department);
                refreshDepartmentTable();
                clearDepartmentForm(null);
                statusArea.setText("SUCCESS: Department updated successfully\nDepartment: " + department.getName());
            } catch (Exception ex) {
                showErrorMessage("Failed to update department.\nReason: " + ex.getMessage());
            }
        }
        
        private void deleteDepartment(ActionEvent e) {
            try {
                int selectedRow = departmentTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select a department to delete.");
                    return;
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                Department department = dataManager.findDepartmentById(id);
                
                int confirm = showConfirmDialog("Confirm Department Deletion", 
                                                "Are you sure you want to delete department: " + department.getName() + "?",
                                                JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dataManager.deleteDepartment(id);
                    refreshDepartmentTable();
                    clearDepartmentForm(null);
                    statusArea.setText("SUCCESS: Department deleted successfully");
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to delete department.\nReason: " + ex.getMessage());
            }
        }
        
        private void clearDepartmentForm(ActionEvent e) {
            idField.setText("");
            nameField.setText("");
            managerField.setText("");
            locationField.setText("");
            statusArea.setText("INFO: Department form cleared");
            departmentTable.clearSelection();
        }
        
        private void loadSelectedDepartment() {
            int selectedRow = departmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                DefaultTableModel model = (DefaultTableModel) departmentTable.getModel();
                idField.setText(model.getValueAt(selectedRow, 0).toString());
                nameField.setText(model.getValueAt(selectedRow, 1).toString());
                managerField.setText(model.getValueAt(selectedRow, 2).toString());
                locationField.setText(model.getValueAt(selectedRow, 3).toString());
                statusArea.setText("INFO: Loaded department for editing: " + model.getValueAt(selectedRow, 1).toString());
            }
        }
        
        private void searchDepartments() {
            String searchTerm = searchField.getText().toLowerCase().trim();
            
            tableModel.setRowCount(0);
            int matchCount = 0;
            for (Department dept : dataManager.getDepartments()) {
                if (dept.getName().toLowerCase().contains(searchTerm) ||
                    dept.getManager().toLowerCase().contains(searchTerm) ||
                    dept.getLocation().toLowerCase().contains(searchTerm) ||
                    String.valueOf(dept.getId()).contains(searchTerm)) {
                    tableModel.addRow(dept.toTableRow());
                    matchCount++;
                }
            }
            statusArea.setText("SEARCH: Found " + matchCount + " departments matching '" + searchTerm + "'");
            if (searchTerm.isEmpty()) {
                statusArea.setText("INFO: Department search cleared. Showing all records.");
            }
        }
        
        private void refreshDepartmentTable() {
            tableModel.setRowCount(0);
            for (Department dept : dataManager.getDepartments()) {
                tableModel.addRow(dept.toTableRow());
            }
        }
        
        private void exportToCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Export Departments to CSV");
                fileChooser.setSelectedFile(new File("MotorPH_Departments_" +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv"));
                
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                        writer.println("ID,DepartmentName,Manager,Location");
                        for (Department dept : dataManager.getDepartments()) {
                            writer.printf("%d,\"%s\",\"%s\",\"%s\"%n",
                                dept.getId(), dept.getName(), dept.getManager(), dept.getLocation());
                        }
                    }
                    statusArea.setText("SUCCESS: Departments exported to CSV\nFile: " + file.getName() +
                        " | Records: " + dataManager.getDepartments().size());
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to export departments.\nReason: " + ex.getMessage());
            }
        }
        
        private void importFromCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Import Departments from CSV");
                
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    int importCount = 0;
                    int errorCount = 0;
                    
                    try (Scanner scanner = new Scanner(file)) {
                        if (scanner.hasNextLine()) {
                            scanner.nextLine(); // Skip header
                        }
                        
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                            
                            try {
                                if (parts.length >= 4) {
                                    Department department = new Department(
                                        dataManager.getNextDepartmentId(),
                                        parts[1].trim().replace("\"", ""),
                                        parts[2].trim().replace("\"", ""),
                                        parts[3].trim().replace("\"", "")
                                    );
                                    dataManager.addDepartment(department);
                                    importCount++;
                                } else {
                                    errorCount++;
                                }
                            } catch (Exception lineEx) {
                                errorCount++;
                            }
                        }
                    }
                    
                    refreshDepartmentTable();
                    statusArea.setText("SUCCESS: Imported " + importCount + " departments from CSV | Errors: " + errorCount);
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to import departments.\nReason: " + ex.getMessage());
            }
        }
    }
    
    // Attendance Panel with Full CRUD Operations
    class AttendancePanel extends JPanel {
        private JTextField idField, empIdField, empNameField, dateField, timeInField, timeOutField, hoursField, searchField;
        private JTable attendanceTable;
        private DefaultTableModel tableModel;
        private JTextArea statusArea;
        
        public AttendancePanel() {
            setLayout(new BorderLayout(15, 15)); // Increased outer padding
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Increased outer padding
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            
            // Left Form Panel
            JPanel formPanelWrapper = createAttendanceForm();
            JScrollPane formScrollPane = new JScrollPane(formPanelWrapper);
            formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            formScrollPane.setBorder(null);
            formScrollPane.getViewport().setBackground(SECONDARY_COLOR);
            formScrollPane.setMinimumSize(new Dimension(400, 0)); // Adjusted minimum width
            
            // Right Table Panel
            JPanel tablePanelWrapper = createAttendanceTable();
            
            splitPane.setLeftComponent(formScrollPane);
            splitPane.setRightComponent(tablePanelWrapper);
            splitPane.setDividerLocation(400); // Adjusted initial divider location
            splitPane.setResizeWeight(0.0); // Form side fixed, table takes all extra space
            splitPane.setBorder(null);
            splitPane.setBackground(SECONDARY_COLOR);
            splitPane.setOneTouchExpandable(true);
            splitPane.setContinuousLayout(true);
            
            add(splitPane, BorderLayout.CENTER);
            add(createAttendanceControls(), BorderLayout.SOUTH);
            
            refreshAttendanceTable();
        }
        
        private JPanel createAttendanceForm() {
            JPanel formCard = new JPanel(new GridBagLayout()) { // Changed to GridBagLayout
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Subtle gradient background
                    GradientPaint gradient = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, getHeight(), new Color(252, 252, 254)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Increased padding
            ));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 0, 12, 0); // Adjusted padding between components
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = GridBagConstraints.REMAINDER; // Each component takes full width
            
            // Enhanced header with consistent styling
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setOpaque(false);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Adjusted bottom padding
            
            JPanel colorAccent = createColoredPanel(ATTENDANCE_COLOR, 5, 35); // Restored original accent size
            
            JLabel headerLabel = new JLabel("ATTENDANCE INFORMATION");
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Restored original font size
            headerLabel.setForeground(TEXT_PRIMARY);
            headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            headerPanel.add(colorAccent, BorderLayout.WEST);
            headerPanel.add(headerLabel, BorderLayout.CENTER);
            
            gbc.gridy = 0;
            formCard.add(headerPanel, gbc);
            
            // Initialize fields with consistent styling
            idField = createProfessionalTextField(25);
            idField.setEditable(false);
            idField.setBackground(new Color(248, 250, 252));
            empIdField = createProfessionalTextField(25);
            empNameField = createProfessionalTextField(25);
            dateField = createProfessionalTextField(25);
            timeInField = createProfessionalTextField(25);
            timeOutField = createProfessionalTextField(25);
            hoursField = createProfessionalTextField(25);
            hoursField.setEditable(false);
            hoursField.setBackground(new Color(248, 250, 252));
            
            // Set current date as default
            dateField.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)); // Use ISO format for consistency
            
            // Consistent labels across all modules
            String[] labels = {"RECORD ID:", "EMPLOYEE ID:", "EMPLOYEE NAME:", "DATE (YYYY-MM-DD):", "TIME IN (HH:MM):", "TIME OUT (HH:MM):", "HOURS WORKED:"};
            JTextField[] fields = {idField, empIdField, empNameField, dateField, timeInField, timeOutField, hoursField};
            
            for (int i = 0; i < labels.length; i++) {
                gbc.gridy = i * 2 + 1; // Label row
                JLabel label = new JLabel(labels[i]);
                label.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Restored original font size
                label.setForeground(TEXT_SECONDARY);
                label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // Adjusted padding below label
                formCard.add(label, gbc);
                
                gbc.gridy = i * 2 + 2; // Field row
                gbc.insets = new Insets(0, 0, 18, 0); // Adjusted padding after field
                formCard.add(fields[i], gbc);
                gbc.insets = new Insets(0, 0, 12, 0); // Reset padding for next label
            }
            
            // Add calculate hours button
            JPanel utilityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10)); // Restored original vertical gap
            utilityPanel.setOpaque(false);
            utilityPanel.add(createProfessionalButton("CALCULATE HOURS", this::calculateHours));
            
            gbc.gridy = labels.length * 2 + 1;
            gbc.insets = new Insets(0, 0, 10, 0); // Adjusted padding
            formCard.add(utilityPanel, gbc);
            
            // Consistent action buttons
            JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Restored original gaps
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0)); // Adjusted padding above buttons
            
            buttonPanel.add(createProfessionalButton("CREATE ATTENDANCE", this::saveAttendance));
            buttonPanel.add(createProfessionalButton("UPDATE ATTENDANCE", this::updateAttendance));
            buttonPanel.add(createProfessionalButton("DELETE ATTENDANCE", this::deleteAttendance));
            buttonPanel.add(createProfessionalButton("CLEAR FORM", this::clearAttendanceForm));
            
            gbc.gridy = labels.length * 2 + 2;
            gbc.insets = new Insets(20, 0, 0, 0); // Adjusted top padding for button panel
            formCard.add(buttonPanel, gbc);
            
            return formCard;
        }
        
        private JPanel createAttendanceTable() {
            JPanel tableCard = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Subtle gradient background
                    GradientPaint gradient = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, getHeight(), new Color(252, 252, 254)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Increased padding
            ));
            
            // Enhanced table header with consistent styling
            JPanel tableHeader = new JPanel(new BorderLayout());
            tableHeader.setOpaque(false);
            tableHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Adjusted bottom padding
            
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setOpaque(false);
            
            JPanel colorAccent = createColoredPanel(ATTENDANCE_COLOR, 5, 35); // Restored original accent size
            
            JLabel tableTitle = new JLabel("ATTENDANCE RECORDS");
            tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Restored original font size
            tableTitle.setForeground(TEXT_PRIMARY);
            tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            titlePanel.add(colorAccent, BorderLayout.WEST);
            titlePanel.add(tableTitle, BorderLayout.CENTER);
            
            // Enhanced search panel
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            searchPanel.setOpaque(false);
            
            searchField = createProfessionalTextField(20);
            searchField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    searchAttendance();
                }
            });
            searchField.setPreferredSize(new Dimension(250, 38));
            searchField.setMaximumSize(new Dimension(300, 38));
            
            JLabel searchLabel = new JLabel("SEARCH RECORDS:");
            searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            searchLabel.setForeground(TEXT_SECONDARY);
            searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
            
            searchPanel.add(searchLabel);
            searchPanel.add(searchField);
            
            tableHeader.add(titlePanel, BorderLayout.WEST);
            tableHeader.add(searchPanel, BorderLayout.EAST);
            
            String[] columns = {"ID", "Emp ID", "Employee Name", "Date", "Time In", "Time Out", "Hours"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            attendanceTable = createProfessionalTable(tableModel);
            attendanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            attendanceTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedAttendance();
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(attendanceTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
            scrollPane.getViewport().setBackground(Color.WHITE);
            
            tableCard.add(tableHeader, BorderLayout.NORTH);
            tableCard.add(scrollPane, BorderLayout.CENTER);
            
            return tableCard;
        }
        
        private JPanel createAttendanceControls() {
            JPanel controlsPanel = new JPanel(new GridBagLayout());
            controlsPanel.setBackground(SECONDARY_COLOR);
            controlsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.BOTH;
            
            JPanel csvPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
            csvPanel.setOpaque(false);
            
            csvPanel.add(createProfessionalButton("EXPORT TO CSV", this::exportToCSV));
            csvPanel.add(createProfessionalButton("IMPORT FROM CSV", this::importFromCSV));
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.3;
            gbc.anchor = GridBagConstraints.WEST;
            controlsPanel.add(csvPanel, gbc);
            
            statusArea = new JTextArea(3, 60);
            statusArea.setEditable(false);
            statusArea.setBackground(CARD_BG);
            statusArea.setForeground(TEXT_SECONDARY);
            statusArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            statusArea.setLineWrap(true);
            statusArea.setWrapStyleWord(true);
            statusArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDER_COLOR),
                    "System Messages", TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 12), TEXT_PRIMARY),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            
            JScrollPane statusScrollPane = new JScrollPane(statusArea);
            statusScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            statusScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            statusScrollPane.setPreferredSize(new Dimension(300, 80));
            statusScrollPane.setBorder(null);
            
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 0.7;
            gbc.anchor = GridBagConstraints.EAST;
            controlsPanel.add(statusScrollPane, gbc);
            
            return controlsPanel;
        }
        
        private void calculateHours(ActionEvent e) {
            try {
                String timeIn = timeInField.getText().trim();
                String timeOut = timeOutField.getText().trim();
                
                if (timeIn.isEmpty() || timeOut.isEmpty()) {
                    statusArea.setText("INFO: Please enter both time in and time out to calculate hours.");
                    return;
                }
                
                LocalTime inTime = LocalTime.parse(timeIn, DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime outTime = LocalTime.parse(timeOut, DateTimeFormatter.ofPattern("HH:mm"));
                
                long minutesDifference = java.time.Duration.between(inTime, outTime).toMinutes();
                if (minutesDifference < 0) {
                    minutesDifference += 24 * 60; // Handle overnight shifts
                }
                
                double workedHours = minutesDifference / 60.0;
                
                hoursField.setText(String.format("%.2f", workedHours));
                statusArea.setText("SUCCESS: Hours calculated successfully\nWorked Hours: " + String.format("%.2f", workedHours));
                
            } catch (Exception ex) {
                showErrorMessage("Failed to calculate hours.\nReason: " + ex.getMessage() +
                    "\nPlease use format HH:MM (e.g., 08:30).");
            }
        }
        
        private void saveAttendance(ActionEvent e) {
            try {
                if (empIdField.getText().trim().isEmpty() || empNameField.getText().trim().isEmpty() ||
                    dateField.getText().trim().isEmpty() || timeInField.getText().trim().isEmpty() ||
                    timeOutField.getText().trim().isEmpty() || hoursField.getText().trim().isEmpty()) {
                    showErrorMessage("All attendance fields are required.");
                    return;
                }
                
                AttendanceRecord attendance = new AttendanceRecord(
                    dataManager.getNextAttendanceId(),
                    Integer.parseInt(empIdField.getText().trim()),
                    empNameField.getText().trim(),
                    dateField.getText().trim(),
                    timeInField.getText().trim(),
                    timeOutField.getText().trim(),
                    Double.parseDouble(hoursField.getText().trim())
                );
                
                dataManager.addAttendance(attendance);
                refreshAttendanceTable();
                clearAttendanceForm(null);
                statusArea.setText("SUCCESS: Attendance record created\nEmployee: " + attendance.getEmployeeName() +
                    " | Date: " + attendance.getDate() + " | Hours: " + attendance.getHours());
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid number format for Employee ID or Hours Worked.\nPlease ensure these fields contain valid digits.");
            } catch (Exception ex) {
                showErrorMessage("Failed to create attendance record.\nReason: " + ex.getMessage());
            }
        }
        
        private void updateAttendance(ActionEvent e) {
            try {
                int selectedRow = attendanceTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select an attendance record to update.");
                    return;
                }
                if (empIdField.getText().trim().isEmpty() || empNameField.getText().trim().isEmpty() ||
                    dateField.getText().trim().isEmpty() || timeInField.getText().trim().isEmpty() ||
                    timeOutField.getText().trim().isEmpty() || hoursField.getText().trim().isEmpty()) {
                    showErrorMessage("All attendance fields are required for update.");
                    return;
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                AttendanceRecord attendance = new AttendanceRecord(
                    id,
                    Integer.parseInt(empIdField.getText().trim()),
                    empNameField.getText().trim(),
                    dateField.getText().trim(),
                    timeInField.getText().trim(),
                    timeOutField.getText().trim(),
                    Double.parseDouble(hoursField.getText().trim())
                );
                
                dataManager.updateAttendance(id, attendance);
                refreshAttendanceTable();
                clearAttendanceForm(null);
                statusArea.setText("SUCCESS: Attendance record updated\nEmployee: " + attendance.getEmployeeName());
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid number format for Employee ID or Hours Worked.\nPlease ensure these fields contain valid digits.");
            } catch (Exception ex) {
                showErrorMessage("Failed to update attendance record.\nReason: " + ex.getMessage());
            }
        }
        
        private void deleteAttendance(ActionEvent e) {
            try {
                int selectedRow = attendanceTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select an attendance record to delete.");
                    return;
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                
                int confirm = showConfirmDialog("Confirm Attendance Record Deletion", 
                                                "Are you sure you want to delete this attendance record?",
                                                JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dataManager.deleteAttendance(id);
                    refreshAttendanceTable();
                    clearAttendanceForm(null);
                    statusArea.setText("SUCCESS: Attendance record deleted successfully");
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to delete attendance record.\nReason: " + ex.getMessage());
            }
        }
        
        private void clearAttendanceForm(ActionEvent e) {
            idField.setText("");
            empIdField.setText("");
            empNameField.setText("");
            dateField.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
            timeInField.setText("");
            timeOutField.setText("");
            hoursField.setText("");
            statusArea.setText("INFO: Attendance form cleared");
            attendanceTable.clearSelection();
        }
        
        private void loadSelectedAttendance() {
            int selectedRow = attendanceTable.getSelectedRow();
            if (selectedRow >= 0) {
                DefaultTableModel model = (DefaultTableModel) attendanceTable.getModel();
                idField.setText(model.getValueAt(selectedRow, 0).toString());
                empIdField.setText(model.getValueAt(selectedRow, 1).toString());
                empNameField.setText(model.getValueAt(selectedRow, 2).toString());
                dateField.setText(model.getValueAt(selectedRow, 3).toString());
                timeInField.setText(model.getValueAt(selectedRow, 4).toString());
                timeOutField.setText(model.getValueAt(selectedRow, 5).toString());
                hoursField.setText(model.getValueAt(selectedRow, 6).toString());
                statusArea.setText("INFO: Loaded attendance record for " + model.getValueAt(selectedRow, 2).toString());
            }
        }
        
        private void searchAttendance() {
            String searchTerm = searchField.getText().toLowerCase().trim();
            
            tableModel.setRowCount(0);
            int matchCount = 0;
            for (AttendanceRecord att : dataManager.getAttendanceRecords()) {
                if (att.getEmployeeName().toLowerCase().contains(searchTerm) ||
                    att.getDate().toLowerCase().contains(searchTerm) ||
                    String.valueOf(att.getEmployeeId()).contains(searchTerm) ||
                    String.valueOf(att.getId()).contains(searchTerm)) {
                    tableModel.addRow(att.toTableRow());
                    matchCount++;
                }
            }
            statusArea.setText("SEARCH: Found " + matchCount + " attendance records matching '" + searchTerm + "'");
            if (searchTerm.isEmpty()) {
                statusArea.setText("INFO: Attendance search cleared. Showing all records.");
            }
        }
        
        private void refreshAttendanceTable() {
            tableModel.setRowCount(0);
            for (AttendanceRecord att : dataManager.getAttendanceRecords()) {
                tableModel.addRow(att.toTableRow());
            }
        }
        
        private void exportToCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Export Attendance to CSV");
                fileChooser.setSelectedFile(new File("MotorPH_Attendance_" +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv"));
                
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                        writer.println("ID,EmployeeID,EmployeeName,Date,TimeIn,TimeOut,Hours");
                        
                        for (AttendanceRecord att : dataManager.getAttendanceRecords()) {
                            writer.printf("%d,%d,\"%s\",\"%s\",\"%s\",\"%s\",%.2f%n",
                                att.getId(), att.getEmployeeId(), att.getEmployeeName(),
                                att.getDate(), att.getTimeIn(), att.getTimeOut(), att.getHours());
                        }
                    }
                    statusArea.setText("SUCCESS: Attendance records exported to CSV\nFile: " + file.getName() +
                        " | Records: " + dataManager.getAttendanceRecords().size());
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to export attendance records.\nReason: " + ex.getMessage());
            }
        }
        
        private void importFromCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Import Attendance from CSV");
                
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    int importCount = 0;
                    int errorCount = 0;
                    
                    try (Scanner scanner = new Scanner(file)) {
                        if (scanner.hasNextLine()) {
                            scanner.nextLine(); // Skip header
                        }
                        
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                            
                            try {
                                if (parts.length >= 7) {
                                    AttendanceRecord attendance = new AttendanceRecord(
                                        dataManager.getNextAttendanceId(),
                                        Integer.parseInt(parts[1].trim()),
                                        parts[2].trim().replace("\"", ""),
                                        parts[3].trim().replace("\"", ""),
                                        parts[4].trim().replace("\"", ""),
                                        parts[5].trim().replace("\"", ""),
                                        Double.parseDouble(parts[6].trim())
                                    );
                                    dataManager.addAttendance(attendance);
                                    importCount++;
                                } else {
                                    errorCount++;
                                }
                            } catch (Exception lineEx) {
                                errorCount++;
                            }
                        }
                    }
                    
                    refreshAttendanceTable();
                    statusArea.setText("SUCCESS: Imported " + importCount + " attendance records from CSV | Errors: " + errorCount);
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to import attendance records.\nReason: " + ex.getMessage());
            }
        }
    }
    
    // Payslip Panel with Full CRUD Operations
    class PayslipPanel extends JPanel {
        private JTextField idField, payslipIdField, empIdField, empNameField, basicPayField, deductionsField, netPayField, periodField, searchField;
        private JTable payslipTable;
        private DefaultTableModel tableModel;
        private JTextArea statusArea;
        
        public PayslipPanel() {
            setLayout(new BorderLayout(15, 15)); // Increased outer padding
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Increased outer padding
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            
            // Left Form Panel
            JPanel formPanelWrapper = createPayslipForm();
            JScrollPane formScrollPane = new JScrollPane(formPanelWrapper);
            formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            formScrollPane.setBorder(null);
            formScrollPane.getViewport().setBackground(SECONDARY_COLOR);
            formScrollPane.setMinimumSize(new Dimension(400, 0)); // Adjusted minimum width
            
            // Right Table Panel
            JPanel tablePanelWrapper = createPayslipTable();
            
            splitPane.setLeftComponent(formScrollPane);
            splitPane.setRightComponent(tablePanelWrapper);
            splitPane.setDividerLocation(400); // Adjusted initial divider location
            splitPane.setResizeWeight(0.0); // Form side fixed, table takes all extra space
            splitPane.setBorder(null);
            splitPane.setBackground(SECONDARY_COLOR);
            splitPane.setOneTouchExpandable(true);
            splitPane.setContinuousLayout(true);
            
            add(splitPane, BorderLayout.CENTER);
            add(createPayslipControls(), BorderLayout.SOUTH);
            
            refreshPayslipTable();
        }
        
        private JPanel createPayslipForm() {
            JPanel formCard = new JPanel(new GridBagLayout()) { // Changed to GridBagLayout
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Subtle gradient background
                    GradientPaint gradient = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, getHeight(), new Color(252, 252, 254)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Increased padding
            ));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 0, 12, 0); // Adjusted padding between components
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = GridBagConstraints.REMAINDER; // Each component takes full width
            
            // Enhanced header with consistent styling
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setOpaque(false);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Adjusted bottom padding
            
            JPanel colorAccent = createColoredPanel(PAYSLIP_COLOR, 5, 35); // Restored original accent size
            
            JLabel headerLabel = new JLabel("PAYSLIP INFORMATION");
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Restored original font size
            headerLabel.setForeground(TEXT_PRIMARY);
            headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            headerPanel.add(colorAccent, BorderLayout.WEST);
            headerPanel.add(headerLabel, BorderLayout.CENTER);
            
            gbc.gridy = 0;
            formCard.add(headerPanel, gbc);
            
            // Initialize fields with consistent styling
            idField = createProfessionalTextField(25);
            idField.setEditable(false);
            idField.setBackground(new Color(248, 250, 252));
            payslipIdField = createProfessionalTextField(25);
            empIdField = createProfessionalTextField(25);
            empNameField = createProfessionalTextField(25);
            basicPayField = createProfessionalTextField(25);
            deductionsField = createProfessionalTextField(25);
            netPayField = createProfessionalTextField(25);
            netPayField.setEditable(false);
            netPayField.setBackground(new Color(248, 250, 252));
            periodField = createProfessionalTextField(25);
            
            // Consistent labels across all modules
            String[] labels = {"RECORD ID:", "PAYSLIP ID:", "EMPLOYEE ID:", "EMPLOYEE NAME:", "BASIC PAY:", "DEDUCTIONS:", "NET PAY:", "PERIOD:"};
            JTextField[] fields = {idField, payslipIdField, empIdField, empNameField, basicPayField, deductionsField, netPayField, periodField};
            
            for (int i = 0; i < labels.length; i++) {
                gbc.gridy = i * 2 + 1; // Label row
                JLabel label = new JLabel(labels[i]);
                label.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Restored original font size
                label.setForeground(TEXT_SECONDARY);
                label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // Adjusted padding below label
                formCard.add(label, gbc);
                
                gbc.gridy = i * 2 + 2; // Field row
                gbc.insets = new Insets(0, 0, 18, 0); // Adjusted padding after field
                formCard.add(fields[i], gbc);
                gbc.insets = new Insets(0, 0, 12, 0); // Reset padding for next label
            }
            
            // Add calculate net pay button
            JPanel utilityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10)); // Restored original vertical gap
            utilityPanel.setOpaque(false);
            utilityPanel.add(createProfessionalButton("CALCULATE NET PAY", this::calculateNetPay));
            
            gbc.gridy = labels.length * 2 + 1;
            gbc.insets = new Insets(0, 0, 10, 0); // Adjusted padding
            formCard.add(utilityPanel, gbc);
            
            // Consistent action buttons
            JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Restored original gaps
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0)); // Adjusted padding above buttons
            
            buttonPanel.add(createProfessionalButton("CREATE PAYSLIP", this::savePayslip));
            buttonPanel.add(createProfessionalButton("UPDATE PAYSLIP", this::updatePayslip));
            buttonPanel.add(createProfessionalButton("DELETE PAYSLIP", this::deletePayslip));
            buttonPanel.add(createProfessionalButton("CLEAR FORM", this::clearPayslipForm));
            
            gbc.gridy = labels.length * 2 + 2;
            gbc.insets = new Insets(20, 0, 0, 0); // Adjusted top padding for button panel
            formCard.add(buttonPanel, gbc);
            
            return formCard;
        }
        
        private JPanel createPayslipTable() {
            JPanel tableCard = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Subtle gradient background
                    GradientPaint gradient = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, getHeight(), new Color(252, 252, 254)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Increased padding
            ));
            
            // Enhanced table header with consistent styling
            JPanel tableHeader = new JPanel(new BorderLayout());
            tableHeader.setOpaque(false);
            tableHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Adjusted bottom padding
            
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setOpaque(false);
            
            JPanel colorAccent = createColoredPanel(PAYSLIP_COLOR, 5, 35); // Restored original accent size
            
            JLabel tableTitle = new JLabel("PAYSLIP RECORDS");
            tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Restored original font size
            tableTitle.setForeground(TEXT_PRIMARY);
            tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            titlePanel.add(colorAccent, BorderLayout.WEST);
            titlePanel.add(tableTitle, BorderLayout.CENTER);
            
            // Enhanced search panel
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            searchPanel.setOpaque(false);
            
            searchField = createProfessionalTextField(20);
            searchField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    searchPayslips();
                }
            });
            searchField.setPreferredSize(new Dimension(250, 38));
            searchField.setMaximumSize(new Dimension(300, 38));
            
            JLabel searchLabel = new JLabel("SEARCH RECORDS:");
            searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            searchLabel.setForeground(TEXT_SECONDARY);
            searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
            
            searchPanel.add(searchLabel);
            searchPanel.add(searchField);
            
            tableHeader.add(titlePanel, BorderLayout.WEST);
            tableHeader.add(searchPanel, BorderLayout.EAST);
            
            String[] columns = {"ID", "Payslip ID", "Emp ID", "Employee Name", "Basic Pay", "Deductions", "Net Pay", "Period"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            payslipTable = createProfessionalTable(tableModel);
            payslipTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            payslipTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedPayslip();
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(payslipTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
            scrollPane.getViewport().setBackground(Color.WHITE);
            
            tableCard.add(tableHeader, BorderLayout.NORTH);
            tableCard.add(scrollPane, BorderLayout.CENTER);
            
            return tableCard;
        }
        
        private JPanel createPayslipControls() {
            JPanel controlsPanel = new JPanel(new GridBagLayout());
            controlsPanel.setBackground(SECONDARY_COLOR);
            controlsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.BOTH;
            
            JPanel csvPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
            csvPanel.setOpaque(false);
            
            csvPanel.add(createProfessionalButton("EXPORT TO CSV", this::exportToCSV));
            csvPanel.add(createProfessionalButton("IMPORT FROM CSV", this::importFromCSV));
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.3;
            gbc.anchor = GridBagConstraints.WEST;
            controlsPanel.add(csvPanel, gbc);
            
            statusArea = new JTextArea(3, 60);
            statusArea.setEditable(false);
            statusArea.setBackground(CARD_BG);
            statusArea.setForeground(TEXT_SECONDARY);
            statusArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            statusArea.setLineWrap(true);
            statusArea.setWrapStyleWord(true);
            statusArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDER_COLOR),
                    "System Messages", TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 12), TEXT_PRIMARY),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            
            JScrollPane statusScrollPane = new JScrollPane(statusArea);
            statusScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            statusScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            statusScrollPane.setPreferredSize(new Dimension(300, 80));
            statusScrollPane.setBorder(null);
            
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 0.7;
            gbc.anchor = GridBagConstraints.EAST;
            controlsPanel.add(statusScrollPane, gbc);
            
            return controlsPanel;
        }
        
        private void calculateNetPay(ActionEvent e) {
            try {
                String basicPayText = basicPayField.getText().trim();
                String deductionsText = deductionsField.getText().trim();
                
                if (basicPayText.isEmpty() || deductionsText.isEmpty()) {
                    statusArea.setText("INFO: Please enter both basic pay and deductions to calculate net pay.");
                    return;
                }
                
                double basicPay = Double.parseDouble(basicPayText);
                double deductions = Double.parseDouble(deductionsText);
                double netPay = basicPay - deductions;
                
                netPayField.setText(String.format("%.2f", netPay));
                statusArea.setText("SUCCESS: Net pay calculated successfully\nBasic Pay: ₱" + String.format("%,.2f", basicPay) +
                    " | Deductions: ₱" + String.format("%,.2f", deductions) + " | Net Pay: ₱" + String.format("%,.2f", netPay));
                
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid number format for Basic Pay or Deductions.\nPlease ensure these fields contain valid numeric values.");
            } catch (Exception ex) {
                showErrorMessage("Failed to calculate net pay.\nReason: " + ex.getMessage() +
                    "\nPlease enter valid numeric values.");
            }
        }
        
        private void savePayslip(ActionEvent e) {
            try {
                if (payslipIdField.getText().trim().isEmpty() || empIdField.getText().trim().isEmpty() ||
                    basicPayField.getText().trim().isEmpty() || deductionsField.getText().trim().isEmpty() ||
                    netPayField.getText().trim().isEmpty() || periodField.getText().trim().isEmpty()) {
                    showErrorMessage("All payslip fields are required.");
                    return;
                }
                
                Payslip payslip = new Payslip(
                    dataManager.getNextPayslipId(),
                    Integer.parseInt(payslipIdField.getText().trim()),
                    Integer.parseInt(empIdField.getText().trim()),
                    empNameField.getText().trim(),
                    Double.parseDouble(basicPayField.getText().trim()),
                    Double.parseDouble(deductionsField.getText().trim()),
                    Double.parseDouble(netPayField.getText().trim()),
                    periodField.getText().trim()
                );
                
                dataManager.addPayslip(payslip);
                refreshPayslipTable();
                clearPayslipForm(null);
                statusArea.setText("SUCCESS: Payslip created successfully\nEmployee: " + payslip.getEmployeeName() +
                    " | Period: " + payslip.getPeriod() + " | Net Pay: ₱" + String.format("%,.2f", payslip.getNetPay()));
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid number format for Payslip ID, Employee ID, Basic Pay, Deductions, or Net Pay.\nPlease ensure these fields contain valid digits.");
            } catch (Exception ex) {
                showErrorMessage("Failed to create payslip.\nReason: " + ex.getMessage());
            }
        }
        
        private void updatePayslip(ActionEvent e) {
            try {
                int selectedRow = payslipTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select a payslip to update.");
                    return;
                }
                if (payslipIdField.getText().trim().isEmpty() || empIdField.getText().trim().isEmpty() ||
                    basicPayField.getText().trim().isEmpty() || deductionsField.getText().trim().isEmpty() ||
                    netPayField.getText().trim().isEmpty() || periodField.getText().trim().isEmpty()) {
                    showErrorMessage("All payslip fields are required for update.");
                    return;
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                Payslip payslip = new Payslip(
                    id,
                    Integer.parseInt(payslipIdField.getText().trim()),
                    Integer.parseInt(empIdField.getText().trim()),
                    empNameField.getText().trim(),
                    Double.parseDouble(basicPayField.getText().trim()),
                    Double.parseDouble(deductionsField.getText().trim()),
                    Double.parseDouble(netPayField.getText().trim()),
                    periodField.getText().trim()
                );
                
                dataManager.updatePayslip(id, payslip);
                refreshPayslipTable();
                clearPayslipForm(null);
                statusArea.setText("SUCCESS: Payslip updated successfully\nEmployee: " + payslip.getEmployeeName());
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid number format for Payslip ID, Employee ID, Basic Pay, Deductions, or Net Pay.\nPlease ensure these fields contain valid digits.");
            } catch (Exception ex) {
                showErrorMessage("Failed to update payslip.\nReason: " + ex.getMessage());
            }
        }
        
        private void deletePayslip(ActionEvent e) {
            try {
                int selectedRow = payslipTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select a payslip to delete.");
                    return;
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                
                int confirm = showConfirmDialog("Confirm Payslip Deletion", 
                                                "Are you sure you want to delete this payslip?",
                                                JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dataManager.deletePayslip(id);
                    refreshPayslipTable();
                    clearPayslipForm(null);
                    statusArea.setText("SUCCESS: Payslip deleted successfully");
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to delete payslip.\nReason: " + ex.getMessage());
            }
        }
        
        private void clearPayslipForm(ActionEvent e) {
            idField.setText("");
            payslipIdField.setText("");
            empIdField.setText("");
            empNameField.setText("");
            basicPayField.setText("");
            deductionsField.setText("");
            netPayField.setText("");
            periodField.setText("");
            statusArea.setText("INFO: Payslip form cleared");
            payslipTable.clearSelection();
        }
        
        private void loadSelectedPayslip() {
            int selectedRow = payslipTable.getSelectedRow();
            if (selectedRow >= 0) {
                DefaultTableModel model = (DefaultTableModel) payslipTable.getModel();
                idField.setText(model.getValueAt(selectedRow, 0).toString());
                payslipIdField.setText(model.getValueAt(selectedRow, 1).toString());
                empIdField.setText(model.getValueAt(selectedRow, 2).toString());
                empNameField.setText(model.getValueAt(selectedRow, 3).toString());
                basicPayField.setText(model.getValueAt(selectedRow, 4).toString().replace("₱", "").replace(",", ""));
                deductionsField.setText(model.getValueAt(selectedRow, 5).toString().replace("₱", "").replace(",", ""));
                netPayField.setText(model.getValueAt(selectedRow, 6).toString().replace("₱", "").replace(",", ""));
                periodField.setText(model.getValueAt(selectedRow, 7).toString());
                statusArea.setText("INFO: Loaded payslip for " + model.getValueAt(selectedRow, 3).toString());
            }
        }
        
        private void searchPayslips() {
            String searchTerm = searchField.getText().toLowerCase().trim();
            
            tableModel.setRowCount(0);
            int matchCount = 0;
            for (Payslip pay : dataManager.getPayslips()) {
                if (pay.getEmployeeName().toLowerCase().contains(searchTerm) ||
                    pay.getPeriod().toLowerCase().contains(searchTerm) ||
                    String.valueOf(pay.getPayslipId()).contains(searchTerm) ||
                    String.valueOf(pay.getEmployeeId()).contains(searchTerm) ||
                    String.valueOf(pay.getId()).contains(searchTerm)) {
                    tableModel.addRow(pay.toTableRow());
                    matchCount++;
                }
            }
            statusArea.setText("SEARCH: Found " + matchCount + " payslips matching '" + searchTerm + "'");
            if (searchTerm.isEmpty()) {
                statusArea.setText("INFO: Payslip search cleared. Showing all records.");
            }
        }
        
        private void refreshPayslipTable() {
            tableModel.setRowCount(0);
            for (Payslip pay : dataManager.getPayslips()) {
                tableModel.addRow(pay.toTableRow());
            }
        }
        
        private void exportToCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Export Payslips to CSV");
                fileChooser.setSelectedFile(new File("MotorPH_Payslips_" +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv"));
                
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                        writer.println("ID,PayslipID,EmployeeID,EmployeeName,BasicPay,Deductions,NetPay,Period");
                        
                        for (Payslip pay : dataManager.getPayslips()) {
                            writer.printf("%d,%d,%d,\"%s\",%.2f,%.2f,%.2f,\"%s\"%n",
                                pay.getId(), pay.getPayslipId(), pay.getEmployeeId(), pay.getEmployeeName(),
                                pay.getBasicPay(), pay.getDeductions(), pay.getNetPay(), pay.getPeriod());
                        }
                    }
                    statusArea.setText("SUCCESS: Payslips exported to CSV\nFile: " + file.getName() +
                        " | Records: " + dataManager.getPayslips().size());
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to export payslips.\nReason: " + ex.getMessage());
            }
        }
        
        private void importFromCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Import Payslips from CSV");
                
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    int importCount = 0;
                    int errorCount = 0;
                    
                    try (Scanner scanner = new Scanner(file)) {
                        if (scanner.hasNextLine()) {
                            scanner.nextLine(); // Skip header
                        }
                        
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                            
                            try {
                                if (parts.length >= 8) {
                                    Payslip payslip = new Payslip(
                                        dataManager.getNextPayslipId(),
                                        Integer.parseInt(parts[1].trim()),
                                        Integer.parseInt(parts[2].trim()),
                                        parts[3].trim().replace("\"", ""),
                                        Double.parseDouble(parts[4].trim()),
                                        Double.parseDouble(parts[5].trim()),
                                        Double.parseDouble(parts[6].trim()),
                                        parts[7].trim().replace("\"", "")
                                    );
                                    dataManager.addPayslip(payslip);
                                    importCount++;
                                } else {
                                    errorCount++;
                                }
                            } catch (Exception lineEx) {
                                errorCount++;
                            }
                        }
                    }
                    
                    refreshPayslipTable();
                    statusArea.setText("SUCCESS: Imported " + importCount + " payslips from CSV | Errors: " + errorCount);
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to import payslips.\nReason: " + ex.getMessage());
            }
        }
    }
    
    // Leave Request Panel with Full CRUD Operations
    class LeaveRequestPanel extends JPanel {
        private JTextField idField, requestIdField, empIdField, empNameField, leaveTypeField, startDateField, endDateField, daysField, reasonField, statusField, searchField;
        private JTable leaveTable;
        private DefaultTableModel tableModel;
        private JTextArea statusArea;
        
        public LeaveRequestPanel() {
            setLayout(new BorderLayout(15, 15)); // Increased outer padding
            setBackground(SECONDARY_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Increased outer padding
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            
            // Left Form Panel
            JPanel formPanelWrapper = createLeaveForm();
            JScrollPane formScrollPane = new JScrollPane(formPanelWrapper);
            formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            formScrollPane.setBorder(null);
            formScrollPane.getViewport().setBackground(SECONDARY_COLOR);
            formScrollPane.setMinimumSize(new Dimension(400, 0)); // Adjusted minimum width
            
            // Right Table Panel
            JPanel tablePanelWrapper = createLeaveTable();
            
            splitPane.setLeftComponent(formScrollPane);
            splitPane.setRightComponent(tablePanelWrapper);
            splitPane.setDividerLocation(400); // Adjusted initial divider location
            splitPane.setResizeWeight(0.0); // Form side fixed, table takes all extra space
            splitPane.setBorder(null);
            splitPane.setBackground(SECONDARY_COLOR);
            splitPane.setOneTouchExpandable(true);
            splitPane.setContinuousLayout(true);
            
            add(splitPane, BorderLayout.CENTER);
            add(createLeaveControls(), BorderLayout.SOUTH);
            
            refreshLeaveTable();
        }
        
        private JPanel createLeaveForm() {
            JPanel formCard = new JPanel(new GridBagLayout()) { // Changed to GridBagLayout
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Subtle gradient background
                    GradientPaint gradient = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, getHeight(), new Color(252, 252, 254)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Increased padding
            ));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 0, 12, 0); // Adjusted padding between components
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = GridBagConstraints.REMAINDER; // Each component takes full width
            
            // Enhanced header with consistent styling
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setOpaque(false);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Adjusted bottom padding
            
            JPanel colorAccent = createColoredPanel(LEAVE_COLOR, 5, 35); // Restored original accent size
            
            JLabel headerLabel = new JLabel("LEAVE REQUEST INFORMATION");
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Restored original font size
            headerLabel.setForeground(TEXT_PRIMARY);
            headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            headerPanel.add(colorAccent, BorderLayout.WEST);
            headerPanel.add(headerLabel, BorderLayout.CENTER);
            
            gbc.gridy = 0;
            formCard.add(headerPanel, gbc);
            
            // Initialize fields with consistent styling
            idField = createProfessionalTextField(25);
            idField.setEditable(false);
            idField.setBackground(new Color(248, 250, 252));
            requestIdField = createProfessionalTextField(25);
            empIdField = createProfessionalTextField(25);
            empNameField = createProfessionalTextField(25);
            leaveTypeField = createProfessionalTextField(25);
            startDateField = createProfessionalTextField(25);
            endDateField = createProfessionalTextField(25);
            daysField = createProfessionalTextField(25);
            daysField.setEditable(false);
            daysField.setBackground(new Color(248, 250, 252));
            reasonField = createProfessionalTextField(25);
            statusField = createProfessionalTextField(25);
            
            // Set default status
            statusField.setText("Pending");
            
            // Consistent labels across all modules
            String[] labels = {"RECORD ID:", "REQUEST ID:", "EMPLOYEE ID:", "EMPLOYEE NAME:", "LEAVE TYPE:", "START DATE (YYYY-MM-DD):", "END DATE (YYYY-MM-DD):", "DAYS:", "REASON:", "STATUS:"};
            JTextField[] fields = {idField, requestIdField, empIdField, empNameField, leaveTypeField, startDateField, endDateField, daysField, reasonField, statusField};
            
            for (int i = 0; i < labels.length; i++) {
                gbc.gridy = i * 2 + 1; // Label row
                JLabel label = new JLabel(labels[i]);
                label.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Restored original font size
                label.setForeground(TEXT_SECONDARY);
                label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // Adjusted padding below label
                formCard.add(label, gbc);
                
                gbc.gridy = i * 2 + 2; // Field row
                gbc.insets = new Insets(0, 0, 18, 0); // Adjusted padding after field
                formCard.add(fields[i], gbc);
                gbc.insets = new Insets(0, 0, 12, 0); // Reset padding for next label
            }
            
            // Add calculate days button
            JPanel utilityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10)); // Restored original vertical gap
            utilityPanel.setOpaque(false);
            utilityPanel.add(createProfessionalButton("CALCULATE DAYS", this::calculateDays));
            
            gbc.gridy = labels.length * 2 + 1;
            gbc.insets = new Insets(0, 0, 10, 0); // Adjusted padding
            formCard.add(utilityPanel, gbc);
            
            // Consistent action buttons
            JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Restored original gaps
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0)); // Adjusted padding above buttons
            
            buttonPanel.add(createProfessionalButton("CREATE LEAVE REQUEST", this::saveLeaveRequest));
            buttonPanel.add(createProfessionalButton("UPDATE LEAVE REQUEST", this::updateLeaveRequest));
            buttonPanel.add(createProfessionalButton("DELETE LEAVE REQUEST", this::deleteLeaveRequest));
            buttonPanel.add(createProfessionalButton("CLEAR FORM", this::clearLeaveForm));
            
            gbc.gridy = labels.length * 2 + 2;
            gbc.insets = new Insets(20, 0, 0, 0); // Adjusted top padding for button panel
            formCard.add(buttonPanel, gbc);
            
            return formCard;
        }
        
        private JPanel createLeaveTable() {
            JPanel tableCard = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Subtle gradient background
                    GradientPaint gradient = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, getHeight(), new Color(252, 252, 254)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Increased padding
            ));
            
            // Enhanced table header with consistent styling
            JPanel tableHeader = new JPanel(new BorderLayout());
            tableHeader.setOpaque(false);
            tableHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Adjusted bottom padding
            
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setOpaque(false);
            
            JPanel colorAccent = createColoredPanel(LEAVE_COLOR, 5, 35); // Restored original accent size
            
            JLabel tableTitle = new JLabel("LEAVE REQUEST RECORDS");
            tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Restored original font size
            tableTitle.setForeground(TEXT_PRIMARY);
            tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            titlePanel.add(colorAccent, BorderLayout.WEST);
            titlePanel.add(tableTitle, BorderLayout.CENTER);
            
            // Enhanced search panel
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            searchPanel.setOpaque(false);
            
            searchField = createProfessionalTextField(20);
            searchField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    searchLeaveRequests();
                }
            });
            searchField.setPreferredSize(new Dimension(250, 38));
            searchField.setMaximumSize(new Dimension(300, 38));
            
            JLabel searchLabel = new JLabel("SEARCH RECORDS:");
            searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            searchLabel.setForeground(TEXT_SECONDARY);
            searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
            
            searchPanel.add(searchLabel);
            searchPanel.add(searchField);
            
            tableHeader.add(titlePanel, BorderLayout.WEST);
            tableHeader.add(searchPanel, BorderLayout.EAST);
            
            String[] columns = {"ID", "Request ID", "Emp ID", "Employee Name", "Leave Type", "Start Date", "End Date", "Days", "Reason", "Status"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            leaveTable = createProfessionalTable(tableModel);
            leaveTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            leaveTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedLeaveRequest();
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(leaveTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
            scrollPane.getViewport().setBackground(Color.WHITE);
            
            tableCard.add(tableHeader, BorderLayout.NORTH);
            tableCard.add(scrollPane, BorderLayout.CENTER);
            
            return tableCard;
        }
        
        private JPanel createLeaveControls() {
            JPanel controlsPanel = new JPanel(new GridBagLayout());
            controlsPanel.setBackground(SECONDARY_COLOR);
            controlsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.BOTH;
            
            JPanel csvPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Adjusted horizontal gap
            csvPanel.setOpaque(false);
            
            csvPanel.add(createProfessionalButton("EXPORT TO CSV", this::exportToCSV));
            csvPanel.add(createProfessionalButton("IMPORT FROM CSV", this::importFromCSV));
            csvPanel.add(createProfessionalButton("APPROVE", this::approveLeave));
            csvPanel.add(createProfessionalButton("REJECT", this::rejectLeave));
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.5; // Give CSV/Action buttons more space
            gbc.anchor = GridBagConstraints.WEST;
            controlsPanel.add(csvPanel, gbc);
            
            statusArea = new JTextArea(3, 60);
            statusArea.setEditable(false);
            statusArea.setBackground(CARD_BG);
            statusArea.setForeground(TEXT_SECONDARY);
            statusArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            statusArea.setLineWrap(true);
            statusArea.setWrapStyleWord(true);
            statusArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDER_COLOR),
                    "System Messages", TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 12), TEXT_PRIMARY),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            
            JScrollPane statusScrollPane = new JScrollPane(statusArea);
            statusScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            statusScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            statusScrollPane.setPreferredSize(new Dimension(300, 80));
            statusScrollPane.setBorder(null);
            
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 0.5; // Status area takes remaining space
            gbc.anchor = GridBagConstraints.EAST;
            controlsPanel.add(statusScrollPane, gbc);
            
            return controlsPanel;
        }
        
        private void calculateDays(ActionEvent e) {
            try {
                String startDateStr = startDateField.getText().trim();
                String endDateStr = endDateField.getText().trim();
                
                if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
                    statusArea.setText("INFO: Please enter both start and end dates to calculate days.");
                    return;
                }
                
                LocalDate start = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate end = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                
                if (end.isBefore(start)) {
                    showErrorMessage("End date cannot be before start date.");
                    return;
                }
                
                long days = java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1; // Include end date
                
                daysField.setText(String.valueOf(days));
                statusArea.setText("SUCCESS: Leave days calculated successfully\nDays requested: " + days +
                    " | From: " + startDateStr + " | To: " + endDateStr);
                
            } catch (Exception ex) {
                showErrorMessage("Failed to calculate days.\nReason: " + ex.getMessage() +
                    "\nPlease use format YYYY-MM-DD (e.g., 2025-07-15).");
            }
        }
        
        private void saveLeaveRequest(ActionEvent e) {
            try {
                if (requestIdField.getText().trim().isEmpty() || empIdField.getText().trim().isEmpty() ||
                    empNameField.getText().trim().isEmpty() || leaveTypeField.getText().trim().isEmpty() ||
                    startDateField.getText().trim().isEmpty() || endDateField.getText().trim().isEmpty() ||
                    daysField.getText().trim().isEmpty() || reasonField.getText().trim().isEmpty() ||
                    statusField.getText().trim().isEmpty()) {
                    showErrorMessage("All leave request fields are required.");
                    return;
                }
                
                LeaveRequest leave = new LeaveRequest(
                    dataManager.getNextLeaveRequestId(),
                    Integer.parseInt(requestIdField.getText().trim()),
                    Integer.parseInt(empIdField.getText().trim()),
                    empNameField.getText().trim(),
                    leaveTypeField.getText().trim(),
                    startDateField.getText().trim(),
                    endDateField.getText().trim(),
                    Integer.parseInt(daysField.getText().trim()),
                    reasonField.getText().trim(),
                    statusField.getText().trim()
                );
                
                dataManager.addLeaveRequest(leave);
                refreshLeaveTable();
                clearLeaveForm(null);
                statusArea.setText("SUCCESS: Leave request created\nEmployee: " + leave.getEmployeeName() +
                    " | Type: " + leave.getLeaveType() + " | Days: " + leave.getDays() + " | Status: " + leave.getStatus());
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid number format for Request ID, Employee ID, or Days.\nPlease ensure these fields contain valid digits.");
            } catch (Exception ex) {
                showErrorMessage("Failed to create leave request.\nReason: " + ex.getMessage());
            }
        }
        
        private void updateLeaveRequest(ActionEvent e) {
            try {
                int selectedRow = leaveTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select a leave request to update.");
                    return;
                }
                if (requestIdField.getText().trim().isEmpty() || empIdField.getText().trim().isEmpty() ||
                    empNameField.getText().trim().isEmpty() || leaveTypeField.getText().trim().isEmpty() ||
                    startDateField.getText().trim().isEmpty() || endDateField.getText().trim().isEmpty() ||
                    daysField.getText().trim().isEmpty() || reasonField.getText().trim().isEmpty() ||
                    statusField.getText().trim().isEmpty()) {
                    showErrorMessage("All leave request fields are required for update.");
                    return;
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                LeaveRequest leave = new LeaveRequest(
                    id,
                    Integer.parseInt(requestIdField.getText().trim()),
                    Integer.parseInt(empIdField.getText().trim()),
                    empNameField.getText().trim(),
                    leaveTypeField.getText().trim(),
                    startDateField.getText().trim(),
                    endDateField.getText().trim(),
                    Integer.parseInt(daysField.getText().trim()),
                    reasonField.getText().trim(),
                    statusField.getText().trim()
                );
                
                dataManager.updateLeaveRequest(id, leave);
                refreshLeaveTable();
                clearLeaveForm(null);
                statusArea.setText("SUCCESS: Leave request updated\nEmployee: " + leave.getEmployeeName() +
                    " | Status: " + leave.getStatus());
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid number format for Request ID, Employee ID, or Days.\nPlease ensure these fields contain valid digits.");
            } catch (Exception ex) {
                showErrorMessage("Failed to update leave request.\nReason: " + ex.getMessage());
            }
        }
        
        private void deleteLeaveRequest(ActionEvent e) {
            try {
                int selectedRow = leaveTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select a leave request to delete.");
                    return;
                }
                
                int id = Integer.parseInt(idField.getText().trim());
                
                int confirm = showConfirmDialog("Confirm Leave Request Deletion", 
                                                "Are you sure you want to delete this leave request?",
                                                JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dataManager.deleteLeaveRequest(id);
                    refreshLeaveTable();
                    clearLeaveForm(null);
                    statusArea.setText("SUCCESS: Leave request deleted successfully");
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to delete leave request.\nReason: " + ex.getMessage());
            }
        }
        
        private void approveLeave(ActionEvent e) {
            try {
                int selectedRow = leaveTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select a leave request to approve.");
                    return;
                }
                
                // Directly update the status field and then call update
                statusField.setText("Approved");
                updateLeaveRequest(e); // This will refresh table and status area
                statusArea.setText("SUCCESS: Leave request approved for " + empNameField.getText());
            } catch (Exception ex) {
                showErrorMessage("Failed to approve leave request.\nReason: " + ex.getMessage());
            }
        }
        
        private void rejectLeave(ActionEvent e) {
            try {
                int selectedRow = leaveTable.getSelectedRow();
                if (selectedRow < 0) {
                    showErrorMessage("Please select a leave request to reject.");
                    return;
                }
                
                // Directly update the status field and then call update
                statusField.setText("Rejected");
                updateLeaveRequest(e); // This will refresh table and status area
                statusArea.setText("SUCCESS: Leave request rejected for " + empNameField.getText());
            } catch (Exception ex) {
                showErrorMessage("Failed to reject leave request.\nReason: " + ex.getMessage());
            }
        }
        
        private void clearLeaveForm(ActionEvent e) {
            idField.setText("");
            requestIdField.setText("");
            empIdField.setText("");
            empNameField.setText("");
            leaveTypeField.setText("");
            startDateField.setText("");
            endDateField.setText("");
            daysField.setText("");
            reasonField.setText("");
            statusField.setText("Pending");
            statusArea.setText("INFO: Leave request form cleared");
            leaveTable.clearSelection();
        }
        
        private void loadSelectedLeaveRequest() {
            int selectedRow = leaveTable.getSelectedRow();
            if (selectedRow >= 0) {
                DefaultTableModel model = (DefaultTableModel) leaveTable.getModel();
                idField.setText(model.getValueAt(selectedRow, 0).toString());
                requestIdField.setText(model.getValueAt(selectedRow, 1).toString());
                empIdField.setText(model.getValueAt(selectedRow, 2).toString());
                empNameField.setText(model.getValueAt(selectedRow, 3).toString());
                leaveTypeField.setText(model.getValueAt(selectedRow, 4).toString());
                startDateField.setText(model.getValueAt(selectedRow, 5).toString());
                endDateField.setText(model.getValueAt(selectedRow, 6).toString());
                daysField.setText(model.getValueAt(selectedRow, 7).toString());
                reasonField.setText(model.getValueAt(selectedRow, 8).toString());
                statusField.setText(model.getValueAt(selectedRow, 9).toString());
                statusArea.setText("INFO: Loaded leave request for " + model.getValueAt(selectedRow, 3).toString());
            }
        }
        
        private void searchLeaveRequests() {
            String searchTerm = searchField.getText().toLowerCase().trim();
            
            tableModel.setRowCount(0);
            int matchCount = 0;
            for (LeaveRequest leave : dataManager.getLeaveRequests()) {
                if (leave.getEmployeeName().toLowerCase().contains(searchTerm) ||
                    leave.getLeaveType().toLowerCase().contains(searchTerm) ||
                    leave.getStatus().toLowerCase().contains(searchTerm) ||
                    String.valueOf(leave.getRequestId()).contains(searchTerm) ||
                    String.valueOf(leave.getEmployeeId()).contains(searchTerm) ||
                    leave.getStartDate().toLowerCase().contains(searchTerm) ||
                    leave.getEndDate().toLowerCase().contains(searchTerm)) {
                    tableModel.addRow(leave.toTableRow());
                    matchCount++;
                }
            }
            statusArea.setText("SEARCH: Found " + matchCount + " leave requests matching '" + searchTerm + "'");
            if (searchTerm.isEmpty()) {
                statusArea.setText("INFO: Leave request search cleared. Showing all records.");
            }
        }
        
        private void refreshLeaveTable() {
            tableModel.setRowCount(0);
            for (LeaveRequest leave : dataManager.getLeaveRequests()) {
                tableModel.addRow(leave.toTableRow());
            }
        }
        
        private void exportToCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Export Leave Requests to CSV");
                fileChooser.setSelectedFile(new File("MotorPH_LeaveRequests_" +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv"));
                
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                        writer.println("ID,RequestID,EmployeeID,EmployeeName,LeaveType,StartDate,EndDate,Days,Reason,Status");
                        
                        for (LeaveRequest leave : dataManager.getLeaveRequests()) {
                            writer.printf("%d,%d,%d,\"%s\",\"%s\",\"%s\",\"%s\",%d,\"%s\",\"%s\"%n",
                                leave.getId(), leave.getRequestId(), leave.getEmployeeId(), leave.getEmployeeName(),
                                leave.getLeaveType(), leave.getStartDate(), leave.getEndDate(), leave.getDays(),
                                leave.getReason(), leave.getStatus());
                        }
                    }
                    statusArea.setText("SUCCESS: Leave requests exported to CSV\nFile: " + file.getName() +
                        " | Records: " + dataManager.getLeaveRequests().size());
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to export leave requests.\nReason: " + ex.getMessage());
            }
        }
        
        private void importFromCSV(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Import Leave Requests from CSV");
                
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    int importCount = 0;
                    int errorCount = 0;
                    
                    try (Scanner scanner = new Scanner(file)) {
                        if (scanner.hasNextLine()) {
                            scanner.nextLine(); // Skip header
                        }
                        
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                            
                            try {
                                if (parts.length >= 10) {
                                    LeaveRequest leave = new LeaveRequest(
                                        dataManager.getNextLeaveRequestId(),
                                        Integer.parseInt(parts[1].trim()),
                                        Integer.parseInt(parts[2].trim()),
                                        parts[3].trim().replace("\"", ""),
                                        parts[4].trim().replace("\"", ""),
                                        parts[5].trim().replace("\"", ""),
                                        parts[6].trim().replace("\"", ""),
                                        Integer.parseInt(parts[7].trim()),
                                        parts[8].trim().replace("\"", ""),
                                        parts[9].trim().replace("\"", "")
                                    );
                                    dataManager.addLeaveRequest(leave);
                                    importCount++;
                                } else {
                                    errorCount++;
                                }
                            } catch (Exception lineEx) {
                                errorCount++;
                            }
                        }
                    }
                    
                    refreshLeaveTable();
                    statusArea.setText("SUCCESS: Imported " + importCount + " leave requests from CSV | Errors: " + errorCount);
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to import leave requests.\nReason: " + ex.getMessage());
            }
        }
    }
    
    // Custom error/confirm dialogs to replace JOptionPane for better control and eventual customization
    private void showErrorMessage(String message) {
        // Implement a custom JPanel message box here if JOptionPane is undesired,
        // for now, using JOptionPane.showMessageDialog as it's safe and standard.
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        if (statusArea != null) { // Update status area with error
            statusArea.setText("ERROR: " + message);
        }
    }

    private int showConfirmDialog(String title, String message, int messageType) {
        // Implement a custom JPanel confirmation box here if JOptionPane is undesired.
        // For now, using JOptionPane.showConfirmDialog as it's safe and standard.
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, messageType);
    }

    // Data Model Classes (unchanged as per query focus on UI)
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
            return new Object[]{id, employeeID, fullName, position, department, String.format("₱%,.2f", salary)};
        }
    }
    
    class Department {
        int id;
        String name, manager, location;
        
        public Department(int id, String name, String manager, String location) {
            this.id = id;
            this.name = name;
            this.manager = manager;
            this.location = location;
        }
        
        public int getId() { return id; }
        public String getName() { return name; }
        public String getManager() { return manager; }
        public String getLocation() { return location; }
        
        public Object[] toTableRow() {
            return new Object[]{id, name, manager, location};
        }
    }
    
    class AttendanceRecord {
        int id, employeeId;
        String employeeName, date, timeIn, timeOut;
        double hours;
        
        public AttendanceRecord(int id, int employeeId, String employeeName, String date, String timeIn, String timeOut, double hours) {
            this.id = id;
            this.employeeId = employeeId;
            this.employeeName = employeeName;
            this.date = date;
            this.timeIn = timeIn;
            this.timeOut = timeOut;
            this.hours = hours;
        }
        
        public int getId() { return id; }
        public int getEmployeeId() { return employeeId; }
        public String getEmployeeName() { return employeeName; }
        public String getDate() { return date; }
        public String getTimeIn() { return timeIn; }
        public String getTimeOut() { return timeOut; }
        public double getHours() { return hours; }
        
        public Object[] toTableRow() {
            return new Object[]{id, employeeId, employeeName, date, timeIn, timeOut, String.format("%.2f", hours)};
        }
    }
    
    class Payslip {
        int id, payslipId, employeeId;
        String employeeName, period;
        double basicPay, deductions, netPay;
        
        public Payslip(int id, int payslipId, int employeeId, String employeeName, double basicPay, double deductions, double netPay, String period) {
            this.id = id;
            this.payslipId = payslipId;
            this.employeeId = employeeId;
            this.employeeName = employeeName;
            this.basicPay = basicPay;
            this.deductions = deductions;
            this.netPay = netPay;
            this.period = period;
        }
        
        public int getId() { return id; }
        public int getPayslipId() { return payslipId; }
        public int getEmployeeId() { return employeeId; }
        public String getEmployeeName() { return employeeName; }
        public double getBasicPay() { return basicPay; }
        public double getDeductions() { return deductions; }
        public double getNetPay() { return netPay; }
        public String getPeriod() { return period; }
        
        public Object[] toTableRow() {
            return new Object[]{id, payslipId, employeeId, employeeName,
                String.format("₱%,.2f", basicPay), String.format("₱%,.2f", deductions),
                String.format("₱%,.2f", netPay), period};
        }
    }
    
    class LeaveRequest {
        int id, requestId, employeeId, days;
        String employeeName, leaveType, startDate, endDate, reason, status;
        
        public LeaveRequest(int id, int requestId, int employeeId, String employeeName, String leaveType, String startDate, String endDate, int days, String reason, String status) {
            this.id = id;
            this.requestId = requestId;
            this.employeeId = employeeId;
            this.employeeName = employeeName;
            this.leaveType = leaveType;
            this.startDate = startDate;
            this.endDate = endDate;
            this.days = days;
            this.reason = reason;
            this.status = status;
        }
        
        public int getId() { return id; }
        public int getRequestId() { return requestId; }
        public int getEmployeeId() { return employeeId; }
        public String getEmployeeName() { return employeeName; }
        public String getLeaveType() { return leaveType; }
        public String getStartDate() { return startDate; }
        public String getEndDate() { return endDate; }
        public int getDays() { return days; }
        public String getReason() { return reason; }
        public String getStatus() { return status; }
        
        public Object[] toTableRow() {
            return new Object[]{id, requestId, employeeId, employeeName, leaveType, startDate, endDate, days, reason, status};
        }
    }
    
    // Data Manager Class
    class DataManager {
        private List<Employee> employees = new ArrayList<>();
        private List<Department> departments = new ArrayList<>();
        private List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        private List<Payslip> payslips = new ArrayList<>();
        private List<LeaveRequest> leaveRequests = new ArrayList<>();
        
        private int nextEmployeeId = 1;
        private int nextDepartmentId = 1;
        private int nextAttendanceId = 1;
        private int nextPayslipId = 1;
        private int nextLeaveRequestId = 1;
        
        public DataManager() {
            initializeSampleData();
        }
        
        private void initializeSampleData() {
            // Professional sample data for employees
            employees.add(new Employee(nextEmployeeId++, 1001, "Juan Miguel Dela Cruz", "Senior Software Developer", "Information Technology", 75000.00));
            employees.add(new Employee(nextEmployeeId++, 1002, "Maria Isabella Santos", "Human Resources Manager", "Human Resources", 85000.00));
            employees.add(new Employee(nextEmployeeId++, 1003, "Pedro Antonio Garcia", "Senior Financial Analyst", "Finance & Accounting", 70000.00));
            employees.add(new Employee(nextEmployeeId++, 1004, "Ana Sophia Rodriguez", "Operations Supervisor", "Operations Management", 78000.00));
            employees.add(new Employee(nextEmployeeId++, 1005, "Luis Fernando Martinez", "System Administrator", "Information Technology", 72000.00));
            employees.add(new Employee(nextEmployeeId++, 1006, "Carmen Elena Villanueva", "Marketing Specialist", "Marketing & Sales", 65000.00));
            employees.add(new Employee(nextEmployeeId++, 1007, "Roberto Carlos Mendoza", "Quality Assurance Engineer", "Quality Control", 68000.00));
            employees.add(new Employee(nextEmployeeId++, 1008, "Sofia Grace Reyes", "Executive Assistant", "Executive Office", 55000.00));
            
            // Sample departments
            departments.add(new Department(nextDepartmentId++, "Information Technology", "John Smith", "Building A - Floor 3"));
            departments.add(new Department(nextDepartmentId++, "Human Resources", "Sarah Johnson", "Building B - Floor 2"));
            departments.add(new Department(nextDepartmentId++, "Finance & Accounting", "Michael Brown", "Building C - Floor 1"));
            departments.add(new Department(nextDepartmentId++, "Operations Management", "Emily Davis", "Building D - Floor 2"));
            departments.add(new Department(nextDepartmentId++, "Marketing & Sales", "David Wilson", "Building B - Floor 3"));
            departments.add(new Department(nextDepartmentId++, "Quality Control", "Lisa Anderson", "Building A - Floor 2"));
            
            // Sample attendance records
            attendanceRecords.add(new AttendanceRecord(nextAttendanceId++, 1001, "Juan Miguel Dela Cruz", "2025-06-27", "08:00", "17:00", 8.0));
            attendanceRecords.add(new AttendanceRecord(nextAttendanceId++, 1002, "Maria Isabella Santos", "2025-06-27", "08:30", "17:30", 8.0));
            attendanceRecords.add(new AttendanceRecord(nextAttendanceId++, 1003, "Pedro Antonio Garcia", "2025-06-27", "09:00", "18:00", 8.0));
            attendanceRecords.add(new AttendanceRecord(nextAttendanceId++, 1004, "Ana Sophia Rodriguez", "2025-06-27", "08:15", "17:15", 8.0));
            attendanceRecords.add(new AttendanceRecord(nextAttendanceId++, 1001, "Juan Miguel Dela Cruz", "2025-06-26", "08:00", "17:30", 8.5));
            
            // Sample payslips
            payslips.add(new Payslip(nextPayslipId++, 2001, 1001, "Juan Miguel Dela Cruz", 75000.00, 8000.00, 67000.00, "June 2025"));
            payslips.add(new Payslip(nextPayslipId++, 2002, 1002, "Maria Isabella Santos", 85000.00, 9500.00, 75500.00, "June 2025"));
            payslips.add(new Payslip(nextPayslipId++, 2003, 1003, "Pedro Antonio Garcia", 70000.00, 7500.00, 62500.00, "June 2025"));
            payslips.add(new Payslip(nextPayslipId++, 2004, 1004, "Ana Sophia Rodriguez", 78000.00, 8200.00, 69800.00, "June 2025"));
            
            // Sample leave requests
            leaveRequests.add(new LeaveRequest(nextLeaveRequestId++, 3001, 1001, "Juan Miguel Dela Cruz", "Vacation Leave", "2025-07-01", "2025-07-05", 5, "Family vacation", "Pending"));
            leaveRequests.add(new LeaveRequest(nextLeaveRequestId++, 3002, 1002, "Maria Isabella Santos", "Sick Leave", "2025-06-30", "2025-07-01", 2, "Medical checkup", "Approved"));
            leaveRequests.add(new LeaveRequest(nextLeaveRequestId++, 3003, 1003, "Pedro Antonio Garcia", "Personal Leave", "2025-07-10", "2025-07-12", 3, "Personal matters", "Pending"));
            leaveRequests.add(new LeaveRequest(nextLeaveRequestId++, 3004, 1005, "Luis Fernando Martinez", "Emergency Leave", "2025-06-28", "2025-06-28", 1, "Family emergency", "Approved"));
        }
        
        // Employee CRUD operations
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
        
        // Department CRUD operations
        public List<Department> getDepartments() { return departments; }
        public int getNextDepartmentId() { return nextDepartmentId++; }
        public void addDepartment(Department department) { departments.add(department); }
        
        public void updateDepartment(int id, Department department) {
            for (int i = 0; i < departments.size(); i++) {
                if (departments.get(i).getId() == id) {
                    departments.set(i, department);
                    break;
                }
            }
        }
        
        public void deleteDepartment(int id) {
            departments.removeIf(dept -> dept.getId() == id);
        }
        
        public Department findDepartmentById(int id) {
            return departments.stream().filter(dept -> dept.getId() == id).findFirst().orElse(null);
        }
        
        // Attendance CRUD operations
        public List<AttendanceRecord> getAttendanceRecords() { return attendanceRecords; }
        public int getNextAttendanceId() { return nextAttendanceId++; }
        public void addAttendance(AttendanceRecord attendance) { attendanceRecords.add(attendance); }
        
        public void updateAttendance(int id, AttendanceRecord attendance) {
            for (int i = 0; i < attendanceRecords.size(); i++) {
                if (attendanceRecords.get(i).getId() == id) {
                    attendanceRecords.set(i, attendance);
                    break;
                }
            }
        }
        
        public void deleteAttendance(int id) {
            attendanceRecords.removeIf(att -> att.getId() == id);
        }
        
        // Payslip CRUD operations
        public List<Payslip> getPayslips() { return payslips; }
        public int getNextPayslipId() { return nextPayslipId++; }
        public void addPayslip(Payslip payslip) { payslips.add(payslip); }
        
        public void updatePayslip(int id, Payslip payslip) {
            for (int i = 0; i < payslips.size(); i++) {
                if (payslips.get(i).getId() == id) {
                    payslips.set(i, payslip);
                    break;
                }
            }
        }
        
        public void deletePayslip(int id) {
            payslips.removeIf(pay -> pay.getId() == id);
        }
        
        // Leave Request CRUD operations
        public List<LeaveRequest> getLeaveRequests() { return leaveRequests; }
        public int getNextLeaveRequestId() { return nextLeaveRequestId++; }
        public void addLeaveRequest(LeaveRequest leaveRequest) { leaveRequests.add(leaveRequest); }
        
        public void updateLeaveRequest(int id, LeaveRequest leaveRequest) {
            for (int i = 0; i < leaveRequests.size(); i++) {
                if (leaveRequests.get(i).getId() == id) {
                    leaveRequests.set(i, leaveRequest);
                    break;
                }
            }
        }
        
        public void deleteLeaveRequest(int id) {
            leaveRequests.removeIf(leave -> leave.getId() == id);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for native appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Project();
        });
    }
}
