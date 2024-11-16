package hmsystem.models;
import hmsystem.data.Consts;
import hmsystem.io.*;
import hmsystem.models.enums.BloodType;
import java.io.IOException;
import java.util.List;

public class Patient extends User {


    MedicalRecord medicalRecord;

    
    private Patient(String patientID, IOHandler handler) throws Exception {
        super (patientID);

        this.medicalRecord = new MedicalRecord(patientID, handler);
        //super(userID, name, age, gender, email, contactNumber, userRole);
        
    }


    public static Patient getPatient(String patientID, IOHandler handler) throws Exception{

        List<String[]> userDetails = handler.getRows(Consts.Patient.ID_COLUMN, patientID);
        if (userDetails.isEmpty()) {
            return null;
        }
        else {
            return new Patient(patientID, handler);
        }

    }


    public void _View_Medical_Record() {

    }

    public void _Update_Personal_Information() {

    }
    public void _View_Available_Appointment_Slots() {

    }

    public void _Schedule_an_Appointment() {

    }

    public void _Reschedule_an_Appointment() {

    }

    public void _Cancel_an_Appointment() {

    }

    public void _View_Scheduled_Appointments() {

    }

    public void _View_Past_Appointment_Outcome_Records() {

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
