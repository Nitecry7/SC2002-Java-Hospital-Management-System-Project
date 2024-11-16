package hmsystem.models;

import hmsystem.models.enums.BloodType;

public class Patient extends User {

    private String dateOfBirth;
    private BloodType bloodType;

    public Patient(String userID, String name, int age, String gender, String email, int contactNumber, String userRole,
            String dateOfBirth, BloodType bloodType) {
        super(userID, name, age, gender, email, contactNumber, userRole);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public String getName() {
        return super.getName();
    }

    public String getEmail() {
        return super.getEmail();
    }

    public int getContactNumber() {
        return super.getContactNumber();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

public void setContactNumber(int contactNumber) {
        super.setContactNumber(con
