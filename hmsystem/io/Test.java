package hmsystem.io;

import hmsystem.io.IOHandler;

import java.io.IOException;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        try {
            // Initialize IOHandler for staff and patient data
            IOHandler staffHandler = new IOHandler("hmsystem\\data\\staff.csv");
            IOHandler patientHandler = new IOHandler("hmsystem\\data\\patient.csv");
            IOHandler medicineHandler = new IOHandler("hmsystem\\data\\medicine.csv");

            // Display patient details given patient ID
            String patientId = "P1001";
            System.out.println("Displaying patient details for ID: " + patientId);
            try {
                String[] patientHeaders = { "Patient ID", "Name", "Date of Birth", "Gender", "Blood Type", "Email" };
                for (String header : patientHeaders) {
                    System.out.println(header + ": " + patientHandler.getField(patientId, header));
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Update patient email given patient ID
            String newEmail = "updated.email@example.com";
            System.out.println("\nUpdating email for patient ID: " + patientId + " to " + newEmail);
            try {
                patientHandler.setField(patientId, "Email", newEmail);
                System.out.println("Updated email: " + patientHandler.getField(patientId, "Email"));
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
