package hmsystem.controllers;
//Singleton
public class StaffController {

    private static final StaffController staffController = new StaffController();

    protected StaffController() {
    }

    public static StaffController getInstance() {
            return staffController;
    }

    public void viewStaff(){
    }

    public void addStaff(){
    }

    public void removeStaff(){
    }

    public void updateStaff(){
    }

}
