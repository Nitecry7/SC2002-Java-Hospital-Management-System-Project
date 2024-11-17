import java.util.List;


public class AppointmentController {

    private AppointmentList appointmentList;

    public AppointmentController() {
        this.appointmentList = new AppointmentList();
    }

    // Patient Methods
    // Patients view available appointment slots with doctors.
    public List<Appointment> viewAvailableSlots(String doctorID) {
        return appointmentList.getAppointmentsForDoctor(doctorID);
    }

    // Patients schedule appointments
    public void scheduleAppointment(String patientID, String doctorID, Appointment appointment) {
        appointmentList.addAppointment(appointment);
    }

    // Patients reschedule appointments
    public void rescheduleAppointment(String patientID, String appointmentID, Appointment newAppointment) {
        Appointment appointment = appointmentList.findAppointmentByID(appointmentID);
        if (appointment != null && appointment.getPatientID().equals(patientID)) {
            appointmentList.removeAppointment(appointmentID);
            appointmentList.addAppointment(newAppointment);
        }
    }

    // Patients cancel appointments
    public void cancelAppointment(String patientID, String appointmentID) {
        Appointment appointment = appointmentList.findAppointmentByID(appointmentID);
        if (appointment != null && appointment.getPatientID().equals(patientID)) {
            appointmentList.removeAppointment(appointmentID);
        }
    }

    // Patients view the status of their schedules appointments
    public List<Appointment> getPatientAppointments(String patientID) {
        return appointmentList.getAppointmentsForPatient(patientID);
    }

    // Doctor Methods
    // Doctors view their personal schedule
    public List<Appointment> getDoctorSchedule(String doctorID) {
        return appointmentList.getAppointmentsForDoctor(doctorID);
    }

    // Doctors set their availability for appointments
    public void setDoctorAvailability(String doctorID, Appointment appointment) {
        appointmentList.addAppointment(appointment);
    }

    // Doctors accept appointment requests
    public void acceptAppointmentRequest(String doctorID, String appointmentID) {
        Appointment appointment = appointmentList.findAppointmentByID(appointmentID);
        if (appointment != null && appointment.getDoctorID().equals(doctorID)) {
            appointment.setStatus(Appointment.AppointmentStatus.CONFIRMED);
        }
    }

    // Doctors decline appointment requests
    public void declineAppointmentRequest(String doctorID, String appointmentID) {
        Appointment appointment = appointmentList.findAppointmentByID(appointmentID);
        if (appointment != null && appointment.getDoctorID().equals(doctorID)) {
            appointment.setStatus(Appointment.AppointmentStatus.CANCELED);
        }
    }

    // Doctors 
    public List<Appointment> getDoctorAppointments(String doctorID) {
        return appointmentList.getAppointmentsForDoctor(doctorID);
    }

    // Administrator Methods
    public List<Appointment> getAllAppointments() {
        return appointmentList.getAllAppointments();
    }
}
