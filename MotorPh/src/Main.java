public class Main {
    public static void main(String[] args) {
        Employee emp = new Employee(1001, "Juan", "Dela Cruz", "1990-05-15", "09171234567",
                "Makati", "Software Engineer", "IT");

        AttendanceRecord record = new AttendanceRecord("2025-06-06", "08:15", "17:00", emp);
        System.out.println("Hours Worked: " + record.calculateHoursWorked());
        System.out.println("Is Late? " + record.isLate());

        Payslip payslip = new Payslip(1, emp, 40000, 5000, "June 1-15, 2025");
        System.out.println(payslip.generatePayslip());

        LeaveRequest leave = new LeaveRequest(101, emp, "Vacation", "2025-06-10", "2025-06-12");
        leave.approveLeave();
        System.out.println("Leave Status: " + leave.getStatus());
        System.out.println("Leave Days: " + leave.calculateLeaveDays());

        Department dept = new Department("IT", "Maria Santos");
        System.out.println(dept.getDepartmentInfo());
    }
}
