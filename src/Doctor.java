import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Represents a Doctor, extending the Staff class.
 * Provides methods to manage and view appointments, availability, and patient records.
 */
public class Doctor extends Staff {

    /**
     * Constructor to create a Doctor instance.
     *
     * @param details The details of the doctor.
     * @param handler The IOHandler for managing data.
     */
    private Doctor(String[] details, IOHandler handler) {
        super(details, handler);
    }

    /**
     * Factory method to create a Doctor instance.
     *
     * @param doctorID The ID of the doctor.
     * @param handler  The IOHandler for managing data.
     * @return A Doctor instance or null if the doctor is not found.
     */
    public static Doctor getDoctor(String doctorID, IOHandler handler) {
        List<String[]> userDetails = handler.getRows(Consts.Staff.ID_COLUMN, doctorID);
        if (userDetails.isEmpty()) {
            return null;
        }
        return new Doctor(userDetails.get(0), handler);
    }

    /**
     * View a patient's medical records.
     *
     * @return The patient's MedicalRecord object or null if no records are found.
     */
    public MedicalRecord viewPatientMedicalRecords() {
        try {
            AttributeController ac = AttributeController.getInstance();
            AORController aorc = AORController.getInstance();
            List<String> patientIDList = aorc.getPatientList(getUserID());
            int idx = 1, choice = -1;
            if (patientIDList.size() == 0) {
                System.out.println("Currently no patient");
                return null;
            }
            System.out.println("Here are your patients:");
            for (String patientID : patientIDList) {
                System.out.printf("%d. %s\n", idx, patientID);
                idx = idx + 1;
            }
            do {
                choice = ac.inputInt("Enter choice of patient:");
            } while (choice < 1 || choice >= idx);
            String patientID = patientIDList.get(choice - 1);
            Patient patient = Patient.getPatient(patientID, new CsvHandler(Consts.Patient.FILE_NAME));
            patient._View_Medical_Record();
            return patient.getMedicalRecord();
        } catch (Exception e) {
            System.err.println("Error viewing patient medical records: " + e.getMessage());
            return null;
        }
    }

    /**
     * Update a patient's medical records.
     */
    public void updatePatientMedicalRecords() {
        try {
            AttributeController ac = AttributeController.getInstance();
            System.out.println("Enter the patient ID whose record needs updating: ");
            MedicalRecord patientRecord = viewPatientMedicalRecords();

            if (patientRecord == null) {
                return;
            }

            String diagnosis = ac.inputString("Input diagnosis, if any: ");
            String treatment = ac.inputString("Input patient treatment, if any: ");
            String notes = ac.inputNote("Input notes, if any. Type END at the last line to finish: ");
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            patientRecord.addMedicalHistory(new MedicalDiagnosis(diagnosis, treatment, notes, today));
            System.out.println("Patient medical record updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating patient medical records: " + e.getMessage());
        }
    }

    /**
     * View the personal schedule of the doctor.
     */
    public void viewPersonalSchedule() {
        try {
            AORController aorc = AORController.getInstance();

            System.out.println("Personal Schedule:");
            List<String> appointments = aorc.viewScheduledAppointmentsDoctor(getUserID());
            for (String appointmentInfo : appointments) {
                System.out.println(appointmentInfo);
            }

        } catch (Exception e) {
            System.err.println("Error viewing personal schedule: " + e.getMessage());
        }
    }

    /**
     * Set availability for appointments.
     */
    public void setAvailabilityForAppointments() {
        try {
            System.out.println("1. Set unavailable");
            System.out.println("2. Set available");
            System.out.println("3. Go Back");
            AttributeController ac = AttributeController.getInstance();
            int operation = ac.inputInt("Enter your choice(1-3): ");
            while (operation > 3 || operation < 1) {
                System.out.println("Please enter a valid choice.");
                operation = ac.inputInt("Enter your choice(1-3): ");
            }
            AvailabilityController avC = AvailabilityController.getInstance();
            switch (operation) {
                case 1:
                    avC.addBlockedSlots(userID);
                    break;
                case 2:
                    avC.deleteBlockedSlot(userID);
                    break;
                case 3:
                    return;
            }
            System.out.println("Availability set successfully.");
        } catch (Exception e) {
            System.err.println("Error setting availability: " + e.getMessage());
        }
    }

    /**
     * Accept or decline appointment requests.
     */
    public void acceptOrDeclineAppointmentRequests() {
        try {
            AttributeController ac = AttributeController.getInstance();
            AORController aorc = AORController.getInstance();
            String appID = ac.inputString("Enter appointment ID: ");
            System.out.println("1. Accept");
            System.out.println("2. Decline");
            System.out.println("3. Go Back");
            int operation = ac.inputInt("Enter your choice(1-3): ");
            while (operation > 3 || operation < 1) {
                System.out.println("Please enter a valid choice.");
                operation = ac.inputInt("Enter your choice(1-3): ");
            }
            switch (operation) {
                case 1:
                    aorc.acceptAppointment(appID);
                    break;
                case 2:
                    aorc.cancelAppointment(appID);
                    break;
                case 3:
                    return;
            }
        } catch (Exception e) {
            System.err.println("Error accepting or declining appointments: " + e.getMessage());
        }
    }

    /**
     * View upcoming appointments.
     */
    public void viewUpcomingAppointments() {
        try {
            AORController aorc = AORController.getInstance();

            System.out.println("Personal Schedule:");
            List<String> appointments = aorc.viewConfirmedAppointments(getUserID());
            for (String appointmentInfo : appointments) {
                System.out.println(appointmentInfo);
            }

        } catch (Exception e) {
            System.err.println("Error viewing upcoming appointments: " + e.getMessage());
        }
    }

    /**
     * Record the outcome of an appointment.
     */
    public void recordAppointmentOutcome() {
        try {
            AttributeController ac = AttributeController.getInstance();

            String appointmentID = ac.inputString("Enter Appointment ID: ");
            String prescription = "";
            while (true) {
                String choice = ac.inputString("Add prescription?(y/n)");
                if (choice.equalsIgnoreCase("n")) break;
                Prescription p = new Prescription();
                prescription += p.getMedicineName() + " ";
            }
            String notes = ac.inputNote("Enter consultation notes, type END in a new line to finish: ");

            AORController aorController = AORController.getInstance();
            aorController.recordAppointmentOutcome(appointmentID, prescription, notes);

            System.out.println("Appointment outcome recorded successfully.");
        } catch (Exception e) {
            System.err.println("Error recording appointment outcome: " + e.getMessage());
        }
    }
}
