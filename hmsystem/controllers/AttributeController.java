package hmsystem.controllers;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

private Scanner sc;
private static final AttributeController attributeController = new AttributeController();

protected class AttributeController {
    public AttributeController(){
        sc = new Scanner(System.in);
    }

public static class getInstance() {
    return attributeController;
}

    public void displayMessage(String message){
        System.out.println(message);
    }

    public int inputInt(Scanner sc, String message) {
        int value = 0;
        while (true) {
            displayMessage(message);
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

    public String inputString(String message){
        String value = "";
        while (true) {
            displayMessage(message);
            try {
                value = sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid string.");
                sc.next();
            }
        }
        return value;
    }

    public Calendar inputDate(String message){
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
                System.out.println("Invalid input. Please enter a valid date.");
                date = null;
            }
        }
        return date;
    }

    public Calendar inputDateTime(String message){
        Calendar date = null;
        System.out.println(message);
        int year, month, day, hour, minute;
        while(date == null){
            day = inputInt(sc, "Please enter day: ");
            month = inputInt(sc, "Please enter month: ") - 1; // 0-based
            year = inputInt(sc, "Please enter year: ");
            hour = inputInt(sc, "Please enter hour:");
            minute = inputInt(sc, "Please enter minute:");
            try {
                date = new GregorianCalendar(year, month, day, hour, minute);
                if (date.get(Calendar.YEAR) != year || 
                    date.get(Calendar.MONTH) != month || 
                    date.get(Calendar.DAY_OF_MONTH) != day || 
                    date.get(Calendar.HOUR_OF_DAY) != hour || 
                    date.get(Calendar.MINUTE) != minute
                    ) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a valid date.");
                date = null;
            }
        }
        return date;
    }
    
    public String inputNote(String message){
        System.out.println(message);

        String input = sc.nextLine();
        String note = "";
        
        while(!input.equals("")) {
            note =  note.concat(input + "\n");
            input = sc.nextLine();
        }

        return note;
    }
}
