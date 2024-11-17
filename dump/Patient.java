
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Patient extends User {
    private IOHandler patientCsvHandler;
    private AttributeController getter = AttributeController.getInstance();
    private MedicalRecord medicalRecord;

    private Patient(String patientID, IOHandler patientCsvHandler) {
        super(patientID);
        try{
        this.patientCsvHandler = patientCsvHandler;
        /*
        this.appointmentCsvHandler = appointmentCsvHandler;
        this.staffCsvHandler = staffCsvHandler;
        this.medicalRecordCsvHandler = medicalRecordCsvHandler;
        */

        this.medicalRecord = new MedicalRecord(patientID, new CsvHandler(Consts.Patient.FILE_NAME));
        // super(userID, name, age, gender, email, contactNumber, userRole);
        }catch (Exception e){
            System.out.println("Error at initializing patient");
        }

    }

    public static Patient getPatient(String patientID, IOHandler handler)  {
        try{
        List<String[]> userDetails = handler.getRows(Consts.Patient.ID_COLUMN, patientID);
        if (userDetails.isEmpty()) {
            return null;
        } else {
            return new Patient(patientID, handler);
        }
        }catch(Exception e){
            System.out.println("Error at getting patient details");
            return null;
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

    public void _Schedule_an_Appointment() throws IOException {
        AttributeController ac = AttributeController.getInstance();

        String doctorID = ac.inputString("Enter the doctor ID to schedule an appointment:");

        Calendar date = ac.inputDate("Enter the new appointment date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String newDate = dateFormat.format(date.getTime());

        System.out.println("Pick an appointment slot: ");
        date.set(Calendar.HOUR_OF_DAY, 9);
        
        
        dateFormat = new SimpleDateFormat("HH:mm");
        int x = 1;
        List<String> timeSlotList = new ArrayList<String>();
        while (date.get(Calendar.HOUR_OF_DAY) < 18) {
            System.out.println(x + ". " + dateFormat.format(date.getTime()));
            timeSlotList.add(dateFormat.format(date.getTime()));
            date.add(Calendar.MINUTE, 30);
            x++;
        }

        int timeSlot = 0;
        do {
            timeSlot = ac.inputInt("Input your choice of time slot:");
        } while (timeSlot < 1 && timeSlot > x);


        String serviceType = ac.inputString("Enter the type of service for the appointment (e.g., 'General Consultation'):");
        String time = timeSlotList.get(timeSlot - 1);
        // Check if timeslot is available
        AvailabilityController availabilityController = AvailabilityController.getInstance();


        if (!availabilityController.checkSlot(doctorID, newDate, time)) {
            // Slot is not available
            System.out.println("The selected time slot is already filled. Please choose another time.");
        } else {
            // Slot is available, proceed with scheduling the appointment
            try {
                AORController aorController = AORController.getInstance();
                aorController.scheduleAppointment(getUserID(), doctorID, time, newDate, serviceType);
                System.out.println("Appointment scheduled successfully!");
            } catch (IOException e) {
                System.out.println("Error scheduling appointment: " + e.getMessage());
            }
        }
    }

    public void _Reschedule_an_Appointment() throws IOException {
        AttributeController ac = AttributeController.getInstance();

        // Get the appointment ID, new doctor ID, new date, and new time from the user
        String appointmentID = ac.inputString("Enter the appointment ID to reschedule:");
        
        try {
            AORController aorController = AORController.getInstance();
            aorController.cancelAppointment(appointmentID);
        } catch (IOException e) {
            System.out.println("Error rescheduling appointment: " + e.getMessage());
        }

        String doctorID = ac.inputString("Enter the doctor ID to schedule an appointment:");

        Calendar date = ac.inputDate("Enter the new appointment date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String newDate = dateFormat.format(date.getTime());

        System.out.println("Pick an appointment slot: ");
        date.set(Calendar.HOUR_OF_DAY, 9);
        
        
        dateFormat = new SimpleDateFormat("HH:mm");
        int x = 1;
        List<String> timeSlotList = new ArrayList<String>();
        while (date.get(Calendar.HOUR_OF_DAY) < 18) {
            System.out.println(x + ". " + dateFormat.format(date.getTime()));
            timeSlotList.add(dateFormat.format(date.getTime()));
            date.add(Calendar.MINUTE, 30);
            x++;
        }

        int timeSlot = 0;
        do {
            timeSlot = ac.inputInt("Input your choice of time slot:");
        } while (timeSlot < 1 && timeSlot > x);


        String serviceType = ac.inputString("Enter the type of service for the appointment (e.g., 'General Consultation'):");
        String time = timeSlotList.get(timeSlot - 1);
        // Check if timeslot is available
        AvailabilityController availabilityController = AvailabilityController.getInstance();


        if (!availabilityController.checkSlot(doctorID, newDate, time)) {
            // Slot is not available
            System.out.println("The selected time slot is already filled. Please choose another time.");
        } else {
            // Slot is available, proceed with scheduling the appointment
            try {
                AORController aorController = AORController.getInstance();
                aorController.scheduleAppointment(getUserID(), doctorID, time, newDate, serviceType);
                System.out.println("Appointment scheduled successfully!");
            } catch (IOException e) {
                System.out.println("Error scheduling appointment: " + e.getMessage());
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
    public void cancelAppointment(String appointmentID) {

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
        String patientID = getUserID();
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

        String patientID = getUserID();

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
