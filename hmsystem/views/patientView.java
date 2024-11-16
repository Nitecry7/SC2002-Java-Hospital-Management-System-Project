package hmsystem.views;

import java.util.InputMismatchException;
import hmsystem.controllers.AttributeController;

public class patientView extends generalView
{
    public boolean useViewer()
    {
        displayMenu();
        int operation = getOperation();
        boolean ret = (operation != 9);
        
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
            case 9:
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
}
