
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;


public class AttributeController {
    
    private final Scanner sc;
    private static final AttributeController attributeController = new AttributeController();
    
    protected AttributeController(){
        sc = new Scanner(System.in);
    }

    public static AttributeController getInstance() {
        return attributeController;
    }

    public void displayMessage(String message){
        System.out.println(message);
    }

    public int inputInt(String message) {
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
                value = sc.next();
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
            day = inputInt("Please enter day: ");
            month = inputInt("Please enter month: ") - 1; // 0-based
            year = inputInt("Please enter year: ");
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
            day = inputInt("Please enter day: ");
            month = inputInt("Please enter month: ") - 1; // 0-based
            year = inputInt("Please enter year: ");
            hour = inputInt("Please enter hour:");
            minute = inputInt("Please enter minute:");
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
    
    public String inputNote(String message) {
        System.out.println(message);
    
        String note = "";
        String input;
    
        while (true) {
            input = sc.nextLine();
            if (input.equals("END")) { 
                break;
            }
            if(!input.isEmpty()) note = note.concat(input + "\n"); 
        }
        System.out.println(note);
    
        return note.trim();
    }
}
