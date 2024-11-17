package hmsystem.views;

import hmsystem.controllers.*;
import hmsystem.data.*;
import hmsystem.models.*;
import hmsystem.io.*;
import hmsystem.views.*;



public class doctorView extends generalView
{
    private final Doctor doctor;

    public doctorView(Doctor doctor) 
    {
        this.doctor = doctor;
    }
    public boolean useViewer()
    {
       
        displayMenu();
        int operation = getOperation();
        boolean ret = (operation != 8);
        
        switch(operation)
        {
            case 1: 
            viewPatientMedicalRecords();
                break;
            case 2:
            updatePatientMedicalRecords();
                break;
            case 3:
            viewPersonalSchedule();
                break;
            case 4:
            setAvailabilityForAppointments();
                break;
            case 5:
            acceptOrDeclineAppointments();
                break;
            case 6:
            viewUpcomingAppointments();
                break;
            case 7:
            recordAppointmentOutcome();
                break;
            case 8:
            Logout();
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
                op = getter.inputInt("Please enter your choice (1-8): ");
                if (op >= 1 && op <= 8) 
                {
                    break;
                } 
                else 
                {
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                }
            } 
            catch (Exception e) 
            {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return op;
    }

    private void viewPatientMedicalRecords() 
    {
        System.out.println("You have chosen: View Patient Medical Records:");
        try 
        {
            doctor._View_Patient_Medical_Records();
        } 
        catch (Exception e) 
        {
            System.err.println("Error viewing patient medical records: " + e.getMessage());
        }
    }

    private void updatePatientMedicalRecords() 
    {
        System.out.println("You have chosen: Update Patient Medical Records:");
        try 
        {
            doctor._Update_Patient_Medical_Records();
        } 
        catch (Exception e) 
        {
            System.err.println("Error updating patient medical records: " + e.getMessage());
        }
    }

    private void viewPersonalSchedule() 
    {
        System.out.println("You have chosen: View Personal Schedule:");
        try 
        {
            doctor._View_Personal_Schedule();
        } 
        catch (Exception e)
        {
            System.err.println("Error viewing personal schedule: " + e.getMessage());
        }
    }

    private void setAvailabilityForAppointments() {
        System.out.println("You have chosen: Set Availability for Appointments:");
        try 
        {
            doctor._Set_Availability_or_Appointments();
        } 
        catch (Exception e) 
        {
            System.err.println("Error setting availability: " + e.getMessage());
        }
    }

    private void acceptOrDeclineAppointments() 
    {
        System.out.println("You have chosen: Accept or Decline Appointment Requests:");
        try 
        {
            doctor._Accept_or_Decline_Appointment_Requests();

        } 
        catch (Exception e) 
        {
            System.err.println("Error managing appointment requests: " + e.getMessage());
        }
    }

    private void viewUpcomingAppointments() 
    {
        System.out.println("You have chosen: View Upcoming Appointments:");
        try 
        {
            doctor._View_Upcoming_Appointments();
        } 
        catch (Exception e) 
        {
            System.err.println("Error viewing upcoming appointments: " + e.getMessage());
        }
    }

    private void recordAppointmentOutcome() 
    {
        System.out.println("You have chosen: Record Appointment Outcome:");
        try 
        {
            doctor._Record_Appointment_Outcome();
        } 
        catch (Exception e) 
        {
            System.err.println("Error recording appointment outcome: " + e.getMessage());
        }
    }

    private void Logout() 
    {
        System.out.println("You have chosen: Logout. Logging out Please wait...");
        // Add any additional cleanup or session handling logic here if needed.

        try 
        {
        System.out.println("Returning to login...");
        LoginHandler loginHandler = new LoginHandler(); // Instantiate LoginHandler

        User user = loginHandler.authenticate(); // Redirect to login flow

        if (user != null) 
        {
            System.out.println("Logged in successfully as " + user.getUserID());
            // Redirect to the appropriate view based on user type
            if (user instanceof Doctor) 
            {
                new doctorView((Doctor) user).useViewer();
            } 
            else 
            {
                System.out.println("User type not recognized for redirection.");
            }
        } 
        else 
        {
            System.out.println("Login failed or canceled.");
        }
    } 
    catch (Exception e) 
    {
        System.err.println("Error during logout and login transition: " + e.getMessage());
    }
    }
    
    
}
