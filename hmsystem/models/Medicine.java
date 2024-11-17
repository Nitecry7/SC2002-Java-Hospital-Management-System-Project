package hmsystem.models;

import hmsystem.data.Consts;
import hmsystem.io.CsvHandler;
import hmsystem.io.IOHandler;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Medicine {
    private String name;
    private int alertLine, stock;
    private IOHandler handler;

    // Static final file path for the CSV file
    private static final String FILE_PATH = "hmsystem/data/Medicine_List.csv";
    private static CsvHandler csvHandler;

    // Constructor
    public Medicine() {
        try {
            // Initialize CsvHandler with the file path
            if (csvHandler == null) {
                csvHandler = new CsvHandler(FILE_PATH);
            }
        } catch (IOException e) {
            System.err.println("Error initializing CSV handler: " + e.getMessage());
        }
        build();
    }

    private Medicine(String[] medicineDetails, IOHandler handler) {
        this.handler = handler;
        this.name = medicineDetails[Consts.Medicine.NAME_COLUMN];
        this.alertLine = Integer.parseInt(medicineDetails[Consts.Medicine.ALERT_COLUMN]);
        this.stock = Integer.parseInt(medicineDetails[Consts.Medicine.STOCK_COLUMN]);
    }

    public static Medicine getMedicine(String medicineName, IOHandler handler) {
       
       List<String[]> medicineDetails =  handler.getRows(Consts.Medicine.NAME_COLUMN, medicineName);
         if (medicineDetails.isEmpty()) {
            return null;
        }
        else {
            return new Medicine(medicineDetails.get(0), handler);
        }
    }

    // Method to build a new Medicine entry
    private void build() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter medicine name: ");
        this.name = sc.nextLine();

        this.alertLine = inputInt(sc, "Enter alert line: ");
        this.stock = inputInt(sc, "Enter stock: ");

        // Prepare the row details for the new medicine
        String[] rowDetails = { name, String.valueOf(stock), String.valueOf(alertLine) };

        try {
            // Add the new row details to the CSV file
            csvHandler.addRow(rowDetails);
        } catch (IOException e) {
            System.err.println("Error adding medicine to CSV: " + e.getMessage());
        }

        sc.close();
    }

    // Helper method to input integer values
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

    public void saveData() throws Exception {
        
    }

    // Getters and Setters for the medicine properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlertLine() {
        return alertLine;
    }

    public void setAlertLine(int alertLine) {
        this.alertLine = alertLine;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Method to reduce stock and return success/failure
    public boolean reduceStock(int amount) {
        if (stock >= amount) {
            System.out.printf("Successfully reduced %d amount of %s\n", amount, name);
            return true;
        }
        System.out.printf("Error! %s not enough!\n", name);
        return false;
    }
}
