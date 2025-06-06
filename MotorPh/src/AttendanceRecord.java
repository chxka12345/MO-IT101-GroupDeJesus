import java.time.Duration;
import java.time.LocalTime;

public class AttendanceRecord {
    private String date;
    private String timeIn;
    private String timeOut;
    private Employee employee;

    public AttendanceRecord(String date, String timeIn, String timeOut, Employee employee) {
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.employee = employee;
    }

    public long calculateHoursWorked() {
        LocalTime in = LocalTime.parse(timeIn);
        LocalTime out = LocalTime.parse(timeOut);
        return Duration.between(in, out).toHours();
    }

    public boolean isLate() {
        LocalTime in = LocalTime.parse(timeIn);
        return in.isAfter(LocalTime.of(8, 0));
    }
}
