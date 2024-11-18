/**
 * Abstract class representing a staff member in the system.
 * Provides functionality for managing personal details such as name, age, gender, and password.
 * Includes functionality to save staff data to the associated data handler.
 */
public abstract class Staff extends User {

    /**
     * Unique identifier for the staff member.
     */
    String userID;

    /**
     * Name of the staff member.
     */
    String name;

    /**
     * Role of the staff member in the organization.
     */
    String userRole;

    /**
     * Gender of the staff member.
     */
    String gender;

    /**
     * Age of the staff member.
     */
    int age;

    /**
     * Handler for managing input/output operations with the data source.
     */
    IOHandler handler;

    /**
     * Constructor for creating a Staff instance.
     *
     * @param details  Array containing details of the staff member.
     * @param handler  IOHandler for managing data persistence.
     */
    public Staff(String[] details, IOHandler handler) {
        super(details[Consts.Staff.ID_COLUMN]);

        this.userID = details[Consts.Staff.ID_COLUMN];
        this.name = details[Consts.Staff.NAME_COLUMN];
        this.userRole = details[Consts.Staff.ROLE_COLUMN];
        this.gender = details[Consts.Staff.GENDER_COLUMN];
        this.age = Integer.parseInt(details[Consts.Staff.AGE_COLUMN]);

        this.handler = handler;
    }

    /**
     * Saves the current staff details to the data source.
     * Updates the corresponding row in the CSV file using the handler.
     */
    public void saveData() {
        String[] details = new String[6];
        details[Consts.Staff.PW_COLUMN] = getPassword();
        details[Consts.Staff.ID_COLUMN] = userID;
        details[Consts.Staff.NAME_COLUMN] = name;
        details[Consts.Staff.ROLE_COLUMN] = userRole;
        details[Consts.Staff.GENDER_COLUMN] = gender;
        details[Consts.Staff.AGE_COLUMN] = String.valueOf(age);

        try {
            handler.updateRow(Consts.Staff.ID_COLUMN, userID, details);
            System.out.println("Row updated successfully for UserID: " + userID);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Prompts the staff member to set a new password.
     * Verifies the new password before saving it.
     */
    public void _Set_new_password() {
        AttributeController ac = AttributeController.getInstance();
        String newPassword = ac.inputString("Enter new password:");
        String verify = ac.inputString("Verify the password:");
        if (verify.equals(newPassword)) {
            System.out.println("New password set");
            setPassword(newPassword);
            saveData();
        } else {
            System.out.println("Password does not match. Exiting to menu...");
        }
    }

    /**
     * Updates the user ID for the staff member.
     *
     * @param userID The new user ID to set.
     */
    public void setUserID(String userID) {
        this.userID = userID;
        saveData();
    }

    /**
     * Updates the name of the staff member.
     *
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
        saveData();
    }

    /**
     * Retrieves the role of the staff member.
     *
     * @return The role of the staff member.
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Updates the role of the staff member.
     *
     * @param userRole The new role to set.
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
        saveData();
    }

    /**
     * Updates the gender of the staff member.
     *
     * @param gender The new gender to set.
     */
    public void setGender(String gender) {
        this.gender = gender;
        saveData();
    }

    /**
     * Updates the age of the staff member.
     *
     * @param age The new age to set.
     */
    public void setAge(int age) {
        this.age = age;
        saveData();
    }
}
