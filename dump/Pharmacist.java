import java.util.List;

class Pharmacist extends Staff {


    
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
            AORController.getInstance().viewPastAppointmentsOutcome(patientID);
        }
        catch (Exception e) {
            System.out.println("No such patient");
        }

    }

    public void _Update_Prescription_Status() throws Exception {

        AttributeController ac = AttributeController.getInstance();
        String patientID = ac.inputString("Input patient's ID");

        List<AOR> aors;

        try {
            aors = AORController.getInstance().returnPastAppointmentsOutcome(patientID);
        }
        catch (Exception e) {
            System.out.println("No such patient found");
            return;
        }

        System.out.println("All appointment outcomes of this patient: ");

        if (aors.isEmpty()) {
            System.out.println("Patient has no past appointment outcomes");
            return;
        }

        for (int i = 0; i < aors.size(); i++) {
            System.out.print("Appointment outcome " + (i+1) + ":");
            aors.get(i).display();
        }


        int choice = ac.inputInt("Enter appointment outcome number from the above list to change one of its precripiton status");

        if (choice < 1 || choice > aors.size()) {
            System.out.println("Invalid choice, returning to menu...");
            return;
        }

        Prescription[] prescriptions = aors.get(choice-1).getPrescriptions();

        if (prescriptions.length == 0) {
            System.out.println("This appointment outcome has no prescriptions");
            return;
        }

        for (int i = 0; i < prescriptions.length; i++) {
            System.out.print("Prescription " + (i+1) + ":");
            prescriptions[i].display();
        }

        int prescriptionChoice = ac.inputInt("Enter prescription number from the above list to Dispense prescription and reduce inventory stock");

        if (prescriptionChoice < 1 || prescriptionChoice > prescriptions.length) {
            System.out.println("Invalid choice, returning to menu...");
            return;
        }

   

        if (prescriptions[prescriptionChoice].isDispensed()) {
            System.out.println("The selected prescripiton has already beed dispensed. Returning to menu...");
        }
        else if (InventoryController.getInstance().reduceStock(prescriptions[prescriptionChoice].getMedicineName(), prescriptions[prescriptionChoice].getAmount())) {
            
            prescriptions[prescriptionChoice].setDispensed(true);
            aors.get(choice).saveData();

        }
        else {
            System.out.println("Unsuccessful dispensing, returning to menu...");
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
