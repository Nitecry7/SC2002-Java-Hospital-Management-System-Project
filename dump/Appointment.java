

import java.time.LocalTime;

import java.util.List;

public class Appointment 
{
    
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private AppointmentStatus status;
    private String date;
    private int timeSlot;
    private IOHandler handler;

    // Constructor
    private Appointment(String[] details, IOHandler handler) {
        this.appointmentID = details[Consts.AOR.ID_COLUMN];
        this.patientID = details[Consts.AOR.PATIENT_ID_COLUMN];
        this.doctorID = details[Consts.AOR.DOCTOR_ID_COLUMN];
        this.status = AppointmentStatus.valueOf(details[Consts.AOR.APPOINTMENT_STATUS_COLUMN]);
        this.date = details[Consts.AOR.DATE_COLUMN];
        this.timeSlot = Integer.parseInt(details[Consts.AOR.TIME_COLUMN]);
        this.handler = handler;
        //this.LocalDate = String[]
        //this.date = date;
        //this.time = time;
        
        this.status = AppointmentStatus.PENDING;
    }

    public static Appointment getAppointment(String appointmentID, IOHandler handler) {
        List<String[]> appointmentDetails = handler.getRows(Consts.Staff.ID_COLUMN, appointmentID);
        if (appointmentDetails.isEmpty()) {
            return null;
        }
        else {
            return new Appointment(appointmentDetails.get(0), handler);
        }
    }

    // Getters
    public String getAppointmentID() {
        return appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    // Setters
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getTime(){
        LocalTime time = LocalTime.of(9,0,0);
        time.plusMinutes(30 * timeSlot);
        return time.toString();
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentID +
                "\nPatient ID: " + patientID +
                "\nDoctor ID: " + doctorID +
                "\nStatus: " + status +
                "\nDate" + date +
                "\nTime" + getTime();
    }

}
