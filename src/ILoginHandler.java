/**
 * Interface representing a login handler responsible for user authentication.
 */
public interface ILoginHandler {

    /**
     * Authenticates a user and returns a {@link User} object if successful.
     *
     * @return A {@link User} object representing the authenticated user.
     * @throws Exception If authentication fails or an error occurs during the process.
     */
    public User authenticate() throws Exception;
}
