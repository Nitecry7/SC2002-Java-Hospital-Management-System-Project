package hmsystem.models;

import java.util.*;

public class Replenishment {
    private enum Status{
        PENDING,
        APPROVED,
        REJECTED
    }
    List<String> medicineNames;
    Status status = Status.PENDING;

    public Replenishment(){
        medicineNames = getLowMedicine();
    }

    public List<String> getLowMedicine(){
        List<String> ret = new ArrayList<String>();
        // Use CSV Handler to get all low level medicine
        return ret;
    }

    public void rejectRequest(){
        status = Status.REJECTED;
    }

    public void approveRequest(){
        status = Status.APPROVED;
        // Call Medicine Controller or sth and change the amount to 3x alert line.
    }

    public void viewRequest(){
        System.out.println("Medicine Requested: ");
        for(String medicineName:medicineNames){
            System.out.println(medicineName);
        }
        System.out.println("Status: " + status.toString());
    }
}
