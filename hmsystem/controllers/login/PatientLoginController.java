package hmsystem.controllers.login;
import hmsystem.io.*;
import hmsyste.models.*;
//Singleton
public class PatientLoginController implements ILoginController {

    private static PatientLoginController loginController = null;

    private IOHandler handler = null;

    protected PatientLoginController(IOHandler handler) {
        this.handler = handler;

    }

    public static PatientLoginController getInstance(IOHandler handler) {
       if (handler != null) {
            loginController = new PatientLoginController(handler);
        }
            return loginController;
        
    }

    //Authentication for patients
    @Override
    public User authenticate(String userID, String password) {
        User user;

        try {

            String actualPassword = handler.getField(userID, "Password");

            if ((actualPassword.equals("") && password.equals("password")) || password.equals(actualPassword)) {

                user = (User) new Patient();
            }
            else {
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
