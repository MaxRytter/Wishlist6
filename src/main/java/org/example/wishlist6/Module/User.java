package org.example.wishlist6.Module;

public class User {
    private int userId;
    private String userName;
    private String userEmail;
    private String passwordHash;
    //tilføj map senere

    public User(String userName, String userEmail, String passwordHash) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.passwordHash = passwordHash;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
}

// test, er der stadig conflict hvis jeg pusher nu?