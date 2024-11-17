package hmsystem.controllers;

import java.util.*;

import java.io.IOException;

import hmsystem.data.Consts;
import hmsystem.io.*;

import hmsystem.models.Replenishment;

public class ReplenishmentController 
{
    public static final ReplenishmentController replenishmentController = new ReplenishmentController();
    public static ReplenishmentController getInstance(){
        return replenishmentController;
    }
    private IOHandler replenishmentCsvHandler, medicineCsvHandler;
    private AttributeController getter = AttributeController.getInstance();
    ReplenishmentController(){
        try{
            this.replenishmentCsvHandler = new CsvHandler(Consts.Replenishment.FILE_NAME);
            this.medicineCsvHandler = new CsvHandler(Consts.Medicine.FILE_NAME);
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
        List<String> medicineNames = Arrays.asList(row[1]);
        Replenishment request = new Replenishment(medicineCsvHandler, replenishmentCsvHandler, requestIDnum, medicineNames, row[2]);
        request.rejectRequest();
    }

    public void approveRequest(){
        String requestID = getter.inputString("Please input requestID to approve: ");
        List<String[]> rows = replenishmentCsvHandler.getRows(0, requestID);
        if(rows.size() == 0){
            System.out.println("Request Not Found!");
            return;
        }
        String[] row = rows.getFirst();
        int requestIDnum = Integer.valueOf(requestID);
        String[] arrMedicineNames = row[1].split(" ");
        List<String> medicineList = new ArrayList<String>();
        for(String medicineName:arrMedicineNames){
            medicineList.add(medicineName);
        }
        Replenishment request = new Replenishment(medicineCsvHandler, replenishmentCsvHandler, requestIDnum, medicineList, row[2]);
        request.approveRequest();
    }
}
