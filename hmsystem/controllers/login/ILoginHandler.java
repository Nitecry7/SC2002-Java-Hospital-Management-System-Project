package hmsystem.controllers.login;
import hmsystem.models.User;

public interface ILoginHandler {
    public User authenticate() throws Exception;
}
