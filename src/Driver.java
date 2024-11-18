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
        int login = 1;
        AttributeController ac = AttributeController.getInstance();
        while (login == 1) {
            
            login = ac.inputInt("\nWhat would you like to do?:\n1. Log in\n2. Leave\nEnter your choice: ");

            
            if (login == 1) {
                User user = loginHandler.authenticate();
                if (user != null) {
                    IMenuController menuController = new MenuController(user);
                    
                    
                    menuController.takeInput();
                
                }
                else {
                    System.out.println("\nWrong ID or password\n");
                }
            }
            
        }
        System.out.println("Have a good day");
    
    }

}
