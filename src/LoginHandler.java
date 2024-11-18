/**
 * Handles user authentication and login process.
 * Supports multiple user types and dynamically configures login controllers based on user type.
 */
public class LoginHandler implements ILoginHandler {

    private ILoginController loginController;
    private final AttributeController attributeController;

    /**
     * Default constructor initializes the AttributeController instance.
     */
    public LoginHandler() {
        this.attributeController = AttributeController.getInstance();
    }

    /**
     * Authenticates a user by prompting for user type, ID, and password.
     * Dynamically loads the corresponding login controller for the user type.
     *
     * @return A {@link User} object if authentication is successful; {@code null} otherwise.
     * @throws Exception If an error occurs during the authentication process.
     */
    @Override
    public User authenticate() throws Exception {

        String[] allUserTypes = Consts.USER_TYPES;

        // Prompt user to select a user type
        System.out.println("Choose user type:");

        for (int x = 0; x < allUserTypes.length; x++) {
            System.out.println((x + 1) + ". " + allUserTypes[x]);
        }

        int input = attributeController.inputInt("Please enter user type:");
        while (input < 1 || input > allUserTypes.length) {
            System.out.println("Invalid input");
            input = attributeController.inputInt("");
        }

        User user = null;
        // Dynamically load the corresponding login controller based on user type
        Class<?> constantClass = Class.forName("Consts$" + allUserTypes[input - 1]);
        String fileName = (String) constantClass.getField("FILE_NAME").get(null);

        loginController = (ILoginController) Class.forName(allUserTypes[input - 1] + "LoginController")
                .getMethod("getInstance", IOHandler.class)
                .invoke(null, new CsvHandler(fileName));

        // Authenticate using the selected login controller
        String ID = attributeController.inputString("Input ID");
        String pw = attributeController.inputString("Input password");

        user = loginController.authenticate(ID, pw);

        return user;
    }
}
