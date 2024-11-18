import java.io.Serializable;

/**
 * Represents a medical diagnosis entry in a patient's medical record.
 * This class encapsulates details about the diagnosis, treatment, doctor notes, and the diagnosis date.
 */
public class MedicalDiagnosis implements Serializable {

    /**
     * Diagnosis for the patient
     */
    private final String patientDiagnosis;
    /**
     * Treatment administered for the patient
     */
    private final String patientTreatment; 
    /**
     * Additional doctor notes
     */
    private final String doctorNotes;     
    /**
     * Date the patient was diagnosed
     */
    private final String diagnosedDate;   

    /**
     * Constructs a MedicalDiagnosis object with the specified details.
     *
     * @param patientDiagnosis The diagnosis for the patient.
     * @param patientTreatment The treatment administered for the patient.
     * @param doctorNotes      Additional notes from the doctor.
     * @param diagnosedDate    The date the diagnosis was made.
     */
    public MedicalDiagnosis(String patientDiagnosis, String patientTreatment, String doctorNotes, String diagnosedDate) {
        this.patientDiagnosis = patientDiagnosis;
        this.patientTreatment = patientTreatment;
        this.doctorNotes = doctorNotes;
        this.diagnosedDate = diagnosedDate;
    }

    /**
     * Retrieves the patient's diagnosis.
     *
     * @return The diagnosis for the patient.
     */
    public String getPatientDiagnosis() {
        return patientDiagnosis;
    }

    /**
     * Retrieves the treatment administered to the patient.
     *
     * @return The treatment administered for the patient.
     */
    public String getPatientTreatment() {
        return patientTreatment;
    }

    /**
     * Retrieves the doctor's notes for the diagnosis.
     *
     * @return Additional notes from the doctor.
     */
    public String getDoctorNotes() {
        return doctorNotes;
    }

    /**
     * Retrieves the date of the diagnosis.
     *
     * @return The date the diagnosis was made.
     */
    public String getDiagnosedDate() {
        return diagnosedDate;
    }

    /**
     * Provides a string representation of the medical diagnosis, including the
     * diagnosis date, details, treatment, and doctor notes.
     *
     * @return A formatted string containing the medical diagnosis details.
     */
    @Override
    public String toString() {
        return "Date: " + diagnosedDate + "\n" +
               "Diagnosis: " + patientDiagnosis + "\n" +
               "Treatment: " + patientTreatment + "\n" +
               "Doctor Notes: " + doctorNotes;
    }
}
