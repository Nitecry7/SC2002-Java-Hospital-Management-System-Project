package hmsystem.controllers;
import java.util.List;

import hmsystem.models.Appointment;
import hmsystem.models.Appointment2;
import hmsystem.models.AppointmentList;

public class AppointmentController
        implements AppointmentManagerDoctor, AppointmentManagerPatient, AppointmentManagerAdmin {

    private AppointmentList appointmentList;

    public AppointmentController() {
        this.appointmentList = new AppointmentList();
    }

    // Patient Methods
    @Override
    // Patients view available appointment slots with doctors.
    public List<Appointment2> viewAvailableSlots(String doctorID) {
        return appointmentList.getAppointmentsForDoctor(doctorID);
    }

    @Override
    // Patients schedule appointments
    public void scheduleAppointment(String patientID, String doctorID, Appointment2 appointment) {
        appointmentList.addAppointment(appointment);
    }

    @Override
    // Patients reschedule appointments
    public void rescheduleAppointment(String patientID, String appointmentID, Appointment2 newAppointment) {
        Appointment2 appointment = appointmentList.findAppointmentByID(appointmentID);
        if (appointment != null && appointment.getPatientID().equals(patientID)) {
            appointmentList.removeAppointment(appointmentID);
            appointmentList.addAppointment(newAppointment);
        }
    }

    @Override
    // Patients cancel appointments
    public void cancelAppointment(String patientID, String appointmentID) {
        Appointment2 appointment = appointmentList.findAppointmentByID(appointmentID);
        if (appointment != null && appointment.getPatientID().equals(patientID)) {
            appointmentList.removeAppointment(appointmentID);
        }
    }

    @Override
    // Patients view the status of their schedules appointments
    public List<Appointment2> getPatientAppointments(String patientID) {
        return appointmentList.getAppointmentsForPatient(patientID);
    }

    // Doctor Methods
    @Override
    // Doctors view their personal schedule
    public List<Appointment2> getDoctorSchedule(String doctorID) {
        return appointmentList.getAppointmentsForDoctor(doctorID);
    }

    @Override
    // Doctors set their availability for appointments
    public void setDoctorAvailability(String doctorID, Appointment2 appointment) {
        appointmentList.addAppointment(appointment);
    }

    @Override
    // Doctors accept appointment requests
    public void acceptAppointmentRequest(String doctorID, String appointmentID) {
        Appointment2 appointment = appointmentList.findAppointmentByID(appointmentID);
        if (appointment != null && appointment.getDoctorID().equals(doctorID)) {
            appointment.setStatus(Appointment2.AppointmentStatus.CONFIRMED);
        }
    }

    @Override
    // Doctors decline appointment requests
    public void declineAppointmentRequest(String doctorID, String appointmentID) {
        Appointment2 appointment = appointmentList.findAppointmentByID(appointmentID);
        if (appointment != null && appointment.getDoctorID().equals(doctorID)) {
            appointment.setStatus(Appointment2.AppointmentStatus.CANCELED);
        }
    }

    @Override
    // Doctors 
    public List<Appointment2> getDoctorAppointments(String doctorID) {
        return appointmentList.getAppointmentsForDoctor(doctorID);
    }

    // Administrator Methods
    @Override
    public List<Appointment2> getAllAppointments() {
        return appointmentList.getAllAppointments();
    }
}
