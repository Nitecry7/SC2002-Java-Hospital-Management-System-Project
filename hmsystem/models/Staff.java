package hmsystem.models;
import hmsystem.data.Consts;
import hmsystem.io.*;
abstract class Staff extends User{

    String userID, name, userRole, gender;
    int age;
    IOHandler handler;

    public Staff(String details[], IOHandler handler) {
              super (details[Consts.Staff.ID_COLUMN], details[Consts.Staff.NAME_COLUMN], Integer.parseInt(details[Consts.Staff.AGE_COLUMN]),
                details[Consts.Staff.GENDER_COLUMN]);

        this.userID = details[Consts.Staff.ID_COLUMN];
        this.name = details[Consts.Staff.NAME_COLUMN];
        this.userRole = details[Consts.Staff.ROLE_COLUMN];
        this.gender = details[Consts.Staff.GENDER_COLUMN];
        this.age = Integer.parseInt(details[Consts.Staff.AGE_COLUMN]);

        this.handler = handler;
    }

       public void saveData() {
     
        String[] details = new String[5];
        details[Consts.Staff.ID_COLUMN] = userID;
        details[Consts.Staff.NAME_COLUMN] = name;
        details[Consts.Staff.ROLE_COLUMN] = userRole;
        details[Consts.Staff.GENDER_COLUMN] = gender;
        details[Consts.Staff.AGE_COLUMN] = String.valueOf(age);

        handler.updateRow(Consts.Staff.ID_COLUMN, userID, details);

    }

    @Override
    public void setUserID(String userID) {
        this.userID = userID;
        saveData();
    }

    @Override
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

    @Override
    public void setGender(String gender) {
        this.gender = gender;
        saveData();
    }

    @Override
    public void setAge(int age) {
        this.age = age;
        saveData();
    }

    

}
