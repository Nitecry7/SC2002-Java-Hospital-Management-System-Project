package hmsystem.models;
import hmsystem.data.Consts;
import hmsystem.io.*;
import hmsystem.models.enums.BloodType;
import java.io.IOException;
import java.util.List;

class Patient extends User {

    String name, gender, email, phone, dateOfBirth;
    BloodType bloodType;
    IOHandler handler;


    
    private Patient(String[] details, IOHandler handler) {
        super (details[Consts.Patient.ID_COLUMN]);

        this.handler = handler;
        this.name = details[Consts.Patient.NAME_COLUMN];
        this.gender = details[Consts.Patient.GENDER_COLUMN];
        this.email = details[Consts.Patient.EMAIL_COLUMN];
        this.dateOfBirth = details[Consts.Patient.DOB_COLUMN];
        this.phone = details[Consts.Patient.CONTACTNUMBER_COUMN];
        this.bloodType = BloodType.valueOf(details[Consts.Patient.BLOODTYPE_COLUMN]);
        
        //super(userID, name, age, gender, email, contactNumber, userRole);
        
    }


    public static Patient getPatient(String adminID, IOHandler handler) {

        List<String[]> userDetails = handler.getRows(Consts.Patient.ID_COLUMN, adminID);
        if (userDetails.isEmpty()) {
            return null;
        }
        else {
            return new Patient(userDetails.get(0), handler);
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




    public void saveData() throws IOException {
     
        String[] details = new String[6];
        details[Consts.Patient.ID_COLUMN] = getUserID();
        details[Consts.Patient.NAME_COLUMN] = name;
        details[Consts.Patient.EMAIL_COLUMN] = email;
        details[Consts.Patient.GENDER_COLUMN] = gender;
        details[Consts.Patient.CONTACTNUMBER_COUMN] = phone;
        details[Consts.Patient.DOB_COLUMN] = dateOfBirth;
        details[Consts.Patient.BLOODTYPE_COLUMN] = bloodType.name();

        
        handler.updateRow(Consts.Patient.ID_COLUMN, getUserID(), details);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IOException {
        this.name = name;
        saveData();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) throws IOException {
        this.gender = gender;
        saveData();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email)throws IOException {
        this.email = email;
        saveData();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws IOException {
        this.phone = phone;
        saveData();
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) throws IOException {
        this.dateOfBirth = dateOfBirth;
        saveData();
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) throws IOException {
        this.bloodType = bloodType;
        saveData();
    }
  


}
