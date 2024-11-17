
public class teststaffcontroller {
    public static void main(String[] args) {
        StaffController staffController = StaffController.getInstance();

        // View all staff
        staffController.viewStaff();

        // Add new staff
        String[] newStaff = { "", "Jane Doe", "Nurse", "Female", "35", "jane.doe@hospital.com", "98765432" };
        staffController.addStaff(newStaff);

        // Update a staff member
        staffController.updateStaff("N0001", 1, "Alice Smith");

        // Remove a staff member
        staffController.removeStaff("D0004");
    }
}
