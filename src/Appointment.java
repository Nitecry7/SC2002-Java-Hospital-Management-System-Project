import java.time.LocalTime;
import java.util.List;

/**
 * The Appointment class represents a medical appointment with details such as
 * patient, doctor, status, date, and time.
 */
public class Appointment 
{
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private AppointmentStatus status;
    private String date;
    private int timeSlot;
    private IOHandler handler;

    /**
     * Private constructor to initialize an Appointment object with details and a handler.
     *
     * @param details An array of appointment details.
     * @param handler The IOHandler for managing appointment data.
     */
    private Appointment(String[] details, IOHandler handler) {
        this.appointmentID = details[Consts.AOR.ID_COLUMN];
        this.patientID = details[Consts.AOR.PATIENT_ID_COLUMN];
        this.doctorID = details[Consts.AOR.DOCTOR_ID_COLUMN];
        this.status = AppointmentStatus.valueOf(details[Consts.AOR.APPOINTMENT_STATUS_COLUMN].toUpperCase());
        this.date = details[Consts.AOR.DATE_COLUMN];
        this.timeSlot = Integer.parseInt(details[Consts.AOR.TIME_COLUMN]);
        this.handler = handler;
    }

    /**
     * Factory method to create an Appointment object based on an appointment ID.
     *
     * @param appointmentID The ID of the appointment.
     * @param handler       The IOHandler for managing appointment data.
     * @return The Appointment object if found, otherwise null.
     */
    public static Appointment getAppointment(String appointmentID, IOHandler handler) {
        List<String[]> appointmentDetails = handler.getRows(Consts.Staff.ID_COLUMN, appointmentID);

        if (appointmentDetails.isEmpty()) {
            return null;
        } else {
            return new Appointment(appointmentDetails.get(0), handler);
        }
    }

    /**
     * Gets the appointment ID.
     *
     * @return The appointment ID.
     */
    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * Gets the patient ID.
     *
     * @return The patient ID.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Gets the doctor ID.
     *
     * @return The doctor ID.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Gets the status of the appointment.
     *
     * @return The appointment status.
     */
    public AppointmentStatus getStatus() {
        return status;
    }

    /**
     * Gets the date of the appointment.
     *
     * @return The appointment date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the time slot of the appointment.
     *
     * @return The appointment time slot as an integer.
     */
    public int getTimeSlot() {
        return timeSlot;
    }

    /**
     * Sets the status of the appointment.
     *
     * @param status The new status to set.
     */
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    /**
     * Sets the date of the appointment.
     *
     * @param date The new date to set.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the time slot of the appointment.
     *
     * @param timeSlot The new time slot to set.
     */
    public void setTime(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Converts the time slot to a human-readable time in HH:mm format.
     *
     * @return The appointment time as a string.
     */
    public String getTime(){
        LocalTime time = LocalTime.of(9,0,0);
        time = time.plusMinutes(30 * timeSlot);
        return time.toString();
    }

    /**
     * Returns a string representation of the Appointment object.
     *
     * @return A string containing appointment details.
     */
    @Override
    public String toString() {
        return "\nAppointment ID: " + appointmentID +
                "\nPatient ID: " + patientID +
                "\nDoctor ID: " + doctorID +
                "\nStatus: " + status +
                "\nDate: " + date +
                "\nTime: " + getTime();
    }
}
