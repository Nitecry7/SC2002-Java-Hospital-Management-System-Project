package hmsystem.controllers;
import hmsystem.controllers.login.*;
import hmsystem.models.*;

public class Driver{

    public static void main(String[] args) throws Exception {

        //Default login handler and attribute controller
        ILoginHandler loginHandler =  new LoginHandler();
        switch (args.length) {
            case 0 -> {
                System.out.println("Default config chosen");
            }
            case 1 -> {
                try {

                    System.out.println("Attempting custom config");
                    //Custom login handler specified by args
                    loginHandler = (ILoginHandler) Class.forName(args[0]).getDeclaredConstructor().newInstance();
                    
                }
                
                catch (Exception e) {
                    System.out.println("Invalid controller names");
                    System.exit(0);
                }
            }

            default -> {
                System.out.println("Invalid input");
                System.exit(0);
            }
        }

        //Authetnicate using chosen handler
        User user = loginHandler.authenticate();
        IMenuController menuController = new MenuController(user);
        
        while (true) { 
            menuController.takeInput();
        }

    
    }

}
