package hmsystem.models;

class Administrator extends Staff {
    
    public Administrator(String userID, String name, int age, String gender, String email, int contactNumber, String userRole) {
        super(userID, name, age, gender, email, contactNumber, userRole);
    }
}
