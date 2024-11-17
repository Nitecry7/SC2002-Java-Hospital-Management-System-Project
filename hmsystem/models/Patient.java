package hmsystem.models;
import hmsystem.controllers.AORController;
import hmsystem.controllers.AttributeController;
import hmsystem.data.Consts;
import hmsystem.io.*;
import hmsystem.models.enums.BloodType;
import java.io.IOException;
import java.util.List;


public class Patient extends User {
    private CsvHandler patientCsvHandler, appointmentCsvHandler, staffCsvHandler, medicalRecordCsvHandler;
    private AttributeController getter = AttributeController.getInstance();
    MedicalRecord medicalRecord;

    
    private Patient(String patientID, CsvHandler patientCsvHandler, CsvHandler appointmentCsvHandler, CsvHandler staffCsvHandler, CsvHandler medicalRecordCsvHandler) throws Exception {
        super(patientID);
        this.patientCsvHandler = patientCsvHandler;
        this.appointmentCsvHandler = appointmentCsvHandler;
        this.staffCsvHandler = staffCsvHandler;
        this.medicalRecordCsvHandler = medicalRecordCsvHandler;

        this.medicalRecord = new MedicalRecord(patientID, medicalRecordCsvHandler);
        //super(userID, name, age, gender, email, contactNumber, userRole);
        
    }

    public static Patient getPatient(String patientID, IOHandler handler) throws Exception{

        List<String[]> userDetails = patientCsvHandler.getRows(Consts.Patient.ID_COLUMN, patientID);
        if (userDetails.isEmpty()) {
            return null;
        }
        else {
            return new Patient(patientID, handler);
        }

    }


    public void _View_Medical_Record() {
        medicalRecord.displayPatientMedicalRecord();
    }

    public void _Update_Personal_Information() throws IOException {

        AttributeController ac = AttributeController.getInstance();
        switch(ac.inputInt("Update your personal information\n1. Email\n2. Phone number")) {
            case 1 ->  {
                setEmail(ac.inputString("Input your new E-mail address:"));
            }
            case 2 ->  {
                setPhone(ac.inputString("Input your new phone number"));
            }
            default -> {
                System.out.println("Invalid input");
            }
        }
        

    }

    public void _View_Available_Appointment_Slots() {
        
    }

    public void _Schedule_an_Appointment() {

    }

    public void _Reschedule_an_Appointment() {

    }

    public void _Cancel_an_Appointment() {
         
    }

    public void _View_Scheduled_Appointments() throws IOException {

         new AORController(new CsvHandler(Consts.AOR.FILE_NAME), new CsvHandler(Consts.Patient.FILE_NAME), 
                new CsvHandler(Consts.Staff.FILE_NAME)).viewScheduledAppointments(getUserID());

    }

    public void _View_Past_Appointment_Outcome_Records() throws IOException {

        new AORController(new CsvHandler(Consts.AOR.FILE_NAME), new CsvHandler(Consts.Patient.FILE_NAME), 
                new CsvHandler(Consts.Staff.FILE_NAME)).viewPastAppointmentsOutcome(getUserID());

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

    public void setEmail(String email)throws IOException {
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

}
