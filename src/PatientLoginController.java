/**
 * Singleton class for handling patient login authentication.
 * Provides mechanisms to authenticate a patient based on their ID and password.
 */
public class PatientLoginController implements ILoginController {

    private static PatientLoginController loginController = null;
    private IOHandler handler;

    /**
     * Private constructor to initialize the PatientLoginController with an IOHandler.
     *
     * @param handler The IOHandler instance to handle CSV operations.
     */
    private PatientLoginController(IOHandler handler) {
        this.handler = handler;
    }

    /**
     * Retrieves the singleton instance of PatientLoginController.
     * If an instance does not exist, it creates one using the provided handler.
     *
     * @param handler The IOHandler instance to handle CSV operations.
     * @return The singleton instance of PatientLoginController.
     */
    public static PatientLoginController getInstance(IOHandler handler) {
        if (handler != null) {
            loginController = new PatientLoginController(handler);
        }
        return loginController;
    }

    /**
     * Authenticates a patient based on their user ID and password.
     * If the user ID and password match, retrieves the corresponding Patient object.
     *
     * @param userID   The unique identifier of the patient.
     * @param password The password entered by the patient.
     * @return A User object representing the authenticated patient, or null if authentication fails.
     */
    @Override
    public User authenticate(String userID, String password) {
        User user;

        try {
            // Retrieve the actual password for the provided user ID
            String actualPassword = handler.getField(Consts.Patient.ID_COLUMN, userID, Consts.Patient.PW_COLUMN);

            // Validate the password
            if ((actualPassword.equals("") && password.equals("password")) || password.equals(actualPassword)) {
                // If valid, retrieve the Patient object
                user = Patient.getPatient(userID, new CsvHandler(Consts.Patient.FILE_NAME));
            } else {
                System.out.println("Wrong password");
                return null;
            }
        } catch (Exception e) {
            System.out.println("ID does not exist");
            return null;
        }

        return user;
    }
}
