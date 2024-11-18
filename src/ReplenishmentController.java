import java.io.IOException;
import java.util.*;
/**
 * Controller class for managing replenishment requests. 
 * This class is responsible for submitting, viewing, approving, and rejecting 
 * replenishment requests for medicines. It interacts with the replenishment
 * and medicine CSV files to track and update replenishment data.
 */
public class ReplenishmentController {

    /**
     * Singleton instance of the ReplenishmentController.
     */
    public static final ReplenishmentController replenishmentController = new ReplenishmentController();

    /**
     * Retrieves the singleton instance of the ReplenishmentController.
     *
     * @return The singleton instance of the ReplenishmentController.
     */
    public static ReplenishmentController getInstance() {
        return replenishmentController;
    }

    /**
     * IOHandler for managing replenishment data.
     */
    private IOHandler replenishmentCsvHandler;

    /**
     * IOHandler for managing medicine data.
     */
    private IOHandler medicineCsvHandler;

    /**
     * AttributeController instance for managing user inputs.
     */
    private AttributeController getter = AttributeController.getInstance();

    /**
     * Constructor for initializing the ReplenishmentController.
     * Loads data handlers for replenishment and medicine CSV files.
     */
    ReplenishmentController() {
        try {
            this.replenishmentCsvHandler = new CsvHandler(Consts.Replenishment.FILE_NAME);
            this.medicineCsvHandler = new CsvHandler(Consts.Medicine.FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error occurred creating replenishment controller.");
        }
    }

    /**
     * Submits a replenishment request for medicines with low stock levels.
     * Prompts the user to select medicines for the request and stores the request
     * in the replenishment CSV file.
     */
    public void submitRequest() {
        Replenishment request = new Replenishment(medicineCsvHandler, replenishmentCsvHandler);
        if (request.getMedicineSize() != 0) {
            request.submitRequest();
        } else {
            System.out.println("Cannot request without medication selected!");
        }
    }

    /**
     * Displays all replenishment requests, including their IDs, requested medications,
     * and statuses, by reading from the replenishment CSV file.
     */
    public void viewAllRequest() {
        Collection<String[]> data = replenishmentCsvHandler.readCsvValues();
        for (String[] row : data) {
            System.out.println("RequestID: " + row[0]);
            System.out.println("Requested Medication: " + row[1]);
            System.out.println("Request Status: " + row[2]);
        }
    }

    /**
     * Displays all pending replenishment requests by reading from the replenishment CSV file.
     * Only requests with a status of "PENDING" are shown.
     */
    public void viewPendingRequest() {
        Collection<String[]> data = replenishmentCsvHandler.readCsvValues();
        for (String[] row : data) {
            if (row[2].equals("PENDING")) {
                System.out.println("RequestID: " + row[0]);
                System.out.println("Requested Medication: " + row[1]);
                System.out.println("Request Status: " + row[2]);
            }
        }
    }

    /**
     * Rejects a replenishment request based on its ID.
     * Updates the status of the request to "REJECTED" in the replenishment CSV file.
     *
     * Prompts the user to enter a valid request ID. If the request is found, its status
     * is updated to REJECTED.
     */
    public void rejectRequest() {
        String requestID = getter.inputString("Please input requestID to reject: ");
        List<String[]> rows = replenishmentCsvHandler.getRows(0, requestID);
        if (rows.size() == 0) {
            System.out.println("Request Not Found!");
            return;
        }
        String[] row = rows.get(0);
        int requestIDnum = Integer.valueOf(requestID);
        List<String> medicineNames = Arrays.asList(row[1].split(" "));
        Replenishment request = new Replenishment(medicineCsvHandler, replenishmentCsvHandler, requestIDnum, medicineNames, row[2]);
        request.rejectRequest();
    }

    /**
     * Approves a replenishment request based on its ID.
     * Updates the status of the request to "APPROVED" in the replenishment CSV file.
     *
     * Prompts the user to enter a valid request ID. If the request is found, its status
     * is updated to APPROVED, and the inventory is updated accordingly.
     */
    public void approveRequest() {
        String requestID = getter.inputString("Please input requestID to approve: ");
        List<String[]> rows = replenishmentCsvHandler.getRows(0, requestID);
        if (rows.size() == 0) {
            System.out.println("Request Not Found!");
            return;
        }
        String[] row = rows.get(0);
        int requestIDnum = Integer.valueOf(requestID);
        String[] arrMedicineNames = row[1].split(" ");
        List<String> medicineList = new ArrayList<>(Arrays.asList(arrMedicineNames));
        Replenishment request = new Replenishment(medicineCsvHandler, replenishmentCsvHandler, requestIDnum, medicineList, row[2]);
        request.approveRequest();
    }
}
