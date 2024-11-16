package hmsystem.models;

import java.io.IOException;
import java.util.*;
import hmsystem.io.*;

public class Replenishment {
    private CsvHandler csvHandler;
    private enum Status{
        PENDING,
        APPROVED,
        REJECTED,
        FAILED
    }
    private List<String> medicineNames;
    private Status status = Status.PENDING;

    public Replenishment(CsvHandler csvHandler){
        medicineNames = getLowMedicine();
        this.csvHandler = csvHandler;
    }

    public void submitRequest(){
        medicineNames = getLowMedicine();
        status = Status.PENDING;
    }

    public List<String> getLowMedicine(){
        List<String> ret = new ArrayList<String>();
        // Use CSV Handler to get all low level medicine
        Collection<String[]> data = csvHandler.readCsvValues();
        for(String[] row:data){
            int amount = Integer.valueOf(row[1]);
            int alertLine = Integer.valueOf(row[2]);
            if(amount <= alertLine) ret.add(row[0]);
        }
        return ret;
    }

    public void rejectRequest(){
        status = Status.REJECTED;
    }

    public void approveRequest(){
        status = Status.APPROVED;
        // Call Medicine Controller or sth and change the amount to 3x alert line.
        for(String medicineName:medicineNames){
            try{
                csvHandler.setField(0, medicineName, 1, "250");
            }catch (IOException e){
                System.out.println("Error occured during setting value!");
                status = Status.FAILED;
                break;
            }
        }
    }

    public void viewRequest(){
        System.out.println("Medicine Requested: ");
        for(String medicineName:medicineNames){
            System.out.println(medicineName);
        }
        System.out.println("Status: " + status.toString());
    }
}
