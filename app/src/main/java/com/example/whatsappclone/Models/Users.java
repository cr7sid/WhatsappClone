package com.example.whatsappclone.Models;

public class Users {

    private String profilePic, userName , email, userId, password, lastMessage, about;



    public Users() {
        //Empty constructor required for Firebase
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //SignUp constructor
    public Users(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Users(String profilePic, String userName, String email, String userId, String password, String lastMessage, String about) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.lastMessage = lastMessage;
        this.about = about;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
