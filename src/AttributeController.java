import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The AttributeController class manages user input operations, providing
 * methods for input validation and formatted data collection.
 */
public class AttributeController {

    private final Scanner sc;
    private static final AttributeController attributeController = new AttributeController();

    /**
     * Private constructor to initialize the AttributeController.
     */
    protected AttributeController() {
        sc = new Scanner(System.in);
    }

    /**
     * Retrieves the singleton instance of AttributeController.
     *
     * @return The singleton instance of AttributeController.
     */
    public static AttributeController getInstance() {
        return attributeController;
    }

    /**
     * Displays a message to the console.
     *
     * @param message The message to display.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prompts the user to input an integer and validates the input.
     *
     * @param message The prompt message.
     * @return The validated integer input.
     */
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

    /**
     * Prompts the user to input a string and validates the input.
     *
     * @param message The prompt message.
     * @return The validated string input.
     */
    public String inputString(String message) {
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

    /**
     * Prompts the user to input a date and validates the input.
     *
     * @param message The prompt message.
     * @return The validated date as a Calendar object.
     */
    public Calendar inputDate(String message) {
        Calendar date = null;
        System.out.println(message);
        int year, month, day;
        while (date == null) {
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

    /**
     * Prompts the user to input a date and time and validates the input.
     *
     * @param message The prompt message.
     * @return The validated date and time as a Calendar object.
     */
    public Calendar inputDateTime(String message) {
        Calendar date = null;
        System.out.println(message);
        int year, month, day, hour, minute;
        while (date == null) {
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
                    date.get(Calendar.MINUTE) != minute) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a valid date.");
                date = null;
            }
        }
        return date;
    }

    /**
     * Prompts the user to input notes and collects them until "END" is entered.
     *
     * @param message The prompt message.
     * @return The concatenated notes as a string.
     */
    public String inputNote(String message) {
        System.out.println(message);

        String note = "";
        String input;

        while (true) {
            input = sc.nextLine();
            if (input.equals("END")) {
                break;
            }
            if (!input.isEmpty()) note = note.concat(input + " ");
        }

        return note.trim();
    }

    /**
     * Prompts the user to input a frequency and validates the input.
     *
     * @param message The prompt message.
     * @return The validated frequency as a Frequency enum.
     */
    public Frequency inputFrequency(String message) {
        Frequency frequency = null;
        while (frequency == null) {
            System.out.println(message);
            String input = sc.next().toUpperCase();
            try {
                frequency = Frequency.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a valid frequency.");
            }
        }
        return frequency;
    }
}
