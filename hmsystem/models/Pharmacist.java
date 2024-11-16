package hmsystem.models;
import hmsystem.controllers.InventoryController;
import hmsystem.data.Consts;
import hmsystem.io.*;
import java.util.List;

class Pharmacist extends Staff {


    
    private Pharmacist(String[] details, IOHandler handler) {
        super (details, handler);

        //super(userID, name, age, gender, email, contactNumber, userRole);
        
    }


    public static Pharmacist getPharmacist(String adminID, IOHandler handler) {

        List<String[]> userDetails = handler.getRows(Consts.Staff.ID_COLUMN, adminID);
        if (userDetails.isEmpty()) {
            return null;
        }
        else {
            return new Pharmacist(userDetails.get(0), handler);
        }

    }


    public void _View_Appointment_Outcome_Record(){

    }

    public void _Update_Prescription_Status() {

    }
    
    public void _View_Medication_Inventory() {
        InventoryController ic = InventoryController.getInstance();
        ic.viewMedicationInventory();
    }
    public void _Submit_Replenishment_Request() {

    }
  


}
