package hmsystem.io;

import hmsystem.data.Consts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;

public class Test {

    public static void main(String[] args) {
        try {
            // Initialize IOHandler for staff, patient, appointment, and appointmentAOR data
            IOHandler staffHandler = new CsvHandler(Consts.Staff.FILE_NAME);
            IOHandler patientHandler = new CsvHandler(Consts.Patient.FILE_NAME);
            IOHandler appointmentAORHandler = new CsvHandler(Consts.AppointmentAORList.FILE_NAME);

            // Test readCsvValues() for staff data (just rows, excluding headers)
            System.out.println("\nReading all staff data (excluding headers):");
            try {
                Collection<String[]> staffRows = staffHandler.readCsvValues(); // Fetch rows excluding headers
                for (String[] staffDetails : staffRows) {
                    System.out.println("Staff ID: " + staffDetails[Consts.Staff.ID_COLUMN] + " - "
                            + "Name: " + staffDetails[Consts.Staff.NAME_COLUMN] + ", "
                            + "Role: " + staffDetails[Consts.Staff.ROLE_COLUMN]);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Patient Example: Retrieve and Update Email
            String patientId = "P1001";
            System.out.println("\nFetching details for Patient ID: " + patientId);
            try {
                String patientEmail = patientHandler.getField(Consts.Patient.ID_COLUMN, patientId,
                        Consts.Patient.EMAIL_COLUMN);
                System.out.println("Patient Email: " + patientEmail);

                String newEmail = "updated.email@example.com";
                System.out.println("\nUpdating email to: " + newEmail);
                patientHandler.setField(Consts.Patient.ID_COLUMN, patientId, Consts.Patient.EMAIL_COLUMN, newEmail);

                String updatedEmail = patientHandler.getField(Consts.Patient.ID_COLUMN, patientId,
                        Consts.Patient.EMAIL_COLUMN);
                System.out.println("Updated Email: " + updatedEmail);
            } catch (IllegalArgumentException | IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Display All Staff Details
            System.out.println("\nViewing all staff details:");
            try {
                // Instead of accessing 'data' directly from CsvHandler, use the method provided
                // by IOHandler
                for (String staffId : staffHandler.readCsv().keySet()) {
                    String[] staffDetails = staffHandler.readCsv().get(staffId);
                    System.out.println("Staff ID: " + staffDetails[Consts.Staff.ID_COLUMN] + " - "
                            + "Name: " + staffDetails[Consts.Staff.NAME_COLUMN] + ", "
                            + "Role: " + staffDetails[Consts.Staff.ROLE_COLUMN]);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Add New Staff
            System.out.println("\nAdding a new staff member...");
            String[] newStaffDetails = { "S102", "Alice Johnson", "Nurse", "Female", "30", "nurse.alice@example.com",
                    "91234123" };
            try {
                staffHandler.addStaff(newStaffDetails);
                System.out.println("New staff added: " + Arrays.toString(newStaffDetails));
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Fetch Patients by Name
            System.out.println("\nFetching patients named 'Charlie White':");
            List<String[]> patients = patientHandler.getRows(Consts.Patient.NAME_COLUMN, "Charlie White");
            for (String[] patient : patients) {
                System.out.println("Patient ID: " + patient[Consts.Patient.ID_COLUMN] + " - Name: "
                        + patient[Consts.Patient.NAME_COLUMN]);
            }

            // Retrieve Appointment Details with Prescriptions
            System.out.println("\nRetrieving detailed appointment information:");
            try {
                for (String appointmentId : appointmentAORHandler.readCsv().keySet()) {
                    String[] appointmentDetails = appointmentAORHandler.readCsv().get(appointmentId);
                    System.out.println("Appointment ID: " + appointmentDetails[Consts.AppointmentAORList.ID_COLUMN]
                            + " - "
                            + "Patient Name: " + appointmentDetails[Consts.AppointmentAORList.PATIENT_NAME_COLUMN]
                            + ", "
                            + "Doctor Name: " + appointmentDetails[Consts.AppointmentAORList.DOCTOR_NAME_COLUMN] + ", "
                            + "Service: " + appointmentDetails[Consts.AppointmentAORList.SERVICE_COLUMN] + ", "
                            + "Prescription: " + appointmentDetails[Consts.AppointmentAORList.PRESCRIPTION_COLUMN]
                            + ", "
                            + "Notes: " + appointmentDetails[Consts.AppointmentAORList.NOTES_COLUMN] + ", "
                            + "Date: " + appointmentDetails[Consts.AppointmentAORList.DATE_COLUMN] + " "
                            + appointmentDetails[Consts.AppointmentAORList.TIME_COLUMN]);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
        try {
            // Initialize the CsvHandler with the file path
            IOHandler csvhandler = new CsvHandler(Consts.Medicine.FILE_NAME);
        
            // Read the CSV data
            Collection<String[]> rows = csvhandler.readCsvValues();
        
            // Initialise ArrayLists to store medications and their quantities
            ArrayList<String> medications = new ArrayList<>();
            ArrayList<String> quantities = new ArrayList<>();
            ArrayList<String> topup = new ArrayList<>();
        
            // Iterate through rows to extract medication names and quantities
            for (String[] row : rows) {
                if (row.length >= 3) { // Ensure the row has at least two columns
                    medications.add(row[0]); // First column: Medication name
                    quantities.add(row[1]);  // Second column: Quantity
                    topup.add(row[2]);
                }
            }
        
            // Print the medications and quantities
            System.out.println("Medication Inventory:");
            for (int i = 0; i < medications.size(); i++) {
                System.out.print(medications.get(i) + " : " + quantities.get(i));
                if (Integer.parseInt(quantities.get(i)) <= Integer.parseInt(topup.get(i)))
                {
                    System.out.println(" Alert: below " + topup.get(i));
                } 
                else
                {
                    System.out.println();
                }
            }
        
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
   }
}
