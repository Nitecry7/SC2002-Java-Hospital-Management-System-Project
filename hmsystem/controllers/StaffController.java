package hmsystem.controllers;
//Singleton

import java.util.*;
import hmsystem.models.*;

public class StaffController {

    private static final StaffController staffController = new StaffController();

    protected StaffController() {
    }

    public static StaffController getInstance() {
            return staffController;
    }

    public void viewStaff(){
        AttributeController getter = AttributeController.getInstance();
        List<Staff> staffList = new ArrayList<Staff>();
    }

    public void addStaff(){
    }

    public void removeStaff(){
    }

    public void updateStaff(){
    }

}
