package hmsystem.testers;

import java.io.IOException;
import hmsystem.csvhandlers.CsvFileHandlerStaff;

public class CsvFileHandlerStaffTest {
    public static void main(String[] args) {
        // CSV file path
        String csvFilePath = "hmsystem\\data\\staff.csv";

        try {
            CsvFileHandlerStaff csvFileHandler = new CsvFileHandlerStaff(csvFilePath);

            // Test addDoctor method
            csvFileHandler.addDoctor("Jane Doe", "Female", "40");
            System.out.println("Added new Doctor: Jane Doe");

            // Test addPharmacist method
            csvFileHandler.addPharmacist("David Kim", "Male", "35");
            System.out.println("Added new Pharmacist: David Kim");

            // Test addAdmin method
            csvFileHandler.addAdmin("Anna Lee", "Female", "28");
            System.out.println("Added new Administrator: Anna Lee");

            // Retrieve and print the details of an existing staff member
            String staffId = "D003";
            System.out.println("\nStaff Details for ID: " + staffId);
            System.out.println("Name: " + csvFileHandler.getCsvStaffName(staffId));
            System.out.println("Role: " + csvFileHandler.getCsvStaffRole(staffId));
            System.out.println("Gender: " + csvFileHandler.getCsvStaffGender(staffId));
            System.out.println("Age: " + csvFileHandler.getCsvStaffAge(staffId));

            // Update Staff detail
            csvFileHandler.setCsvStaffRole(staffId, "Senior Doctor");
            System.out.println("\nUpdated Role for " + staffId + ": " + csvFileHandler.getCsvStaffRole(staffId));

        } catch (IOException e) {
            System.out.println("An error occurred while processing the CSV file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
