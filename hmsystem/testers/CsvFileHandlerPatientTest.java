package hmsystem.testers;

import java.io.IOException;

import hmsystem.csvhandlers.CsvFileHandlerPatient;

public class CsvFileHandlerPatientTest {
    public static void main(String[] args) {
        // CSV file path
        String csvFilePath = "hmsystem\\data\\patient.csv";

        // Add Patient
        try {
            CsvFileHandlerPatient csvFileHandler = new CsvFileHandlerPatient(csvFilePath);
            csvFileHandler.addNewPatient(
                    "Jane Doe",
                    "1985-02-15",
                    "Female",
                    "AB+",
                    "jane.doe@example.com"
            );

        // Get Patient details from PatientID
        String patientId = "P1004";
        System.out.println("Patient Details for ID: " + patientId);
        System.out.println("Name: " + csvFileHandler.getCsvPatientName(patientId));
        System.out.println("Date of Birth: " + csvFileHandler.getCsvPatientDateOfBirth(patientId));
        System.out.println("Gender: " + csvFileHandler.getCsvPatientGender(patientId));
        System.out.println("Blood Type: " + csvFileHandler.getCsvPatientBloodType(patientId));
        System.out.println("Email: " + csvFileHandler.getCsvPatientEmail(patientId));

        // Update Patient detail
        csvFileHandler.setCsvPatientGender(patientId, "Peekaboo");
        System.out.println("\nUpdated Name: " + csvFileHandler.getCsvPatientName(patientId));

        } catch (IOException e) {
            System.out.println("An error occurred while processing the CSV file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
