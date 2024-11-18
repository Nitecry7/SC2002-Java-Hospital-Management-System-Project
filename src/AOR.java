import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.lang.Exception;

/**
 * The AOR (Appointment Outcome Record) class represents the details of an appointment
 * and its outcome, including patient and doctor information, service type, prescriptions,
 * notes, and date.
 */
public class AOR {

    private final String appointmentID;
    private final String patientName;
    private final String doctorName;
    private Date date;
    private String serviceType;
    private Prescription[] prescriptions;
    private String notes;

    /**
     * Constructs an AOR object when a doctor completes an appointment.
     *
     * @param appointment The completed appointment details.
     */
    public AOR(Appointment appointment) {
        this.appointmentID = appointment.getAppointmentID();
        this.patientName = appointment.getPatientID();
        this.doctorName = appointment.getDoctorID();
        build();
    }

    /**
     * Constructs an AOR object from existing data.
     *
     * @param s The array of data representing the AOR.
     * @throws Exception If there is an error during deserialization.
     */
    private AOR(String[] s) throws Exception {
        appointmentID = s[Consts.AOR.ID_COLUMN];
        patientName = s[Consts.AOR.PATIENT_NAME_COLUMN];
        doctorName = s[Consts.AOR.DOCTOR_NAME_COLUMN];
        serviceType = s[Consts.AOR.SERVICE_COLUMN];
        notes = s[Consts.AOR.NOTES_COLUMN].replace("␟", ",");

        byte[] dateData = Base64.getDecoder().decode(s[Consts.AOR.DATE_COLUMN]);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dateData));
        date = (Date) ois.readObject();

        byte[] prescriptionData = Base64.getDecoder().decode(s[Consts.AOR.PRESCRIPTION_COLUMN]);
        ois = new ObjectInputStream(new ByteArrayInputStream(prescriptionData));
        prescriptions = (Prescription[]) ois.readObject();

        ois.close();
    }

    /**
     * Searches and retrieves an AOR object by its ID.
     *
     * @param appointmentID The ID of the appointment.
     * @return The AOR object if found, otherwise null.
     * @throws Exception If there is an error during file reading or deserialization.
     */
    public static AOR findAOR(String appointmentID) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("AOR.csv"));
        String details;

        while ((details = br.readLine()) != null) {
            if (details.split(",")[Consts.AOR.ID_COLUMN].equals(appointmentID)) {
                break;
            }
        }

        if (details == null) {
            return null;
        } else {
            return new AOR(details.split(","));
        }
    }

    /**
     * Builds the remaining fields of the AOR based on user input.
     */
    private void build() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter notes. Press enter twice on a line to finish");

        String input = in.nextLine();
        while (!input.equals("")) {
            this.notes = getNotes().concat(input + "\n");
            input = in.nextLine();
        }

        ArrayList<Prescription> prescriptionList = new ArrayList<>();
        while (true) {
            System.out.println("Add a new prescription? Y/N");
            String choice = in.nextLine().toUpperCase();
            switch (choice) {
                case "Y":
                    prescriptionList.add(new Prescription());
                    break;
                case "N":
                    prescriptions = prescriptionList.toArray(Prescription[]::new);
                    System.out.println("AOR completed for patient: " + getPatientName());
                    return;
                default:
                    System.out.println("Invalid input, try again");
            }
        }
    }

    /**
     * Displays the details of the AOR.
     */
    public void display() {
        System.out.println("AOR details:\n");
        System.out.println("Patient: " + getPatientName());
        System.out.println("Doctor: " + getDoctorName());
        System.out.println("Appointment date: " + getDate().toString());
        System.out.println("\nNotes:\n" + getNotes());

        displayPrescriptions(false);
    }

    /**
     * Displays the prescriptions associated with the AOR.
     *
     * @param onlyPending If true, displays only pending prescriptions.
     */
    private void displayPrescriptions(boolean onlyPending) {
        System.out.println(onlyPending ? "\n\n Pending prescriptions:\n\n" : "\n\n All prescriptions:\n\n");
        int n = 1;
        for (Prescription p : getPrescriptions()) {
            if (!p.isDispensed() || !onlyPending) {
                System.out.print(n + ".  ");
                p.display();
                System.out.println("\n\n");
                n++;
            }
        }
    }

    /**
     * Saves the current AOR data back to the file.
     *
     * @throws Exception If there is an error during file writing or serialization.
     */
    public void saveData() throws Exception {
        List<String> allAOR = Files.readAllLines(Paths.get("AOR.csv"));

        int i = 0;
        while (!allAOR.get(i).split(",")[0].equals(getID())) {
            i++;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(getDate());
        String serializedDate = Base64.getEncoder().encodeToString(baos.toByteArray());

        baos.reset();

        oos.writeObject(getPrescriptions());
        String serializedPrescriptions = Base64.getEncoder().encodeToString(baos.toByteArray());

        String fileString = appointmentID + "," + patientName + "," + doctorName + "," + serviceType + ","
                + serializedDate + "," + serializedPrescriptions + notes.replace(",", "␟");

        allAOR.set(i, fileString);
        Files.write(Paths.get("AOR.csv"), allAOR);
    }

    /**
     * Returns the ID of the AOR.
     *
     * @return The appointment ID.
     */
    public String getID() {
        return appointmentID;
    }

    /**
     * Returns the service type.
     *
     * @return The service type.
     */
    public String serviceType() {
        return serviceType;
    }

    /**
     * Returns the prescriptions associated with the AOR.
     *
     * @return An array of Prescription objects.
     */
    public Prescription[] getPrescriptions() {
        return prescriptions;
    }

    /**
     * Returns the notes of the AOR.
     *
     * @return The notes.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Returns the patient's name.
     *
     * @return The patient's name.
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Returns the doctor's name.
     *
     * @return The doctor's name.
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * Returns the date of the appointment.
     *
     * @return The appointment date.
     */
    public Date getDate() {
        return date;
    }
}
