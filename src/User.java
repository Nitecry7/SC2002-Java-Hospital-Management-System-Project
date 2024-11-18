package src;

public abstract class User
{
    private final String userID;
    private String password;

    public User(String userID)
    {
        this.userID = userID;
    }

    public void _Logout() {

        /*
        Scanner in = new Scanner(System.in);
        System.out.println("Are you sure? Enter Y to Exit or any other button to Continue");
        String e = in.nextLine();

        e = e.toUpperCase();

        if (e.equals("Y")) {
            System.exit(0);
        }
        else{
            System.out.println("Returning to menu...");
        }
            */

    
    }

    public String getUserID() {
        return userID;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    //public abstract void _Set_new_password();

}
