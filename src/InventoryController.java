/**
 * Singleton class that manages the medication inventory.
 * Handles operations such as adding, editing, viewing, and deleting medications.
 */
public class InventoryController {

    private static InventoryController inventoryController = null;
    private static IOHandler csvhandler;

    /**
     * Protected constructor to initialize the CSV handler for inventory management.
     */
    protected InventoryController() {
        try {
            InventoryController.csvhandler = new CsvHandler(Consts.Medicine.FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    /**
     * Provides a single instance of InventoryController.
     *
     * @return The singleton instance of InventoryController.
     */
    public static InventoryController getInstance() {
        if (inventoryController == null) {
            inventoryController = new InventoryController();
        }
        return inventoryController;
    }

    /**
     * Reduces the stock of a specified medication.
     *
     * @param medicineName The name of the medication.
     * @param amount       The amount to reduce from the stock.
     * @return {@code true} if the stock was successfully reduced; {@code false} otherwise.
     */
    public boolean reduceStock(String medicineName, int amount) {
        Medicine medicine = Medicine.getMedicine(medicineName, csvhandler);
        if (medicine == null) {
            System.out.println("Medicine Not Found.");
            return false;
        }
        return medicine.reduceStock(amount);
    }

    /**
     * Adds a new medication to the inventory.
     *
     * @return 1 if the medication was successfully added; 0 otherwise.
     */
    public int addMedication() {
        AttributeController getter = AttributeController.getInstance();
        String name = getter.inputString("Enter new medication name: ");
        int quantityNum = getter.inputInt("Enter new medication quantity: ");
        String quantity = Integer.toString(quantityNum);
        int lowAlertNum = getter.inputInt("Enter new medication low alert value: ");
        String lowAlert = Integer.toString(lowAlertNum);
        String[] temp = {name, quantity, lowAlert};

        try {
            csvhandler.addRow(temp);
        } catch (IOException e) {
            System.out.println("Error occurred during adding medication!");
            return 0;
        }
        return 1;
    }

    /**
     * Displays the current inventory of medications along with their quantities and alerts.
     */
    public void viewMedicationInventory() {
        Collection<String[]> rows = csvhandler.readCsvValues();

        ArrayList<String> medications = new ArrayList<>();
        ArrayList<String> quantities = new ArrayList<>();
        ArrayList<String> topup = new ArrayList<>();

        for (String[] row : rows) {
            if (row.length >= 3) {
                medications.add(row[0]);
                quantities.add(row[1]);
                topup.add(row[2]);
            }
        }

        System.out.println("Medication Inventory:");
        for (int i = 0; i < medications.size(); i++) {
            System.out.println(medications.get(i) + " : " + quantities.get(i) + ".");
            if (Integer.parseInt(quantities.get(i)) <= Integer.parseInt(topup.get(i))) {
                System.out.printf("Alert! %s amount under %s.\n", medications.get(i), topup.get(i));
            }
        }
    }

    /**
     * Edits the stock of a specified medication by adding a top-up amount.
     *
     * @param Medication The name of the medication to be edited.
     * @return 1 if the medication stock was successfully updated; 0 otherwise.
     */
    public int editMedication(String Medication) {
        AttributeController getter = AttributeController.getInstance();
        int change = getter.inputInt("Enter the top-up amount of " + Medication + ": ");
        String oldValue = csvhandler.getField(0, Medication, 1);
        int newValue = Integer.parseInt(oldValue) + change;

        try {
            csvhandler.setField(0, Medication, 1, String.valueOf(newValue));
        } catch (IOException e) {
            System.out.println("Error occurred during setting value!");
            return 0;
        }
        return 1;
    }

    /**
     * Edits the stock of multiple medications by adding a top-up amount to each.
     *
     * @param Medications A list of medication names to be edited.
     * @return 1 if all medications were successfully updated; 0 otherwise.
     */
    public int editMedicationList(List<String> Medications) {
        int x = 1;
        for (String medication : Medications) {
            x &= editMedication(medication);
        }
        return x;
    }

    /**
     * Deletes a medication from the inventory.
     *
     * @return 1 if the medication was successfully deleted; 0 otherwise.
     */
    public int deleteMedication() {
        AttributeController getter = AttributeController.getInstance();
        String name = getter.inputString("Enter medication name to delete: ");
        try {
            csvhandler.removeRows(0, name);
        } catch (IOException e) {
            System.out.println("Error occurred during deleting medication!");
            return 0;
        }
        return 1;
    }
}
