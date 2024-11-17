
import java.io.*;
import java.util.*;

public class CsvHandler implements IOHandler {

    public Map<String, String[]> data; // Stores data rows, keyed by staff ID
    private String filePath; // Path to the CSV file
    private String[] headers; // CSV headers

    // Constructor to initialize the IOHandler with the staff CSV file
    public CsvHandler(String filePath) throws IOException {
        this.filePath = filePath;
        loadCsv(); // Load data from the CSV file into memory
    }

    // Load the CSV into memory
    private void loadCsv() throws IOException {
        data = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read and parse headers
            String headerLine = reader.readLine();
            headers = headerLine.split(",");

            // Read and parse data rows
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                
                if( values == null) {
                    System.out.println("values is null");
                }
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

    // Get Headers
    public String[] getHeaders() {
        return headers; // This assumes headers are loaded in the CsvHandler when the CSV is read
    }

    // Read CSV
    public Map<String, String[]> readCsv() {
        try{
        loadCsv();
        }catch(IOException e){
        }
        return new HashMap<>(data); // Return a copy of the data to prevent external modification
    }

    // Read CSV values (rows only, without headers)
    public Collection<String[]> readCsvValues() {
        try{
        loadCsv();
        }catch(IOException e){
        }
        return new ArrayList<>(data.values()); // Return only the rows (values), excluding headers
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

    // Get all rows where a given column matches a specified value
    public List<String[]> getRows(int columnToSearch, String valueToFind) {

        try{
        loadCsv();
        }catch(IOException e){
        }
        List<String[]> matchingRows = new ArrayList<>();

        // Search through the rows to find the matching value in the specified column
        for (String[] row : data.values()) {
            if (row[columnToSearch].equals(valueToFind)) {
                matchingRows.add(row);
            }
        }

        return matchingRows;
    }

    // Add a new row (no ID to automate)
    public void addRow(String[] rowDetails) throws IOException {
        if (rowDetails.length != headers.length) {
            throw new IllegalArgumentException("Row details must match the number of columns in the CSV.");
        }

        // Add the new row to the data map. The first column is assumed to be the unique
        // key.
        String uniqueKey = rowDetails[0];
        if (data.containsKey(uniqueKey)) {
            throw new IllegalArgumentException("A row with the given key already exists: " + uniqueKey);
        }

        data.put(uniqueKey, rowDetails);
        saveCsv();
    }

    // Update a row where a given column matches a specified value
    public void updateRow(int columnToSearch, String valueToFind, String[] rowData) throws IOException {
        boolean found = false;

        // Iterate over all rows to find the matching row by column value
        for (Map.Entry<String, String[]> entry : data.entrySet()) {
            String[] row = entry.getValue();

            // Check if the specified column matches the valueToFind
            if (row[columnToSearch].equals(valueToFind)) {
                // If the row is found, replace the old row with the new data
                data.put(entry.getKey(), rowData);
                found = true;
                break;
            }
        }

        // If no matching row was found, throw an exception
        if (!found) {
            throw new IllegalArgumentException(
                    "No row found with " + headers[columnToSearch] + "='" + valueToFind + "'");
        }

        // After updating the row, save the changes back to the CSV file
        saveCsv();
    }

    // Remove rows where a given column matches a specified value
    public void removeRows(int columnToSearch, String valueToFind) throws IOException {
        Iterator<Map.Entry<String, String[]>> iterator = data.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String[]> entry = iterator.next();
            String[] row = entry.getValue();
            if (row[columnToSearch].equals(valueToFind)) {
                iterator.remove();
            }
        }
        saveCsv();
    }

    // Get a field value for a given column index (instead of column name), value to
    // search for, and column to get
    public String getField(int columnToSearch, String valueToFindRow, int columnToGet) {

        try{
            loadCsv();
        }catch(IOException e){
            }
        // Search through the rows to find the matching value
        for (String[] row : data.values()) {
            if (row[columnToSearch].equals(valueToFindRow)) {
                return row[columnToGet]; // Return the value in the specified column
            }
        }

        throw new IllegalArgumentException(
                "No row found with column index " + columnToSearch + "='" + valueToFindRow + "'");
    }

    // Set a field value for a given column index to search for, value to find,
    // column to change, and new value
    public void setField(int columnToSearch, String valueToFindRow, int columnToChange, String newValue)
            throws IOException {
        // Search through the rows to find the matching value
        for (Map.Entry<String, String[]> entry : data.entrySet()) {
            String[] row = entry.getValue();
            if (row[columnToSearch].equals(valueToFindRow)) {
                row[columnToChange] = newValue; // Update the field value
                saveCsv(); // Save changes back to the CSV
                return;
            }
        }

        throw new IllegalArgumentException(
                "No row found with column index " + columnToSearch + "='" + valueToFindRow + "'");
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
