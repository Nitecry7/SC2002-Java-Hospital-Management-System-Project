import java.io.IOException;
import java.util.List;

/**
 * The Administrator class extends the Staff class and provides functionalities
 * specific to managing hospital staff, appointments, medication inventory,
 * and replenishment requests. It acts as an interface for administrative
 * tasks within the hospital management system.
 */
public class Administrator extends Staff {

    private AttributeController getter = AttributeController.getInstance();

    /**
     * Private constructor for Administrator.
     *
     * @param details An array of administrator details.
     * @param handler The IOHandler for file operations.
     */
    private Administrator(String[] details, IOHandler handler) {
        super(details, handler);
    }

    /**
     * Factory method to create an Administrator instance.
     *
     * @param adminID The ID of the administrator.
     * @param handler The IOHandler for file operations.
     * @return An Administrator object if the admin ID is found, otherwise null.
     */
    public static Administrator getAdministrator(String adminID, IOHandler handler) {
        List<String[]> userDetails = handler.getRows(Consts.Staff.ID_COLUMN, adminID);
        if (userDetails.isEmpty()) {
            return null;
        } else {
            return new Administrator(userDetails.get(0), handler);
        }
    }

    /**
     * Displays and manages hospital staff options, such as viewing, adding,
     * removing, or updating staff information.
     */
    public void _View_and_Manage_Hospital_Staff() {
        StaffController sc = StaffController.getInstance();
        System.out.println("1. View All Staff: ");
        System.out.println("2. Add Staff: ");
        System.out.println("3. Remove Staff: ");
        System.out.println("4. Update Staff: ");
        System.out.println("5. Go Back: ");

        int operation = getter.inputInt("Enter your choice(1-5):");
        while (operation > 5 || operation < 1) {
            System.out.println("Please input a valid integer(1-5)!");
            getter.inputInt("Enter your choice(1-5):");
        }
        switch (operation) {
            case 1:
                sc.viewStaff();
                break;
            case 2:
                addStaff(sc);
                break;
            case 3:
                removeStaff(sc);
                break;
            case 4:
                updateStaff(sc);
                break;
            case 5:
                return;
        }
    }

    /**
     * Adds a new staff member to the hospital.
     *
     * @param sc The StaffController instance.
     */
    public void addStaff(StaffController sc) {
        try {
            IOHandler staffCsvHandler = new CsvHandler(Consts.Staff.FILE_NAME);
            String firstName = getter.inputString("Please enter first name:");
            String lastName = getter.inputString("Please enter last name:");
            String staffName = firstName + " " + lastName;
            String staffRole = getter.inputString("Please enter role(Administrator/Doctor/Pharmacist):");
            while (!staffRole.equals("Administrator") && !staffRole.equals("Doctor") && !staffRole.equals("Pharmacist")) {
                System.out.println("Please enter a valid role!");
                staffRole = getter.inputString("Please enter role(Admin/Doctor/Pharmacist):");
            }
            String staffGender = getter.inputString("Please enter gender(Male/Female):");
            while (!staffGender.equals("Male") && !staffGender.equals("Female")) {
                System.out.println("Please enter a valid gender!");
                staffGender = getter.inputString("Please enter gender(Male/Female):");
            }
            int sid = staffCsvHandler.readCsvValues().size();
            String staffID = staffRole.charAt(0) + "00" + sid;
            int ageNum = getter.inputInt("Please enter age:");
            String staffAge = String.valueOf(ageNum);
            String staffEmail = getter.inputString("Please enter email:");
            String staffPhoneNumber = getter.inputString("Please enter phone number:");

            String[] staffInfo = {staffID, staffName, staffRole, staffGender, staffAge, staffEmail, staffPhoneNumber};
            sc.addStaff(staffInfo);

        } catch (IOException e) {
            System.out.println("Error when adding staff");
        }
    }

    /**
     * Removes a staff member from the hospital.
     *
     * @param sc The StaffController instance.
     */
    public void removeStaff(StaffController sc) {
        String staffID = getter.inputString("Please enter staff ID for removal: ");
        sc.removeStaff(staffID);
    }

    /**
     * Updates information for a specific staff member.
     *
     * @param sc The StaffController instance.
     */
    public void updateStaff(StaffController sc) {
        String staffID = getter.inputString("Please enter staff ID for edit: ");

        System.out.println("1. Edit Name: ");
        System.out.println("2. Edit Role: ");
        System.out.println("3. Edit Gender: ");
        System.out.println("4. Edit Age: ");
        System.out.println("5. Edit Email: ");
        System.out.println("6. Edit Phone Number: ");
        System.out.println("7. Go Back: ");

        int operation = getter.inputInt("Enter your choice(1-7):");
        while (operation > 7 || operation < 1) {
            System.out.println("Please input a valid integer(1-7)!");
            getter.inputInt("Enter your choice(1-7):");
        }
        String updatedInfo = "";
        switch (operation) {
            case 1:
                String firstName = getter.inputString("Please enter first name:");
                String lastName = getter.inputString("Please enter last name:");
                updatedInfo = firstName + " " + lastName;
                break;
            case 2:
                updatedInfo = getter.inputString("Please enter role(Admin/Doctor/Nurse/Pharmacist):");
                while (!updatedInfo.equals("Admin") && !updatedInfo.equals("Doctor") && !updatedInfo.equals("Nurse") && !updatedInfo.equals("Pharmacist")) {
                    System.out.println("Please enter a valid role!");
                    updatedInfo = getter.inputString("Please enter role(Admin/Doctor/Nurse/Pharmacist):");
                }
                break;
            case 3:
                updatedInfo = getter.inputString("Please enter gender(Male/Female):");
                while (!updatedInfo.equals("Male") && !updatedInfo.equals("Female")) {
                    System.out.println("Please enter a valid gender!");
                    updatedInfo = getter.inputString("Please enter gender(Male/Female):");
                }
                break;
            case 4:
                int ageNum = getter.inputInt("Please enter age:");
                updatedInfo = String.valueOf(ageNum);
                break;
            case 5:
                updatedInfo = getter.inputString("Please enter email:");
                break;
            case 6:
                updatedInfo = getter.inputString("Please enter phone number:");
                break;
            case 7:
                return;
        }
        sc.updateStaff(staffID, operation, updatedInfo);
    }

    /**
     * Displays all appointment details.
     */
    public void _View_Appointments_details() {
        try {
            AORController appCon = AORController.getInstance();
            List<String> appList = appCon.viewAllAppointmentsDetails();
            for (String app : appList) {
                System.out.println(app);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    /**
     * Manages the medication inventory, including viewing, adding, editing,
     * and deleting medications.
     */
    public void _View_and_Manage_Medication_Inventory() {
        InventoryController ic = InventoryController.getInstance();
        System.out.println("1. View Medication Inventory");
        System.out.println("2. Add Medication");
        System.out.println("3. Edit Medication Inventory");
        System.out.println("4. Delete Medication");
        System.out.println("5. Go Back");

        int operation = getter.inputInt("Enter your choice(1-5):");
        while (operation > 5 || operation < 1) {
            System.out.println("Please input a valid integer(1-5)!");
            getter.inputInt("Enter your choice(1-5):");
        }
        switch (operation) {
            case 1:
                ic.viewMedicationInventory();
                break;
            case 2:
                ic.addMedication();
                break;
            case 3:
                String medicineName = getter.inputString("Please input the name of medicine:");
                ic.editMedication(medicineName);
                break;
            case 4:
                ic.deleteMedication();
                break;
            case 5:
                return;
        }
    }

    /**
     * Handles replenishment actions, including viewing all requests, viewing pending requests,
     * approving, and rejecting replenishment requests.
     */
    public void _Replenishment_Action() {
        System.out.println("1. View All Request");
        System.out.println("2. View Pending Request");
        System.out.println("3. Reject Request");
        System.out.println("4. Approve Request");
        System.out.println("5. Go Back");
        int operation = getter.inputInt("Enter your choice(1-5):");
        while (operation > 5 || operation < 1) {
            System.out.println("Please input a valid integer(1-5)!");
            getter.inputInt("Enter your choice(1-5):");
        }
        switch (operation) {
            case 1:
                viewAllRequest();
                break;
            case 2:
                viewPendingRequest();
                break;
            case 3:
                rejectReplenishmentRequests();
                break;
            case 4:
                approveReplenishmentRequests();
                break;
            case 5:
                return;
        }
    }

    /**
     * Displays all replenishment requests.
     */
    public void viewAllRequest() {
        ReplenishmentController rc = ReplenishmentController.getInstance();
        rc.viewAllRequest();
    }

    /**
     * Displays all pending replenishment requests.
     */
    public void viewPendingRequest() {
        ReplenishmentController rc = ReplenishmentController.getInstance();
        rc.viewPendingRequest();
    }

    /**
     * Rejects replenishment requests.
     */
    public void rejectReplenishmentRequests() {
        ReplenishmentController rc = ReplenishmentController.getInstance();
        rc.rejectRequest();
    }

    /**
     * Approves replenishment requests.
     */
    public void approveReplenishmentRequests() {
        ReplenishmentController rc = ReplenishmentController.getInstance();
        rc.approveRequest();
    }
}
