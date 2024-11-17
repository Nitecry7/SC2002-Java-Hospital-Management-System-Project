
import java.io.IOException;
import java.util.List;

public class AORControllerTest {

    public static void main(String[] args) {
        // Access the singleton instance of AORController
        AORController aorController = AORController.getInstance();

        try {
            // Test scheduleAppointment
            System.out.println("Testing scheduleAppointment...");
            aorController.scheduleAppointment("P1001", "D003", "14:00", "2024-11-20", "General Consultation");

            // Test viewScheduledAppointments for a valid patientID
            System.out.println("\nTesting viewScheduledAppointments for Patient ID P1001...");
            try {
                List<String> appointments = aorController.viewScheduledAppointments("P1001");
                Utils.printView(appointments, "Scheduled Appointments for Patient P1001:");
            } catch (IOException e) {
                System.err.println("Error retrieving scheduled appointments: " + e.getMessage());
            }

            // Test rescheduleAppointment
            System.out.println("\nTesting rescheduleAppointment...");
            aorController.rescheduleAppointment("A003", "D002", "15:00", "2024-11-22");

            // Test viewScheduledAppointments after reschedule
            System.out.println("\nTesting viewScheduledAppointments after reschedule for Patient ID P1001...");
            try {
                List<String> appointments = aorController.viewScheduledAppointments("P1001");
                Utils.printView(appointments, "Scheduled Appointments after Reschedule for Patient P1001:");
            } catch (IOException e) {
                System.err.println("Error retrieving scheduled appointments: " + e.getMessage());
            }

            // Test cancelAppointment
            System.out.println("\nTesting cancelAppointment...");
            aorController.cancelAppointment("A003");

            // Test viewScheduledAppointments after cancellation
            System.out.println("\nTesting viewScheduledAppointments after cancellation for Patient ID P1001...");
            try {
                List<String> appointments = aorController.viewScheduledAppointments("P1001");
                Utils.printView(appointments, "Scheduled Appointments after Cancellation for Patient P1001:");
            } catch (IOException e) {
                System.err.println("Error retrieving scheduled appointments: " + e.getMessage());
            }

            // Test viewPastAppointmentsOutcome for a valid patientID
            System.out.println("\nTesting viewPastAppointmentsOutcome for Patient ID P1001...");
            try {
                List<String> pastAppointments = aorController.viewPastAppointmentsOutcome("P1001");
                Utils.printView(pastAppointments, "Past Appointments Outcome for Patient P1001:");
            } catch (IOException e) {
                System.err.println("Error retrieving past appointment outcomes: " + e.getMessage());
            }

            // Test acceptAppointment
            System.out.println("\nTesting acceptAppointment...");
            aorController.acceptAppointment("A007"); // Accept appointment with ID A003

            // Test viewScheduledAppointments after accepting
            System.out.println("\nTesting viewScheduledAppointments after acceptance for Patient ID P1001...");
            try {
                List<String> appointments = aorController.viewScheduledAppointments("P1001");
                Utils.printView(appointments, "Scheduled Appointments after Acceptance for Patient P1001:");
            } catch (IOException e) {
                System.err.println("Error retrieving scheduled appointments: " + e.getMessage());
            }

            // Test viewPendingAppointments for a valid doctorID
            System.out.println("\nTesting viewPendingAppointments for Doctor ID D003...");
            try {
                List<String> pendingAppointments = aorController.viewPendingAppointments("D003");
                Utils.printView(pendingAppointments, "Pending Appointments for Doctor D003:");
            } catch (IOException e) {
                System.err.println("Error retrieving pending appointments: " + e.getMessage());
            }

            // Test recordAppointmentOutcome
            System.out.println("\nTesting recordAppointmentOutcome for Appointment ID A003...");
            aorController.recordAppointmentOutcome("A003", "Cold medicine prescribed",
                    "Patient advised to take the medicine twice daily and follow up in 2 weeks.");

            // Test viewPastAppointmentsOutcome after recording outcome
            System.out.println("\nTesting viewPastAppointmentsOutcome for Patient ID P1001 after recording outcome...");
            try {
                List<String> pastAppointments = aorController.viewPastAppointmentsOutcome("P1001");
                Utils.printView(pastAppointments,
                        "Past Appointments Outcome for Patient P1001 after Recording Outcome:");
            } catch (IOException e) {
                System.err.println(
                        "Error retrieving past appointment outcomes after recording outcome: " + e.getMessage());
            }

            // Test viewAllAppointmentsDetails
            System.out.println("\nTesting viewAllAppointmentsDetails...");
            try {
                List<String> appointmentDetails = aorController.viewAllAppointmentsDetails();
                Utils.printView(appointmentDetails, "All Appointments:");
            } catch (IOException e) {
                System.err.println("Error retrieving appointments: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error during test execution: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid data: " + e.getMessage());
        }
    }
}
