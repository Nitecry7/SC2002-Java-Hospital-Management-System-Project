
// Singleton

import java.io.IOException;
import java.util.Map;

public class StaffController {

    private static final StaffController staffController = new StaffController();
    private static IOHandler csvHandler; // Fix the variable name to match

    private StaffController() {
        try {
            StaffController.csvHandler = new CsvHandler(Consts.Staff.FILE_NAME); // Make sure to use the same name here
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize StaffController: " + e.getMessage());
        }
    }

    public static StaffController getInstance() {
        return staffController;
    }

    public void viewStaff() {
        Map<String, String[]> staffData = csvHandler.readCsv();

        // Get the headers
        String[] headers = csvHandler.getHeaders();

        // Use the utility class to print the table
        TablePrinter.printTable(staffData, headers);
    }

    public void addStaff(String[] staffDetails) {
        try {
            csvHandler.addStaff(staffDetails);
            System.out.println("Staff added successfully.");
        } catch (IOException e) {
            System.err.println("Error adding staff: " + e.getMessage());
        }
    }

    public void removeStaff(String staffID) {
        try {
            csvHandler.removeRows(0, staffID);
            System.out.println("Staff removed successfully.");
        } catch (IOException e) {
            System.err.println("Error removing staff: " + e.getMessage());
        }
    }

    public void updateStaff(String staffID, int columnToChange, String newValue) {
        try {
            csvHandler.setField(0, staffID, columnToChange, newValue);
            System.out.println("Staff updated successfully.");
        } catch (IOException e) {
            System.err.println("Error updating staff: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}