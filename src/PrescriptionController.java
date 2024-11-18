/**
 * Singleton class for managing prescriptions, including dispensing medicines
 * and viewing prescription details. This class interacts with the inventory
 * and prescription data stored in CSV files.
 */
import java.io.IOException;
import java.util.*;

public class PrescriptionController {

    /**
     * Singleton instance of the PrescriptionController.
     */
    public final static PrescriptionController pc = new PrescriptionController();

    /**
     * IOHandler for managing prescription CSV operations.
     */
    private IOHandler prescriptionHandler;

    /**
     * Retrieves the singleton instance of the PrescriptionController.
     *
     * @return The singleton instance of the PrescriptionController.
     */
    public static PrescriptionController getInstance() {
        return pc;
    }

    /**
     * Constructor for initializing the PrescriptionController.
     * Loads the prescription data from the "Prescription_List.csv" file.
     */
    public PrescriptionController() {
        try {
            prescriptionHandler = new CsvHandler("Prescription_List.csv");
        } catch (IOException e) {
            System.out.println("Something went wrong while initializing PrescriptionController.");
        }
    }

    /**
     * Dispenses medicine for a given prescription ID by reducing the stock in inventory
     * and updating the prescription's dispense status.
     *
     * @return true if the medicine was successfully dispensed; false otherwise.
     */
    public boolean dispenseMedicine() {
        AttributeController getter = AttributeController.getInstance();
        String ID = getter.inputString("Input Prescription ID");

        // Retrieve prescription row
        List<String[]> rows = prescriptionHandler.getRows(0, ID);
        if (rows.isEmpty()) {
            System.out.println("Prescription ID not found.");
            return false;
        }

        String[] row = rows.get(0);
        int amount = Integer.parseInt(row[1]);
        String name = row[3];

        // Check if already dispensed
        if (row[4].equals("true")) {
            System.out.println("Medicine already dispensed.");
            return false;
        }

        // Attempt to reduce stock
        InventoryController ic = InventoryController.getInstance();
        boolean ret = ic.reduceStock(name, amount);

        // Update dispense status if stock reduction is successful
        if (ret) {
            try {
                prescriptionHandler.setField(0, ID, 4, "true");
            } catch (Exception e) {
                System.out.println("Error updating prescription dispense status.");
            }
        }

        return ret;
    }

    /**
     * Displays all prescriptions in the system along with their details, including
     * the prescription ID, amount, frequency, medicine name, and dispense status.
     */
    public void viewPrescriptions() {
        Collection<String[]> data = prescriptionHandler.readCsvValues();

        for (String[] row : data) {
            System.out.printf("ID: %s\n", row[0]);
            System.out.printf("Amount: %s\n", row[1]);
            System.out.printf("Frequency: %s\n", row[2]);
            System.out.printf("Medicine Name: %s\n", row[3]);
            System.out.printf("Dispense Status: %s\n", row[4]);
            System.out.println("--------------------------------");
        }
    }
}
