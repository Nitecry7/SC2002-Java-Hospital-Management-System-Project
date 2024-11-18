import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

/**
 * The AvailabilityController class manages the availability of doctors,
 * including checking, adding, and deleting blocked slots, as well as
 * retrieving available slots.
 */
public class AvailabilityController {

    private static AvailabilityController availabilityController = null;
    private IOHandler csvhandler;
    private IOHandler csvhandlerAppt;

    /**
     * Protected constructor to initialize the AvailabilityController with
     * handlers for availability and appointment CSV files.
     *
     * @throws IOException If there is an issue initializing the CSV handlers.
     */
    protected AvailabilityController() throws IOException {
        this.csvhandler = new CsvHandler("Availability.csv");
        this.csvhandlerAppt = new CsvHandler(Consts.AOR.FILE_NAME);
    }

    /**
     * Retrieves the singleton instance of AvailabilityController.
     *
     * @return The singleton instance of AvailabilityController.
     * @throws IOException If there is an issue initializing the instance.
     */
    public static AvailabilityController getInstance() throws IOException {
        if (availabilityController == null) {
            availabilityController = new AvailabilityController();
        }
        return availabilityController;
    }

    /**
     * Checks if a specific time slot for a doctor on a given date is available.
     *
     * @param doctorID The ID of the doctor.
     * @param date The date to check availability.
     * @param timeSlot The time slot to check availability.
     * @return True if the slot is available; false otherwise.
     * @throws IOException If there is an issue reading data.
     */
    public boolean checkSlot(String doctorID, String date, String timeSlot) throws IOException {
        List<String[]> rows = csvhandlerAppt.getRows(Consts.AOR.DOCTOR_ID_COLUMN, doctorID);

        for (String[] row : rows) {
            if (row[Consts.AOR.DATE_COLUMN].equals(date) && row[Consts.AOR.TIME_COLUMN].equals(timeSlot)) {
                return false;
            }
        }

        rows = csvhandler.getRows(Consts.Availability.DOCTOR_ID_COLUMN, doctorID);
        for (String[] row : rows) {
            if (row.length >= 4 &&
                doctorID.equals(row[Consts.Availability.DOCTOR_ID_COLUMN]) &&
                date.equals(row[Consts.Availability.DATE_COLUMN]) &&
                row[Consts.Availability.TIME_COLUMN].equals(timeSlot)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Adds a blocked slot for a doctor.
     *
     * @param userID The ID of the doctor.
     * @return 1 if successful; 0 otherwise.
     */
    public int addBlockedSlots(String userID) {
        AttributeController getter = AttributeController.getInstance();
        String date = getter.inputString("Enter date(yyyy-mm-dd): ");
        String time = getter.inputString("Enter time(hh:mm): ");

        String[] temp = {String.valueOf(getLowestAID()), userID, date, time};
        try {
            csvhandler.addRow(temp);
        } catch (IOException e) {
            System.out.println("Error occurred during adding blocked slot!");
            return 0;
        }
        return 1;
    }

    /**
     * Retrieves a list of available slots for a specific doctor on a given date.
     *
     * @param doctorID The ID of the doctor.
     * @param date The date to check availability.
     * @return A list of available time slots.
     */
    public List<String> getAvailableSlots(String doctorID, String date) {
        Collection<String[]> rows = csvhandler.readCsvValues();
        Collection<String[]> rowsAppt = csvhandlerAppt.readCsvValues();

        List<String> workingHours = new ArrayList<>();
        for (int hour = 9; hour <= 17; hour++) {
            workingHours.add(String.format("%02d:00", hour));
            workingHours.add(String.format("%02d:30", hour));
        }

        HashSet<String> bookedSlots = new HashSet<>();
        for (String[] row : rows) {
            if (row.length >= 4 &&
                doctorID.equals(row[Consts.Availability.DOCTOR_ID_COLUMN]) &&
                date.equals(row[Consts.Availability.DATE_COLUMN])) {
                bookedSlots.add(row[Consts.Availability.TIME_COLUMN]);
            }
        }

        for (String[] row : rowsAppt) {
            if (row.length >= 4 &&
                doctorID.equals(row[Consts.AOR.DOCTOR_ID_COLUMN]) &&
                date.equals(row[Consts.AOR.DATE_COLUMN]) &&
                !row[Consts.AOR.APPOINTMENT_STATUS_COLUMN].equalsIgnoreCase("Cancelled")) {
                bookedSlots.add(row[Consts.AOR.TIME_COLUMN]);
            }
        }

        List<String> availableSlots = new ArrayList<>();
        for (String hour : workingHours) {
            if (!bookedSlots.contains(hour)) {
                availableSlots.add(hour);
            }
        }

        return availableSlots;
    }

    /**
     * Displays available slots for a specific doctor on a given date.
     *
     * @param doctorID The ID of the doctor.
     * @param date The date to display availability.
     */
    public void viewAvailableSlots(String doctorID, String date) {
        for (String hour : getAvailableSlots(doctorID, date)) {
            System.out.println(hour);
        }
    }

    /**
     * Retrieves the lowest available appointment ID.
     *
     * @return The lowest available appointment ID.
     */
    public int getLowestAID() {
        Map<String, String[]> data = csvhandler.readCsv();

        int id = 1;
        while (data.containsKey(String.valueOf(id))) {
            id++;
        }
        return id;
    }

    /**
     * Deletes a blocked slot for a doctor.
     *
     * @param userID The ID of the doctor.
     * @return 1 if successful; 0 otherwise.
     */
    public int deleteBlockedSlot(String userID) {
        Scanner sc = new Scanner(System.in);

        System.out.println("These are your blocked slots:");

        Collection<String[]> rows = csvhandler.readCsvValues();
        List<String[]> blockedSlots = new ArrayList<>();
        int index = 1;

        for (String[] row : rows) {
            if (row.length >= 4 && row[Consts.Availability.DOCTOR_ID_COLUMN].equals(userID)) {
                blockedSlots.add(row);
                System.out.println(index + ". Date: " + row[Consts.Availability.DATE_COLUMN] + ", Time: " + row[Consts.Availability.TIME_COLUMN]);
                index++;
            }
        }

        if (blockedSlots.isEmpty()) {
            System.out.println("You have no blocked slots.");
            return 0;
        }

        System.out.println("Enter the number corresponding to the slot you want to delete: ");
        int choice;
        try {
            choice = sc.nextInt();
            if (choice < 1 || choice > blockedSlots.size()) {
                System.out.println("Invalid choice.");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return 0;
        }

        String[] selectedRow = blockedSlots.get(choice - 1);
        String rowID = selectedRow[Consts.Availability.ID_COLUMN];

        try {
            csvhandler.removeRows(Consts.Availability.ID_COLUMN, rowID);
            System.out.println("Blocked slot successfully deleted.");
        } catch (IOException e) {
            System.out.println("Error while deleting the blocked slot: " + e.getMessage());
            return 0;
        }

        return 1;
    }
}
