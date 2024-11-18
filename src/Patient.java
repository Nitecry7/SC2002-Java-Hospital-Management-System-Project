import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents a Patient with functionalities such as managing appointments, viewing medical records,
 * and updating personal information. This class interacts with various controllers to perform operations.
 */
public class Patient extends User {
    private IOHandler patientCsvHandler;
    private AttributeController getter = AttributeController.getInstance();
    private MedicalRecord medicalRecord;

    /**
     * Constructor to initialize a Patient instance with their ID and CSV handler.
     *
     * @param patientID           The patient's unique identifier.
     * @param patientCsvHandler   The handler for CSV operations related to the patient.
     */
    private Patient(String patientID, IOHandler patientCsvHandler) {
        super(patientID);
        try {
            this.patientCsvHandler = patientCsvHandler;
            this.medicalRecord = new MedicalRecord(patientID, new CsvHandler(Consts.Patient.FILE_NAME));
        } catch (Exception e) {
            System.out.println("Error initializing patient");
        }
    }

    /**
     * Factory method to retrieve a Patient instance based on their ID.
     *
     * @param patientID The patient's unique identifier.
     * @param handler   The handler for CSV operations.
     * @return A Patient instance or null if the patient is not found.
     */
    public static Patient getPatient(String patientID, IOHandler handler) {
        try {
            List<String[]> userDetails = handler.getRows(Consts.Patient.ID_COLUMN, patientID);
            if (userDetails.isEmpty()) {
                return null;
            } else {
                return new Patient(patientID, handler);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving patient details");
            return null;
        }
    }

    /**
     * Displays the patient's medical record.
     */
    public void _View_Medical_Record() {
        medicalRecord.displayPatientMedicalRecord();
    }

    /**
     * Updates the patient's personal information such as email or phone number.
     */
    public void _Update_Personal_Information() throws IOException {
        AttributeController ac = AttributeController.getInstance();
        switch (ac.inputInt("Update your personal information\n1. Email\n2. Phone number")) {
            case 1 -> setEmail(ac.inputString("Input your new E-mail address:"));
            case 2 -> setPhone(ac.inputString("Input your new phone number"));
            default -> System.out.println("Invalid input");
        }
    }

    /**
     * Allows the patient to set a new password.
     */
    public void _Set_new_password() {
        AttributeController ac = AttributeController.getInstance();
        String newPassword = ac.inputString("Enter new password:");
        String verify = ac.inputString("Verify the password:");
        if (verify.equals(newPassword)) {
            System.out.println("New password set");
            setPassword(newPassword);
        } else {
            System.out.println("Password does not match. Exiting to menu...");
        }
    }

    /**
     * Displays available appointment slots for a given doctor and date.
     */
    public void _View_Available_Appointment_Slots() {
        try {
            AttributeController getter = AttributeController.getInstance();
            AvailabilityController ac = AvailabilityController.getInstance();
            String doctorID = getter.inputString("Enter the doctor ID for availability:");
            Calendar date = getter.inputDate("Enter the date");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String newDate = dateFormat.format(date.getTime());
            ac.viewAvailableSlots(doctorID, newDate);
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    /**
     * Schedules a new appointment for the patient.
     */
    public void _Schedule_an_Appointment() throws IOException {
        AttributeController ac = AttributeController.getInstance();
        String doctorID = ac.inputString("Enter the doctor ID to schedule an appointment:");
        Calendar date = ac.inputDate("Enter the appointment date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = dateFormat.format(date.getTime());

        System.out.println("Pick an appointment slot:");
        date.set(Calendar.HOUR_OF_DAY, 9);
        dateFormat = new SimpleDateFormat("HH:mm");
        List<String> timeSlotList = new ArrayList<>();
        while (date.get(Calendar.HOUR_OF_DAY) < 18) {
            timeSlotList.add(dateFormat.format(date.getTime()));
            date.add(Calendar.MINUTE, 30);
        }

        int timeSlot;
        do {
            timeSlot = ac.inputInt("Input your choice of time slot:");
        } while (timeSlot < 1 || timeSlot > timeSlotList.size());

        String serviceType = ac.inputString("Enter the type of service for the appointment (e.g., 'General Consultation'):");
        String time = timeSlotList.get(timeSlot - 1);
        AvailabilityController availabilityController = AvailabilityController.getInstance();

        if (!availabilityController.checkSlot(doctorID, newDate, time)) {
            System.out.println("The selected time slot is already filled. Please choose another time.");
        } else {
            AORController aorController = AORController.getInstance();
            aorController.scheduleAppointment(getUserID(), doctorID, time, newDate, serviceType);
            System.out.println("Appointment scheduled successfully!");
        }
    }

    /**
     * Allows the patient to reschedule an existing appointment.
     */
    public void _Reschedule_an_Appointment() throws IOException {
        AttributeController ac = AttributeController.getInstance();
        String appointmentID = ac.inputString("Enter the appointment ID to reschedule:");
        AORController aorController = AORController.getInstance();
        aorController.cancelAppointment(appointmentID);
        _Schedule_an_Appointment();
    }

    /**
     * Cancels an appointment based on the provided appointment ID.
     */
    public void _Cancel_an_Appointment() {
        AttributeController ac = AttributeController.getInstance();
        String appointmentID = ac.inputString("Enter the appointment ID to cancel:");
        try {
            AORController aorController = AORController.getInstance();
            aorController.cancelAppointment(appointmentID);
            System.out.println("Appointment canceled successfully!");
        } catch (IOException e) {
            System.out.println("Error canceling appointment: " + e.getMessage());
        }
    }

    /**
     * Views all scheduled appointments for the patient.
     */
    public void _View_Scheduled_Appointments() {
        try {
            AORController aorc = AORController.getInstance();
            List<String> appointments = aorc.viewScheduledAppointments(getUserID());

            if (appointments.isEmpty()) {
                System.out.println("No scheduled appointments found.");
            } else {
                appointments.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.err.println("Error retrieving scheduled appointments: " + e.getMessage());
        }
    }
    /**
     * Views the outcomes of past appointments for the patient.
     */
    public void _View_Past_Appointment_Outcome_Records() {
        try {
            AORController aorController = AORController.getInstance();
            List<String> pastAppointments = aorController.viewPastAppointmentsOutcome(getUserID());

            if (pastAppointments.isEmpty()) {
                System.out.println("No past appointments found.");
            } else {
                pastAppointments.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.err.println("Error retrieving past appointment outcomes: " + e.getMessage());
        }
    }

    /**
     * Retrieves the patient's medical record.
     *
     * @return The MedicalRecord object for the patient.
     * @throws Exception If an error occurs during retrieval.
     */
    public MedicalRecord getMedicalRecord() throws Exception {
        return medicalRecord;
    }

    /**
     * Retrieves the patient's name.
     *
     * @return The name of the patient.
     */
    public String getName() {
        return medicalRecord.getPatientName();
    }

    /**
     * Sets the patient's name.
     *
     * @param name The name to set.
     * @throws IOException If an error occurs during the operation.
     */
    public void setName(String name) throws IOException {
        medicalRecord.setPatientName(name);
    }

    /**
     * Retrieves the patient's gender.
     *
     * @return The gender of the patient.
     */
    public String getGender() {
        return medicalRecord.getPatientGender();
    }

    /**
     * Sets the patient's gender.
     *
     * @param gender The gender to set.
     * @throws IOException If an error occurs during the operation.
     */
    public void setGender(String gender) throws IOException {
        medicalRecord.setPatientGender(gender);
    }

    /**
     * Retrieves the patient's email.
     *
     * @return The email of the patient.
     */
    public String getEmail() {
        return medicalRecord.getPatientEmail();
    }

    /**
     * Sets the patient's email.
     *
     * @param email The email to set.
     * @throws IOException If an error occurs during the operation.
     */
    public void setEmail(String email) throws IOException {
        medicalRecord.setPatientEmail(email);
    }

    /**
     * Retrieves the patient's phone number.
     *
     * @return The phone number of the patient.
     */
    public String getPhone() {
        return medicalRecord.getPatientPhone();
    }

    /**
     * Sets the patient's phone number.
     *
     * @param phone The phone number to set.
     * @throws IOException If an error occurs during the operation.
     */
    public void setPhone(String phone) throws IOException {
        medicalRecord.setPatientPhone(phone);
    }

    /**
     * Retrieves the patient's date of birth.
     *
     * @return The date of birth of the patient.
     */
    public String getDateOfBirth() {
        return medicalRecord.getPatientDateOfBirth();
    }

    /**
     * Sets the patient's date of birth.
     *
     * @param dateOfBirth The date of birth to set.
     * @throws IOException If an error occurs during the operation.
     */
    public void setDateOfBirth(String dateOfBirth) throws IOException {
        medicalRecord.setPatientDateOfBirth(dateOfBirth);
    }

    /**
     * Retrieves the patient's blood type.
     *
     * @return The blood type of the patient.
     */
    public BloodType getBloodType() {
        return medicalRecord.getPatientBloodType();
    }

    /**
     * Sets the patient's blood type.
     *
     * @param bloodType The blood type to set.
     * @throws IOException If an error occurs during the operation.
     */
    public void setBloodType(BloodType bloodType) throws IOException {
        medicalRecord.setPatientBloodType(bloodType);
    }

    /**
     * Sets the patient's password.
     *
     * @param password The password to set.
     */
    @Override
    public void setPassword(String password) {
        try {
            medicalRecord.setPatientPassword(password);
        } catch (Exception e) {
            System.out.println("Password was not set, an error occurred");
        }
    }
}
