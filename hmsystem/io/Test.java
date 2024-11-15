package hmsystem.io;

import hmsystem.io.CsvHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        try {
            // Initialize IOHandler for staff and patient data
            CsvHandler staffHandler = new CsvHandler("hmsystem\\data\\staff.csv");
            CsvHandler patientHandler = new CsvHandler("hmsystem\\data\\patient.csv");
            CsvHandler medicineHandler = new CsvHandler("hmsystem\\data\\medicine.csv");

            // Indices based on the CSV headers
            int patientIdColumn = 0; // "Patient ID" column
            int emailColumn = 5; // "Email" column (assuming "Email" is column 5)
            int staffIdColumn = 0; // "Staff ID" column
            int staffNameColumn = 1; // "Name" column
            int staffRoleColumn = 2; // "Role" column

            // Display patient details given patient ID using the new getField method
            String patientId = "P1001";
            System.out.println("Displaying patient details for ID: " + patientId);
            try {
                String patientEmail = patientHandler.getField(patientIdColumn, patientId, emailColumn);
                System.out.println("Patient Email: " + patientEmail);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Update patient email given patient ID using the new setField method
            String newEmail = "updated.email@example.com";
            System.out.println("\nUpdating email for patient ID: " + patientId + " to " + newEmail);
            try {
                patientHandler.setField(patientIdColumn, patientId, emailColumn, newEmail);
                String updatedEmail = patientHandler.getField(patientIdColumn, patientId, emailColumn);
                System.out.println("Updated email: " + updatedEmail);
            } catch (IllegalArgumentException | IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // View all staff details
            System.out.println("\nViewing staff details:");
            try {
                for (String staffId : staffHandler.data.keySet()) {
                    String[] staffDetails = staffHandler.data.get(staffId);
                    System.out.println("Staff ID: " + staffDetails[staffIdColumn] + " - " 
                            + "Name: " + staffDetails[staffNameColumn] + ", " 
                            + "Role: " + staffDetails[staffRoleColumn]);
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
                    String[] medicineDetails = medicineHandler.data.get(medicineId);
                    System.out.println("Medicine: " + Arrays.toString(medicineDetails));
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Test the getRows method for staff with role "Nurse"
            System.out.println("\nGetting all staff with role 'Nurse':");
            List<String[]> nurseStaff = staffHandler.getRows(staffRoleColumn, "Nurse");
            for (String[] staff : nurseStaff) {
                System.out.println("Staff ID: " + staff[staffIdColumn] + " - " 
                        + "Name: " + staff[staffNameColumn] + ", " 
                        + "Role: " + staff[staffRoleColumn]);
            }

            // Test the getRows method for patients with a certain condition (if such data exists)
            System.out.println("\nGetting all patients with a specific condition (example):");
            // Assuming we have a column for "Condition" (let's say it's at index 3)
            List<String[]> patientOfInterest = patientHandler.getRows(0, "P1006"); // Replace with actual condition
            for (String[] patient : patientOfInterest) {
                System.out.println("Patient ID: " + patient[patientIdColumn] + " - "
                        + "Name: " + patient[1] + ", " // Assuming Name is at index 1
                        + "Condition: " + patient[3]);
            }

        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }
}
