package hmsystem.models;
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

    }
    public void _Approve_Replenishment_Requests () {

    }

 


}
