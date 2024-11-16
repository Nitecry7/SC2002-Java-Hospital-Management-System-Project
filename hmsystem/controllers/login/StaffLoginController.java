package hmsystem.controllers.login;
import hmsystem.data.Consts;
import hmsystem.io.*;
import hmsystem.models.*;
//Singleton
public class StaffLoginController implements ILoginController {

    private static StaffLoginController loginController = null;

    private IOHandler handler = null;

    protected StaffLoginController(IOHandler handler) {
        this.handler = handler;
    }

    public static StaffLoginController getInstance(IOHandler handler) {
        if (handler != null) {
            loginController = new StaffLoginController(handler);
        }
            return loginController;
        
    }

    //Authentication for staff (added consideration for role type)
    @Override
    public User authenticate(String userID, String password) throws Exception {
        
        User user;
        String roleName = "";

        try {

            String actualPassword = handler.getField(Consts.Staff.ID_COLUMN, userID, Consts.Staff.PW_COLUMN);

            if ((actualPassword.equals("") && password.equals("password")) || password.equals(actualPassword)) {
                roleName = handler.getField(Consts.Staff.ID_COLUMN, userID, Consts.Staff.ROLE_COLUMN);
                Class<?> staffRole = Class.forName(roleName);
                user = (User) staffRole.getDeclaredConstructor(String.class).newInstance(userID);
            }
            else {
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