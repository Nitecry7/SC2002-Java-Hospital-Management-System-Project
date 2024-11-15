package hmsystem.io;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IOHandler {

    // Method to read CSV data into memory
    Map<String, String[]> readCsv();

    // Method to update CSV data in memory and save it back to the file
    void updateCsv(Map<String, String[]> newData) throws IOException;

    // Method to get rows based on column name and row value
    public List<String[]> getRows(int columnToSearch, String valueToFind);

    // Method to get a specific field based on column name and row value
    String getField(int columnToSearch, String valueToFindRow, int columnToGet);

    // Method to set a specific field value based on column name and row value
    void setField(int columnToSearch, String valueToFindRow, int columnToChange, String newValue)
            throws IOException;

    // Method to add a new patient record to the CSV
    void addPatient(String[] patientDetails) throws IOException;

    // Method to add a new staff record to the CSV
    void addStaff(String[] staffDetails) throws IOException;
}
