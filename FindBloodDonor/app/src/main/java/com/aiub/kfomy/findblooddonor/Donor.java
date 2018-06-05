package com.aiub.kfomy.findblooddonor;

public class Donor {
    String name,email,phone,age,pass,bloodGroup,gender;

    public Donor() {

        this.name = null;
        this.email = null;
        this.phone = null;
        this.age = null;
        this.pass = null;
        this.bloodGroup = null;
        this.gender = null;
    }

    public Donor(String name, String email, String phone, String age, String pass, String bloodGroup, String gender) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.pass = pass;
        this.bloodGroup = bloodGroup;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAge() {
        return age;
    }

    public String getPass() {
        return pass;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getGender() {
        return gender;
    }
}
