package hmsystem.views;

import java.util.InputMismatchException;
import java.util.Scanner;

public class doctorView extends generalView
{
    public boolean useViewer()
    {
        displayMenu();
        boolean ret = (getOperation() != 8);
        /*
         * here we call the controllers based on the operation given
         */
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
        Scanner in = new Scanner(System.in);
        int op = -1; 
        while (true) 
        {
            System.out.print("Please enter your choice (1-8): ");
            try 
            {
                op = in.nextInt();
                if (op >= 1 && op <= 8) 
                {
                    break;
                } 
                else 
                {
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
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
