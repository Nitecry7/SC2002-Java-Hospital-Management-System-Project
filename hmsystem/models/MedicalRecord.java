package hmsystem.models;
import java.util.*;

public class MedicalRecord 
{
    private String patientID;
    private String patientName;
    private String patientDateOfBirth;
    private String patientGender;
    private String patientBloodType;

    private List<MedicalDiagnosis> patientMedicalHistory; //List Interface is used to be general, so that ArrayList can implement it
    
    public MedicalRecord(String patientID, String patientName, String patientDateOfBirth, String patientGender, String patientBloodType)
    {
        this.patientID = patientID;
        this.patientName = patientName;
        this.patientDateOfBirth = patientDateOfBirth;
        this.patientGender = patientGender;
        this.patientBloodType = patientBloodType;

        this.patientMedicalHistory = new ArrayList<>();
    }

    public String getpatientID()
    {
        return patientID;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getPatientDateOfBirth()
    {
        return patientDateOfBirth;
    }

    public String getPatientGender()
    {
        return patientGender;
    }

    public String getPatientBloodType()
    {
        return patientBloodType;
    }

    public List<MedicalDiagnosis> getPatientMedicalHistory() 
    {
        return patientMedicalHistory;
    }

    public String displayPatientMedicalRecord()
    {
        String record = "Patient ID: " + patientID +  "\nName: " + patientName +  "\nDate of Birth: " + patientDateOfBirth +  "\nGender: " + patientGender + "\nBlood Type: " + patientBloodType + "\n\n" + "Medical History:\n";

        for(int i = 0; i < patientMedicalHistory.size(); i++)
        {
           record += patientMedicalHistory.get(i) + "\n\n"; 
        }

        return record;
    }
    
}
