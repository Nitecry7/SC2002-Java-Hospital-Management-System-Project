package hmsystem.models;

import java.io.IOException;
import java.util.*;

import hmsystem.io.*;
import hmsystem.controllers.AttributeController;
import hmsystem.controllers.InventoryController;

public class Replenishment {
    private IOHandler medicineCsvHandler, replenishmentCsvHandler;
    private AttributeController getter = AttributeController.getInstance();
    private enum Status{
        PENDING,
        APPROVED,
        REJECTED
    }
    private int requestID;
    private List<String> medicineNames;
    private Status status = Status.PENDING;
    public Replenishment(IOHandler medicineCsvHandler, IOHandler replenishmentCsvHandler){
        this.medicineCsvHandler = medicineCsvHandler;
        this.replenishmentCsvHandler = replenishmentCsvHandler;
        Collection<String[]> data = replenishmentCsvHandler.readCsvValues();
        requestID = data.size();
        medicineNames = getLowMedicine();
    }
    public Replenishment(IOHandler medicineCsvHandler, IOHandler replenishmentCsvHandler,
                        int requestID, List<String> medicineNames, String status){
        this.medicineCsvHandler = medicineCsvHandler;
        this.replenishmentCsvHandler = replenishmentCsvHandler;
        this.requestID = requestID;
        this.medicineNames = medicineNames;
        this.status = Replenishment.Status.valueOf(status);
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
        if(status != Status.PENDING){
            System.out.println("Cannot reject non-pending request!");
            return;
        }
        status = Status.REJECTED;
        try{
            replenishmentCsvHandler.setField(0, Integer.toString(requestID), 2, "REJECTED");
        }catch(IOException e){
            System.out.println("Error occured when rejecting request");
        }
    }

    public void approveRequest(){
        if(status != Status.PENDING){
            System.out.println("Cannot approve non-pending request!");
            return;
        }
        status = Status.APPROVED;
        try{
            replenishmentCsvHandler.setField(0, Integer.toString(requestID), 2, "APPROVED");
        }catch(IOException e){
            System.out.println("Error occured when approving request");
        }
        InventoryController inventoryController = InventoryController.getInstance();
        inventoryController.editMedicationList(medicineNames);
    }

    public void viewRequest(){
        System.out.printf("RequestID: %d\n", requestID);
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
