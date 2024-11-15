package hmsystem.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment2 
{
    
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private AppointmentStatus status;
    private LocalDate date;
    private LocalTime time;

    public enum AppointmentStatus {
        PENDING,
        CONFIRMED,
        CANCELED,
        COMPLETED
    }

    // Constructor
    public Appointment2(String appointmentID, String patientID, String doctorID, LocalDate date, LocalTime time) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.date = date;
        this.time = time;
        this.status = AppointmentStatus.PENDING;
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    // Setters
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentID +
                "\nPatient ID: " + patientID +
                "\nDoctor ID: " + doctorID +
                "\nStatus: " + status +
                "\nDate" + date +
                "\nTime" + time;
    }

}
