
import java.io.IOException;
import java.util.Collection;
/**
 * Singleton class responsible for managing staff data operations.
 * Provides methods to view, add, remove, and update staff information stored in a CSV file.
 */
public class StaffController {

    /**
     * Singleton instance of the StaffController.
     */
    private static final StaffController staffController = new StaffController();

    /**
     * IOHandler for managing CSV operations related to staff data.
     */
    private static IOHandler csvHandler;

    /**
     * Private constructor to initialize the StaffController.
     * Initializes the IOHandler for the staff CSV file.
     */
    private StaffController() {
        try {
            StaffController.csvHandler = new CsvHandler("Staff_List.csv");
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize StaffController: " + e.getMessage());
        }
    }

    /**
     * Retrieves the singleton instance of StaffController.
     *
     * @return The singleton instance of StaffController.
     */
    public static StaffController getInstance() {
        return staffController;
    }

    /**
     * Displays all staff information stored in the CSV file.
     * Prints the headers and rows to the console.
     */
    public void viewStaff() {
        // Retrieve the headers and data rows from the CSV file.
        String[] headers = csvHandler.getHeaders();
        Collection<String[]> data = csvHandler.readCsvValues();

        // Print the headers.
        for (String item : headers) {
            if(item.equals("PW")) continue;
            System.out.printf("%-30s |", item);
        }
        System.out.println();

        // Print each row of staff data.
        for (String[] row : data) {
            for(int i = 0; i < 8; ++i){
                if(i == Consts.Staff.PW_COLUMN) continue;
                System.out.printf("%-30s |", row[i]);
            }
            System.out.println();
        }
    }

    /**
     * Adds a new staff member to the CSV file.
     *
     * @param staffDetails Array containing details of the new staff member.
     */
    public void addStaff(String[] staffDetails) {
        try {
            csvHandler.addStaff(staffDetails);
            System.out.println("Staff added successfully.");
        } catch (IOException e) {
            System.err.println("Error adding staff: " + e.getMessage());
        }
    }

    /**
     * Removes a staff member from the CSV file based on their ID.
     *
     * @param staffID The ID of the staff member to be removed.
     */
    public void removeStaff(String staffID) {
        try {
            csvHandler.removeRows(0, staffID);
            System.out.println("Staff removed successfully.");
        } catch (IOException e) {
            System.err.println("Error removing staff: " + e.getMessage());
        }
    }

    /**
     * Updates a specific field of a staff member's record in the CSV file.
     *
     * @param staffID         The ID of the staff member to be updated.
     * @param columnToChange  The column index to update.
     * @param newValue        The new value to set in the specified column.
     */
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
