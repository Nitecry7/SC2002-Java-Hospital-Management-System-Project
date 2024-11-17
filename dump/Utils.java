
import java.util.List;

public class Utils {

    /**
     * Helper function to print appointment details in a consistent format.
     * This can be used for any function that retrieves and displays a list of
     * appointments.
     *
     * @param appointments List of appointment details to print.
     * @param message      The message to display before the appointments list
     *                     (e.g., "Scheduled Appointments").
     */
    public static void printView(List<String> appointments, String message) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println(message);
            for (String appointment : appointments) {
                System.out.println(appointment);
                System.out.println("----------------------------"); // Separator for readability
            }
        }
    }
}
