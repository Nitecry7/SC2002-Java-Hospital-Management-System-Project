package hmsystem.views;

import java.util.InputMismatchException;
import java.util.Scanner;

public class adminView extends generalView
{
    public boolean useViewer()
    {
        displayMenu();
        int operation = getOperation();
        boolean ret = (operation != 5);
        /*
         * here we call the controllers based on the operation given
         */
        return ret;
    }
    public void displayMenu()
    {
        System.out.println("[-------------------------------------------]");
        System.out.println("| 1. View and Manage Hospital Staff         |");
        System.out.println("| 2. View Appointments details              |");
        System.out.println("| 3. View and Manage Medication Inventory   |");
        System.out.println("| 4. Approve Replenishment Requests         |");
        System.out.println("| 5. Logout                                 |");
        System.out.println("[-------------------------------------------]");
    }
    public int getOperation() 
    {
        Scanner in = new Scanner(System.in);
        int op = -1; 
        while (true) 
        {
            System.out.print("Please enter your choice (1-5): ");
            try 
            {
                op = in.nextInt();
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
                in.next(); 
            }
        }
        in.close();
        return op;
    }
}
