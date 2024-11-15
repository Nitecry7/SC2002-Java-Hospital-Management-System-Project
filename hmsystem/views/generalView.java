package hmsystem.views;
public abstract class generalView

{

    // We have a controller for every Object
    // The flow should look somewhat like this

    // Later will implement a login manager for the main application
    // After logging in, we do `while(useViewer());`
    // We call displayMenu() and getOperation() in each useViewer()
    // Call the controller based on what operation we given
    // Return False and go back to the login page if the user logged out

    // protected MedicalRecordController MRC = new MedicalRecordController();
    // protected AppointmentController AC = new AppointmentController();
    // protected AppointmentOutcomeController AOC = new AppointmentOutcomeController();
    // protected InventoryController IC = new InventoryController();
    // protected ReplenishmentController RC = new ReplenishmentController();
    // protected StaffListController SLC = new StaffListController();

    public abstract boolean useViewer();
    public abstract void displayMenu();
    public abstract int getOperation();
}