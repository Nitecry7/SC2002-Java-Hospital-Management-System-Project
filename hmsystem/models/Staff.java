package hmsystem.models;
abstract class Staff extends User {

    public Staff(String userID, String name, int age, String gender, String email, int contactNumber, String userRole) {
        super(userID, name, age, gender, email, contactNumber, userRole);
    }

}
