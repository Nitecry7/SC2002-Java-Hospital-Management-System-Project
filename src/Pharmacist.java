/**
 * Represents a Pharmacist, inheriting from the Staff class.
 * This class provides functionalities such as viewing appointment outcomes,
 * updating prescription statuses, viewing medication inventory, and submitting replenishment requests.
 */
import java.util.List;

public class Pharmacist extends Staff {

    /**
     * Private constructor to initialize a Pharmacist instance.
     *
     * @param details The array containing pharmacist details.
     * @param handler The IOHandler instance for CSV operations.
     */
    private Pharmacist(String[] details, IOHandler handler) {
        super(details, handler);
    }

    /**
     * Factory method to retrieve a Pharmacist instance based on their ID.
     *
     * @param pharmacistID The unique identifier of the pharmacist.
     * @param handler       The IOHandler instance for CSV operations.
     * @return A Pharmacist instance or null if the pharmacist is not found.
     */
    public static Pharmacist getPharmacist(String pharmacistID, IOHandler handler) {
        List<String[]> userDetails = handler.getRows(Consts.Staff.ID_COLUMN, pharmacistID);
        if (userDetails.isEmpty()) {
            return null;
        } else {
            return new Pharmacist(userDetails.get(0), handler);
        }
    }

    /**
     * Displays the outcome records of past appointments for a specific patient.
     */
    public void _View_Appointment_Outcome_Record() {
        AttributeController ac = AttributeController.getInstance();
        String patientID = ac.inputString("Input patient's ID");

        try {
            List<String> pastAORs = AORController.getInstance().viewPastAppointmentsOutcome(patientID);
            for (String s : pastAORs) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No such patient");
        }
    }

    /**
     * Updates the status of prescriptions, allowing the pharmacist to view all prescriptions
     * or dispense medicine.
     */
    public void _Update_Prescription_Status() {
        PrescriptionController pc = PrescriptionController.getInstance();
        System.out.println("1. View All Prescription Status");
        System.out.println("2. Dispense medicine");
        System.out.println("3. Go back");
        AttributeController ac = AttributeController.getInstance();
        int operation = ac.inputInt("Enter your choice(1-3): ");
        while (operation > 3 || operation < 1) {
            System.out.println("Please enter a valid choice.");
            operation = ac.inputInt("Enter your choice(1-3): ");
        }
        switch (operation) {
            case 1:
                pc.viewPrescriptions();
                break;
            case 2:
                pc.dispenseMedicine();
                break;
            case 3:
                return;
        }
    }

    /**
     * Views the current medication inventory using the InventoryController.
     */
    public void _View_Medication_Inventory() {
        InventoryController ic = InventoryController.getInstance();
        ic.viewMedicationInventory();
    }

    /**
     * Submits a replenishment request for medications using the ReplenishmentController.
     */
    public void _Submit_Replenishment_Request() {
        ReplenishmentController rc = ReplenishmentController.getInstance();
        rc.submitRequest();
    }
}
