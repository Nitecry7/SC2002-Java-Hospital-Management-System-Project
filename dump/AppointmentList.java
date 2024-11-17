import java.util.ArrayList;
import java.util.List;

public class AppointmentList {

    private List<Appointment> appointments;

    public AppointmentList() {
        this.appointments = new ArrayList<>();
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void removeAppointment(String appointmentID) {
        appointments.removeIf(appointment -> appointment.getAppointmentID().equals(appointmentID));
    }

    public Appointment findAppointmentByID(String appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                return appointment;
            }
        }
        return null;
    }

    public List<Appointment> getAppointmentsForPatient(String patientID) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID)) {
                result.add(appointment);
            }
        }
        return result;
    }

    public List<Appointment> getAppointmentsForDoctor(String doctorID) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctorID)) {
                result.add(appointment);
            }
        }
        return result;
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }
}
