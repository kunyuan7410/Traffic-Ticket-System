package com.example.trafficticketsystem;

public class model {
    String name, username, password, ic, phoneNo, as="police";

    model() {

    }

    public model(String name, String username, String password, String ic, String phoneNo, String as) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.ic = ic;
        this.phoneNo = phoneNo;
        this.as = as;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }
}