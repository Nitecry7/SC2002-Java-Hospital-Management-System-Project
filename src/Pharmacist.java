package src;
import java.util.List;

public class Pharmacist extends Staff {


    
    private Pharmacist(String[] details, IOHandler handler) {
        super (details, handler);

        //super(userID, name, age, gender, email, contactNumber, userRole);
        
    }


    public static Pharmacist getPharmacist(String pharmacistID, IOHandler handler) {

        List<String[]> userDetails = handler.getRows(Consts.Staff.ID_COLUMN, pharmacistID);
        if (userDetails.isEmpty()) {
            return null;
        }
        else {
            return new Pharmacist(userDetails.get(0), handler);
        }

    }


    public void _View_Appointment_Outcome_Record() {

        AttributeController ac = AttributeController.getInstance();
        String patientID = ac.inputString("Input patient's ID");

        try {
            List<String> pastAORs = AORController.getInstance().viewPastAppointmentsOutcome(patientID);
            for (String s : pastAORs) {
                System.out.println(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("No such patient");
        }

    }

    public void _Update_Prescription_Status() {
        PrescriptionController pc = PrescriptionController.getInstance();
        System.out.println("1. View All Prescription Status");
        System.out.println("2. Dispense medicine");
        System.out.println("3. Go back");
        AttributeController ac = AttributeController.getInstance();
        int operation = ac.inputInt("Enter your choice(1-3): ");
        while(operation > 3 || operation < 1){
            System.out.println("Please enter a valid choice.");
            operation = ac.inputInt("Enter your choice(1-3): ");
        }
        switch(operation){
            case 1:
                pc.viewPrescriptions();
                break;
            case 2:
                pc.dispenseMedicine();
                break;
            case 3:
                return;
        }
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
