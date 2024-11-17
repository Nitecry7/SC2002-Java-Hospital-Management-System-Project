package hmsystem.controllers;

import hmsystem.io.IOHandler;
import hmsystem.data.Consts;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class AORController {
    private IOHandler appointmentHandler;
    private IOHandler patientHandler;
    private IOHandler doctorHandler;

    public AORController(IOHandler appointmentHandler, IOHandler patientHandler, IOHandler doctorHandler) {
        this.appointmentHandler = appointmentHandler;
        this.patientHandler = patientHandler;
        this.doctorHandler = doctorHandler;
    }

    public void viewAllAppointmentsDetails() throws IOException {
        // Read all rows (appointments) using the readCsvValues method
        Collection<String[]> rows = appointmentHandler.readCsvValues();
        
        // If there are no appointments, notify the user
        if (rows.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        // Print the details of each appointment
        System.out.println("All appointments:");
        for (String[] row : rows) {
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String patientName = row[Consts.AOR.PATIENT_NAME_COLUMN];
            String doctorName = row[Consts.AOR.DOCTOR_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String status = row[Consts.AOR.APPOINTMENT_STATUS_COLUMN];

            System.out.println("Appointment ID: " + appointmentID);
            System.out.println("Patient: " + patientName);
            System.out.println("Doctor: " + doctorName);
            System.out.println("Date: " + date);
            System.out.println("Time: " + time);
            System.out.println("Status: " + status);
            System.out.println("----------------------------");
        }
    }


    /**
     * Schedule a new appointment.
     *
     * @param patientID The patient ID.
     * @param doctorID  The doctor ID.
     * @param time      The time of the appointment.
     * @param date      The date of the appointment.
     * @param service   The service to be provided.
     * @throws IOException If there's an error updating the CSV.
     */
    public void scheduleAppointment(String patientID, String doctorID, String time, String date, String service)
            throws IOException {
        String appointmentID = generateNewAppointmentID();
        String[] newAppointment = {
                appointmentID,
                patientID,
                getPatientName(patientID),
                doctorID,
                getDoctorName(doctorID),
                date,
                time,
                service,
                "",
                "",
                "Pending"
        };
        appointmentHandler.addRow(newAppointment);
        System.out.println("Appointment scheduled successfully: " + appointmentID);
    }

    /**
     * Reschedule an existing appointment.
     *
     * @param appointmentID The ID of the appointment to reschedule.
     * @param doctorID      The doctor ID.
     * @param time          The new time for the appointment.
     * @param date          The new date for the appointment.
     * @throws IOException If there's an error updating the CSV.
     */
    public void rescheduleAppointment(String appointmentID, String doctorID, String time, String date)
            throws IOException {
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentID);
        }
        String[] updatedAppointment = rows.get(0);
        updatedAppointment[Consts.AOR.DOCTOR_ID_COLUMN] = doctorID;
        updatedAppointment[Consts.AOR.DOCTOR_NAME_COLUMN] = getDoctorName(doctorID);
        updatedAppointment[Consts.AOR.DATE_COLUMN] = date;
        updatedAppointment[Consts.AOR.TIME_COLUMN] = time;
        updatedAppointment[Consts.AOR.APPOINTMENT_STATUS_COLUMN] = "Pending";
        appointmentHandler.updateRow(Consts.AOR.ID_COLUMN, appointmentID, updatedAppointment);
        System.out.println("Appointment rescheduled successfully: " + appointmentID);
    }

    /**
     * Cancel an existing appointment.
     *
     * @param appointmentID The ID of the appointment to cancel.
     * @throws IOException If there's an error updating the CSV.
     */
    public void cancelAppointment(String appointmentID) throws IOException {
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentID);
        }
        String[] updatedAppointment = rows.get(0);
        updatedAppointment[Consts.AOR.APPOINTMENT_STATUS_COLUMN] = "Cancelled";
        appointmentHandler.updateRow(Consts.AOR.ID_COLUMN, appointmentID, updatedAppointment);
        System.out.println("Appointment cancelled successfully: " + appointmentID);
    }

    /**
     * Accept an appointment and update its status to 'Confirmed'.
     *
     * @param appointmentID The ID of the appointment to accept.
     * @throws IOException If there's an error updating the CSV.
     */
    public void acceptAppointment(String appointmentID) throws IOException {
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentID);
        }
        String[] updatedAppointment = rows.get(0);
        updatedAppointment[Consts.AOR.APPOINTMENT_STATUS_COLUMN] = "Confirmed"; // Set status to Confirmed
        appointmentHandler.updateRow(Consts.AOR.ID_COLUMN, appointmentID, updatedAppointment);
        System.out.println("Appointment accepted and status updated to Confirmed: " + appointmentID);
    }

    /**
     * Generate a new unique appointment ID.
     *
     * @return A unique appointment ID.
     */
    private String generateNewAppointmentID() {
        int maxId = 0;
        for (String appointmentID : appointmentHandler.readCsv().keySet()) {
            maxId = Math.max(maxId, Integer.parseInt(appointmentID.substring(1)));
        }
        return "A" + String.format("%03d", maxId + 1);
    }

    /**
     * Displays all scheduled appointments for a given patient.
     * 
     *
     * @param patientID The ID of the patient for whom scheduled appointments need
     *                  to be retrieved.
     * @throws IOException If there's an error reading or updating the appointment
     *                     data from the CSV.
     */
    public void viewScheduledAppointments(String patientID) throws IOException {
        // Fetch rows for the given patientID from the AOR (Appointment Order Records)
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.PATIENT_ID_COLUMN, patientID);

        // If no appointments are found for the patient, notify the user
        if (rows.isEmpty()) {
            System.out.println("No scheduled appointments found for patient ID: " + patientID);
            return;
        }

        // Print the details of each appointment for the given patient
        System.out.println("Scheduled appointments for Patient ID: " + patientID);
        for (String[] row : rows) {
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String doctorName = row[Consts.AOR.DOCTOR_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String status = row[Consts.AOR.APPOINTMENT_STATUS_COLUMN];

            System.out.println("Appointment ID: " + appointmentID);
            System.out.println("Doctor: " + doctorName);
            System.out.println("Date: " + date);
            System.out.println("Time: " + time);
            System.out.println("Status: " + status);
            System.out.println("----------------------------");
        }
    }

    /**
     * View completed appointments with their outcome details.
     * 
     * @param patientID The ID of the patient for whom completed appointments need
     *                  to be retrieved.
     * @throws IOException If there's an error reading or updating the appointment
     *                     data from the CSV.
     */
    public void viewPastAppointmentsOutcome(String patientID) throws IOException {
        // Fetch rows for the given patientID from the AOR (Appointment Order Records)
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.PATIENT_ID_COLUMN, patientID);

        // If no appointments are found for the patient, notify the user
        if (rows.isEmpty()) {
            System.out.println("No appointments found for patient ID: " + patientID);
            return;
        }

        // Print the details of completed appointments for the given patient
        System.out.println("Completed appointments for Patient ID: " + patientID);
        boolean foundCompleted = false;
        for (String[] row : rows) {
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String doctorName = row[Consts.AOR.DOCTOR_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String status = row[Consts.AOR.APPOINTMENT_STATUS_COLUMN];
            String prescription = row[Consts.AOR.PRESCRIPTION_COLUMN]; // Assuming prescription info is in this column
            String notes = row[Consts.AOR.NOTES_COLUMN]; // Assuming notes about the outcome are in this column

            // Only display completed appointments
            if ("Completed".equalsIgnoreCase(status)) {
                foundCompleted = true;
                System.out.println("Appointment ID: " + appointmentID);
                System.out.println("Doctor: " + doctorName);
                System.out.println("Date: " + date);
                System.out.println("Time: " + time);
                System.out.println("Status: " + status);
                System.out.println("Prescription: " + prescription);
                System.out.println("Notes: " + notes);
                System.out.println("----------------------------");
            }
        }

        if (!foundCompleted) {
            System.out.println("No completed appointments found for patient ID: " + patientID);
        }
    }

    /**
     * View all pending appointments for a given doctor.
     *
     * @param doctorID The ID of the doctor for whom pending appointments need to be
     *                 retrieved.
     * @throws IOException If there's an error reading or updating the appointment
     *                     data from the CSV.
     */
    public void viewPendingAppointments(String doctorID) throws IOException {
        // Fetch rows for the given doctorID from the AOR (Appointment Order Records)
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.DOCTOR_ID_COLUMN, doctorID);

        // If no appointments are found for the doctor, notify the user
        if (rows.isEmpty()) {
            System.out.println("No appointments found for Doctor ID: " + doctorID);
            return;
        }

        // Print the details of each pending appointment for the given doctor
        System.out.println("Pending appointments for Doctor ID: " + doctorID);
        boolean foundPending = false;
        for (String[] row : rows) {
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String patientName = row[Consts.AOR.PATIENT_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String status = row[Consts.AOR.APPOINTMENT_STATUS_COLUMN];

            // Only display pending appointments
            if ("Pending".equalsIgnoreCase(status)) {
                foundPending = true;
                System.out.println("Appointment ID: " + appointmentID);
                System.out.println("Patient: " + patientName);
                System.out.println("Date: " + date);
                System.out.println("Time: " + time);
                System.out.println("Status: " + status);
                System.out.println("----------------------------");
            }
        }

        if (!foundPending) {
            System.out.println("No pending appointments found for Doctor ID: " + doctorID);
        }
    }

    /**
     * Record the outcome of an appointment by adding prescription and notes.
     * 
     * @param appointmentID The ID of the appointment to update.
     * @param prescription  The prescription given during the appointment.
     * @param notes         Additional notes regarding the outcome of the
     *                      appointment.
     * @throws IOException If there's an error updating the CSV.
     */
    public void recordAppointmentOutcome(String appointmentID, String prescription, String notes) throws IOException {
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentID);
        }

        String[] updatedAppointment = rows.get(0);
        updatedAppointment[Consts.AOR.PRESCRIPTION_COLUMN] = prescription; // Set prescription
        updatedAppointment[Consts.AOR.NOTES_COLUMN] = notes; // Set outcome notes
        updatedAppointment[Consts.AOR.APPOINTMENT_STATUS_COLUMN] = "Completed"; // Mark the appointment as completed
        appointmentHandler.updateRow(Consts.AOR.ID_COLUMN, appointmentID, updatedAppointment);

        System.out.println("Appointment outcome recorded successfully: " + appointmentID);
    }

    /**
     * Retrieve the patient's name by their ID.
     *
     * @param patientID The patient ID.
     * @return The patient's name.
     */
    private String getPatientName(String patientID) {
        List<String[]> rows = patientHandler.getRows(0, patientID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Patient ID not found: " + patientID);
        }
        return rows.get(0)[1]; // Assuming the patient's name is in the NAME_COLUMN
    }

    /**
     * Retrieve the doctor's name by their ID.
     *
     * @param doctorID The doctor ID.
     * @return The doctor's name.
     */
    private String getDoctorName(String doctorID) {
        List<String[]> rows = doctorHandler.getRows(0, doctorID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Doctor ID not found: " + doctorID);
        }
        return rows.get(0)[1]; // Assuming the doctor's name is in the NAME_COLUMN
    }    
}
