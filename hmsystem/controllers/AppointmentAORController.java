package hmsystem.controller;

import hmsystem.io.IOHandler;
import hmsystem.data.Consts;

import java.io.IOException;
import java.util.List;

public class AppointmentAORController {
    private IOHandler appointmentHandler;

    public AppointmentAORController(IOHandler appointmentHandler) {
        this.appointmentHandler = appointmentHandler;
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
        List<String[]> rows = appointmentHandler.getRows(Consts.AppointmentAORList.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentID);
        }
        String[] updatedAppointment = rows.get(0);
        updatedAppointment[Consts.AppointmentAORList.DOCTOR_ID_COLUMN] = doctorID;
        updatedAppointment[Consts.AppointmentAORList.DOCTOR_NAME_COLUMN] = getDoctorName(doctorID);
        updatedAppointment[Consts.AppointmentAORList.DATE_COLUMN] = date;
        updatedAppointment[Consts.AppointmentAORList.TIME_COLUMN] = time;
        updatedAppointment[Consts.AppointmentAORList.APPOINTMENT_STATUS_COLUMN] = "Pending";
        appointmentHandler.updateRow(Consts.AppointmentAORList.ID_COLUMN, appointmentID, updatedAppointment);
        System.out.println("Appointment rescheduled successfully: " + appointmentID);
    }

    /**
     * Cancel an existing appointment.
     *
     * @param appointmentID The ID of the appointment to cancel.
     * @throws IOException If there's an error updating the CSV.
     */
    public void cancelAppointment(String appointmentID) throws IOException {
        List<String[]> rows = appointmentHandler.getRows(Consts.AppointmentAORList.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentID);
        }
        String[] updatedAppointment = rows.get(0);
        updatedAppointment[Consts.AppointmentAORList.APPOINTMENT_STATUS_COLUMN] = "Cancelled";
        appointmentHandler.updateRow(Consts.AppointmentAORList.ID_COLUMN, appointmentID, updatedAppointment);
        System.out.println("Appointment cancelled successfully: " + appointmentID);
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
     * Retrieve the patient's name by their ID.
     *
     * @param patientID The patient ID.
     * @return The patient's name.
     */
    private String getPatientName(String patientID) {
        // Replace this with actual logic to fetch the patient's name.
        return "Mock Patient Name";
    }

    /**
     * Retrieve the doctor's name by their ID.
     *
     * @param doctorID The doctor ID.
     * @return The doctor's name.
     */
    private String getDoctorName(String doctorID) {
        // Replace this with actual logic to fetch the doctor's name.
        return "Mock Doctor Name";
    }
}
