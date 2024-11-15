import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class User
{
    protected String hospitalID;
    protected String password;
    protected String role;
    protected List<Method> menuMethods;

    public User(String hospitalID, List<Method> methodNames )
    {
        this.hospitalID = hospitalID;
        this.password = "password";

        this.role = this.getClass().getSimpleName();
        this.menuMethods = methodNames;
    }
    
    public abstract void displayMenu(); //Various Profiles will implement their own version of this method, therefore I have set it as Abstract

    public void _setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return this.role;
    }

    public void _logout()
    {
        System.out.println("Logging out...");
    }

    public void _displayMenu()
    {
        displayMenu();
    }

}
