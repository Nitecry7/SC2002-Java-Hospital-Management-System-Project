
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AORController {

    private static AORController AorController = null;
    private static IOHandler appointmentHandler, patientHandler, doctorHandler;

    public static AORController getInstance() throws IOException {
        appointmentHandler = new CsvHandler(Consts.AOR.FILE_NAME);
        patientHandler = new CsvHandler(Consts.Patient.FILE_NAME);
        doctorHandler = new CsvHandler(Consts.Staff.FILE_NAME);
        if (AorController == null) {
            AORController.AorController = new AORController();
        }
        return AorController;
    }

    protected AORController() {
        /* 
        System.out.println("test");
        try {
            System.out.println("test2");
        } catch (IOException e) {
            System.out.println("Error occurred creating AORController");
            e.printStackTrace();
        }
            */
    }

public List<String> viewAllAppointmentsDetails() throws IOException {
    // Read all rows (appointments) using the readCsvValues method
    Collection<String[]> rows = appointmentHandler.readCsvValues();

    // If there are no appointments, return an empty list
    if (rows.isEmpty()) {
        return new ArrayList<>(); // Return an empty list if no appointments are found
    }

    // List to hold all the appointment details as strings
    List<String> appointmentDetails = new ArrayList<>();

    // Define date and time format (adjust the format based on your CSV content)
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    // Create a formatted string for each appointment row
    for (String[] row : rows) {
        String appointmentID = row[Consts.AOR.ID_COLUMN];
        String patientID = row[Consts.AOR.PATIENT_ID_COLUMN]; // Adjust column index if necessary
        String doctorID = row[Consts.AOR.DOCTOR_ID_COLUMN];   // Adjust column index if necessary
        String dateStr = row[Consts.AOR.DATE_COLUMN];
        String timeStr = row[Consts.AOR.TIME_COLUMN];

        // Convert date and time strings to LocalDate and LocalTime
        LocalDate date = LocalDate.parse(dateStr, dateFormatter);
        LocalTime time = LocalTime.parse(timeStr, timeFormatter);

        // Build a formatted string for the appointment
        String formattedDetails = String.format(
            "Appointment ID: %s\nPatient ID: %s\nDoctor ID: %s\nDate: %s\nTime: %s",
            appointmentID, patientID, doctorID, date.format(dateFormatter), time.format(timeFormatter)
        );

        // Add the formatted string to the list
        appointmentDetails.add(formattedDetails);
    }

    // Return the list of formatted appointment details
    return appointmentDetails;
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
    public void scheduleAppointment(String patientID, String doctorID, int timeSlot, String date, String service)
            throws IOException {
        String appointmentID = generateNewAppointmentID();
        String[] newAppointment = {
                appointmentID,
                patientID,
                getPatientName(patientID),
                doctorID,
                getDoctorName(doctorID),
                date,
                String.valueOf(timeSlot),
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
     * Retrieves all scheduled appointments for a given patient.
     *
     * @param patientID The ID of the patient for whom scheduled appointments need
     *                  to be retrieved.
     * @return A list of strings, each representing a scheduled appointment for the
     *         given patient.
     * @throws IOException If there's an error reading or updating the appointment
     *                     data from the CSV.
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
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String doctorName = row[Consts.AOR.DOCTOR_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String serviceType = row[Consts.AOR.SERVICE_COLUMN];

            // Format the appointment details
            String details = String.format(
                    "Appointment ID: %s\nDoctor: %s\nDate: %s\nTime: %s\nService Type: %s",
                    appointmentID, doctorName, date, time, serviceType);

            scheduledAppointments.add(details); // Add the formatted string to the list
        }

        return scheduledAppointments; // Return the list of appointment details
    }

    /**
     * View completed appointments with their outcome details.
     * 
     * @param patientID The ID of the patient for whom completed appointments need
     *                  to be retrieved.
     * @throws IOException If there's an error reading or updating the appointment
     *                     data from the CSV.
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

    public List<AOR> returnPastAppointmentsOutcome(String patientID) throws Exception {

    
        List<String[]> rows = appointmentHandler.getRows(Consts.AOR.PATIENT_ID_COLUMN, patientID);

        List<AOR> appointments= new ArrayList<>();

        for (String[] s : rows) {
            if (s[Consts.AOR.APPOINTMENT_STATUS_COLUMN].equals(AppointmentStatus.COMPLETED.name())) {
                appointments.add(AOR.findAOR(s[Consts.AOR.ID_COLUMN]));
            }
        }
        return appointments;
    }

    /**
     * View all pending appointments for a given doctor.
     *
     * @param doctorID The ID of the doctor for whom pending appointments need to be
     *                 retrieved.
     * @throws IOException If there's an error reading or updating the appointment
     *                     data from the CSV.
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

        boolean foundPending = false;
        for (String[] row : rows) {
            String appointmentID = row[Consts.AOR.ID_COLUMN];
            String patientName = row[Consts.AOR.PATIENT_NAME_COLUMN];
            String date = row[Consts.AOR.DATE_COLUMN];
            String time = row[Consts.AOR.TIME_COLUMN];
            String status = row[Consts.AOR.APPOINTMENT_STATUS_COLUMN];

            // Only include pending appointments
            if ("Pending".equalsIgnoreCase(status)) {
                foundPending = true;
                appointmentDetails.add("Appointment ID: " + appointmentID);
                appointmentDetails.add("Patient: " + patientName);
                appointmentDetails.add("Date: " + date);
                appointmentDetails.add("Time: " + time);
                appointmentDetails.add("Status: " + status);
                appointmentDetails.add("----------------------------");
            }
        }

        // If no pending appointments are found, add a message to the list
        if (!foundPending) {
            appointmentDetails.add("No pending appointments found for Doctor ID: " + doctorID);
        }

        // Return the list of appointment details
        return appointmentDetails;
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
        return rows.get(0)[Consts.Patient.NAME_COLUMN]; // Assuming the patient's name is in the NAME_COLUMN
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
        return rows.get(0)[Consts.Staff.NAME_COLUMN]; // Assuming the doctor's name is in the NAME_COLUMN
    }    
}
