import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LeaveRequest {
    private int requestID;
    private Employee employee;
    private String leaveType;
    private String startDate;
    private String endDate;
    private String status;

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
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ChronoUnit.DAYS.between(start, end) + 1;
    }

    public String getStatus() {
        return status;
    }
}
