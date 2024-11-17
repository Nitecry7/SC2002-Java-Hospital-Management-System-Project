package hmsystem.controllers.login;
import hmsystem.models.User;
public interface ILoginController{


    public User authenticate(String userID, String password) throws Exception;

}
