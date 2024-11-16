package hmsystem.controllers;

import java.util.*;

import java.io.IOException;

import hmsystem.io.CsvHandler;

import hmsystem.models.Replenishment;

public class ReplenishmentController 
{
    public final ReplenishmentController replenishmentController = new ReplenishmentController();
    public ReplenishmentController getInstance(){
        return replenishmentController;
    }
    CsvHandler replenishmentCsvHandler, medicineCsvHandler;
    AttributeController getter = AttributeController.getInstance();
    ReplenishmentController(){
        try{
            replenishmentCsvHandler = new CsvHandler("hmsystem\\data\\replenishment.csv");
            medicineCsvHandler = new CsvHandler("hmsystem\\data\\Medicine_List.csv");
        }catch (IOException e){
            System.out.println("Error occured creating replenishment controller");
        }
    }
    public void submitRequest(){
        Replenishment request = new Replenishment(medicineCsvHandler, replenishmentCsvHandler);
        if(request.getMedicineSize() != 0)
            request.submitRequest();
        else{
            System.out.println("Cannot request without medication selected!");
        }
    }
    public void viewAllRequest(){
        Collection<String[]> data = replenishmentCsvHandler.readCsvValues();
        for(String[] row:data){
            System.out.println("RequestID: " + row[0]);
            System.out.println("Requested Medication: " + row[1]);
            System.out.println("Request Status: " + row[2]);
        }
    }
    public void viewPendingRequest(){
        Collection<String[]> data = replenishmentCsvHandler.readCsvValues();
        for(String[] row:data){
            if(row[2].equals("PENDING")){
                System.out.println("RequestID: " + row[0]);
                System.out.println("Requested Medication: " + row[1]);
                System.out.println("Request Status: " + row[2]);
            }
        }
    }

    public void rejectRequest(){
        String requestID = getter.inputString("Please input requestID to reject: ");
        List<String[]> rows = replenishmentCsvHandler.getRows(0, requestID);
        if(rows.size() == 0){
            System.out.println("Request Not Found!");
            return;
        }
        String[] row = rows.getFirst();
        int requestIDnum = Integer.valueOf(requestID);
        List<String> medicineNames = Arrays.asList(row[1].split("\\s*"));
        Replenishment request = new Replenishment(medicineCsvHandler, replenishmentCsvHandler, requestIDnum, medicineNames, row[3]);
        request.rejectRequest();
    }

    public void approveRequest(){
        String requestID = getter.inputString("Please input requestID to reject: ");
        List<String[]> rows = replenishmentCsvHandler.getRows(0, requestID);
        if(rows.size() == 0){
            System.out.println("Request Not Found!");
            return;
        }
        String[] row = rows.getFirst();
        int requestIDnum = Integer.valueOf(requestID);
        List<String> medicineNames = Arrays.asList(row[1].split("\\s*"));
        Replenishment request = new Replenishment(medicineCsvHandler, replenishmentCsvHandler, requestIDnum, medicineNames, row[3]);
        request.rejectRequest();
    }
}
