package hmsystem.models;
import java.util.Scanner;

public abstract class User
{
    private String userID;
    private String name;
    private int age;
    private String gender;

    public User(String userID, String name, int age, String gender)
    {
        this.userID = userID;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // Getters
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

    public abstract void setUserID(String userID);

    public String getName() {
        return userID;
    }

    public abstract void setName(String name);

    public int getAge() {
        return age;
    }

    public abstract void setAge(int age);

    public String getGender() {
        return gender;
    }

    public abstract void setGender(String gender);
}
