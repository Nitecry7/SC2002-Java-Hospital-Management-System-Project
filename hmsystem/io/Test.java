package hmsystem.io;

import hmsystem.io.CsvHandler;

import java.io.IOException;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        try {
            // Initialize IOHandler for staff and patient data
            CsvHandler staffHandler = new CsvHandler("hmsystem\\data\\staff.csv");
            CsvHandler patientHandler = new CsvHandler("hmsystem\\data\\patient.csv");
            CsvHandler medicineHandler = new CsvHandler("hmsystem\\data\\medicine.csv");

            // Display patient details given patient ID using the new getField method
            String patientId = "P1001";
            String columnToFindRow = "Patient ID"; // Column to search
            String valueToFindRow = patientId; // Value to search for in the column
            String columnToGet = "Email"; // Column to retrieve the value from
            String columnToChange = "Email";
            
            System.out.println("Displaying patient details for ID: " + patientId);
            try {
                String patientEmail = patientHandler.getField(columnToFindRow, valueToFindRow, columnToGet);
                System.out.println("Patient Email: " + patientEmail);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Update patient email given patient ID using the new setField method
            String newEmail = "updated.email@example.com";
            System.out.println("\nUpdating email for patient ID: " + patientId + " to " + newEmail);
            try {
                patientHandler.setField(columnToFindRow, valueToFindRow, columnToChange, newEmail);
                String updatedEmail = patientHandler.getField(columnToFindRow, valueToFindRow, columnToChange);
                System.out.println("Updated email: " + updatedEmail);
            } catch (IllegalArgumentException | IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // View all staff details
            System.out.println("\nViewing staff details:");
            try {
                for (String staffId : staffHandler.data.keySet()) {
                    System.out
                            .println("Staff ID: " + staffId + " - " + Arrays.toString(staffHandler.data.get(staffId)));
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Add a new staff member
            System.out.println("\nAdding new staff member...");
            String[] newStaffDetails = { null, "Alice Johnson", "Nurse", "Female", "30" };
            try {
                staffHandler.addStaff(newStaffDetails);
                System.out.println("New staff added: " + Arrays.toString(newStaffDetails));
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // View all medicines
            System.out.println("\nViewing medicines:");
            try {
                for (String medicineId : medicineHandler.data.keySet()) {
                    System.out.println("Medicine ID: " + medicineId + " - "
                            + Arrays.toString(medicineHandler.data.get(medicineId)));
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }
}
