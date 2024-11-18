/**
 * Represents a Replenishment request for medicines. This class is responsible
 * for creating, submitting, and managing replenishment requests, including their
 * approval or rejection. It interacts with the inventory and replenishment
 * CSV files to track and update replenishment data.
 */
import java.io.IOException;
import java.util.*;

public class Replenishment {
    /**
     * IOHandler for managing medicine data.
     */
    private IOHandler medicineCsvHandler;

    /**
     * IOHandler for managing replenishment data.
     */
    private IOHandler replenishmentCsvHandler;

    /**
     * AttributeController instance for handling user inputs.
     */
    private AttributeController getter = AttributeController.getInstance();

    /**
     * Enum representing the status of a replenishment request.
     */
    private enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    /**
     * Unique identifier for the replenishment request.
     */
    private int requestID;

    /**
     * List of medicine names included in the replenishment request.
     */
    private List<String> medicineNames;

    /**
     * Current status of the replenishment request.
     */
    private Status status = Status.PENDING;

    /**
     * Constructor for creating a new replenishment request.
     * Initializes the request ID and identifies medicines with low stock levels.
     *
     * @param medicineCsvHandler       IOHandler for medicine data.
     * @param replenishmentCsvHandler  IOHandler for replenishment data.
     */
    public Replenishment(IOHandler medicineCsvHandler, IOHandler replenishmentCsvHandler) {
        this.medicineCsvHandler = medicineCsvHandler;
        this.replenishmentCsvHandler = replenishmentCsvHandler;
        Collection<String[]> data = replenishmentCsvHandler.readCsvValues();
        requestID = data.size();
        medicineNames = getLowMedicine();
    }

    /**
     * Constructor for initializing a replenishment request with existing data.
     *
     * @param medicineCsvHandler       IOHandler for medicine data.
     * @param replenishmentCsvHandler  IOHandler for replenishment data.
     * @param requestID                The unique identifier for the request.
     * @param medicineNames            List of medicine names in the request.
     * @param status                   Status of the request (PENDING, APPROVED, REJECTED).
     */
    public Replenishment(IOHandler medicineCsvHandler, IOHandler replenishmentCsvHandler,
                         int requestID, List<String> medicineNames, String status) {
        this.medicineCsvHandler = medicineCsvHandler;
        this.replenishmentCsvHandler = replenishmentCsvHandler;
        this.requestID = requestID;
        this.medicineNames = medicineNames;
        this.status = Status.valueOf(status);
    }

    /**
     * Submits the replenishment request by adding it to the CSV file.
     */
    public void submitRequest() {
        String[] newRow = new String[3];
        newRow[0] = Integer.toString(requestID);
        newRow[1] = String.join(" ", medicineNames);
        newRow[2] = status.toString();
        try {
            replenishmentCsvHandler.addRow(newRow);
        } catch (IOException e) {
            System.out.println("Error occurred when submitting request.");
        }
    }

    /**
     * Retrieves a list of medicines with stock levels below the alert threshold.
     * Allows the user to select medicines to include in the replenishment request.
     *
     * @return List of selected medicine names.
     */
    public List<String> getLowMedicine() {
        List<String> ret = new ArrayList<>();
        Collection<String[]> data = medicineCsvHandler.readCsvValues();

        for (String[] row : data) {
            int amount = Integer.parseInt(row[1]);
            int alertLine = Integer.parseInt(row[2]);

            if (amount > alertLine) continue;

            String choice = getter.inputString("Request for " + row[0] + " (current amount: " + row[1] + ")? (y/n)");
            while (!choice.equals("y") && !choice.equals("n")) {
                System.out.println("Invalid input.");
                choice = getter.inputString("Request for " + row[0] + " (current amount: " + row[1] + ")? (y/n)");
            }
            if (choice.equals("y")) ret.add(row[0]);
        }
        return ret;
    }

    /**
     * Rejects the replenishment request and updates its status to REJECTED.
     *
     * Displays an error message if the request is not in the PENDING state.
     */
    public void rejectRequest() {
        if (status != Status.PENDING) {
            System.out.println("Cannot reject a non-pending request!");
            return;
        }
        status = Status.REJECTED;
        try {
            replenishmentCsvHandler.setField(0, Integer.toString(requestID), 2, "REJECTED");
        } catch (IOException e) {
            System.out.println("Error occurred when rejecting request.");
        }
    }

    /**
     * Approves the replenishment request and updates its status to APPROVED.
     *
     * Updates inventory stock levels for all medicines in the request.
     * Displays an error message if the request is not in the PENDING state.
     */
    public void approveRequest() {
        if (status != Status.PENDING) {
            System.out.println("Cannot approve a non-pending request!");
            return;
        }
        status = Status.APPROVED;
        try {
            replenishmentCsvHandler.setField(0, Integer.toString(requestID), 2, "APPROVED");
        } catch (IOException e) {
            System.out.println("Error occurred when approving request.");
        }

        InventoryController inventoryController = InventoryController.getInstance();
        inventoryController.editMedicationList(medicineNames);
    }

    /**
     * Displays the details of the replenishment request, including the request ID,
     * list of medicines, and current status.
     */
    public void viewRequest() {
        System.out.printf("Request ID: %d\n", requestID);
        System.out.println("Medicine Requested:");
        for (String medicineName : medicineNames) {
            System.out.println(medicineName);
        }
        System.out.println("Status: " + status);
    }

    /**
     * Retrieves the number of medicines included in the replenishment request.
     *
     * @return The size of the medicine list.
     */
    public int getMedicineSize() {
        return medicineNames.size();
    }
}
