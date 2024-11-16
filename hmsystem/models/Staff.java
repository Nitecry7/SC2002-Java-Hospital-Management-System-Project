package hmsystem.models;

import hmsystem.data.Consts;
import hmsystem.io.*;
import java.io.IOException;



public abstract class Staff extends User {

    private String name, userRole, gender;
    private int age;
    private final IOHandler handler;

    public Staff(String details[], IOHandler handler) {
              super (details[Consts.Staff.ID_COLUMN]);

        this.name = details[Consts.Staff.NAME_COLUMN];
        this.userRole = details[Consts.Staff.ROLE_COLUMN];
        this.gender = details[Consts.Staff.GENDER_COLUMN];
        this.age = Integer.parseInt(details[Consts.Staff.AGE_COLUMN]);

        this.handler = handler;
    }

       public void saveData() throws IOException {
     
        String[] details = new String[5];
        details[Consts.Staff.ID_COLUMN] = getUserID();
        details[Consts.Staff.NAME_COLUMN] = name;
        details[Consts.Staff.ROLE_COLUMN] = userRole;
        details[Consts.Staff.GENDER_COLUMN] = gender;
        details[Consts.Staff.AGE_COLUMN] = String.valueOf(age);

        handler.updateRow(Consts.Staff.ID_COLUMN, getUserID(), details);

    }


    public String getName() {
        return name;
    }
  
    public void setName(String name) throws IOException {
        this.name = name;
        saveData();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) throws IOException {
        this.userRole = userRole;
        saveData();
    }

    public String getGender() {
        return gender;
    }

  
    public void setGender(String gender) throws IOException {
        this.gender = gender;
        saveData();
    }

    public int getAge() {
        return age;
    }


    public void setAge(int age) throws IOException {
        this.age = age;
        saveData();
    }

    

}
