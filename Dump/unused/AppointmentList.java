package unused;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Appointment;
import Consts;
import CsvHandler;
import IOHandler;
import Consts.AOR;

public class AppointmentList {

    private List<Appointment> appointments;

    IOHandler csvHandler;

    public AppointmentList() {
        try {
        csvHandler = new CsvHandler(Consts.AOR.FILE_NAME);
        this.appointments = new ArrayList<>();
        
        List<String[]> appointmentDetails = new ArrayList<>(csvHandler.readCsvValues());
        for (String[] s : appointmentDetails) {
            appointments.add(Appointment.getAppointment(s[Consts.AOR.ID_COLUMN], csvHandler));
        }
       
        } catch (Exception e) {
            System.out.println("Error reading appointments");
            e.printStackTrace();
        }
        
    }
    /*
     * private String appointmentID;
    private String patientID;
    private String doctorID;
    private AppointmentStatus status;
    private String date;
    private int timeSlot;
     */

    public void addAppointment(Appointment appointment) throws IOException {
        appointments.add(appointment);
        String[] details = new String[11];
        details[Consts.AOR.ID_COLUMN] = appointment.getAppointmentID();
        details[Consts.AOR.PATIENT_ID_COLUMN] = appointment.getPatientID();
        details[Consts.AOR.DOCTOR_ID_COLUMN] = appointment.getDoctorID();
        details[Consts.AOR.DATE_COLUMN] = appointment.getDate();
        details[Consts.AOR.TIME_COLUMN] = String.valueOf(appointment.getTimeSlot());
        
        csvHandler.addRow(details);

    }

    public void removeAppointment(String appointmentID) { 
        try { 
            csvHandler.removeRows(Consts.AOR.ID_COLUMN, appointmentID);
            appointments.removeIf(appointment -> appointment.getAppointmentID().equals(appointmentID));
        } catch (Exception e) {
            System.out.println("No such appointment found");
        }
    }

    public Appointment findAppointmentByID(String appointmentID) {

        List<String[]> rows = csvHandler.getRows(Consts.AOR.ID_COLUMN, appointmentID);
        if (rows.isEmpty()) {
            System.out.println("Could not find appointments");
            return null;
        }
        else {
            return Appointment.getAppointment(appointmentID, csvHandler);
        }
        
    }

    public void updatedAppointment(Appointment appointment) {

    }

    public List<Appointment> getAppointmentsForPatient(String patientID) {

        List<String[]> rows = csvHandler.getRows(Consts.AOR.PATIENT_ID_COLUMN, patientID);
        List<Appointment> result = new ArrayList<>();

        for (String[] s : rows) {
            result.add(Appointment.getAppointment(s[Consts.AOR.ID_COLUMN], csvHandler));
        }
        return result;
    }

    public List<Appointment> getAppointmentsForDoctor(String doctorID) {
        List<String[]> rows = csvHandler.getRows(Consts.AOR.DOCTOR_ID_COLUMN, doctorID);
        List<Appointment> result = new ArrayList<>();

        for (String[] s : rows) {
            result.add(Appointment.getAppointment(s[Consts.AOR.ID_COLUMN], csvHandler));
        }
        return result;
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }
}
