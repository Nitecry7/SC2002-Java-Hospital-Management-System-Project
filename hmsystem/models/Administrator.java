package hmsystem.models;
import hmsystem.controllers.AttributeController;
import hmsystem.controllers.InventoryController;
import hmsystem.controllers.ReplenishmentController;

import hmsystem.data.Consts;
import hmsystem.io.*;

import java.util.List;

class Administrator extends Staff {

    private AttributeController getter = AttributeController.getInstance();
    
    private Administrator(String[] details, IOHandler handler) {
        super (details, handler);

        //super(userID, name, age, gender, email, contactNumber, userRole);
        
    }


    public static Administrator getAdministrator(String adminID, IOHandler handler) {

        List<String[]> userDetails = handler.getRows(Consts.Staff.ID_COLUMN, adminID);
        if (userDetails.isEmpty()) {
            return null;
        }
        else {
            return new Administrator(userDetails.get(0), handler);
        }

    }


    public void _View_and_Manage_Hospital_Staff() {

    }

    public void _View_Appointments_details() {

    }
    
    public void _View_and_Manage_Medication_Inventory() {
        // view, create, edit, delete, logout
        InventoryController ic = InventoryController.getInstance();
        System.out.println("1. View Medication Inventory: ");
        System.out.println("2. Add Medication: ");
        System.out.println("3. Edit Medication Inventory: ");
        System.out.println("4. Delete Medication: ");
        System.out.println("5. Logout: ");

        int operation = getter.inputInt("Enter your choice(1-5):");
        while(operation > 5 || operation < 1){
            System.out.println("Please input a valid integer(1-5)!");
            getter.inputInt("Enter your choice(1-5):");
        }
        switch (operation)
        {
            case 1:
                ic.viewMedicationInventory();
                break;
            case 2: 
                ic.addMedication();
                break;
            case 3:
            {
                String medicineName = getter.inputString("Please input the name of medicine:");
                ic.editMedication(medicineName);
                break;
            }
            case 4:
                ic.deleteMedication();
                break;
            case 5:
                return;
        }
    }
    public void _Replenishment_Action(){
        System.out.println("1. View All Request");
        System.out.println("2. View Pending Request");
        System.out.println("3. Reject Request");
        System.out.println("4. Approve Request");
        System.out.println("5. Go Back");
        int operation = getter.inputInt("Enter your choice(1-5):");
        while(operation > 5 || operation < 1){
            System.out.println("Please input a valid integer(1-5)!");
            getter.inputInt("Enter your choice(1-5):");
        }
        switch(operation){
            case 1:
                _View_All_Request();
                break;
            case 2:
                _View_Pending_Request();
                break;
            case 3:
                _Reject_Replenishment_Requests();
                break;
            case 4:
                _Approve_Replenishment_Requests();
                break;
            case 5:
                return;
        }
    }
    public void _View_All_Request () {
        ReplenishmentController rc = ReplenishmentController.getInstance();
        rc.viewAllRequest();
    }
    public void _View_Pending_Request () {
        ReplenishmentController rc = ReplenishmentController.getInstance();
        rc.viewPendingRequest();
    }
    public void _Reject_Replenishment_Requests () {
        ReplenishmentController rc = ReplenishmentController.getInstance();
        rc.rejectRequest();
    }
    public void _Approve_Replenishment_Requests () {
        ReplenishmentController rc = ReplenishmentController.getInstance();
        rc.approveRequest();
    }

 


}
