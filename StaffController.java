//Singleton
public class StaffController {

    private  static final StaffController staffController = new StaffController();

    protected StaffController() {

    }

    public static StaffController getInstance() {
            return staffController;
        
    }

}
