/**
 * Interface representing an IOHandler for CSV file operations.
 * Provides methods for reading, writing, and updating CSV data.
 */
public interface IOHandler {

    /**
     * Retrieves the headers of the CSV file.
     *
     * @return An array of strings representing the headers of the CSV file.
     */
    public String[] getHeaders();

    /**
     * Reads the CSV file and loads its data into memory.
     *
     * @return A map where the key is the unique identifier and the value is an array of row data.
     */
    Map<String, String[]> readCsv();

    /**
     * Updates the in-memory CSV data and saves it back to the file.
     *
     * @param newData A map containing the updated data to save.
     * @throws IOException If an I/O error occurs while saving the file.
     */
    void updateCsv(Map<String, String[]> newData) throws IOException;

    /**
     * Retrieves rows from the CSV where a specific column matches a given value.
     *
     * @param columnToSearch The index of the column to search.
     * @param valueToFind    The value to search for in the specified column.
     * @return A list of rows matching the specified value in the given column.
     */
    List<String[]> getRows(int columnToSearch, String valueToFind);

    /**
     * Retrieves a specific field value from the CSV.
     *
     * @param columnToSearch The index of the column to search.
     * @param valueToFindRow The value to identify the row.
     * @param columnToGet    The index of the column to retrieve the value from.
     * @return The value in the specified column for the matching row.
     */
    String getField(int columnToSearch, String valueToFindRow, int columnToGet);

    /**
     * Sets a specific field value in the CSV.
     *
     * @param columnToSearch The index of the column to search.
     * @param valueToFindRow The value to identify the row.
     * @param columnToChange The index of the column to update.
     * @param newValue       The new value to set.
     * @throws IOException If an I/O error occurs while saving the updated file.
     */
    void setField(int columnToSearch, String valueToFindRow, int columnToChange, String newValue)
            throws IOException;

    /**
     * Adds a new patient record to the CSV.
     *
     * @param patientDetails An array of strings representing the patient's details.
     * @throws IOException If an I/O error occurs while adding the new record.
     */
    void addPatient(String[] patientDetails) throws IOException;

    /**
     * Adds a new staff record to the CSV.
     *
     * @param staffDetails An array of strings representing the staff's details.
     * @throws IOException If an I/O error occurs while adding the new record.
     */
    void addStaff(String[] staffDetails) throws IOException;

    /**
     * Reads the values of the CSV file excluding headers.
     *
     * @return A collection of arrays where each array represents a row in the CSV file.
     */
    Collection<String[]> readCsvValues();

    /**
     * Adds a new row to the CSV file.
     *
     * @param rowDetails An array of strings representing the row details.
     * @throws IOException If an I/O error occurs while adding the row.
     */
    void addRow(String[] rowDetails) throws IOException;

    /**
     * Updates a row in the CSV where a specific column matches a given value.
     *
     * @param columnToSearch The index of the column to search.
     * @param valueToFind    The value to identify the row.
     * @param rowData        An array of strings representing the updated row data.
     * @throws IOException If an I/O error occurs while updating the row.
     */
    void updateRow(int columnToSearch, String valueToFind, String[] rowData) throws IOException;

    /**
     * Removes rows from the CSV where a specific column matches a given value.
     *
     * @param columnToSearch The index of the column to search.
     * @param valueToFind    The value to identify rows to remove.
     * @throws IOException If an I/O error occurs while removing the rows.
     */
    void removeRows(int columnToSearch, String valueToFind) throws IOException;
}
