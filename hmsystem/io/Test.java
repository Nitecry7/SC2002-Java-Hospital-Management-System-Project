package hmsystem.io;

import hmsystem.data.Consts;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        try {
            // Initialize IOHandler for staff, patient, appointment, and appointmentAOR data
            CsvHandler staffHandler = new CsvHandler(Consts.Staff.FILE_NAME);
            CsvHandler patientHandler = new CsvHandler(Consts.Patient.FILE_NAME);
            CsvHandler appointmentHandler = new CsvHandler(Consts.AppointmentList.FILE_NAME);
            CsvHandler appointmentAORHandler = new CsvHandler(Consts.AppointmentAORList.FILE_NAME);

            // Patient Example: Retrieve and Update Email
            String patientId = "P1001";
            System.out.println("Fetching details for Patient ID: " + patientId);
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
                for (String staffId : staffHandler.data.keySet()) {
                    String[] staffDetails = staffHandler.data.get(staffId);
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

            // Display All Appointments
            System.out.println("\nViewing all appointments:");
            try {
                for (String appointmentId : appointmentHandler.data.keySet()) {
                    String[] appointmentDetails = appointmentHandler.data.get(appointmentId);
                    System.out.println("Appointment ID: " + appointmentDetails[Consts.AppointmentList.ID_COLUMN] + " - "
                            + "Patient ID: " + appointmentDetails[Consts.AppointmentList.PATIENT_ID_COLUMN] + ", "
                            + "Doctor ID: " + appointmentDetails[Consts.AppointmentList.DOCTOR_ID_COLUMN] + ", "
                            + "Date: " + appointmentDetails[Consts.AppointmentList.DATE_COLUMN] + ", "
                            + "Time: " + appointmentDetails[Consts.AppointmentList.TIME_COLUMN] + ", "
                            + "Status: " + appointmentDetails[Consts.AppointmentList.APPOINTMENT_STATUS_COLUMN]);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Retrieve Confirmed Appointments
            System.out.println("\nRetrieving appointments with status 'Confirmed':");
            List<String[]> confirmedAppointments = appointmentHandler
                    .getRows(Consts.AppointmentList.APPOINTMENT_STATUS_COLUMN, "Confirmed");
            for (String[] appointment : confirmedAppointments) {
                System.out.println("Appointment ID: " + appointment[Consts.AppointmentList.ID_COLUMN] + " - "
                        + "Patient ID: " + appointment[Consts.AppointmentList.PATIENT_ID_COLUMN] + ", "
                        + "Date: " + appointment[Consts.AppointmentList.DATE_COLUMN] + ", "
                        + "Time: " + appointment[Consts.AppointmentList.TIME_COLUMN] + ", "
                        + "Status: " + appointment[Consts.AppointmentList.APPOINTMENT_STATUS_COLUMN]);
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
                for (String appointmentId : appointmentAORHandler.data.keySet()) {
                    String[] appointmentDetails = appointmentAORHandler.data.get(appointmentId);
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
    }
}
