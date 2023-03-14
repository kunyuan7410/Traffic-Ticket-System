package com.example.trafficticketsystem;

public class User {
    String username, password, name, email, phoneNo, plateNo, icNo, as;

    public User() {

    }

    public User(String username, String password, String name, String email, String phoneNo, String plateNo, String icNo, String as) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.plateNo = plateNo;
        this.icNo = icNo;
        this.as = as;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }
}
