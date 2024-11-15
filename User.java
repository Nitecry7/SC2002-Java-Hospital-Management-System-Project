public abstract class User extends Profile
{
    public User(String hospitalID, String name, String emailAddress, String userRole)
    {
        super(hospitalID, name, emailAddress, DEFAULT_PASSWORD , userRole);
    }
    
    public abstract void displayMenu(); //Various Profiles will implement their own version of this method, therefore I have set it as Abstract

  

    public void displayMessage()
    {
        System.out.println("Welcome to the BrainRot Hospital!");
        System.out.println("Welcome And Glad To See You " + getUserRole() + " " + getName() + "!");
    }
}
