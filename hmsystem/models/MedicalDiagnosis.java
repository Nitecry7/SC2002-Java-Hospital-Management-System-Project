package hmsystem.models;
public class MedicalDiagnosis //This Class represents each diagnosis entry in a patient's medical record
{
    private String patientDiagnosis; //Diagnosis for the patient
    private String patientTreatment; //Treatment administered for the patient
    private String doctorNotes; //Additional Doctor notes
    private String diagnosedDate; //Date patient was diagnosed

    public MedicalDiagnosis(String patientDiagnosis, String patientTreatment, String doctorNotes, String diagnosedDate)
    {
        this.patientDiagnosis = patientDiagnosis;
        this.patientTreatment = patientTreatment;
        this.doctorNotes = doctorNotes;
        this.diagnosedDate = diagnosedDate;
    }

    public String getPatientDiagnosis()
    {
        return patientDiagnosis;
    }

    public String getPatientTreatement()
    {
        return patientTreatment;
    }

    public String getDoctorNotes()
    {
        return doctorNotes;
    }

    public String getDiagnosedDate()
    {
        return diagnosedDate;
    }

    @Override
    public String toString() //toString() encompasses all the details into one neat output format
    {
        return "Date: " + diagnosedDate + "\n" + "Diagnosis: " + patientDiagnosis + "\n" + "Treatment: " + patientTreatment + "\n" + "Doctor Notes: " + doctorNotes;
    }

}
