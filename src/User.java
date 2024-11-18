/**
 * Abstract class representing a generic user in the system.
 * Provides common attributes and functionalities shared by all user types.
 */
public abstract class User {

    /**
     * Unique identifier for the user.
     */
    private final String userID;

    /**
     * Password associated with the user account.
     */
    private String password;

    /**
     * Constructor to initialize a User with a unique identifier.
     *
     * @param userID The unique identifier for the user.
     */
    public User(String userID) {
        this.userID = userID;
    }

    /**
     * Logs out the user. This method can be extended to provide specific logout behavior.
     */
    public void _Logout() {
        // Placeholder for logout functionality.
    }

    /**
     * Retrieves the unique identifier for the user.
     *
     * @return The user's unique identifier.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Retrieves the password associated with the user.
     * This method is protected to restrict access to subclasses.
     *
     * @return The user's password.
     */
    protected String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     * This method is protected to restrict access to subclasses.
     *
     * @param password The new password for the user.
     */
    protected void setPassword(String password) {
        this.password = password;
    }

    // Abstract method for subclasses to implement their own password-setting behavior.
    // public abstract void _Set_new_password();
}
