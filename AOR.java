import java.util.List;
import java.io.BufferedReader;
import java.util.Base64;
import java.io.FileReader;
import java.util.Date;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;

public class AOR {

    private final String appointmentID;
    private final String patientName;
    private final String doctorName;
    private Date date;
    private String serviceType;
    private Prescription[] prescriptions;
    private String notes;

    //Called when doctor completes an appointment & creates a new AOR for it, which is then passed as the parameter
    public AOR(Appointment appointment) {

        this.appointmentID = appointment.getID();
        this.patientName = appointment.getPatientName();
        this.doctorName = appointment.getDoctorName();
        //Function to generate rest of the fields based on user input
        build();

    }
    

    //Called only by findAOR() - constructs an AOR object from existing data in the file
    private AOR(String[] s) throws Exception {
   
        appointmentID = s[0];
        patientName = s[1];
        doctorName = s[2];
        serviceType = s[3];
        notes = s[6].replace("␟", ",");


        byte[] dateData = Base64.getDecoder().decode(s[4]);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dateData));
        date = (Date) ois.readObject();

        byte[] prescriptionData = Base64.getDecoder().decode(s[5]);
        ois = new ObjectInputStream(new ByteArrayInputStream(prescriptionData));

        prescriptions = (Prescription[]) ois.readObject();

        ois.close();

    }

    //Called when someone wants to search and retrieve an AOR object by ID (returns null if no ID is found)
    public static AOR findAOR(String appointmentID) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("AOR.csv"));
        String details = null;

        while ((details = br.readLine()) != null) {
            if (details.split(",")[0].equals(appointmentID)) {
                break;
            }
        }

        if (details == null) {
            return null;
        }
        else {
            return new AOR(details.split(","));
        }

    }
  
    //Function to generate rest of the fields based on user input
    private void build() {

        Scanner in = new Scanner(System.in);
        System.out.println("Enter notes. Press enter twice on a line to finish");

        String input = in.nextLine();

        while(!input.equals("")) {
            this.notes =  getNotes().concat(input + "\n");
            input = in.nextLine();
        }


        while (!input.equals("N")) {

            ArrayList<Prescription> prescriptionList = new ArrayList<>();
            System.out.println("Add a new prescription? Y/N");

            switch(in.nextLine().toUpperCase()) {
                case "Y" ->  {
                    prescriptionList.add(new Prescription());
                }

                case "N" ->  {
                    prescriptions = prescriptionList.toArray(Prescription[]::new);
                    System.out.println("AOR completed for patient: " + getPatientName());
                }

                default -> {
                    System.out.println("Invalid input, try again");
                }
            }
        }


    }

    public void display() {
        System.out.println("AOR details:\n");
        System.out.println("Patient: " + getPatientName());
        System.out.println("Doctor: " + getDoctorName());
        System.out.println("Appointment date: " + getDate().toString());
        System.out.println("\nNotes:\n" + getNotes());

        displayPrescriptions(false);
    }

    //Displays details of each prescription
    private void displayPrescriptions(boolean onlyPending) {

        if (onlyPending) {
            System.out.println("\n\n Pending prescriptions:\n\n");
        }
        else {
            System.out.println("\n\n All prescriptions:\n\n");
        }
        int n = 1;
        for (Prescription p : getPrescriptions()) {

            if (!p.isDispensed() || !onlyPending) {
                System.out.print(n + ".  ");
                //Calls display function of each prescription
                p.display();
                System.out.println("\n\n");
            }
            
        }

    }

    //Read all lines of the file, change the line relating to this AOR, the write all lines back to file (cause apparently you can't change a single line independently in a file in java)
    public void saveData() throws Exception {
        
        List<String> allAOR = Files.readAllLines(Paths.get("AOR.csv"));

        //Finding the line number of this AOR in the csv file
        int i = 0;
        while (!allAOR.get(i).split(",")[0].equals(getID())) { i++; }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        //Encoding the serialized Date object into storeable string
        oos.writeObject(getDate());
        String serializedDate = Base64.getEncoder().encodeToString(baos.toByteArray()); //Encoding the serialized Date object into storeable string

        baos.reset();

        //Encoding the serialized Prescrpition[] object into storeable string   
        oos.writeObject(getPrescriptions());
        String serializedPrescriptions = Base64.getEncoder().encodeToString(baos.toByteArray()); 

        //Constructing new csv file line for this AOR
        String fileString = appointmentID + "," + patientName + "," + doctorName + "," + serviceType + "," + serializedDate + "," + serializedPrescriptions + notes.replace(",", "␟");
                
        //Setting the specific line of the file to the new AOR data
        allAOR.set(i, fileString);
       
        //Writing all lines back to the file
        Files.write(Paths.get("AOR.csv"), allAOR);

    }


    public String getID() {
        return appointmentID;
    }

    public String serviceType() {
        return  serviceType;
    }

    public Prescription[] getPrescriptions() {
        return prescriptions;
    }

    public String getNotes() {
        return notes;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Date getDate() {
        return date;
    }

}
