import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a medicine entry in the inventory.
 * Provides methods to manage the medicine details and interact with inventory data.
 */
public class Medicine {

    private String name;       // Name of the medicine
    private int alertLine;     // Threshold below which alerts are triggered
    private int stock;         // Current stock of the medicine
    private IOHandler csvHandler; // Handler for CSV operations

    /**
     * Constructs a new Medicine object and initializes it using user input.
     */
    public Medicine() {
        try {
            csvHandler = new CsvHandler(Consts.Medicine.FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error initializing CSV handler: " + e.getMessage());
        }
        build();
    }

    /**
     * Constructs a Medicine object with the specified details.
     *
     * @param medicineDetails The details of the medicine.
     * @param handler         The IOHandler for CSV operations.
     */
    private Medicine(String[] medicineDetails, IOHandler handler) {
        this.csvHandler = handler;
        this.name = medicineDetails[Consts.Medicine.NAME_COLUMN];
        this.alertLine = Integer.parseInt(medicineDetails[Consts.Medicine.ALERT_COLUMN]);
        this.stock = Integer.parseInt(medicineDetails[Consts.Medicine.STOCK_COLUMN]);
    }

    /**
     * Retrieves a Medicine object from the inventory by its name.
     *
     * @param medicineName The name of the medicine.
     * @param handler      The IOHandler for CSV operations.
     * @return The Medicine object, or null if not found.
     */
    public static Medicine getMedicine(String medicineName, IOHandler handler) {
        List<String[]> medicineDetails = handler.getRows(Consts.Medicine.NAME_COLUMN, medicineName);
        if (medicineDetails.isEmpty()) {
            return null;
        } else {
            return new Medicine(medicineDetails.get(0), handler);
        }
    }

    /**
     * Builds a new Medicine entry by collecting details from user input.
     */
    private void build() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter medicine name: ");
        this.name = sc.nextLine();

        this.alertLine = inputInt(sc, "Enter alert line: ");
        this.stock = inputInt(sc, "Enter stock: ");

        // Prepare the row details for the new medicine
        String[] rowDetails = { name, String.valueOf(stock), String.valueOf(alertLine) };

        try {
            csvHandler.addRow(rowDetails);
        } catch (IOException e) {
            System.err.println("Error adding medicine to CSV: " + e.getMessage());
        }

        sc.close();
    }

    /**
     * Helper method to input integer values from the user.
     *
     * @param sc      The Scanner object for reading input.
     * @param message The message to display to the user.
     * @return The integer value entered by the user.
     */
    private int inputInt(Scanner sc, String message) {
        int value = 0;
        while (true) {
            System.out.print(message);
            try {
                value = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next();
            }
        }
        return value;
    }

    /**
     * Updates the medicine data and persists it to the storage.
     *
     * @throws Exception If an error occurs during data saving.
     */
    public void saveData() throws Exception {
        String[] updatedRow = { name, String.valueOf(stock), String.valueOf(alertLine) };
        csvHandler.updateRow(Consts.Medicine.NAME_COLUMN, name, updatedRow);
    }

    
    /**
     * Getter for medicine name
     * 
     * @return name of medicine
     */
    public String getName() {
        return name;
    }
    /**
     * Setter for medicine name
     * @param name Name of medicine
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter for medicine alert line
     * 
     * @return alert line of medicine
     */
    public int getAlertLine() {
        return alertLine;
    }
    /**
     * Setter for medicine alert line
     * @param alertLine Alert line of medicine
     */
    public void setAlertLine(int alertLine) {
        this.alertLine = alertLine;
    }
    /**
     * Getter for medicine stock
     * 
     * @return stock of medicine
     */
    public int getStock() {
        return stock;
    }
    /**
     * Setter for medicine stock
     * @param stock Stock of medicine
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Reduces the stock of the medicine by the specified amount.
     *
     * @param amount The amount to reduce from the stock.
     * @return True if the stock was successfully reduced, false otherwise.
     */
    public boolean reduceStock(int amount) {
        if (stock >= amount) {
            try {
                stock -= amount;
                csvHandler.setField(Consts.Medicine.NAME_COLUMN, getName(), Consts.Medicine.STOCK_COLUMN, String.valueOf(stock));
                System.out.printf("Successfully reduced %d amount of %s\n", amount, name);
            } catch (IOException e) {
                System.out.println("Something went wrong.");
            }
            return true;
        }
        System.out.printf("Error! %s not enough!\n", name);
        return false;
    }
}
