package hmsystem.testers;

import java.io.IOException;
import java.util.Map;

import hmsystem.csvhandlers.CsvFileHandlerInventory;

public class CsvFileHandlerInventoryTest {
    public static void main(String[] args) {
        // Define the CSV file path
        String csvFilePath = "hmsystem\\data\\inventory.csv";

        try {
            // Create an instance of CsvFileHandlerInventory
            CsvFileHandlerInventory csvFileHandler = new CsvFileHandlerInventory(csvFilePath);

            // Add or update a medicine (test add and update functionality)
            System.out.println("Adding new medicine 'Aspirin'...");
            csvFileHandler.addOrUpdateMedicine("Aspirin", "200", "50");
            System.out.println("Aspirin added.");

            System.out.println("Updating existing medicine 'Paracetamol'...");
            csvFileHandler.addOrUpdateMedicine("Paracetamol", "150", "25");
            System.out.println("Paracetamol updated.");

            // Get initial stock of a medicine
            System.out.println("\nGetting initial stock for 'Paracetamol':");
            String initialStock = csvFileHandler.getInitialStock("Paracetamol");
            System.out.println("Initial Stock of Paracetamol: " + initialStock);

            // Get low stock level alert of a medicine
            System.out.println("\nGetting low stock level alert for 'Ibuprofen':");
            String lowStockAlert = csvFileHandler.getLowStockLevelAlert("Ibuprofen");
            System.out.println("Low Stock Level Alert of Ibuprofen: " + lowStockAlert);

            // Set new initial stock for a medicine
            System.out.println("\nSetting new initial stock for 'Ibuprofen' to 120...");
            csvFileHandler.setInitialStock("Ibuprofen", "120");
            System.out.println("New Initial Stock of Ibuprofen: " + csvFileHandler.getInitialStock("Ibuprofen"));

            // Set new low stock level alert for a medicine
            System.out.println("\nSetting new low stock level alert for 'Amoxicillin' to 20...");
            csvFileHandler.setLowStockLevelAlert("Amoxicillin", "20");
            System.out.println(
                    "New Low Stock Level Alert of Amoxicillin: " + csvFileHandler.getLowStockLevelAlert("Amoxicillin"));

            // Get all inventory information
            System.out.println("\nGetting all inventory information:");
            Map<String, String[]> allInventory = csvFileHandler.getAllInfo();
            for (Map.Entry<String, String[]> entry : allInventory.entrySet()) {
                System.out.println("Medicine: " + entry.getKey());
                System.out.println(
                        "  Initial Stock: " + entry.getValue()[csvFileHandler.getColumnIndex("Initial Stock")]);
                System.out.println("  Low Stock Level Alert: "
                        + entry.getValue()[csvFileHandler.getColumnIndex("Low Stock Level Alert")]);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while processing the CSV file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
