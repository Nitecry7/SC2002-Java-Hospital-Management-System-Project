package hmsystem.models;
import java.util.Scanner;

public abstract class User
{
    private final String userID;

    public User(String userID)
    {
        this.userID = userID;
    }

    public void _logout() {

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

    
    }

    public String getUserID() {
        return userID;
    }

}
