package org.example.wishlist6.Module;

public class User {
    private int userId;
    private String userName;
    private String userEmail;
    private String passwordHash;
    //tilføj map senere

    public User(int userId, String userName, String userEmail, String passwordHash) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.passwordHash = passwordHash;
    }
    public User(){

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

    public String getpasswordHash() {
        return passwordHash;
    }
    public void setpasswordHash(String hashedPassword) {
        this.passwordHash = hashedPassword;
    }

}
