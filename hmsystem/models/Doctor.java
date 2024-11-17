package hmsystem.models;
import hmsystem.controllers.AppointmentController;
import hmsystem.controllers.AttributeController;
import hmsystem.data.Consts;
import hmsystem.io.*;
import java.util.ArrayList;
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


    public void _View_Patient_Medical_Records() throws Exception {

        List<Appointment> appointments = new AppointmentController().getDoctorAppointments(getUserID());
        List<String> patientIDs = new ArrayList<>();
    

        System.out.println("\nChoose a patient under your care to view medical history:\n");

        for (Appointment a : appointments) {
            String s = a.getPatientID();
            if (!patientIDs.contains(s)) {
                patientIDs.add(s);
                System.out.println(patientIDs.size() + ". ID: " + a.getPatientID());
            }
        }

        AttributeController ac = AttributeController.getInstance();

        int choice = ac.inputInt("Input choice of patient");

        if (choice < 1 || choice > patientIDs.size()) {
            System.out.println("Invalid choice. Exiting...");
        }
        else {
            Patient.getPatient(patientIDs.get(choice - 1), new CsvHandler(Consts.Patient.FILE_NAME)).getMedicalRecord().displayPatientMedicalRecord();
        }

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
  



