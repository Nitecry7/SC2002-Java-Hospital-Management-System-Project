import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Represents a patient's medical record, including personal details, medical history, and AOR IDs.
 * Provides functionality to manage and persist medical record data.
 */
public class MedicalRecord {

    private String patientID; // Unique identifier for the patient
    private String patientName; // Name of the patient
    private String patientDateOfBirth; // Date of birth of the patient
    private String patientGender; // Gender of the patient
    private String patientEmail; // Email address of the patient
    private String patientPhone; // Contact number of the patient
    private String patientPassword; // Password for patient authentication
    private BloodType patientBloodType = BloodType.NOT_SET; // Patient's blood type
    private List<String> AORIDs = new ArrayList<>(); // List of associated Appointment Order Record (AOR) IDs
    private List<MedicalDiagnosis> patientMedicalHistory = new ArrayList<>(); // List of past medical diagnoses

    private IOHandler handler; // Handler for input/output operations

    /**
     * Constructs a MedicalRecord object for the specified patient ID, loading data from the provided IOHandler.
     *
     * @param patientID The unique identifier of the patient.
     * @param handler   The IOHandler to manage data retrieval and updates.
     * @throws Exception If an error occurs during data initialization.
     */
    public MedicalRecord(String patientID, IOHandler handler) throws Exception {
        try {
            String[] details = handler.getRows(Consts.Patient.ID_COLUMN, patientID).get(0);

            if (details.length < 10) {
                details = Arrays.copyOf(details, 10);
            }

            this.handler = handler;
            this.patientID = details[Consts.Patient.ID_COLUMN];
            this.patientPassword = details[Consts.Patient.PW_COLUMN];
            this.patientName = details[Consts.Patient.NAME_COLUMN];
            this.patientGender = details[Consts.Patient.GENDER_COLUMN];
            this.patientEmail = details[Consts.Patient.EMAIL_COLUMN];
            this.patientDateOfBirth = details[Consts.Patient.DOB_COLUMN];
            this.patientPhone = details[Consts.Patient.CONTACTNUMBER_COLUMN];
            this.patientBloodType = BloodType.NOT_SET;

            if (BloodType.valueOf(details[Consts.Patient.BLOODTYPE_COLUMN]) != null) {
                this.patientBloodType = BloodType.valueOf(details[Consts.Patient.BLOODTYPE_COLUMN]);
            }

            if (details[Consts.Patient.AOR_ID_COLUMN] != null) {
                byte[] AORIDdata = Base64.getDecoder().decode(details[Consts.Patient.AOR_ID_COLUMN]);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(AORIDdata));
                this.AORIDs = (List<String>) ois.readObject();
                ois.close();
            } else {
                this.AORIDs = new ArrayList<>();
            }

            if (details[Consts.Patient.DIAGNOSISTREATMENT_COLUMN] != null) {
                byte[] diagnosisData = Base64.getDecoder().decode(details[Consts.Patient.DIAGNOSISTREATMENT_COLUMN]);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(diagnosisData));

                this.patientMedicalHistory = (List<MedicalDiagnosis>) ois.readObject();
                ois.close();

            } else {
                this.patientMedicalHistory = new ArrayList<>();
            }

        } catch (Exception e) {
            System.out.println("Error when initializing medical record.");
            e.printStackTrace();
        }

    }

    /**
     * Saves the current medical record data back to the storage through the IOHandler.
     *
     * @throws IOException If an error occurs during data serialization or saving.
     */
    public void saveData() throws IOException {

        String[] details = new String[10];

        details[Consts.Patient.PW_COLUMN] = patientPassword;
        details[Consts.Patient.ID_COLUMN] = patientID;
        details[Consts.Patient.NAME_COLUMN] = patientName;
        details[Consts.Patient.EMAIL_COLUMN] = patientEmail;
        details[Consts.Patient.GENDER_COLUMN] = patientGender;
        details[Consts.Patient.CONTACTNUMBER_COLUMN] = patientPhone;
        details[Consts.Patient.DOB_COLUMN] = patientDateOfBirth;

        details[Consts.Patient.BLOODTYPE_COLUMN] = patientBloodType.name();

        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ObjectOutputStream oos1 = new ObjectOutputStream(baos1);
        oos1.writeObject(AORIDs);
        details[Consts.Patient.AOR_ID_COLUMN] = Base64.getEncoder().encodeToString(baos1.toByteArray());

        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
        oos2.writeObject(patientMedicalHistory);
        details[Consts.Patient.DIAGNOSISTREATMENT_COLUMN] = Base64.getEncoder().encodeToString(baos2.toByteArray());

        oos1.close();
        baos1.close();
        oos2.close();
        baos2.close();

        handler.updateRow(Consts.Patient.ID_COLUMN, patientID, details);

    }

    /**
     * Displays the patient's medical record, including personal details and medical history.
     *
     * @return A formatted string representation of the medical record.
     */
    public String displayPatientMedicalRecord() {
        String record = "\n----Displaying patient record----\n" + "Patient ID: " + patientID + "\nName: " + patientName + "\nDate of Birth: " +
                patientDateOfBirth + "\nGender: " + patientGender + "\nBlood Type: " + getPatientBloodType().name() + "\nEmail: " + getPatientEmail() + "\nContact Number: " + getPatientPhone() + "\n\n" + "Medical History:\n";

        record = record.concat("\n---Past diagnosis and treatments---\n");

        for (int i = 0; i < patientMedicalHistory.size(); i++) {
            record += patientMedicalHistory.get(i) + "\n\n";
        }
        System.out.println(record);

        return record;
    }

    // Getters and setters for patient details
    /**
     * Getter for patient name
     * 
     * @return name of patient
     */
    public String getPatientName() {
        return patientName;
    }
    /**
     * Setter for patient name
     * @param patientName The name of patient
     * @throws IOException If saving to csv failed
     */
    public void setPatientName(String patientName) throws IOException {
        this.patientName = patientName;
        saveData();
    }
    /**
     * Getter for patient date of birth
     * 
     * @return date of birth of patient
     */
    public String getPatientDateOfBirth() {
        return patientDateOfBirth;
    }
    /**
     * Setter for patient date of birth
     * @param patientDateOfBirth The date of birth of patient
     * @throws IOException If saving to csv failed
     */
    public void setPatientDateOfBirth(String patientDateOfBirth) throws IOException {
        this.patientDateOfBirth = patientDateOfBirth;
        saveData();
    }
    /**
     * Getter for patient gender
     * 
     * @return gender of patient
     */
    public String getPatientGender() {
        return patientGender;
    }
    /**
     * Setter for patient gender
     * @param patientGender The gender of patient
     * @throws IOException If saving to csv failed
     */
    public void setPatientGender(String patientGender) throws IOException {
        this.patientGender = patientGender;
        saveData();
    }
    /**
     * Getter for patient email
     * 
     * @return email of patient
     */
    public String getPatientEmail() {
        return patientEmail;
    }
    /**
     * Setter for patient email
     * @param patientEmail The email of patient
     * @throws IOException If saving to csv failed
     */
    public void setPatientEmail(String patientEmail) throws IOException {
        this.patientEmail = patientEmail;
        saveData();
    }
    /**
     * Getter for patient phone number
     * 
     * @return phone number of patient
     */
    public String getPatientPhone() {
        return patientPhone;
    }
    /**
     * Setter for patient phone number
     * @param patientPhone The phone of patient
     * @throws IOException If saving to csv failed
     */
    public void setPatientPhone(String patientPhone) throws IOException {
        this.patientPhone = patientPhone;
        saveData();
    }
    /**
     * Getter for patient blood type
     * 
     * @return blood type of patient
     */
    public BloodType getPatientBloodType() {
        if (patientBloodType == null) {
            return BloodType.NOT_SET;
        } else {
            return patientBloodType;
        }
    }
    /**
     * Setter for patient blood type
     * @param patientBloodType The blood type of patient
     * @throws IOException If saving to csv failed
     */
    public void setPatientBloodType(BloodType patientBloodType) throws IOException {
        this.patientBloodType = patientBloodType;
        saveData();
    }
    /**
     * Getter for patient appointment outcome IDs
     * 
     * @return appointment outcome IDs of patient
     */
    public List<String> getAORIDs() {
        return AORIDs;
    }
    /**
     * Adding an appointment outcome record into AORIDs
     * @param AORID the ID of appointment outcome record
     * @throws IOException If saving to csv failed
     */
    public void addAORIDs(String AORID) throws IOException {
        this.AORIDs.add(AORID);
        saveData();
    }
    /**
     * Getter for patient medical history
     * 
     * @return medical history of patient
     */
    public List<MedicalDiagnosis> getPatientMedicalHistory() {
        return patientMedicalHistory;
    }
    /**
     * Adding a medical history
     * @param patientMedicalHistory the ID of medical history
     * @throws IOException If saving to csv failed
     */
    public void addMedicalHistory(MedicalDiagnosis patientMedicalHistory) throws IOException {
        this.patientMedicalHistory.add(patientMedicalHistory);
        saveData();
    }
    /**
     * Setting patient new password
     * @param patientPassword New password for patient
     * @throws IOException if not successful
     */
    public void setPatientPassword(String patientPassword) throws IOException {
        this.patientPassword = patientPassword;
        saveData();
    }
}
