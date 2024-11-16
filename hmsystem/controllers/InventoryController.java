package hmsystem.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import hmsystem.data.Consts;
import hmsystem.io.CsvHandler;
import hmsystem.io.IOHandler;
import java.util.Scanner;  


// Singleton Class
public class InventoryController {
    private static InventoryController inventoryController = null;
    private static IOHandler csvhandler;

    
    protected InventoryController() {
        System.out.println("Constructed one inventory controller");
        try {
            this.csvhandler = new CsvHandler(Consts.Medicine.FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }

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
    
    }

    public int editMedication(String Medication)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter change in medication");
        int change = sc.nextInt();
        String oldValue = csvhandler.getField(0, Medication, 1);
        int newValue = Integer.parseInt(oldValue) + change;

        //
        try{
            csvhandler.setField(0, Medication, 1, String.valueOf(newValue));
        }catch (IOException e){
            System.out.println("Error occured during setting value!");
            return 0;
        }
        return 1;
    }





}