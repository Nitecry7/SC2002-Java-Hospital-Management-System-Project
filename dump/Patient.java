
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Patient extends User {
    private IOHandler patientCsvHandler;
    private AttributeController getter = AttributeController.getInstance();
    MedicalRecord medicalRecord;

    private Patient(String patientID, IOHandler patientCsvHandler) throws Exception {
        super(patientID);
        this.patientCsvHandler = patientCsvHandler;
        /*
        this.appointmentCsvHandler = appointmentCsvHandler;
        this.staffCsvHandler = staffCsvHandler;
        this.medicalRecordCsvHandler = medicalRecordCsvHandler;
        */

        this.medicalRecord = new MedicalRecord(patientID, new CsvHandler(Consts.AOR.FILE_NAME));
        // super(userID, name, age, gender, email, contactNumber, userRole);

    }

    public Patient getPatient(String patientID, IOHandler handler) throws Exception {

        List<String[]> userDetails = handler.getRows(Consts.Patient.ID_COLUMN, patientID);
        if (userDetails.isEmpty()) {
            return null;
        } else {
            return new Patient(patientID, handler);
        }

    }

    public void _View_Medical_Record() {
        medicalRecord.displayPatientMedicalRecord();
    }

    public void _Update_Personal_Information() throws IOException {

        AttributeController ac = AttributeController.getInstance();
        switch (ac.inputInt("Update your personal information\n1. Email\n2. Phone number")) {
            case 1 -> {
                setEmail(ac.inputString("Input your new E-mail address:"));
            }
            case 2 -> {
                setPhone(ac.inputString("Input your new phone number"));
            }
            default -> {
                System.out.println("Invalid input");
            }
        }

    }

    

    @Override
    public void _Set_new_password() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter new password");
        String newPassword = in.nextLine();
        System.out.println("Enter it again");
        if (in.nextLine().equals(newPassword)) {
            System.out.println("New password set");
            setPassword(newPassword);
        }
        else {
            System.out.println("Password does not match. Exiting to menu...");
        }
    }

    public void _View_Available_Appointment_Slots(String DoctorID, String date) {
        AvailabilityController ac = AvailabilityController.getInstance();
        ac.viewAvailableSlots(DoctorID, date);
    }

    public void _Schedule_an_Appointment() throws IOException {
        AttributeController ac = AttributeController.getInstance();

        String doctorID = ac.inputString("Enter the doctor ID to schedule an appointment:");

        String appointmentDate = ac.inputString("Enter the appointment date (yyyy-mm-dd):");

        String appointmentTime = ac.inputString("Enter the appointment time (HH:mm):");

        String serviceType = ac.inputString("Enter the type of service for the appointment (e.g., 'General Consultation'):");
        
        // Check if timeslot is available
        AvailabilityController availabilityController = AvailabilityController.getInstance();
        int slotStatus = availabilityController.checkSlot(doctorID, appointmentDate, appointmentTime);

        if (slotStatus == 0) {
            // Slot is not available
            System.out.println("The selected time slot is already filled. Please choose another time.");
        } else {
            // Slot is available, proceed with scheduling the appointment
            try {
                AORController aorController = AORController.getInstance();
                aorController.scheduleAppointment(getUserID(), doctorID, appointmentTime, appointmentDate, serviceType);
                System.out.println("Appointment scheduled successfully!");
            } catch (IOException e) {
                System.out.println("Error scheduling appointment: " + e.getMessage());
            }
        }
    }

    public void _Reschedule_an_Appointment() {
        AttributeController ac = AttributeController.getInstance();

        // Get the appointment ID, new doctor ID, new date, and new time from the user
        String appointmentID = ac.inputString("Enter the appointment ID to reschedule:");
        String doctorID = ac.inputString("Enter the new doctor ID for the appointment:");
        String newDate = ac.inputString("Enter the new appointment date (yyyy-mm-dd):");
        String newTime = ac.inputString("Enter the new appointment time (HH:mm):");

        // Check if time slot is available
        AvailabilityController availabilityController = AvailabilityController.getInstance();
        int slotStatus = availabilityController.checkSlot(doctorID, newDate, newTime);

        if (slotStatus == 0) {
            // Slot is not available
            System.out.println("The selected time slot is already filled. Please choose another time.");
        } else {
            // Slot is available, proceed with rescheduling the appointment
            try {
                AORController aorController = AORController.getInstance();
                aorController.rescheduleAppointment(appointmentID, doctorID, newTime, newDate);
                System.out.println("Appointment rescheduled successfully!");
            } catch (IOException e) {
                System.out.println("Error rescheduling appointment: " + e.getMessage());
            }
        }
    }

    public void _Cancel_an_Appointment() {
        // Instantiate the AttributeController to manage user input
        AttributeController ac = AttributeController.getInstance();

        // Ask for the appointment ID to cancel
        String appointmentID = ac.inputString("Enter the appointment ID to cancel:");

        // Call the AORController to cancel the appointment
        try {
            AORController aorController = AORController.getInstance();
            aorController.cancelAppointment(appointmentID);
            System.out.println("Appointment canceled successfully!");
        } catch (IOException e) {
            System.out.println("Error canceling appointment: " + e.getMessage());
        }
    }

    public void _View_Scheduled_Appointments() {
        AttributeController ac = AttributeController.getInstance();

        String patientID = ac.inputString("Enter the Patient ID to view scheduled appointments:");

        try {
            AORController aorController = AORController.getInstance();
            List<String> appointments = aorController.viewScheduledAppointments(patientID);

            if (appointments.isEmpty()) {
                System.out.println("No scheduled appointments found for Patient ID: " + patientID);
            } else {
                Utils.printView(appointments, "Scheduled Appointments for Patient " + patientID + ":");
            }
        } catch (IOException e) {
            System.err.println("Error retrieving scheduled appointments: " + e.getMessage());
        }
    }

    public void _View_Past_Appointment_Outcome_Records() {
        AttributeController ac = AttributeController.getInstance();

        String patientID = ac.inputString("Enter the Patient ID to view past appointment outcomes:");

        try {
            AORController aorController = AORController.getInstance();
            List<String> pastAppointments = aorController.viewPastAppointmentsOutcome(patientID);

            if (pastAppointments.isEmpty()) {
                System.out.println("No past appointments found for Patient ID: " + patientID);
            } else {
                Utils.printView(pastAppointments, "Past Appointments Outcome for Patient " + patientID + ":");
            }
        } catch (IOException e) {
            System.err.println("Error retrieving past appointment outcomes: " + e.getMessage());
        }
    }

    public MedicalRecord getMedicalRecord() throws Exception {
        return medicalRecord;
    }

    public String getName() {
        return medicalRecord.getPatientName();
    }

    public void setName(String name) throws IOException {
        medicalRecord.setPatientName(name);
    }

    public String getGender() {
        return medicalRecord.getPatientGender();
    }

    public void setGender(String gender) throws IOException {
        medicalRecord.setPatientGender(gender);
    }

    public String getEmail() {
        return medicalRecord.getPatientEmail();
    }

    public void setEmail(String email) throws IOException {
        medicalRecord.setPatientEmail(email);
    }

    public String getPhone() {
        return medicalRecord.getPatientPhone();
    }

    public void setPhone(String phone) throws IOException {
        medicalRecord.setPatientPhone(phone);
    }

    public String getDateOfBirth() {
        return medicalRecord.getPatientDateOfBirth();
    }

    public void setDateOfBirth(String dateOfBirth) throws IOException {
        medicalRecord.setPatientDateOfBirth(dateOfBirth);
    }

    public BloodType getBloodType() {
        return medicalRecord.getPatientBloodType();
    }

    public void setBloodType(BloodType bloodType) throws IOException {
        medicalRecord.setPatientBloodType(bloodType);
    }

    @Override
     public void setPassword(String password) {
        try {
            medicalRecord.setPatientPassword(password);
        } catch (Exception e) {
            System.out.println("Password was not set, an error occurred");
        }
    }

}
