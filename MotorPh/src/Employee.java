public class Employee {
    private int employeeID;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String contactNumber;
    private String address;
    private String position;
    private String department;

    public Employee(int employeeID, String firstName, String lastName, String birthDate,
                    String contactNumber, String address, String position, String department) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.contactNumber = contactNumber;
        this.address = address;
        this.position = position;
        this.department = department;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmployeeInfo() {
        return "ID: " + employeeID + ", Name: " + getFullName() + ", Position: " + position;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Add other getters and setters as needed
}
