
public class test {
    public static void main(String[] args){
        ReplenishmentController rc = ReplenishmentController.getInstance();
        InventoryController ic = InventoryController.getInstance();
        //test1
        ic.viewMedicationInventory();
        rc.viewAllRequest();
        rc.viewPendingRequest();
        rc.submitRequest();
        rc.rejectRequest();
        rc.approveRequest();
        rc.viewAllRequest();
        rc.viewPendingRequest();
        ic.viewMedicationInventory();
        ic.addMedication();
        ic.viewMedicationInventory();
        ic.deleteMedication();
        ic.viewMedicationInventory();
;    }
}
