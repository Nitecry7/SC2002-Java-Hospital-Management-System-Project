/**
 * Singleton class responsible for handling staff login and authentication.
 * Supports authentication for different staff roles such as Doctor, Pharmacist, and Administrator.
 */
public class StaffLoginController implements ILoginController {

    /**
     * Singleton instance of StaffLoginController.
     */
    private static StaffLoginController loginController = null;

    /**
     * IOHandler for managing CSV operations related to staff data.
     */
    private IOHandler handler = null;

    /**
     * Private constructor to initialize StaffLoginController with a specified handler.
     *
     * @param handler IOHandler for accessing staff data.
     */
    protected StaffLoginController(IOHandler handler) {
        this.handler = handler;
    }

    /**
     * Retrieves the singleton instance of StaffLoginController.
     *
     * @param handler IOHandler for accessing staff data.
     * @return The singleton instance of StaffLoginController.
     */
    public static StaffLoginController getInstance(IOHandler handler) {
        if (handler != null) {
            loginController = new StaffLoginController(handler);
        }
        return loginController;
    }

    /**
     * Authenticates a staff member based on their user ID and password.
     * Determines the staff role and returns the corresponding user object.
     *
     * @param userID   The ID of the staff member.
     * @param password The password of the staff member.
     * @return A User object representing the authenticated staff member, or null if authentication fails.
     * @throws Exception If an error occurs during authentication.
     */
    @Override
    public User authenticate(String userID, String password) throws Exception {

        User user = null;
        String roleName = "";

        try {
            // Retrieve the actual password from the CSV file.
            String actualPassword = handler.getField(Consts.Staff.ID_COLUMN, userID, Consts.Staff.PW_COLUMN);

            // If the password matches, proceed with role identification.
            if ((actualPassword.equals("") && password.equals("password")) || password.equals(actualPassword)) {

                // Retrieve the role of the staff member.
                roleName = handler.getField(Consts.Staff.ID_COLUMN, userID, Consts.Staff.ROLE_COLUMN);

                // Instantiate the corresponding User object based on the role.
                switch (roleName) {
                    case "Doctor":
                        user = Doctor.getDoctor(userID, handler);
                        break;

                    case "Pharmacist":
                        user = Pharmacist.getPharmacist(userID, handler);
                        break;

                    case "Administrator":
                        user = Administrator.getAdministrator(userID, handler);
                        break;

                    default:
                        throw new Exception("Unknown role: " + roleName);
                }

            } else {
                System.out.println("Wrong password");
                return null;
            }

        } catch (Exception e) {
            System.out.println("ID or Role " + roleName + " does not exist");
            return null;
        }

        return user;
    }
}
