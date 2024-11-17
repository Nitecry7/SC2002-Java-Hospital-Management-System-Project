
import java.util.Scanner;
public abstract class Staff extends User{

    String userID, name, userRole, gender;
    int age;
    IOHandler handler;

    public Staff(String details[], IOHandler handler) {
              super (details[Consts.Staff.ID_COLUMN]);

        this.userID = details[Consts.Staff.ID_COLUMN];
        this.name = details[Consts.Staff.NAME_COLUMN];
        this.userRole = details[Consts.Staff.ROLE_COLUMN];
        this.gender = details[Consts.Staff.GENDER_COLUMN];
        this.age = Integer.parseInt(details[Consts.Staff.AGE_COLUMN]);

        this.handler = handler;
    }

       public void saveData() {
     
        String[] details = new String[6];
        details[Consts.Staff.PW_COLUMN] = getPassword();
        details[Consts.Staff.ID_COLUMN] = userID;
        details[Consts.Staff.NAME_COLUMN] = name;
        details[Consts.Staff.ROLE_COLUMN] = userRole;
        details[Consts.Staff.GENDER_COLUMN] = gender;
        details[Consts.Staff.AGE_COLUMN] = String.valueOf(age);

        try {
            // Attempt to update the row in the CSV file
            handler.updateRow(Consts.Staff.ID_COLUMN, userID, details);
            System.out.println("Row updated successfully for UserID: " + userID);
        } catch (Exception e) {
            // Catch-all for any other unexpected exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }

    }

    @Override
    public void _Set_new_password() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter new password");
        String newPassword = in.nextLine();
        System.out.println("Enter it again");
        if (in.nextLine().equals(newPassword)) {
            System.out.println("New password set");
            setPassword(newPassword);
            saveData();
        }
        else {
            System.out.println("Password does not match. Exiting to menu...");
        }
    }
   
    public void setUserID(String userID) {
        this.userID = userID;
        saveData();
    }

  
    public void setName(String name) {
        this.name = name;
        saveData();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
        saveData();
    }

  
    public void setGender(String gender) {
        this.gender = gender;
        saveData();
    }


    public void setAge(int age) {
        this.age = age;
        saveData();
    }

   

    

}
