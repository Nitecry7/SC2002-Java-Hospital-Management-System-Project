package hmsystem.models;
import hmsystem.controllers.InventoryController;
import java.util.Scanner;
import hmsystem.data.Consts;
import hmsystem.io.*;
import java.util.List;

class Administrator extends Staff {


    
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
        Scanner sc = new Scanner(System.in);
        InventoryController ic = InventoryController.getInstance();
        int i;
        do{

            System.out.println("1. View Medication Inventory: ");
            System.out.println("2. Add Medication: ");
            System.out.println("3. Edit Medication Inventory: ");
            System.out.println("4. Delete Medication: ");
            System.out.println("5. Logout: ");

            i = sc.nextInt();

            i = sc.nextInt();
            switch (i)
            {
                case 1:
                    ic.viewMedicationInventory();
                    break;
                case 2: 
                    ic.addMedication();
                    break;
                case 3:
                {
                    System.out.println("What Medication?");
                    ic.editMedication(sc.next());
                    break;
                }
                case 4:
                    ic.deleteMedication();
                    break;
                case 5:
                    return;
            }

        }while (1 < i && i < 4);
        sc.close();


    }
    public void _Approve_Replenishment_Requests () {

    }

 


}
