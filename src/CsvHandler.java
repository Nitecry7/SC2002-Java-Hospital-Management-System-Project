import java.io.*;
import java.util.*;

/**
 * CsvHandler provides methods to handle CSV file operations, including reading,
 * writing, updating, and managing data in memory.
 */
public class CsvHandler implements IOHandler {

    /**
     * Stores data rows, keyed by a unique ID.
     */
    public Map<String, String[]> data;

    /**
     * Path to the CSV file.
     */
    private String filePath;

    /**
     * CSV headers.
     */
    private String[] headers;

    /**
     * Constructor to initialize the IOHandler with the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @throws IOException If an error occurs while loading the CSV.
     */
    public CsvHandler(String filePath) throws IOException {
        this.filePath = filePath;
        loadCsv();
    }

    /**
     * Loads the CSV file into memory.
     *
     * @throws IOException If an error occurs while reading the file.
     */
    private void loadCsv() throws IOException {
        data = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            headers = reader.readLine().split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values != null) {
                    data.put(values[0], values);
                }
            }
        }
    }

    /**
     * Saves the in-memory data back to the CSV file.
     *
     * @throws IOException If an error occurs while writing to the file.
     */
    private void saveCsv() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.join(",", headers));
            writer.newLine();

            for (String[] row : data.values()) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }

    /**
     * Gets the headers of the CSV file.
     *
     * @return An array of headers.
     */
    public String[] getHeaders() {
        return headers;
    }

    /**
     * Reads the entire CSV file into a map.
     *
     * @return A copy of the data map.
     */
    public Map<String, String[]> readCsv() {
        try {
            loadCsv();
        } catch (IOException e) {
            // Handle the exception if needed
        }
        return new HashMap<>(data);
    }

    /**
     * Reads only the values (rows) from the CSV file.
     *
     * @return A collection of data rows.
     */
    public Collection<String[]> readCsvValues() {
        try {
            loadCsv();
        } catch (IOException e) {
            // Handle the exception if needed
        }
        return new ArrayList<>(data.values());
    }

    /**
     * Updates the entire CSV data.
     *
     * @param newData The new data to replace the existing data.
     * @throws IOException If an error occurs while saving the file.
     */
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

    /**
     * Retrieves rows where a given column matches a specified value.
     *
     * @param columnToSearch The column index to search.
     * @param valueToFind The value to search for.
     * @return A list of matching rows.
     */
    public List<String[]> getRows(int columnToSearch, String valueToFind) {
        try {
            loadCsv();
        } catch (IOException e) {
            // Handle the exception if needed
        }
        List<String[]> matchingRows = new ArrayList<>();

        for (String[] row : data.values()) {
            if (row[columnToSearch].equals(valueToFind)) {
                matchingRows.add(row);
            }
        }

        return matchingRows;
    }

    /**
     * Adds a new row to the CSV file.
     *
     * @param rowDetails The details of the new row.
     * @throws IOException If an error occurs while saving the file.
     */
    public void addRow(String[] rowDetails) throws IOException {
        if (rowDetails.length != headers.length) {
            throw new IllegalArgumentException("Row details must match the number of columns in the CSV.");
        }

        String uniqueKey = rowDetails[0];
        if (data.containsKey(uniqueKey)) {
            throw new IllegalArgumentException("A row with the given key already exists: " + uniqueKey);
        }

        data.put(uniqueKey, rowDetails);
        saveCsv();
    }

    /**
     * Updates a row where a specific column matches a value.
     *
     * @param columnToSearch The column index to search.
     * @param valueToFind The value to match.
     * @param rowData The new row data.
     * @throws IOException If an error occurs while saving the file.
     */
    public void updateRow(int columnToSearch, String valueToFind, String[] rowData) throws IOException {
        boolean found = false;

        for (Map.Entry<String, String[]> entry : data.entrySet()) {
            String[] row = entry.getValue();

            if (row[columnToSearch].equals(valueToFind)) {
                data.put(entry.getKey(), rowData);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("No row found with value: " + valueToFind);
        }

        saveCsv();
    }

    /**
     * Removes rows where a specific column matches a value.
     *
     * @param columnToSearch The column index to search.
     * @param valueToFind The value to match.
     * @throws IOException If an error occurs while saving the file.
     */
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

    /**
     * Retrieves a specific field value from a matching row.
     *
     * @param columnToSearch The column index to search.
     * @param valueToFindRow The value to match.
     * @param columnToGet The column index of the field to retrieve.
     * @return The value in the specified field.
     */
    public String getField(int columnToSearch, String valueToFindRow, int columnToGet) {
        try {
            loadCsv();
        } catch (IOException e) {
            // Handle the exception if needed
        }
        for (String[] row : data.values()) {
            if (row[columnToSearch].equals(valueToFindRow)) {
                return row[columnToGet];
            }
        }

        throw new IllegalArgumentException("No row found with value: " + valueToFindRow);
    }

    /**
     * Updates a specific field value in a matching row.
     *
     * @param columnToSearch The column index to search.
     * @param valueToFindRow The value to match.
     * @param columnToChange The column index of the field to update.
     * @param newValue The new value to set.
     * @throws IOException If an error occurs while saving the file.
     */
    public void setField(int columnToSearch, String valueToFindRow, int columnToChange, String newValue)
            throws IOException {
        for (Map.Entry<String, String[]> entry : data.entrySet()) {
            String[] row = entry.getValue();
            if (row[columnToSearch].equals(valueToFindRow)) {
                row[columnToChange] = newValue;
                saveCsv();
                return;
            }
        }

        throw new IllegalArgumentException("No row found with value: " + valueToFindRow);
    }

    /**
     * Generates the next available unique ID based on a prefix.
     *
     * @param existingIds The set of existing IDs.
     * @param prefix The prefix for the new ID.
     * @return The generated unique ID.
     */
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

    /**
     * Adds a new patient to the CSV file.
     *
     * @param patientDetails The details of the new patient.
     * @throws IOException If an error occurs while saving the file.
     */
    public void addPatient(String[] patientDetails) throws IOException {
        String nextPatientID = getNextId(data.keySet(), "P");
        patientDetails[0] = nextPatientID;

        data.put(nextPatientID, patientDetails);
        saveCsv();
    }

    /**
     * Adds a new staff member to the CSV file.
     *
     * @param staffDetails The details of the new staff member.
     * @throws IOException If an error occurs while saving the file.
     */
    public void addStaff(String[] staffDetails) throws IOException {
        String nextStaffID = getNextId(data.keySet(), staffDetails[2].substring(0, 1));
        staffDetails[0] = nextStaffID;

        data.put(nextStaffID, staffDetails);
        saveCsv();
    }
}
