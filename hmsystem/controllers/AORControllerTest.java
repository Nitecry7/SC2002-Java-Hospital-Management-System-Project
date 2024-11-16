package hmsystem.controllers;

import hmsystem.io.CsvHandler;
import hmsystem.io.IOHandler;
import hmsystem.data.Consts;

import java.io.IOException;

public class AORControllerTest {

    public static void main(String[] args) {
        IOHandler appointmentHandler = null;
        IOHandler patientHandler = null;
        IOHandler doctorHandler = null;

        // Try to create dummy handlers for appointment, patient, and doctor
        try {
            appointmentHandler = new CsvHandler(Consts.AOR.FILE_NAME);
            patientHandler = new CsvHandler(Consts.Patient.FILE_NAME);
            doctorHandler = new CsvHandler(Consts.Staff.FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error initializing IOHandler: " + e.getMessage());
            return; // Exit if file handling initialization fails
        }

        // Create AORController instance
        AORController aorController = new AORController(appointmentHandler, patientHandler, doctorHandler);

        try {
            // Test scheduleAppointment
            System.out.println("Testing scheduleAppointment...");
            aorController.scheduleAppointment("P1001", "D003", "14:00", "2024-11-20", "General Consultation");

            // Test viewScheduledAppointments for a valid patientID
            System.out.println("\nTesting viewScheduledAppointments for Patient ID P1001...");
            aorController.viewScheduledAppointments("P1001");

            // Test rescheduleAppointment
            System.out.println("\nTesting rescheduleAppointment...");
            aorController.rescheduleAppointment("A003", "D002", "15:00", "2024-11-22");

            // Test viewScheduledAppointments after reschedule
            System.out.println("\nTesting viewScheduledAppointments after reschedule for Patient ID P1001...");
            aorController.viewScheduledAppointments("P1001");

            // Test cancelAppointment
            System.out.println("\nTesting cancelAppointment...");
            aorController.cancelAppointment("A003");

            // Test viewScheduledAppointments after cancellation
            System.out.println("\nTesting viewScheduledAppointments after cancellation for Patient ID P1001...");
            aorController.viewScheduledAppointments("P1001");

            // Test viewPastAppointmentsOutcome for a valid patientID
            System.out.println("\nTesting viewPastAppointmentsOutcome for Patient ID P1001...");
            aorController.viewPastAppointmentsOutcome("P1001");

            // Test acceptAppointment
            System.out.println("\nTesting acceptAppointment...");
            aorController.acceptAppointment("A007"); // Accept appointment with ID A003

            // Test viewScheduledAppointments after accepting
            System.out.println("\nTesting viewScheduledAppointments after acceptance for Patient ID P1001...");
            aorController.viewScheduledAppointments("P1001");

            // Test viewPendingAppointments for a valid doctorID
            System.out.println("\nTesting viewPendingAppointments for Doctor ID D003...");
            aorController.viewPendingAppointments("D003");

            // Test recordAppointmentOutcome
            System.out.println("\nTesting recordAppointmentOutcome for Appointment ID A003...");
            aorController.recordAppointmentOutcome("A003", "Cold medicine prescribed",
                    "Patient advised to take the medicine twice daily and follow up in 2 weeks.");

            // Test viewPastAppointmentsOutcome after recording outcome
            System.out.println("\nTesting viewPastAppointmentsOutcome for Patient ID P1001 after recording outcome...");
            aorController.viewPastAppointmentsOutcome("P1001");

            // Test viewAllAppointmentsDetails
            System.out.println("\nTesting viewAllAppointmentsDetails...");
            aorController.viewAllAppointmentsDetails();

        } catch (IOException e) {
            System.err.println("Error during test execution: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid data: " + e.getMessage());
        }
    }
}
