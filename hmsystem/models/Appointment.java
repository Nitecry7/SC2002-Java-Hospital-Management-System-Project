package hmsystem.models;

import java.sql.Date;

import hmsystem.models.enums.AppointmentStatus;

public class Appointment {
    private String doctorID;
    private String patientID;
    private Date timeSlot;
    private AppointmentStatus status;

    public Appointment(String doctorID, String patientID, Date timeSlot) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.timeSlot = timeSlot;
        this.status = AppointmentStatus.PENDING;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public Date getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Date timeSlot) {
        this.timeSlot = timeSlot;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
