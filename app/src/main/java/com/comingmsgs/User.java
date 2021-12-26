package com.comingmsgs;

public class User {
    private String Userid,Name,PhoneNumber,ProfileImage;
    // create constructors,getter and setters for each variable


    //create an empty constructor to sync with firebase

    public  User()
    {

    }




    public User(String userid, String name, String phoneNumber, String profileImage) {
        Userid = userid;
        Name = name;
        PhoneNumber = phoneNumber;
        ProfileImage = profileImage;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }
}
