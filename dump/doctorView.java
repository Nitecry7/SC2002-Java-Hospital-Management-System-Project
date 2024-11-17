
import java.util.InputMismatchException;

public class doctorView extends generalView
{
    public boolean useViewer()
    {
        displayMenu();
        int operation = getOperation();
        boolean ret = (operation != 8);
        
        switch(operation){
            case 1: 
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
        }

        return ret;
    }
    public void displayMenu()
    {
        System.out.println("[-------------------------------------------]");
        System.out.println("| 1. View Patient Medical Records           |");
        System.out.println("| 2. Update Patient Medical Records         |");
        System.out.println("| 3. View Personal Schedule                 |");
        System.out.println("| 4. Set Availability for Appointments      |");
        System.out.println("| 5. Accept or Decline Appointment Requests |");
        System.out.println("| 6. View Upcoming Appointments             |");
        System.out.println("| 7. Record Appointment Outcome             |");
        System.out.println("| 8. Logout                                 |");
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
                op = getter.inputInt("Please enter your choice (1-5): ");
                if (op >= 1 && op <= 8) 
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
}
