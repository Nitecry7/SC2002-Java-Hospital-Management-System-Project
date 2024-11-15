package hmsystem.csvhandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CsvFileHandlerPatient {
    private final String csvFilePath;
    private final Map<String, String[]> patientData;
    private final Map<String, Integer> columnIndex;

    public CsvFileHandlerPatient(String csvFilePath) throws IOException {
        this.csvFilePath = csvFilePath;
        this.patientData = new HashMap<>();
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
                
                System.out.println("Headers found: " + columnIndex.keySet());
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    patientData.put(values[0], values);
                }
            }
        }
    }

    private void saveCsv() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath))) {
            bw.write(String.join(",", columnIndex.keySet()) + "\n");
            for (String[] values : patientData.values()) {
                bw.write(String.join(",", values) + "\n");
            }
        }
    }

    private String getField(String patientId, String columnName) {
        Integer column = columnIndex.get(columnName);
        if (column == null || !patientData.containsKey(patientId)) {
            return null;
        }
        return patientData.get(patientId)[column];
    }

    private void setField(String patientId, String columnName, String value) throws IOException {
        Integer column = columnIndex.get(columnName);
        if (column != null && patientData.containsKey(patientId)) {
            patientData.get(patientId)[column] = value;
            saveCsv();
        }
    }

    public String getCsvPatientName(String patientId) throws IOException {
        return getField(patientId, "Name");
    }

    public String getCsvPatientDateOfBirth(String patientId) throws IOException {
        return getField(patientId, "Date of Birth");
    }

    public String getCsvPatientGender(String patientId) throws IOException {
        return getField(patientId, "Gender");
    }

    public String getCsvPatientBloodType(String patientId) throws IOException {
        return getField(patientId, "Blood Type");
    }

    public String getCsvPatientEmail(String patientId) throws IOException {
        return getField(patientId, "Email");
    }

    public void setCsvPatientName(String patientId, String name) throws IOException {
        setField(patientId, "Name", name);
    }

    public void setCsvPatientDateOfBirth(String patientId, String dateOfBirth) throws IOException {
        setField(patientId, "Date of Birth", dateOfBirth);
    }

    public void setCsvPatientGender(String patientId, String gender) throws IOException {
        setField(patientId, "Gender", gender);
    }

    public void setCsvPatientBloodType(String patientId, String bloodType) throws IOException {
        setField(patientId, "Blood Type", bloodType);
    }

    public void setCsvPatientEmail(String patientId, String email) throws IOException {
        setField(patientId, "email", email);
    }

    public void addNewPatient(String name, String dateOfBirth, String gender, String bloodType, String email)
            throws IOException {
        String nextPatientId = generateNextPatientId();

        String[] newPatient = new String[columnIndex.size()];
        newPatient[columnIndex.get("Patient ID")] = nextPatientId;
        newPatient[columnIndex.get("Name")] = name;
        newPatient[columnIndex.get("Date of Birth")] = dateOfBirth;
        newPatient[columnIndex.get("Gender")] = gender;
        newPatient[columnIndex.get("Blood Type")] = bloodType;
        newPatient[columnIndex.get("Email")] = email;

        patientData.put(nextPatientId, newPatient);
        saveCsv();
    }

    private String generateNextPatientId() {
        int maxId = 0;

        for (String patientId : patientData.keySet()) {
            if (patientId.startsWith("P")) {
                try {
                    int idNumber = Integer.parseInt(patientId.substring(1));
                    if (idNumber > maxId) {
                        maxId = idNumber;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Patient ID format: " + patientId);
                }
            }
        }

        return "P" + String.format("%04d", maxId + 1);
    }

}
