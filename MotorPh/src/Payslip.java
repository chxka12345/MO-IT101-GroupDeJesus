public class Payslip {
    private int payslipID;
    private Employee employee;
    private double basicSalary;
    private double deductions;
    private String periodCovered;

    public Payslip(int payslipID, Employee employee, double basicSalary, double deductions, String periodCovered) {
        this.payslipID = payslipID;
        this.employee = employee;
        this.basicSalary = basicSalary;
        this.deductions = deductions;
        this.periodCovered = periodCovered;
    }

    public double calculateNetPay() {
        return basicSalary - deductions;
    }

    public String generatePayslip() {
        return "Payslip for " + employee.getFullName() +
               "\nPeriod: " + periodCovered +
               "\nBasic Salary: " + basicSalary +
               "\nDeductions: " + deductions +
               "\nNet Pay: " + calculateNetPay();
    }
}
