package hmsystem.controllers;


import java.util.ArrayList;
// singleton class

import hmsystem.data.Consts;
import hmsystem.io.CsvHandler;

public class InventoryController {
    private static InventoryController inventoryController = null;

    
    protected InventoryController() {
        System.out.println("Constructed one inventory controller");
    }

    public static InventoryController getInstance(InventoryController inventoryController) {
       if (inventoryController != null) {
            inventoryController = new InventoryController();
        }
            return inventoryController;
        
    }
    

    // methods

    public void viewMedicationInventory()
    {
        // initialise csv handler
        CsvHandler csvhandler = new CsvHandler(Consts.Medicine.FILE_NAME);


        // initialise ArrayList of strings to store medications
        ArrayList<String> medications = new ArrayList<String>();

        // iterate through medication csv to find all the medication names somehow, store in list
        
        
    }





}