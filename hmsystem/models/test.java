package hmsystem.models;

import java.io.IOException;

import hmsystem.csvhandlers.CsvFileHandlerPatient;

public class test {
    public static void main(String[] args) {
        // Define the CSV file path
        String csvFilePath = "hmsystem\\data\\patient.csv";

        try {
            // Create an instance of CsvFileHandler
            CsvFileHandlerPatient csvFileHandler = new CsvFileHandlerPatient(csvFilePath);

            // Add a new patient
            // Retrieve and print the patient details
            String patientId = "P12345";
            System.out.println("Patient Details for ID: " + patientId);
            System.out.println("Name: " + csvFileHandler.getCsvPatientName(patientId));
            System.out.println("Date of Birth: " + csvFileHandler.getCsvPatientDateOfBirth(patientId));
            System.out.println("Gender: " + csvFileHandler.getCsvPatientGender(patientId));
            System.out.println("Blood Type: " + csvFileHandler.getCsvPatientBloodType(patientId));
            System.out.println("Email: " + csvFileHandler.getCsvPatientEmail(patientId));

            // Update a field and print the updated details
            csvFileHandler.setCsvPatientName(patientId, "Peekaboo");
            System.out.println("\nUpdated Name: " + csvFileHandler.getCsvPatientName(patientId));

        } catch (IOException e) {
            System.out.println("An error occurred while processing the CSV file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
