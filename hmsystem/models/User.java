package hmsystem.models;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class User
{
    protected String hospitalID;
    protected String password;
    protected String role;
    protected List<Method> menuMethods;
    private String name;
    private int age;
    private String gender;
    private String email;
    private int contactNumber;


    public User(String hospitalID, String name, int age, String gender, String email, int contactNumber)
    {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.contactNumber = contactNumber;
        this.hospitalID = hospitalID;
        this.password = "password";
        this.menuMethods = new ArrayList<>();

        this.role = this.getClass().getSimpleName();

        Method methods[] = this.getClass().getMethods(); 
        for (Method method : methods) {
            if (method.getName().startsWith("_")) {
                menuMethods.add(method);
            }
        }
    }
    
    public abstract void displayMenu(); //Various Profiles will implement their own version of this method, therefore I have set it as Abstract

    public void _setPassword(String password)
    {
        Scanner sc = new Scanner(System.in);
        while (true)
        {            
            System.out.println("Please enter your password: ");
            String newPassword = sc.nextLine();
            System.out.println("Please enter your password again: ");
            String newPassword2 = sc.nextLine();
            if (newPassword.equals(newPassword2))
                this.password = password;
                break;
        }
    }

    public String getRole()
    {
        return this.role;
    }

    public void _logout()
    {
        System.out.println("Logging out...");
        // TODO: execute logout function, 
    }

    public void _displayMenu()
    {
        displayMenu();
    }

}