package hmsystem.csvhandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CsvFileHandlerInventory {
    private final String csvFilePath;
    private final Map<String, String[]> inventoryData;
    private final Map<String, Integer> columnIndex;

    public CsvFileHandlerInventory(String csvFilePath) throws IOException {
        this.csvFilePath = csvFilePath;
        this.inventoryData = new HashMap<>();
        this.columnIndex = new LinkedHashMap<>();
        loadCsv();
    }

    private void loadCsv() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split(",");
                for (int i = 0; i < headers.length; i++) {
                    columnIndex.put(headers[i].trim(), i);
                }

                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    inventoryData.put(values[0].toLowerCase(), values); // Store by lowercased medicine name
                }
            }
        }
    }

    private void saveCsv() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath))) {
            bw.write(String.join(",", columnIndex.keySet()) + "\n");
            for (String[] values : inventoryData.values()) {
                bw.write(String.join(",", values) + "\n");
            }
        }
    }

    public int getColumnIndex(String columnName) {
        return columnIndex.getOrDefault(columnName, -1); // Returns -1 if no column found
    }

    public void addOrUpdateMedicine(String medicineName, String initialStock, String lowStockAlert) throws IOException {
        String lowerCaseName = medicineName.toLowerCase();
        if (inventoryData.containsKey(lowerCaseName)) {
            // Update existing medicine stock and alert levels
            inventoryData.get(lowerCaseName)[columnIndex.get("Initial Stock")] = initialStock;
            inventoryData.get(lowerCaseName)[columnIndex.get("Low Stock Level Alert")] = lowStockAlert;
        } else {
            // Add new medicine
            String[] newMedicine = new String[columnIndex.size()];
            newMedicine[columnIndex.get("Medicine Name")] = medicineName;
            newMedicine[columnIndex.get("Initial Stock")] = initialStock;
            newMedicine[columnIndex.get("Low Stock Level Alert")] = lowStockAlert;
            inventoryData.put(lowerCaseName, newMedicine);
        }
        saveCsv();
    }

    // Get Initial Stock
    public String getInitialStock(String medicineName) {
        String lowerCaseName = medicineName.toLowerCase();
        return inventoryData.containsKey(lowerCaseName)
                ? inventoryData.get(lowerCaseName)[columnIndex.get("Initial Stock")]
                : null;
    }
    // Get Alert Level
    public String getLowStockLevelAlert(String medicineName) {
        String lowerCaseName = medicineName.toLowerCase();
        return inventoryData.containsKey(lowerCaseName)
                ? inventoryData.get(lowerCaseName)[columnIndex.get("Low Stock Level Alert")]
                : null;
    }

    public void setInitialStock(String medicineName, String initialStock) throws IOException {
        String lowerCaseName = medicineName.toLowerCase();
        if (inventoryData.containsKey(lowerCaseName)) {
            inventoryData.get(lowerCaseName)[columnIndex.get("Initial Stock")] = initialStock;
            saveCsv();
        }
    }

    public void setLowStockLevelAlert(String medicineName, String lowStockAlert) throws IOException {
        String lowerCaseName = medicineName.toLowerCase();
        if (inventoryData.containsKey(lowerCaseName)) {
            inventoryData.get(lowerCaseName)[columnIndex.get("Low Stock Level Alert")] = lowStockAlert;
            saveCsv();
        }
    }

    public Map<String, String[]> getAllInfo() {
        return inventoryData;
    }
}
