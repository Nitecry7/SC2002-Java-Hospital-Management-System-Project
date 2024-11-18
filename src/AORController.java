import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
}
