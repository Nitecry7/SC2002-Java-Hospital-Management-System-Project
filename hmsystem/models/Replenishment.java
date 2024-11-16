package hmsystem.models;

import java.io.IOException;
import java.util.*;

import hmsystem.io.*;
import hmsystem.controllers.AttributeController;
import hmsystem.controllers.InventoryController;

public class Replenishment {
    private CsvHandler medicineCsvHandler, replenishmentCsvHandler;
    private AttributeController getter = AttributeController.getInstance();
    private enum Status{
        PENDING("PENDING"),
        APPROVED("APPROVED"),
        REJECTED("REJECTED");
        private String status;
        Status (String status){
            this.status = status;
        }
    }
    private int requestID;
    private List<String> medicineNames;
    private Status status = Status.PENDING;
    public Replenishment(CsvHandler medicineCsvHandler, CsvHandler replenishmentCsvHandler){
        medicineNames = getLowMedicine();
        this.medicineCsvHandler = medicineCsvHandler;
        this.replenishmentCsvHandler = replenishmentCsvHandler;
        Collection<String[]> data = replenishmentCsvHandler.readCsvValues();
        requestID = data.size();
    }
    public Replenishment(CsvHandler medicineCsvHandler, CsvHandler replenishmentCsvHandler,
                        int requestID, List<String> MedicineName, String status){
        medicineNames = getLowMedicine();
        this.medicineCsvHandler = medicineCsvHandler;
        this.replenishmentCsvHandler = replenishmentCsvHandler;
        Collection<String[]> data = replenishmentCsvHandler.readCsvValues();
        requestID = data.size();
    }

    public void submitRequest(){
        String[] newRow = new String[3];
        newRow[0] = Integer.toString(requestID);
        newRow[1] = "";
        for(String medicineName:medicineNames){
            newRow[1] += medicineName + " ";
        }
        newRow[2] = status.toString();
        try{
            replenishmentCsvHandler.addRow(newRow);
        }catch(IOException e){
            System.out.println("Error occured when submitting request");
        }
    }

    public List<String> getLowMedicine(){
        List<String> ret = new ArrayList<String>();
        // Use CSV Handler to get all low level medicine
        Collection<String[]> data = medicineCsvHandler.readCsvValues();
        for(String[] row:data){
            int amount = Integer.valueOf(row[1]);
            int alertLine = Integer.valueOf(row[2]);
            if(amount > alertLine) continue;
            String choice = getter.inputString("Request for " + row[0] + "(current amount: " + row[1] + ")? (y/n)");
            while(!choice.equals("y") && !choice.equals("n")){
                System.out.println("Invalid keyword.?");
                choice = getter.inputString("Request for " + row[0] + "(current amount: " + row[1] + ")? (y/n)");
            }
            if(choice.equals("y")) ret.add(row[0]);
        }
        return ret;
    }

    public void rejectRequest(){
        status = Status.REJECTED;
        try{
            replenishmentCsvHandler.setField(0, Integer.toString(requestID), 2, "REJECTED");
        }catch(IOException e){
            System.out.println("Error occured when rejecting request");
        }
    }

    public void approveRequest(){
        status = Status.APPROVED;
        try{
            replenishmentCsvHandler.setField(0, Integer.toString(requestID), 2, "APPROVING");
        }catch(IOException e){
            System.out.println("Error occured when approving request");
        }
        InventoryController inventoryController = InventoryController.getInstance();
        inventoryController.editMedicationList(medicineNames);
    }

    public void viewRequest(){
        System.out.printf("RequestID: %d", requestID);
        System.out.println("Medicine Requested: ");
        for(String medicineName:medicineNames){
            System.out.println(medicineName);
        }
        System.out.println("Status: " + status.toString());
    }
    public int getMedicineSize(){
        return medicineNames.size();
    }
}
