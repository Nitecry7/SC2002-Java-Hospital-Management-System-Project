package hmsystem.io;

import java.io.*;
import java.util.*;

public class IOHandler {

    public Map<String, String[]> data; // Stores data rows, keyed by staff ID
    private Map<String, Integer> columnIndex; // Maps column names to indices
    private String filePath; // Path to the CSV file
    private String[] headers; // CSV headers

    // Constructor to initialize the IOHandler with the staff CSV file
    public IOHandler(String filePath) throws IOException {
        this.filePath = filePath;
        loadCsv(); // Load data from the CSV file into memory
    }

    // Load the CSV into memory
    private void loadCsv() throws IOException {
        data = new HashMap<>();
        columnIndex = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read and parse headers
            String headerLine = reader.readLine();
            headers = headerLine.split(",");
            for (int i = 0; i < headers.length; i++) {
                columnIndex.put(headers[i], i);
            }

            // Read and parse data rows
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                data.put(values[0], values); // Assuming the first column is the unique ID
            }
        }
    }

    // Save the in-memory data back to the CSV file
    private void saveCsv() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write headers
            writer.write(String.join(",", headers));
            writer.newLine();

            // Write data rows
            for (String[] row : data.values()) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }

    // Read CSV
    public Map<String, String[]> readCsv() {
        return new HashMap<>(data); // Return a copy of the data to prevent external modification
    }

    // Update CSV
    public void updateCsv(Map<String, String[]> newData) throws IOException {
        for (String key : newData.keySet()) {
            String[] values = newData.get(key);
            if (values.length != headers.length) {
                throw new IllegalArgumentException("Mismatch in column count for key: " + key);
            }
        }

        data.clear();
        data.putAll(newData);
        saveCsv();
    }

    // Get a field value for a given staff ID and column name
    public String getField(String staffId, String columnName) {
        Integer column = columnIndex.get(columnName); // Get column index
        if (column == null) {
            throw new IllegalArgumentException("Column name '" + columnName + "' not found.");
        }
        String[] row = data.get(staffId); // Get row by ID
        if (row == null) {
            throw new IllegalArgumentException("Staff ID '" + staffId + "' not found.");
        }
        return row[column]; // Return the requested field value
    }

    // Set a field value for a given staff ID and column name
    public void setField(String staffId, String columnName, String value) throws IOException {
        Integer column = columnIndex.get(columnName); // Get column index
        if (column == null) {
            throw new IllegalArgumentException("Column name '" + columnName + "' not found.");
        }
        String[] row = data.get(staffId); // Get row by ID
        if (row == null) {
            throw new IllegalArgumentException("Staff ID '" + staffId + "' not found.");
        }
        row[column] = value; // Update the field value
        saveCsv(); // Save changes back to the CSV
    }

    // Get next bigger ID
    private static String getNextId(Set<String> existingIds, String prefix) {
        int maxId = 0;

        for (String id : existingIds) {
            if (id.startsWith(prefix)) {
                try {
                    int idValue = Integer.parseInt(id.substring(prefix.length()));
                    maxId = Math.max(maxId, idValue);
                } catch (NumberFormatException e) {
                    // Ignore malformed IDs
                }
            }
        }

        return prefix + String.format("%04d", maxId + 1);
    }

    // Add a new patient
    public void addPatient(String[] patientDetails) throws IOException {
        // Generate the next Patient ID
        String nextPatientID = getNextId(data.keySet(), "P");
        patientDetails[0] = nextPatientID; // Set the new Patient ID

        data.put(nextPatientID, patientDetails);
        saveCsv();
    }

    // Add a new staff
    public void addStaff(String[] staffDetails) throws IOException {
        // Generate the next Staff ID
        String nextStaffID = getNextId(data.keySet(), staffDetails[2].substring(0, 1)); // Prefix based on Role
        staffDetails[0] = nextStaffID; // Set the new Staff ID

        data.put(nextStaffID, staffDetails);
        saveCsv();
    }
}
