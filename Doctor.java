import java.util.*;

public class Doctor extends User
{
    public Doctor(String hospitalID, String name, String emailAddress, String userRole) 
    {
        super(hospitalID, name, emailAddress, "Doctor");
    }

    @Override
    public void displayMenu() // Different Implementation based on User's Role 
    {
       displayMessage(); // Welcome message from User class

        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Schedule");
        System.out.println("4. Set Availability");
        System.out.println("5. Change Password");
        System.out.println("6. Logout");

        DoctorInput();
    }

    private void DoctorInput()
    {
        Scanner input = new Scanner(System.in);
        int userChoice = input.nextInt();

        boolean loggedIn = true;

        while(loggedIn)
        {
            switch(userChoice)
        {
            case 1:
            System.out.println("Enter Patient's ID: ");
            String patientID = input.nextLine();

            MedicalRecord patientRecord = getPatientRecord(patientID);
            
            viewPatientMedicalRecords(); //im not sure how to go about this
            
        }
        } 
        
    }

    public void viewPatientMedicalRecords(MedicalRecord patientRecord)
    {
        System.out.println("---Currently Viewing---");

        System.out.println("Medical Record of Patient: " + patientRecord.getPatientName()); //Shows which patient the doctor is currently accessing

        System.out.println(patientRecord.displayPatientMedicalRecord()); //Display the full medical records of the patient

    }

    public void updatePatientMedicalRecords(MedicalRecord patientRecord, String patientDiagnosis, String patientTreatment, String doctorNotes, String diagnosedDate)
    {
        MedicalDiagnosis newDiagnosis = new MedicalDiagnosis(patientDiagnosis, patientTreatment, doctorNotes, diagnosedDate);
    }
}
