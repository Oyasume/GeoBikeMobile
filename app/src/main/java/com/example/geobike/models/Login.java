package com.example.geobike.models;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("rememberMe")
    private Boolean rememberMe;

    public Login(String username, String password, Boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
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

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
