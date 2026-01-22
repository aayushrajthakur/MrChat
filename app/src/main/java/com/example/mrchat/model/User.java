package com.example.mrchat.model;

public class User {
    private String username;
    private String uniqueId;
    private String email;

    // Empty constructor required for Firebase
    public User() {}

    public User(String username, String uniqueId, String email) {
        this.username = username;
        this.uniqueId = uniqueId;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getEmail() {
        return email;
    }
}
