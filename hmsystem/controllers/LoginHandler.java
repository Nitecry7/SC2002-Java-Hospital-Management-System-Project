package hmsystem.controllers;

public class LoginHandler implements ILoginHandler {

    private ILoginController loginController;
    private final AttributeController attributeController;

    protected LoginHandler() {
        this.attributeController = new AttributeController();
    }

    
    @Override
    public User authenticate() throws Exception {
        
        String[] allUserTypes = EntityConstants.USER_TYPES;

        //Choose staff or patient or anything else that has its own table
        System.out.println("Choose user type:\n");

        for (int x = 0; x < allUserTypes.length; x++) {

            System.out.println((x+1) + ". " + allUserTypes[x]);
        }

        int input = attributeController.inputInt("");
        while (input < 1 || input > allUserTypes.length){
            System.out.println("Invalid input");
            input = attributeController.inputInt("");

        }

        User user = null;
        //Get the appropriate login controller for the related file
        Class<?> constantClass = Class.forName("EntityConstants$" + allUserTypes[input-1]);
        String fileName = (String) constantClass.getField("FILE_NAME").get(null);

        loginController = (ILoginController) Class.forName(allUserTypes[input-1] + "LoginController").getMethod("getInstance", IOHandler.class).invoke(null, new CSVHandler(fileName));
        
        //Authenticate using that login controller until either the user gets in or gives up
        String ID, pw;
        boolean tryAgain = false;
        do {
            if (tryAgain) {
                System.out.println("Quit? Q");
                if (attributeController.inputString("").toUpperCase().equals(input)) {
                    System.exit(0);
                }
            }
            System.out.println("Input ID");
            ID = attributeController.inputString("Input ID");

            System.out.println("Input Password");
            pw = attributeController.inputString("Input ID");
            
            tryAgain = true;
        } while ((user = loginController.authenticate(ID,pw)) == null);


        return user;


    }

}
