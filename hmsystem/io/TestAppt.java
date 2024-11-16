package hmsystem.io;

import hmsystem.data.Consts;

import java.io.IOException;
import java.util.List;

public class TestAppt {

    public static void main(String[] args) {
        try {
            // Initialize IOHandler for appointment and appointmentAOR data
            CsvHandler appointmentHandler = new CsvHandler(Consts.AppointmentList.FILE_NAME);
            CsvHandler appointmentAORHandler = new CsvHandler(Consts.AppointmentAORList.FILE_NAME);

            // View All Appointments
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
            try {
                List<String[]> confirmedAppointments = appointmentHandler.getRows(Consts.AppointmentList.APPOINTMENT_STATUS_COLUMN, "Confirmed");
                for (String[] appointment : confirmedAppointments) {
                    System.out.println("Appointment ID: " + appointment[Consts.AppointmentList.ID_COLUMN] + " - "
                            + "Patient ID: " + appointment[Consts.AppointmentList.PATIENT_ID_COLUMN] + ", "
                            + "Date: " + appointment[Consts.AppointmentList.DATE_COLUMN] + ", "
                            + "Time: " + appointment[Consts.AppointmentList.TIME_COLUMN] + ", "
                            + "Status: " + appointment[Consts.AppointmentList.APPOINTMENT_STATUS_COLUMN]);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Add a New Appointment
            System.out.println("\nAdding a new appointment...");
            String[] newAppointmentDetails = { "A005", "P102", "D202", "2024-11-20", "14:00", "Confirmed" };
            try {
                appointmentHandler.addRow(newAppointmentDetails);
                System.out.println("New appointment added: " + String.join(", ", newAppointmentDetails));
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // View Appointment Details with Prescriptions
            System.out.println("\nRetrieving detailed appointment information with prescriptions:");
            try {
                for (String appointmentId : appointmentAORHandler.data.keySet()) {
                    String[] appointmentDetails = appointmentAORHandler.data.get(appointmentId);
                    System.out.println("Appointment ID: " + appointmentDetails[Consts.AppointmentAORList.ID_COLUMN] + " - "
                            + "Patient Name: " + appointmentDetails[Consts.AppointmentAORList.PATIENT_NAME_COLUMN] + ", "
                            + "Doctor Name: " + appointmentDetails[Consts.AppointmentAORList.DOCTOR_NAME_COLUMN] + ", "
                            + "Service: " + appointmentDetails[Consts.AppointmentAORList.SERVICE_COLUMN] + ", "
                            + "Prescription: " + appointmentDetails[Consts.AppointmentAORList.PRESCRIPTION_COLUMN] + ", "
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
