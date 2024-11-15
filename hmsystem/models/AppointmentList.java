package hmsystem.models;
import java.util.ArrayList;
import java.util.List;

public class AppointmentList {

    private List<Appointment2> appointments;

    public AppointmentList() {
        this.appointments = new ArrayList<>();
    }

    public void addAppointment(Appointment2 appointment) {
        appointments.add(appointment);
    }

    public void removeAppointment(String appointmentID) {
        appointments.removeIf(appointment -> appointment.getAppointmentID().equals(appointmentID));
    }

    public Appointment2 findAppointmentByID(String appointmentID) {
        for (Appointment2 appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                return appointment;
            }
        }
        return null;
    }

    public List<Appointment2> getAppointmentsForPatient(String patientID) {
        List<Appointment2> result = new ArrayList<>();
        for (Appointment2 appointment : appointments) {
            if (appointment.getPatientID().equals(patientID)) {
                result.add(appointment);
            }
        }
        return result;
    }

    public List<Appointment2> getAppointmentsForDoctor(String doctorID) {
        List<Appointment2> result = new ArrayList<>();
        for (Appointment2 appointment : appointments) {
            if (appointment.getDoctorID().equals(doctorID)) {
                result.add(appointment);
            }
        }
        return result;
    }

    public List<Appointment2> getAllAppointments() {
        return appointments;
    }
}
