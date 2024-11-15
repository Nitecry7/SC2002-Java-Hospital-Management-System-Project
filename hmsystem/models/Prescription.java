package hmsystem.models;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import hmsystem.models.enums.Frequency;

import java.io.Serializable;

public class Prescription implements Serializable{
    private Calendar startDate, endDate;
    private Frequency frequency;
    private String note;
    private String medicineName;
    private boolean dispensed = false;

    // yet to be implemented
    // private MedicineModel medicineModel = new MedicineModel();

    public Prescription() {
        build();
    }

    private void build(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter information for prescription");

        this.startDate = inputDate(sc, "Please enter the start date:");
        this.endDate = inputDate(sc, "Please enter the end date:");
        while (endDate.before(startDate)) {
            System.out.println("End date cannot be before start date. Please enter a valid end date.");
            this.endDate = inputDate(sc, "Please enter the end date:");
        }

        this.frequency = inputFrequency(sc, "Please enter the frequency (PRN/BD/TDS/QDS/MANE/NOCTE):");
        this.note = inputNote(sc, "Please enter notes. Press enter twice on a line to finish");
        this.medicineName = inputMedicineName(sc, "Please choose medicine name to use");

        sc.close();
    }

    private String inputMedicineName(Scanner sc, String message){
        // yet to be implemented
        // use medicineModel to fetch data
        return "";
    }

    private Frequency inputFrequency(Scanner sc, String message) {
        Frequency frequency = null;
        while (frequency == null) {
            System.out.println(message);
            String input = sc.nextLine().toUpperCase();
            try {
                frequency = Frequency.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a valid frequency.");
            }
        }
        return frequency;
    }

    private String inputNote(Scanner sc, String message){
        System.out.println(message);

        String input = sc.nextLine();
        String note = "";
        
        while(!input.equals("")) {
            note =  note.concat(input + "\n");
            input = sc.nextLine();
        }

        return note;
    }

    private Calendar inputDate(Scanner sc, String message){
        Calendar date = null;
        System.out.println(message);
        int year, month, day;
        while(date == null){
            day = inputInt(sc, "Please enter day: ");
            month = inputInt(sc, "Please enter month: ") - 1; // 0-based
            year = inputInt(sc, "Please enter year: ");
            try {
                date = new GregorianCalendar(year, month, day);
                if (date.get(Calendar.YEAR) != year || 
                    date.get(Calendar.MONTH) != month || 
                    date.get(Calendar.DAY_OF_MONTH) != day) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date. Please enter a valid date.");
                date = null;
            }
        }
        return date;
    }

    private int inputInt(Scanner sc, String message) {
        int value = 0;
        while (true) {
            System.out.print(message);
            try {
                value = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next();
            }
        }
        return value;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public boolean isDispensed() {
        return dispensed;
    }

    public void setDispensed(boolean dispensed) {
        this.dispensed = dispensed;
    }
}
