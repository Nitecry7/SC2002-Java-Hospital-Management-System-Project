package hmsystem.models;
import hmsystem.controllers.AttributeController;
import hmsystem.controllers.InventoryController;
import hmsystem.controllers.ReplenishmentController;
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

        AttributeController ac = AttributeController.getInstance();
        String patientID = ac.inputString("Input patient's ID to check for pending prescriptions");

        

    }

    public void _Update_Prescription_Status() {

    }
    
    public void _View_Medication_Inventory() {
        // call view medication inventory from inventorycontroller
        InventoryController ic = InventoryController.getInstance();
        ic.viewMedicationInventory();
    }
    
    public void _Submit_Replenishment_Request() {
        ReplenishmentController rc = ReplenishmentController.getInstance();
        rc.submitRequest();
    }
  


}
