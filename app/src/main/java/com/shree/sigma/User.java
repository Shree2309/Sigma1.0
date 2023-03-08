package com.shree.sigma;

public class User {
    private String uID;
    private String name;
    private String phoneNo;

    public User() {
    }

    public User(String uID, String name, String phoneNo) {
        this.uID = uID;
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
