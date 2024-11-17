
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Map;


public class AvailabilityController {
        
    private static AvailabilityController availabilityController = null;
    private IOHandler csvhandler;
    private IOHandler csvhandlerAppt;

    
    protected AvailabilityController() throws IOException{
       
        this.csvhandler = new CsvHandler("Availability.csv");
        this.csvhandlerAppt = new CsvHandler(Consts.AOR.FILE_NAME);

    }

    public static AvailabilityController getInstance() throws IOException{
       if (availabilityController == null) {
            availabilityController = new AvailabilityController();
        }
            return availabilityController;
        
    }

    public boolean checkSlot(String doctorID, String date, String timeSlot) throws IOException {
        List<String[]> rows = csvhandlerAppt.getRows(Consts.AOR.DOCTOR_ID_COLUMN, doctorID);
        System.out.println(rows.size());
        for (String[] row : rows) {
            //System.out.println(row[Consts.AOR.DATE_COLUMN] + "    " + date);
            //System.out.println(row[Consts.AOR.TIME_COLUMN] + "    " + timeSlot);
            if (row[Consts.AOR.DATE_COLUMN].equals(date) && row[Consts.AOR.TIME_COLUMN].equals(timeSlot)) {
                    return false;
            }

        }
        rows = csvhandler.getRows(Consts.Availability.DOCTOR_ID_COLUMN, doctorID);
        for (String[] row : rows) {    
            
            if (row.length >= 4) {
                if (doctorID.equals(row[Consts.Availability.DOCTOR_ID_COLUMN]) && date.equals(row[Consts.Availability.DATE_COLUMN]) && row[Consts.Availability.TIME_COLUMN].equals(timeSlot))
                    // 0 means filled slot, cannot book
                    return false;
            }
             
        }
        
        // 1 means empty slot, can book
        return true;

    }

    public int addBlockedSlots(String userID)
    {
        AttributeController getter = AttributeController.getInstance();
        String date = getter.inputString("Enter date(yyyy-mm-dd): ");
        String time = getter.inputString("Enter time(hh:mm): ");

        
        String[] temp = {String.valueOf(getLowestAID()),userID, date, time};
        //
        try{
            csvhandler.addRow(temp);
        }catch (IOException e){
            System.out.println("Error occured during adding blocked slot!");
            return 0;
        }
        return 1;
    }


    // change to localtime, localdate, object with rows
    public List<String> getAvailableSlots(String doctorID, String date) {
        Collection<String[]> rows = csvhandler.readCsvValues();
        Collection<String[]> rowsAppt = csvhandlerAppt.readCsvValues();
    
        // Define working hours (9:00 AM to 5:00 PM)
        List<String> workingHours = new ArrayList<>();
        for (int hour = 9; hour <= 17; hour++) {
            workingHours.add(String.format("%02d:00", hour));
            workingHours.add(String.format("%02d:30", hour));
        }
    
        // Extract booked slots for the specified doctor and date
        HashSet<String> bookedSlots = new HashSet<>();
        for (String[] row : rows) {
            if (row.length >= 4) {
                String currentDoctorID = row[1].trim(); // Ensure no extra spaces
                String currentDate = row[2].trim();
    
                // Debugging output to verify comparisons
                // System.out.println("Checking Row: DoctorID=" + currentDoctorID + ", Date=" + currentDate + ", Time=" + row[2]);
    
                if (currentDoctorID.equals(doctorID) && currentDate.equals(date)) {
                    bookedSlots.add(row[3].trim()); // Add the booked time slot
                    // System.out.println("Added to booked slots: " + row[3]);
                }
            }
        }

        for (String[] row : rowsAppt) {
            if (row.length >= 4) {
                String currentDoctorID = row[3].trim(); // Ensure no extra spaces
                String currentDate = row[5].trim();
                String status = row[10].trim();
                
                // Debugging output to verify comparisons
                //System.out.println("Checking Row: DoctorID=" + currentDoctorID + ", Date=" + currentDate + ", Time=" + row[6] + ", Status=" + status);
    
                if (currentDoctorID.equals(doctorID) && currentDate.equals(date) && !(status.equals("Cancelled"))) {
                    bookedSlots.add(row[6].trim()); // Add the booked time slot
                    //System.out.println("Added to booked slots: " + row[6]);
                }
            }
        }
        
    
        // Find available slots by subtracting booked slots from working hours
        List<String> availableSlots = new ArrayList<>();
        for (String hour : workingHours) {
            if (!bookedSlots.contains(hour)) {
                availableSlots.add(hour);
            }
        }
    
        return availableSlots;
    }
    

        
    

    public void viewAvailableSlots(String doctorID, String date)
    {
        for (String hour : getAvailableSlots(doctorID, date))
        {
            System.out.println(hour);
        }
    }
    public int getLowestAID()
    {
        
        Map <String, String[]> j = csvhandler.readCsv();
        
        int i = 1; // Start from 1
        while (j.containsKey(String.valueOf(i))) {
            i++; // Increment i until a key doesn't exist
        }
        return i;
    }

    public int deleteBlockedSlot(String userID) {
        Scanner sc = new Scanner(System.in);
    
        System.out.println("These are your blocked slots:");
    
        // Read all rows and collect blocked slots for the given userID
        Collection<String[]> rows = csvhandler.readCsvValues();
        List<String[]> blockedSlots = new ArrayList<>();
        int index = 1;
    
        for (String[] row : rows) {
            if (row.length >= 4 && row[1].equals(userID)) {
                // Save blocked slot details
                blockedSlots.add(row);
                // Display to the user
                System.out.println(index + ". Date: " + row[2] + ", Time: " + row[3]);
                index++;
            }
        }
    
        // Check if there are no blocked slots
        if (blockedSlots.isEmpty()) {
            System.out.println("You have no blocked slots.");
            return 0;
        }
    
        // Prompt user to select a slot to delete
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
    
        // Get the row ID of the selected slot
        String[] selectedRow = blockedSlots.get(choice - 1);
        String rowID = selectedRow[0]; // Assuming the row ID is in the first column
    
        // Remove the row from the CSV
        try {
            csvhandler.removeRows(0, rowID);
            System.out.println("Blocked slot successfully deleted.");
        } catch (IOException e) {
            System.out.println("Error while deleting the blocked slot: " + e.getMessage());
            return 0;
        }
    
        return 1;
    }
    

}


