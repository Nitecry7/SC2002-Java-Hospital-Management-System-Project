

import java.io.IOException;
import java.util.*;

public class PrescriptionController {
    public final static PrescriptionController pc = new PrescriptionController();
    private IOHandler prescriptionHandler;
    public static PrescriptionController getInstance(){
        return pc;
    }
    public PrescriptionController(){
        try{
        prescriptionHandler = new CsvHandler("Prescription_List.csv");
        }catch(IOException e){
            System.out.println("Something went wrong");
        }
    }
    public boolean dispenseMedicine(){
        AttributeController getter = AttributeController.getInstance();
        String ID = getter.inputString("Input Prescription ID");
        List<String[]> rows = prescriptionHandler.getRows(0, ID);
        if(rows.isEmpty()){
            System.out.println("ID not found in prescription");
        }
        String[] row = rows.get(0);
        int amount = Integer.parseInt(row[1]);
        String name = row[3];
        if(row[4].equals("true")){
            System.out.println("Already dispensed.");
            return false;
        }
        InventoryController ic = InventoryController.getInstance();
        boolean ret = ic.reduceStock(name, amount);
        if(ret){
            try{
            prescriptionHandler.setField(0, ID, 4, "true");
            }catch(Exception e){
                System.out.println("Something went wrong.");
            }
        }
        return ret;
    }
    public void viewPrescriptions(){
        Collection<String[]> data = prescriptionHandler.readCsvValues();
        for(String[] row:data){
            System.out.printf("ID:%s\n", row[0]);
            System.out.printf("Amount:%s\n", row[1]);
            System.out.printf("Frequency:%s\n", row[2]);
            System.out.printf("Medicine Name:%s\n", row[3]);
            System.out.printf("Dispense Status:%s\n", row[4]);
            System.out.println("--------------------------------");
        }
    }
}
