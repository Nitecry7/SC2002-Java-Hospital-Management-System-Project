package hmsystem.views;

import java.util.InputMismatchException;
import hmsystem.controllers.AttributeController;

public class pharmacistView extends generalView
{
    public boolean useViewer()
    {
        displayMenu();
        boolean ret = (getOperation() != 5);
        /*
         * here we call the controllers based on the operation given
         */
        return ret;
    }
    public void displayMenu()
    {
        System.out.println("[-------------------------------------------]");
        System.out.println("| 1. View Appointment Outcome Record        |");
        System.out.println("| 2. Update Prescription Status             |");
        System.out.println("| 3. View Medication Inventory              |");
        System.out.println("| 4. Submit Replenishment Request           |");
        System.out.println("| 5. Logout                                 |");
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
                if (op >= 1 && op <= 5) 
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
