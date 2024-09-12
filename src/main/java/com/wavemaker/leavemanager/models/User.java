package com.wavemaker.leavemanager.models;

public class User {
    String email;
    String password;

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
