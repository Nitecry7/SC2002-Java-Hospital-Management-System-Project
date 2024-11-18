/**
 * Interface representing a login controller for authenticating users.
 */
public interface ILoginController {

    /**
     * Authenticates a user based on their user ID and password.
     *
     * @param userID   The unique identifier of the user.
     * @param password The password associated with the user.
     * @return A {@link User} object representing the authenticated user.
     * @throws Exception If authentication fails or an unexpected error occurs.
     */
    public User authenticate(String userID, String password) throws Exception;

}
