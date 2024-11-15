package hmsystem.views;

import java.util.InputMismatchException;
import java.util.Scanner;

public class patientView extends generalView
{
    public boolean useViewer(){
        displayMenu();
        boolean ret = (getOperation() != 9);
        /*
         * here we call the controllers based on the operation given
         */
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
        Scanner in = new Scanner(System.in);
        int op = -1; 
        while (true) 
        {
            System.out.print("Please enter your choice (1-9): ");
            try 
            {
                op = in.nextInt();
                if (op >= 1 && op <= 9) 
                {
                    break;
                } 
                else 
                {
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
                }
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid input. Please enter a valid integer.");
                in.next(); 
            }
        }
        in.close();
        return op;
    }
}
