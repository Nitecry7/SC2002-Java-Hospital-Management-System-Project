
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class MedicalRecord 
{
    private String patientID;
    private String patientName;
    private String patientDateOfBirth;
    private String patientGender;
    private String patientEmail;
    private String patientPhone;
    private String patientPassword;
    private BloodType patientBloodType = BloodType.NOT_SET;
    private List<String> AORIDs = new ArrayList<String>();
    private List<MedicalDiagnosis> patientMedicalHistory = new ArrayList<MedicalDiagnosis>(); //List Interface is used to be general, so that ArrayList can implement it

    private IOHandler handler;

    
    //@SuppressWarnings("unchecked")
    public MedicalRecord(String patientID, IOHandler handler) throws Exception
    {
        try{
        String[] details = handler.getRows(Consts.Patient.ID_COLUMN, patientID).get(0);
       
        if (details.length < 10) {
            details = Arrays.copyOf(details, 10);
            
        }
      
        this.handler = handler;
        this.patientID = details[Consts.Patient.ID_COLUMN];
        this.patientName = details[Consts.Patient.NAME_COLUMN];
        this.patientGender = details[Consts.Patient.GENDER_COLUMN];
        this.patientEmail = details[Consts.Patient.EMAIL_COLUMN];
        this.patientDateOfBirth = details[Consts.Patient.DOB_COLUMN];
        this.patientPhone = details[Consts.Patient.CONTACTNUMBER_COUMN];
        this.patientBloodType = BloodType.NOT_SET;

        if (BloodType.valueOf(details[Consts.Patient.BLOODTYPE_COLUMN]) != null) {
            this.patientBloodType = BloodType.valueOf(details[Consts.Patient.BLOODTYPE_COLUMN]);
        } 
        
        if (details[Consts.Patient.AOR_ID_COLUMN] != null) {
         byte[] AORIDdata = Base64.getDecoder().decode(details[Consts.Patient.AOR_ID_COLUMN]);
         ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(AORIDdata));
         this.AORIDs = (List<String>) ois.readObject();
         ois.close();
        }
        else {
            this.AORIDs = new ArrayList<String>();
        }
        
        if (details[Consts.Patient.DIAGNOSISTREATMENT_COLUMN] != null) {
         byte[] diagnosisData = Base64.getDecoder().decode(details[Consts.Patient.DIAGNOSISTREATMENT_COLUMN]);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(diagnosisData));

         this.patientMedicalHistory = (List<MedicalDiagnosis>) ois.readObject();
         ois.close();

        }
        else {
            this.patientMedicalHistory = new ArrayList<MedicalDiagnosis>();
        }

         
        } catch(Exception e){
            System.out.println("Error when initializing medical record.");
            e.printStackTrace();
        }
            
      

    }



    public void saveData() throws IOException {
     
        String[] details = new String[10];
        
        details[Consts.Patient.PW_COLUMN] = patientPassword;
        details[Consts.Patient.ID_COLUMN] = patientID;
        details[Consts.Patient.NAME_COLUMN] = patientName;
        details[Consts.Patient.EMAIL_COLUMN] = patientEmail;
        details[Consts.Patient.GENDER_COLUMN] = patientGender;
        details[Consts.Patient.CONTACTNUMBER_COUMN] = patientPhone;
        details[Consts.Patient.DOB_COLUMN] = patientDateOfBirth;
        details[Consts.Patient.BLOODTYPE_COLUMN] = patientBloodType.name();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        // Encoding the serialized Date object into storeable string
        oos.writeObject(AORIDs);
        details[Consts.Patient.AOR_ID_COLUMN] = Base64.getEncoder().encodeToString(baos.toByteArray());                                                                           

        baos.reset();

        // Encoding the serialized Prescrpition[] object into storeable string
        oos.writeObject(patientMedicalHistory);
        details[Consts.Patient.DIAGNOSISTREATMENT_COLUMN] = Base64.getEncoder().encodeToString(baos.toByteArray());

        
        handler.updateRow(Consts.Patient.ID_COLUMN, patientID, details);

    }

    public String displayPatientMedicalRecord()
    {
        String record = "\n----Displaying patient record----\n" + "Patient ID: " + patientID +  "\nName: " + patientName +  "\nDate of Birth: " +
            patientDateOfBirth +  "\nGender: " + patientGender + "\nBlood Type: " + getPatientBloodType().name() + "\n\n" + "Medical History:\n";
        
        record = record.concat("\n---Past diagnosis and treatments---\n");

        for(int i = 0; i < patientMedicalHistory.size(); i++)
        {
           record += patientMedicalHistory.get(i) + "\n\n"; 
        }
        System.out.println(record);

        return record;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) throws IOException{
        this.patientName = patientName;
        saveData();
    }

    public String getPatientDateOfBirth() {
        return patientDateOfBirth;
    }

    public void setPatientDateOfBirth(String patientDateOfBirth) throws IOException{
        this.patientDateOfBirth = patientDateOfBirth;
        saveData();
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) throws IOException{
        this.patientGender = patientGender;
        saveData();
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) throws IOException {
        this.patientEmail = patientEmail;
        saveData();
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) throws IOException {
        this.patientPhone = patientPhone;
        saveData();
    }

    public BloodType getPatientBloodType() {
        if (patientBloodType == null) {
            return BloodType.NOT_SET;
        }
        else {
        return patientBloodType;
        }
    }

    public void setPatientBloodType(BloodType patientBloodType) throws IOException {
        this.patientBloodType = patientBloodType;
        saveData();
    }

    public List<String> getAORIDs() {
        return AORIDs;
    }

    public void addAORIDs(String AORID) throws IOException {
        this.AORIDs.add(AORID);
        saveData();
    }

    public List<MedicalDiagnosis> getPatientMedicalHistory() {
        return patientMedicalHistory;
    }

    public void addMedicalHistory(MedicalDiagnosis patientMedicalHistory) throws IOException {
        this.patientMedicalHistory.add(patientMedicalHistory);
        saveData();
    }


    public void setPatientPassword(String patientPassword) throws IOException{
        this.patientPassword = patientPassword;
        saveData();
    }
    
}
