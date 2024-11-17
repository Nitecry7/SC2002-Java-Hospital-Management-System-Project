
import java.util.InputMismatchException;

public class patientView extends generalView
{
    

    public boolean useViewer()
    {
        displayMenu();
        int operation = getOperation();
        boolean ret = (operation != 9);
        
        switch(operation){
            // will be putting the methods below, can transfer to generalView if anything overlaps
            case 1: 
                ViewMedicalRecord();
                break;
            case 2:
                UpdatePersonalInformation();
                break;
            case 3:
                ViewAvailableAppointmentSlots();
                break;
            case 4:
                ScheduleAnAppointment();
                break;
            case 5:
                ReScheduleAnAppointment();
                break;
            case 6:
                CancelAnAppointment();
                break;
            case 7:
                ViewScheduledAppointments();
                break;
            case 8:
                ViewPastAppointmentOutcomeRecords();
                break;
            case 9:
                Logout();
                break;
        }

        return ret;
    }
    public void displayMenu()
    {
        System.out.println("[-------------------------------------------]");
        System.out.println("| 1. View Medical Record                    |");
        System.out.println("| 2. Update Personal Information            |");
        System.out.println("| 3. View Available Appointment Slots       |");
        System.out.println("| 4. Schedule an Appointment                |");
        System.out.println("| 5. Reschedule an Appointment              |");
        System.out.println("| 6. Cancel an Appointment                  |");
        System.out.println("| 7. View Scheduled Appointments            |");
        System.out.println("| 8. View Past Appointment Outcome Records  |");
        System.out.println("| 9. Logout                                 |");
        System.out.println("[-------------------------------------------]");
    }
    public int getOperation() 
    {
        AttributeController getter = AttributeController.getInstance();
        int op = 0;
        while (true) 
        {
            try 
            {
                op = getter.inputInt("Please enter your choice (1-9): ");
                if (op >= 1 && op <= 9) 
                {
                    break;
                } 
                else 
                {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return op;
    }
    public void ViewMedicalRecord()
    {
        System.out.println("You have chosen: View Medical Records:");

    }
    public void UpdatePersonalInformation()
    {

    }
    public void ViewAvailableAppointmentSlots()
    {

    }
    public void ScheduleAnAppointment()
    {

    }
    public void ReScheduleAnAppointment()
    {

    }
    public void CancelAnAppointment()
    {

    }
    public void ViewScheduledAppointments()
    {

    }
    public void ViewPastAppointmentOutcomeRecords()
    {

    }
    public void Logout()
    {

    }

}
