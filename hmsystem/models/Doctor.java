package hmsystem.models;
import hmsystem.data.Consts;
import hmsystem.io.*;
import java.util.List;

class Doctor extends Staff {


    
    private Doctor(String[] details, IOHandler handler) {
        super (details, handler);

        //super(userID, name, age, gender, email, contactNumber, userRole);
        
    }


    public static Doctor getDoctor(String adminID, IOHandler handler) {

        List<String[]> userDetails = handler.getRows(Consts.Staff.ID_COLUMN, adminID);
        if (userDetails.isEmpty()) {
            return null;
        }
        else {
            return new Doctor(userDetails.get(0), handler);
        }

    }


    public void _View_Patient_Medical_Records() {

    }

    public void _Update_Patient_Medical_Records() {

    }

    public void _View_Personal_Schedule() {

    }

    public void _Set_Availability_or_Appointments() {

    }

    public void _Accept_or_Decline_Appointment_Requests() {

    }

    public void _View_Upcoming_Appointments(){

    }

    public void _Record_Appointment_Outcome() {

    }
    }
  


}
