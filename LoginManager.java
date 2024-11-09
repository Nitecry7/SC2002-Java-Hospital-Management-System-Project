import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class LoginManager {

    //.csv field numbers
    public static final int ID_FIELD = 0, PASSWORD_FIELD = 1;
    public static final int PATIENT_NAME_FIELD = 2, PATIENT_DOB_FIELD = 3, PATIENT_GENDER_FIELD = 4, PATIENT_BLOODTYPE_FIELD = 5, PATIENT_CONTACTINFO_FIELD = 6;
    public static final int STAFF_NAME_FIELD = 2, STAFF_ROLE_FIELD = 3, STAFF_GENDER_FIELD = 4, STAFF_AGE_FIELD  = 5;

    public static User authenticate() throws IOException, ClassNotFoundException, InstantiationException {

        //Placeholder User object
        User user = null;
        //Enter User ID
        Scanner in = new Scanner(System.in);
        System.out.println("Enter User ID:");
        String ID = in.nextLine();

        //The number of characters in a Patient's ID is 4 while a Staff's is 3, so you can tell which file to search
        String fileName = (ID.length() == 4) ? "Patient_List.csv" : "Staff_List.csv";

        //Placeholder string for roleName to help with error checking later (in case an invalid role is entered into the file)
        String roleName = "";


        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String userDetails;
            //Reading through records line by line to search for ID match
            while((userDetails = br.readLine()) != null) {
                //Splitting csv into strings
                String[] userFields = userDetails.split(",");

                //Checking if the line contains the entered hospital ID
                if(userFields[ID_FIELD].equals(ID)) {

                    //If it does, prompt user to enter password and verify
                    String pw = in.nextLine();

                    //If password matches
                    if((userFields[PASSWORD_FIELD].equals("") && pw.equals("password")) || (userFields[PASSWORD_FIELD].equals(pw))) {

                        //"Patient" object doesn't current have a Role field
                        //"Patient" must change to userFields[x] (x would be the future "role" field number) if we decide to add different types of patients in the future (minimal change required)
                        roleName = (ID.length() == 4) ? "Patient" : userFields[STAFF_ROLE_FIELD];

                        //Using reference to find the Class with the name of the role and create an object of that class and instantiating it using its paramterless constructor
                        Class<?> obj = Class.forName(roleName);
                        user = (User) obj.getDeclaredConstructor().newInstance();
                        
                    }
                    else {
                        System.out.println("Wrong password");
                    }
                }
            }
            if (user == null) {
                System.out.println("User not found");
                System.exit(0);
            }

        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Illegal role type: " + roleName);
        } catch (Exception e) {
            throw new InstantiationException("Likely no parameterless constructor found: " + roleName + "\n" + Arrays.toString(e.getStackTrace()));
        }

        return user;

    }

}
