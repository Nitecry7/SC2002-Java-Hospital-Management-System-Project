package hmsystem.csvhandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CsvFileHandlerStaff {
    private final String csvFilePath;
    private final Map<String, String[]> staffData;
    private final Map<String, Integer> columnIndex;

    public CsvFileHandlerStaff(String csvFilePath) throws IOException {
        this.csvFilePath = csvFilePath;
        this.staffData = new HashMap<>();
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
                    staffData.put(values[0], values);
                }
            }
        }
    }

    private void saveCsv() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath))) {
            bw.write(String.join(",", columnIndex.keySet()) + "\n");
            for (String[] values : staffData.values()) {
                bw.write(String.join(",", values) + "\n");
            }
        }
    }

    private String getField(String staffId, String columnName) {
        Integer column = columnIndex.get(columnName);
        if (column == null || !staffData.containsKey(staffId)) {
            return null;
        }
        return staffData.get(staffId)[column];
    }

    private void setField(String staffId, String columnName, String value) throws IOException {
        Integer column = columnIndex.get(columnName);
        if (column != null && staffData.containsKey(staffId)) {
            staffData.get(staffId)[column] = value;
            saveCsv();
        }
    }

    public String getCsvStaffName(String staffId) throws IOException {
        return getField(staffId, "Name");
    }

    public String getCsvStaffRole(String staffId) throws IOException {
        return getField(staffId, "Role");
    }

    public String getCsvStaffGender(String staffId) throws IOException {
        return getField(staffId, "Gender");
    }

    public String getCsvStaffAge(String staffId) throws IOException {
        return getField(staffId, "Age");
    }

    public void setCsvStaffName(String staffId, String name) throws IOException {
        setField(staffId, "Name", name);
    }

    public void setCsvStaffRole(String staffId, String role) throws IOException {
        setField(staffId, "Role", role);
    }

    public void setCsvStaffGender(String staffId, String gender) throws IOException {
        setField(staffId, "Gender", gender);
    }

    public void setCsvStaffAge(String staffId, String age) throws IOException {
        setField(staffId, "Age", age);
    }
    
    // Auto-generate next ID for respective staff
    private String generateNextId(String rolePrefix) {
        int maxId = 0;
        for (String id : staffData.keySet()) {
            if (id.startsWith(rolePrefix)) {
                int numericPart = Integer.parseInt(id.substring(1));
                if (numericPart > maxId) {
                    maxId = numericPart;
                }
            }
        }
        return rolePrefix + String.format("%03d", maxId + 1);
    }

    // General add staff
    private void addNewStaff(String staffId, String name, String role, String gender, String age) throws IOException {
        if (staffData.containsKey(staffId)) {
            throw new IllegalArgumentException("Staff ID already exists.");
        }

        String[] newStaff = new String[columnIndex.size()];
        if (columnIndex.get("Staff ID") == null) {
            throw new IllegalArgumentException("Column 'Staff ID' is missing.");
        }
        newStaff[columnIndex.get("Staff ID")] = staffId;
        newStaff[columnIndex.get("Name")] = name;
        newStaff[columnIndex.get("Role")] = role;
        newStaff[columnIndex.get("Gender")] = gender;
        newStaff[columnIndex.get("Age")] = age;

        staffData.put(staffId, newStaff);
        saveCsv();
    }

    // Add Doctor
    public void addDoctor(String name, String gender, String age) throws IOException {
        String staffId = generateNextId("D");
        addNewStaff(staffId, name, "Doctor", gender, age);
    }
    // Add Pharmacist
    public void addPharmacist(String name, String gender, String age) throws IOException {
        String staffId = generateNextId("P");
        addNewStaff(staffId, name, "Pharmacist", gender, age);
    }
    // Add Admin
    public void addAdmin(String name, String gender, String age) throws IOException {
        String staffId = generateNextId("A");
        addNewStaff(staffId, name, "Administrator", gender, age);
    }
}
