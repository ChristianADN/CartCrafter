package com.example.cartcrafter.models;

public class RegisterModel extends ModelBase {
    private String userName;
    private String email;
    private String password;

    public RegisterModel(String user, String email, String password) {
        this.userName = user;
        this.email = email;
        this.password = password;
    }

    public String getUser() {
        return userName;
    }

    public void setUser(String user) {
        this.userName = user;
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
}
