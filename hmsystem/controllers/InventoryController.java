package hmsystem.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import hmsystem.data.Consts;
import hmsystem.io.CsvHandler;
import hmsystem.io.IOHandler;


// Singleton Class
public class InventoryController {
    private static InventoryController inventoryController = null;

    
    protected InventoryController() {
        System.out.println("Constructed one inventory controller");
    }

    public static InventoryController getInstance() {
       if (inventoryController != null) {
            inventoryController = new InventoryController();
        }
            return inventoryController;
        
    }
    

    // methods

    public void viewMedicationInventory()
    {
        // initialise csv handler
        try {
            // Initialize the CsvHandler with the file path
            IOHandler csvhandler = new CsvHandler(Consts.Medicine.FILE_NAME);
        
            // Read the CSV data
            Collection<String[]> rows = csvhandler.readCsvValues();
        
            // Initialise ArrayLists to store medications and their quantities
            ArrayList<String> medications = new ArrayList<>();
            ArrayList<String> quantities = new ArrayList<>();
            ArrayList<String> topup = new ArrayList<>();
        
            // Iterate through rows to extract medication names and quantities
            for (String[] row : rows) {
                if (row.length >= 3) { // Ensure the row has at least two columns
                    medications.add(row[0]); // First column: Medication name
                    quantities.add(row[1]);  // Second column: Quantity
                    topup.add(row[2]); // Third column: low alert
                }
            }
            
        
            // Print the medications and quantities
            System.out.println("Medication Inventory:");
            for (int i = 0; i < medications.size(); i++) {
                System.out.print(medications.get(i) + " : " + quantities.get(i));
                if (Integer.parseInt(quantities.get(i)) <= Integer.parseInt(topup.get(i)))
                {
                    System.out.println(" Alert: below " + topup.get(i));
                } 
                else
                {
                    System.out.println();
                }
            }
        
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }





}