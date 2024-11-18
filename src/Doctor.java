import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Doctor extends Staff 
{

    private Doctor(String[] details, IOHandler handler)
    {
        super(details, handler);
    }

    // Factory Method to Create a Doctor Instance
    public static Doctor getDoctor(String doctorID, IOHandler handler) 
    {
        List<String[]> userDetails = handler.getRows(Consts.Staff.ID_COLUMN, doctorID);
        if (userDetails.isEmpty()) 
        {
            return null;
        }
        return new Doctor(userDetails.get(0), handler);
    }


    public MedicalRecord _View_Patient_Medical_Records()
    {
        try 
        {
            AttributeController ac = AttributeController.getInstance();
            AORController aorc = AORController.getInstance();
            List<String> patientIDList = aorc.getPatientList(getUserID());
            int idx = 1, choice = -1;
            if(patientIDList.size() == 0){
                System.out.println("Currently no patient");
                return null;
            }
            System.out.println("Here are your patients:");
            for(String patientID:patientIDList){
                System.out.printf("%d. %s\n", idx, patientID);
                idx = idx + 1;
            }
            do{
                choice = ac.inputInt("Enter choice of patient:");
            }while(choice < 1 || choice >= idx);
            String patientID = patientIDList.get(choice - 1);
            Patient patient = Patient.getPatient(patientID, new CsvHandler(Consts.Patient.FILE_NAME));
            patient._View_Medical_Record();
            return patient.getMedicalRecord();
        } 
        catch (Exception e) 
        {
            System.err.println("Error viewing patient medical records: " + e.getMessage());
            return null;
        }
    }

    public void _Update_Patient_Medical_Records() {
        try {
            AttributeController ac = AttributeController.getInstance();
            System.out.println("Enter the patient ID whose record needs updating: ");
            MedicalRecord patientRecord = _View_Patient_Medical_Records();

            if (patientRecord == null) {
                return;
            }

            String diagnosis = ac.inputString("Input diagnosis, if any: ");
            String treatment = ac.inputString("Input patient treatment, if any: ");
            String notes = ac.inputNote("Input notes, if any. Type END at the last line to finish: ");
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            patientRecord.addMedicalHistory(new MedicalDiagnosis(diagnosis, treatment, notes, today));
            System.out.println("Patient medical record updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating patient medical records: " + e.getMessage());
        }
    }


    public void _View_Personal_Schedule() 
    {
        try 
        {
            AORController aorc = AORController.getInstance();

            System.out.println("Personal Schedule:");
            List<String> appointments = aorc.viewScheduledAppointmentsDoctor(getUserID());
            for (String appointmentInfo : appointments) 
            {
                System.out.println(appointmentInfo);
            }

        } catch (Exception e) 
        {
            System.err.println("Error viewing personal schedule: " + e.getMessage());
        }
    }


    public void _Set_Availability_for_Appointments()
    {
        try 
        {
            System.out.println("1. Set unavailable");
            System.out.println("2. Set available");
            System.out.println("3. Go Back");
            AttributeController ac = AttributeController.getInstance();
            int operation = ac.inputInt("Enter your choice(1-3): ");
            while(operation > 3 || operation < 1){
                System.out.println("Please enter a valid choice.");
                operation = ac.inputInt("Enter your choice(1-3): ");
            }
            AvailabilityController AvC = AvailabilityController.getInstance();
            switch(operation){
                case 1:
                    AvC.addBlockedSlots(userID);
                    break;
                case 2:
                    AvC.deleteBlockedSlot(userID);
                    break;
                case 3:
                    return;
            }
            System.out.println("Availability set successfully.");
        } catch (Exception e) {
            System.err.println("Error setting availability: " + e.getMessage());
        }
    }


    public void _Accept_or_Decline_Appointment_Requests() 
    {
        try 
        {   
            AttributeController ac = AttributeController.getInstance();
            AORController aorc = AORController.getInstance();
            String appID = ac.inputString("Enter appointment ID: ");
            System.out.println("1. Accept");
            System.out.println("2. Decline");
            System.out.println("3. Go Back");
            int operation = ac.inputInt("Enter your choice(1-3): ");
            while(operation > 3 || operation < 1){
                System.out.println("Please enter a valid choice.");
                operation = ac.inputInt("Enter your choice(1-3): ");
            }
            switch(operation){
                case 1:
                    aorc.acceptAppointment(appID);
                    break;
                case 2:
                    aorc.cancelAppointment(appID);
                    break;
                case 3:
                    return;
            }
        } 
        catch (Exception e) 
        {
            System.err.println("Error accepting or declining appointments: " + e.getMessage());
        }
    }

  
    public void _View_Upcoming_Appointments() 
    {
        try 
        {
            AORController aorc = AORController.getInstance();

            System.out.println("Personal Schedule:");
            List<String> appointments = aorc.viewConfirmedAppointments(getUserID());
            for (String appointmentInfo : appointments) 
            {
                System.out.println(appointmentInfo);
            }

        } catch (Exception e) 
        {
            System.err.println("Error viewing personal schedule: " + e.getMessage());
        }
    }

  
    public void _Record_Appointment_Outcome() 
    {
        try
        {
            AttributeController ac = AttributeController.getInstance();

            String appointmentID = ac.inputString("Enter Appointment ID: ");
            String prescription = "";
            while(true){
                String choice = ac.inputString("Add prescription?(y/n)");
                if(choice.equalsIgnoreCase("n")) break;
                Prescription p = new Prescription();
                prescription += p.getMedicineName() + " ";
            }
            String notes = ac.inputNote("Enter consultation notes, type END in a new line to finish: ");

            AORController aorController = AORController.getInstance();
            aorController.recordAppointmentOutcome(appointmentID, prescription, notes);

            System.out.println("Appointment outcome recorded successfully.");
        } 
        catch (Exception e) 
        {
            System.err.println("Error recording appointment outcome: " + e.getMessage());
        }
    }
}