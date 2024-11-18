import java.util.Scanner;

/**
 * The Driver class serves as the entry point for the application.
 * It manages the login process and provides access to the user-specific menu.
 */
public class Driver {
    /**
     * Driver constructor
     */
    public Driver(){
    }
    /**
     * The main method initializes the application and handles user login.
     *
     * @param args Command-line arguments for configuring the application.
     * @throws Exception If any unexpected error occurs.
     */
    public static void main(String[] args) throws Exception {

        // Default login handler and attribute controller
        ILoginHandler loginHandler = new LoginHandler();

        // Configure login handler based on command-line arguments
        switch (args.length) {
            case 0 -> {
                System.out.println("Default config chosen");
            }
            case 1 -> {
                try {
                    System.out.println("Attempting custom config");
                    // Custom login handler specified by args
                    loginHandler = (ILoginHandler) Class.forName(args[0]).getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    System.out.println("Invalid controller names");
                    System.exit(0);
                }
            }
            default -> {
                System.out.println("Invalid input");
                System.exit(0);
            }
        }

        // Authenticate using the chosen handler
        int login = 1;
        AttributeController ac = AttributeController.getInstance();
        while (login == 1) {
            login = ac.inputInt("\nWhat would you like to do?:\n1. Log in\n2. Leave\nEnter your choice: ");

            if (login == 1) {
                User user = loginHandler.authenticate();
                if (user != null) {
                    IMenuController menuController = new MenuController(user);
                    menuController.takeInput();
                } else {
                    System.out.println("\nWrong ID or password\n");
                }
            }
        }
        System.out.println("Have a good day");
    }
}
