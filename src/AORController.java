import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.lang.Exception;

/**
 * The AORController class is responsible for managing appointment records.
 * It provides functionality to view, schedule, reschedule, cancel, and
 * accept appointments, as well as manage appointment outcomes.
 */
public class AORController {

    private static AORController AorController = null;
    private static IOHandler appointmentHandler, patientHandler, doctorHandler;

    /**
     * Returns the singleton instance of AORController.
     * Initializes the handlers for appointment, patient, and doctor data.
     *
     * @return The AORController instance.
     * @throws IOException If an error occurs while accessing files.
     */
    public static AORController getInstance() throws IOException {
        appointmentHandler = new CsvHandler(Consts.AOR.FILE_NAME);
        patientHandler = new CsvHandler(Consts.Patient.FILE_NAME);
        doctorHandler = new CsvHandler(Consts.Staff.FILE_NAME);
        if (AorController == null) {
            AORController.AorController = new AORController();
        }
        return AorController;
    }

    /**
     * Protected constructor for AORController.
     */
    protected AORController() {
        // Protected constructor to enforce singleton pattern
    }

    /**
     * Retrieves and formats details of all appointments.
     *
     * @return A list of formatted appointment details.
     * @throws IOException If an error occurs while accessing the appointment data.
     */
    public List<String> viewAllAppointmentsDetails() throws IOException {
        Collection<String[]> rows = appointmentHandler.readCsvValues();

        if (rows.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> appointmentDetails = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (String[] row : rows) {
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String patientID = row[Consts.AOR.PATIENT_ID_COLUMN];
            String doctorID = row[Consts.AOR.DOCTOR_ID_COLUMN];
            String dateStr = row[Consts.AOR.DATE_COLUMN];
            String timeStr = row[Consts.AOR.TIME_COLUMN];

            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
            LocalTime time = LocalTime.parse(timeStr, timeFormatter);

            String formattedDetails = String.format(
                "Appointment ID: %s\nPatient ID: %s\nDoctor ID: %s\nDate: %s\nTime: %s",
                appointmentID, patientID, doctorID, date.format(dateFormatter), time.format(timeFormatter)
            );

            appointmentDetails.add(formattedDetails);
            appointmentDetails.add("----------------------------");
        }

        return appointmentDetails;
    }

    /**
     * Schedules a new appointment.
     *
     * @param patientID  The patient ID.
     * @param doctorID   The doctor ID.
     * @param timeSlot   The time slot for the appointment.
     * @param date       The date of the appointment.
     * @param service    The service type.
     * @throws IOException If an error occurs while updating the appointment data.
     */
    public void scheduleAppointment(String patientID, String doctorID, String timeSlot, String date, String service)
            throws IOException {
        String appointmentID = generateNewAppointmentID();
        String[] newAppointment = {
            appointmentID,
            patientID,
            getPatientName(patientID),
            doctorID,
            getDoctorName(doctorID),
            date,
            timeSlot,
            service,
            "",
            "",
            AppointmentStatus.PENDING.name()
        };
        appointmentHandler.addRow(newAppointment);
        System.out.println("Appointment scheduled successfully: " + appointmentID);
    }

    /**
     * Reschedules an existing appointment.
     *
     * @param appointmentID The ID of the appointment to reschedule.
     * @param doctorID      The new doctor ID.
     * @param timeSlot      The new time slot.
     * @param date          The new date.
     * @throws IOException If an error occurs while updating the appointment data.
     */
    public void rescheduleAppointment(String appointmentID, String doctorID, int timeSlot, String date)
            throws IOException {
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentID);
        }
        String[] updatedAppointment = rows.get(0);
        updatedAppointment[Consts.AOR.DOCTOR_ID_COLUMN] = doctorID;
        updatedAppointment[Consts.AOR.DOCTOR_NAME_COLUMN] = getDoctorName(doctorID);
        updatedAppointment[Consts.AOR.DATE_COLUMN] = date;
        updatedAppointment[Consts.AOR.TIME_COLUMN] = String.valueOf(timeSlot);
        updatedAppointment[Consts.AOR.APPOINTMENT_STATUS_COLUMN] = AppointmentStatus.PENDING.name();
        appointmentHandler.updateRow(Consts.AOR.ID_COLUMN, appointmentID, updatedAppointment);
        System.out.println("Appointment rescheduled successfully: " + appointmentID);
    }

    /**
     * Cancels an existing appointment.
     *
     * @param appointmentID The ID of the appointment to cancel.
     * @throws IOException If an error occurs while updating the appointment data.
     */
    public void cancelAppointment(String appointmentID) throws IOException {
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentID);
        }
        String[] updatedAppointment = rows.get(0);
        if (!updatedAppointment[Consts.AOR.APPOINTMENT_STATUS_COLUMN].equals("PENDING")) {
            System.out.println("You can only cancel pending appointments.");
            return;
        }
        updatedAppointment[Consts.AOR.APPOINTMENT_STATUS_COLUMN] = AppointmentStatus.CANCELED.name();
        appointmentHandler.updateRow(Consts.AOR.ID_COLUMN, appointmentID, updatedAppointment);
        System.out.println("Appointment cancelled successfully: " + appointmentID);
    }

    /**
     * Accepts an appointment and updates its status to 'Confirmed'.
     *
     * @param appointmentID The ID of the appointment to accept.
     * @throws IOException If an error occurs while updating the appointment data.
     */
    public void acceptAppointment(String appointmentID) throws IOException {
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentID);
        }
        String[] updatedAppointment = rows.get(0);

        if (!updatedAppointment[Consts.AOR.APPOINTMENT_STATUS_COLUMN].equals("PENDING")) {
            System.out.println("You can only accept pending appointments.");
            return;
        }
        updatedAppointment[Consts.AOR.APPOINTMENT_STATUS_COLUMN] = AppointmentStatus.CONFIRMED.name();
        appointmentHandler.updateRow(Consts.AOR.ID_COLUMN, appointmentID, updatedAppointment);
        System.out.println("Appointment accepted and status updated to Confirmed: " + appointmentID);
    }

    /**
     * Generates a new unique appointment ID.
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
     * view scheduled appointment for patient
     *
     * @param patientID The ID of patient.
     * @throws IOException If an error occurs while getting the appointment data.
     * @return the string list of appointment
     */
    public List<String> viewScheduledAppointments(String patientID) throws IOException {
        // Fetch rows for the given patientID from the AOR (Appointment Order Records)
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.PATIENT_ID_COLUMN, patientID);
        // If no appointments are found for the patient, return an empty list
        if (rows.isEmpty()) {
            return new ArrayList<>(); // Return an empty list
        }
        // Define the list to hold formatted appointment details
        List<String> scheduledAppointments = new ArrayList<>();
        // Format each appointment row into a string and add it to the list
        for (String[] row : rows) {
            if(row[Consts.AOR.APPOINTMENT_STATUS_COLUMN].equals("CANCELED")) continue;
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String doctorName = row[Consts.AOR.DOCTOR_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String status = row[Consts.AOR.APPOINTMENT_STATUS_COLUMN];
            String serviceType = row[Consts.AOR.SERVICE_COLUMN];
            String note = row[Consts.AOR.NOTES_COLUMN];
            if(status.equals("CANCELED") || status.equals("COMPLETED")) continue;
            // Format the appointment details
            String details = String.format(
                    "Appointment ID: %s\nPatient: %s\nDate: %s\nTime: %s\nService Type: %s\nStatus: %s\nNote: %s",
                    appointmentID, doctorName, date, time, serviceType, status, note);
            scheduledAppointments.add(details); // Add the formatted string to the list
            scheduledAppointments.add("----------------------");
        }
        return scheduledAppointments; // Return the list of appointment details
    }
    /**
     * view scheduled appointment for doctor
     *
     * @param doctorID The ID of doctor.
     * @throws IOException If an error occurs while getting the appointment data.
     * @return the string list of appointment
     */
    public List<String> viewScheduledAppointmentsDoctor(String doctorID) throws IOException {
        // Fetch rows for the given patientID from the AOR (Appointment Order Records)
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.DOCTOR_ID_COLUMN, doctorID);
        // If no appointments are found for the patient, return an empty list
        if (rows.isEmpty()) {
            return new ArrayList<>(); // Return an empty list
        }
        // Define the list to hold formatted appointment details
        List<String> scheduledAppointments = new ArrayList<>();
        // Format each appointment row into a string and add it to the list
        for (String[] row : rows) {
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String patientName = row[Consts.AOR.PATIENT_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String serviceType = row[Consts.AOR.SERVICE_COLUMN];
            String status = row[Consts.AOR.APPOINTMENT_STATUS_COLUMN];
            String note = row[Consts.AOR.NOTES_COLUMN];
            if(status.equals("CANCELED") || status.equals("COMPLETED")) continue;
            // Format the appointment details
            String details = String.format(
                    "Appointment ID: %s\nPatient: %s\nDate: %s\nTime: %s\nService Type: %s\nStatus: %s\nNote: %s",
                    appointmentID, patientName, date, time, serviceType, status, note);
            scheduledAppointments.add(details); // Add the formatted string to the list
            scheduledAppointments.add("----------------------");
        }
        return scheduledAppointments; // Return the list of appointment details
    }
    /**
     * view past appointment outcome for patient
     *
     * @param patientID The ID of patient.
     * @throws IOException If an error occurs while getting the appointment data.
     * @return the string list of appointment outcome
     */
    public List<String> viewPastAppointmentsOutcome(String patientID) throws IOException {
        // Fetch rows for the given patientID from the AOR (Appointment Order Records)
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.PATIENT_ID_COLUMN, patientID);
        // List to store the appointment details
        List<String> appointmentDetails = new ArrayList<>();
        // If no appointments are found for the patient, return an empty list with a
        // message
        if (rows.isEmpty()) {
            appointmentDetails.add("No appointments found for patient ID: " + patientID);
            return appointmentDetails;
        }
        // Add header for completed appointments
        appointmentDetails.add("Completed appointments for Patient ID: " + patientID);
        boolean foundCompleted = false;
        for (String[] row : rows) {
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String doctorName = row[Consts.AOR.DOCTOR_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String status = row[Consts.AOR.APPOINTMENT_STATUS_COLUMN];
            String prescription = row[Consts.AOR.PRESCRIPTION_COLUMN]; // Assuming prescription info is in this column
            String notes = row[Consts.AOR.NOTES_COLUMN]; // Assuming notes about the outcome are in this column
            // Only include completed appointments
            if ("Completed".equalsIgnoreCase(status)) {
                foundCompleted = true;
                appointmentDetails.add("Appointment ID: " + appointmentID);
                appointmentDetails.add("Doctor: " + doctorName);
                appointmentDetails.add("Date: " + date);
                appointmentDetails.add("Time: " + time);
                appointmentDetails.add("Status: " + status);
                appointmentDetails.add("Prescription: " + prescription);
                appointmentDetails.add("Notes: " + notes);
                appointmentDetails.add("----------------------------");
            }
        }
        // If no completed appointments are found, add a message to the list
        if (!foundCompleted) {
            appointmentDetails.add("No completed appointments found for patient ID: " + patientID);
        }
        // Return the list of appointment details
        return appointmentDetails;
    }
    
    /**
     * view pending appointment for doctor 
     *
     * @param doctor The ID of doctor.
     * @throws IOException If an error occurs while getting the appointment data.
     * @return the list of string of pending appointment
     */
    public List<String> viewPendingAppointments(String doctorID) throws IOException {
        // Fetch rows for the given doctorID from the AOR (Appointment Order Records)
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.DOCTOR_ID_COLUMN, doctorID);
        // List to store the appointment details
        List<String> appointmentDetails = new ArrayList<>();
        // If no appointments are found for the doctor, return an empty list with a
        // message
        if (rows.isEmpty()) {
            appointmentDetails.add("No appointments found for Doctor ID: " + doctorID);
            return appointmentDetails;
        }
        // Add header for pending appointments
        appointmentDetails.add("Pending appointments for Doctor ID: " + doctorID);
        for (String[] row : rows) {
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String patientName = row[Consts.AOR.PATIENT_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String status = row[Consts.AOR.APPOINTMENT_STATUS_COLUMN];
            // Only include pending appointments
            if ("Pending".equalsIgnoreCase(status)) {
                appointmentDetails.add("Appointment ID: " + appointmentID);
                appointmentDetails.add("Patient: " + patientName);
                appointmentDetails.add("Date: " + date);
                appointmentDetails.add("Time: " + time);
                appointmentDetails.add("Status: " + status);
                appointmentDetails.add("----------------------------");
            }
        }
        return appointmentDetails;
    }
    /**
     * view confirmed appointment for doctor 
     *
     * @param doctorID The ID of doctor.
     * @throws IOException If an error occurs while getting the appointment data.
     * @return the list of string of confirmed appointment
     */
    public List<String> viewConfirmedAppointments(String doctorID) throws IOException {
        // Fetch rows for the given doctorID from the AOR (Appointment Order Records)
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.DOCTOR_ID_COLUMN, doctorID);
        // List to store the appointment details
        List<String> appointmentDetails = new ArrayList<>();
        // If no appointments are found for the doctor, return an empty list with a
        // message
        if (rows.isEmpty()) {
            appointmentDetails.add("No appointments found for Doctor ID: " + doctorID);
            return appointmentDetails;
        }
        // Add header for pending appointments
        appointmentDetails.add("Pending appointments for Doctor ID: " + doctorID);
        for (String[] row : rows) {
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String patientName = row[Consts.AOR.PATIENT_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String status = row[Consts.AOR.APPOINTMENT_STATUS_COLUMN];
            // Only include pending appointments
            if ("Confirmed".equalsIgnoreCase(status)) {
                appointmentDetails.add("Appointment ID: " + appointmentID);
                appointmentDetails.add("Patient: " + patientName);
                appointmentDetails.add("Date: " + date);
                appointmentDetails.add("Time: " + time);
                appointmentDetails.add("Status: " + status);
                appointmentDetails.add("----------------------------");
            }
        }
        return appointmentDetails;
    }
    /**
     * get patient ID for doctor.
     *
     * @param doctorID The ID of doctor.
     * @return the list of string of patient ID
     */
    List<String> getPatientList(String DoctorID){
        Collection<String[]> data = appointmentHandler.readCsvValues();
        List<String> patient = new ArrayList<String>();
        for(String[] row: data){
            if(row[Consts.AOR.DOCTOR_ID_COLUMN].equals(DoctorID) &&
                !patient.contains(row[Consts.AOR.PATIENT_ID_COLUMN])){
                patient.add(row[Consts.AOR.PATIENT_ID_COLUMN]);
            }
        }
        return patient;
    }
    /**
     * record the appointment outcome
     *
     * @param appointmentID the appointment ID to record
     * @param prescription the prescription of appointment
     * @param notes the note of appointment
     */
    public void recordAppointmentOutcome(String appointmentID, String prescription, String notes) throws IOException {
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentID);
        }
        String[] updatedAppointment = rows.get(0);
        updatedAppointment[Consts.AOR.PRESCRIPTION_COLUMN] = prescription; // Set prescription
        updatedAppointment[Consts.AOR.NOTES_COLUMN] = notes; // Set outcome notes
        updatedAppointment[Consts.AOR.APPOINTMENT_STATUS_COLUMN] = "COMPLETED"; // Mark the appointment as completed
        appointmentHandler.updateRow(Consts.AOR.ID_COLUMN, appointmentID, updatedAppointment);
        System.out.println("Appointment outcome recorded successfully: " + appointmentID);
    }

    /**
     * Retrieves the patient's name by their ID.
     *
     * @param patientID The patient ID.
     * @return The patient's name.
     */
    private String getPatientName(String patientID) {
        List<String[]> rows = patientHandler.getRows(0, patientID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Patient ID not found: " + patientID);
        }
        return rows.get(0)[Consts.Patient.NAME_COLUMN];
    }

    /**
     * Retrieves the doctor's name by their ID.
     *
     * @param doctorID The doctor ID.
     * @return The doctor's name.
     */
    private String getDoctorName(String doctorID) {
        List<String[]> rows = doctorHandler.getRows(0, doctorID);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Doctor ID not found: " + doctorID);
        }
        return rows.get(0)[Consts.Staff.NAME_COLUMN];
    }
    /**
     * view past appointment outcome for patient (as appointment outcome)
     *
     * @param patientID The ID of patient.
     * @return the list of appointment outcome
     * @throws Exception If an error occurs while getting the appointment data.
     */
    public List<AORs> returnPastAppointmentsOutcome(String patientID) throws Exception {
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.PATIENT_ID_COLUMN, patientID);
        List<AORs> appointments= new ArrayList<>();
        for (String[] s : rows) {
            if (s[Consts.AOR.APPOINTMENT_STATUS_COLUMN].equals(AppointmentStatus.COMPLETED.name())) {
                appointments.add(AORs.findAOR(s[Consts.AOR.ID_COLUMN]));
            }
        }
        return appointments;
    }
}
