


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends Staff {

    // Private Constructor
    private Doctor(String[] details, IOHandler handler) {
        super(details, handler);
    }

    // Factory Method to Create a Doctor Instance
    public static Doctor getDoctor(String doctorID, IOHandler handler) {
        List<String[]> userDetails = handler.getRows(Consts.Staff.ID_COLUMN, doctorID);
        if (userDetails.isEmpty()) {
            return null;
        }
        return new Doctor(userDetails.get(0), handler);
    }

    // View Patient Medical Records
    public MedicalRecord _View_Patient_Medical_Records() throws Exception {
        try {
            AppointmentController appointmentController = new AppointmentController();
            List<Appointment> appointments = appointmentController.getDoctorAppointments(getUserID());
            List<String> patientIDs = new ArrayList<>();

            System.out.println("\nChoose a patient under your care to view medical history:\n");

            for (Appointment appointment : appointments) {
                String patientID = appointment.getPatientID();
                if (!patientIDs.contains(patientID)) {
                    patientIDs.add(patientID);
                    System.out.println(patientIDs.size() + ". ID: " + patientID);
                }
            }

            AttributeController ac = AttributeController.getInstance();
            int choice = ac.inputInt("Input choice of patient: ");

            if (choice < 1 || choice > patientIDs.size()) {
                System.out.println("Invalid choice. Exiting...");
                return null;
            } else {
                String selectedPatientID = patientIDs.get(choice - 1);
                MedicalRecord medicalRecord = new MedicalRecord(selectedPatientID, new CsvHandler(Consts.Patient.FILE_NAME));
                medicalRecord.displayPatientMedicalRecord();
                return medicalRecord;
            }
        } catch (Exception e) {
            System.err.println("Error viewing patient medical records: " + e.getMessage());
            return null;
        }
    }

    // Update Patient Medical Records
    public void _Update_Patient_Medical_Records() throws Exception {
        try {
            AttributeController ac = AttributeController.getInstance();
            System.out.println("Enter the patient ID whose record needs updating: ");
            MedicalRecord patientRecord = _View_Patient_Medical_Records();

            if (patientRecord == null) {
                return;
            }

            String diagnosis = ac.inputString("Input diagnosis, if any: ");
            String treatment = ac.inputString("Input patient treatment, if any: ");
            String notes = ac.inputString("Input notes, if any. Press enter twice to escape: ");
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            patientRecord.addMedicalHistory(new MedicalDiagnosis(diagnosis, treatment, notes, today));
            System.out.println("Patient medical record updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating patient medical records: " + e.getMessage());
        }
    }

    // View Personal Schedule
    public void _View_Personal_Schedule() {
        try {
            AppointmentController appointmentController = new AppointmentController();
            List<Appointment> appointments = appointmentController.getDoctorSchedule(getUserID());

            System.out.println("Personal Schedule:");
            for (Appointment appointment : appointments) {
                System.out.println(appointment.toString());
            }
        } catch (Exception e) {
            System.err.println("Error viewing personal schedule: " + e.getMessage());
        }
    }

    // Set Availability for Appointments
    public void _Set_Availability_or_Appointments() {
        try {
            AttributeController ac = AttributeController.getInstance();
            String dateInput = ac.inputString("Enter the date (yyyy-mm-dd): ");
            LocalDate date = LocalDate.parse(dateInput);

            String timeInput = ac.inputString("Enter the time (HH:mm): ");
            LocalTime time = LocalTime.parse(timeInput);

            String appointmentID = "APPT-" + getUserID() + "-" + System.currentTimeMillis(); // generate a unique appointment ID

            Appointment newAppointment = new Appointment(appointmentID, null, getUserID(), date, time);
            newAppointment.setStatus(AppointmentStatus.AVAILABLE);

            AppointmentController appointmentController = new AppointmentController();
            appointmentController.setDoctorAvailability(getUserID(), newAppointment);

            System.out.println("Availability set successfully.");
        } catch (Exception e) {
            System.err.println("Error setting availability: " + e.getMessage());
        }
    }

    // Accept or Decline Appointment Requests
    public void _Accept_or_Decline_Appointment_Requests() {
        try {
            AppointmentController appointmentController = new AppointmentController();
            List<Appointment> pendingAppointments = appointmentController.getDoctorAppointments(getUserID());
            pendingAppointments.removeIf(appointment -> appointment.getStatus() != AppointmentStatus.PENDING);

            if (pendingAppointments.isEmpty()) {
                System.out.println("No pending appointments to review.");
                return;
            }

            System.out.println("Pending Appointments:");
            for (int i = 0; i < pendingAppointments.size(); i++) {
                System.out.println((i + 1) + ". " + pendingAppointments.get(i).toString());
            }

            AttributeController ac = AttributeController.getInstance();
            int choice = ac.inputInt("Select an appointment to accept/decline: ");

            if (choice < 1 || choice > pendingAppointments.size()) {
                System.out.println("Invalid choice. Exiting...");
                return;
            }

            Appointment selectedAppointment = pendingAppointments.get(choice - 1);
            System.out.println("1. Accept");
            System.out.println("2. Decline");

            int action = ac.inputInt("Enter your choice: ");

            if (action == 1) {
                appointmentController.acceptAppointmentRequest(getUserID(), selectedAppointment.getAppointmentID());
                System.out.println("Appointment accepted.");
            } else if (action == 2) {
                appointmentController.declineAppointmentRequest(getUserID(), selectedAppointment.getAppointmentID());
                System.out.println("Appointment declined.");
            } else {
                System.out.println("Invalid choice. Exiting...");
            }
        } catch (Exception e) {
            System.err.println("Error accepting or declining appointments: " + e.getMessage());
        }
    }

    // View Upcoming Appointments
    public void _View_Upcoming_Appointments() {
        try {
            AppointmentController appointmentController = new AppointmentController();
            List<Appointment> appointments = appointmentController.getDoctorAppointments(getUserID());

            System.out.println("Upcoming Appointments:");
            for (Appointment appointment : appointments) {
                if (appointment.getStatus() == AppointmentStatus.CONFIRMED) {
                    System.out.println(appointment.toString());
                }
            }
        } catch (Exception e) {
            System.err.println("Error viewing upcoming appointments: " + e.getMessage());
        }
    }

    // Record Appointment Outcome
    public void _Record_Appointment_Outcome() {
        try {
            AttributeController ac = AttributeController.getInstance();

            String appointmentID = ac.inputString("Enter Appointment ID: ");
            String prescription = ac.inputString("Enter prescription details: ");
            String notes = ac.inputString("Enter consultation notes: ");

            AORController aorController = new AORController(new CsvHandler(Consts.AOR.FILE_NAME), null, null);
            aorController.recordAppointmentOutcome(appointmentID, prescription, notes);

            System.out.println("Appointment outcome recorded successfully.");
        } catch (Exception e) {
            System.err.println("Error recording appointment outcome: " + e.getMessage());
        }
    }
}
