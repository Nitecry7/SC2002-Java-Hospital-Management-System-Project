package hmsystem.models;
public abstract class User
{
    private String userID;
    private String name;
    private int age;
    private String gender;
    private String email;
    private int contactNumber;
    private String userRole;

    public User(String userID, String name, int age, String gender, String email, int contactNumber, String userRole)
    {
        this.userID = userID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.contactNumber = contactNumber;
        this.userRole = userRole;
    }

    // Getters
    public String getUserID() 
    {
        return userID;
    }

    public String getName() 
    {
        return name;
    }

    public int getAge() 
    {
        return age;
    }

    public String getGender() 
    {
        return gender;
    }

    public String getEmail() 
    {
        return email;
    }

    public int getContactNumber() 
    {
        return contactNumber;
    }

    public String getUserRole() 
    {
        return userRole;
    }
}

