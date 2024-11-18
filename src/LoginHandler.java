public class LoginHandler implements ILoginHandler {


    private ILoginController loginController;
    private final AttributeController attributeController;

    public LoginHandler() {
        this.attributeController = AttributeController.getInstance();
    }

    
    @Override
    public User authenticate() throws Exception {
        
        String[] allUserTypes = Consts.USER_TYPES;

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
        Class<?> constantClass = Class.forName("Consts$" + allUserTypes[input-1]);
        String fileName = (String) constantClass.getField("FILE_NAME").get(null);

        /* 
        if(userType.equals("Patient")){
            loginController = PatientLoginController.getInstance(new CsvHandler(fileName));
        }else{
            loginController = StaffLoginController.getInstance(new CsvHandler(fileName));
        }
        */
        loginController = (ILoginController) Class.forName(allUserTypes[input-1] + "LoginController").getMethod("getInstance", IOHandler.class).invoke(null, new CsvHandler(fileName));
        
        //Authenticate using that login controller until either the user gets in or gives up
        String ID, pw;
        //boolean tryAgain = false;
        //do {
            /*
            if (tryAgain) {
                if (attributeController.inputString("Quit?(y/n)").toUpperCase().equals("Y")) {
                    System.exit(0);
                }
            }
                */
        ID = attributeController.inputString("Input ID");
        pw = attributeController.inputString("Input password");
            
            //tryAgain = true;
        //} while ((user = loginController.authenticate(ID,pw)) == null);

        user = loginController.authenticate(ID,pw);

        return user;


    }

}
