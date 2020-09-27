package com.example.geobike.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    Long id;

    @SerializedName("login")
    String login;

    @SerializedName("lastname")
    String lastname;

    @SerializedName("firstname")
    String firstname;

    @SerializedName("email")
    String email;

    public User(Long id, String login, String lastname, String firstname, String email) {
        this.id = id;
        this.login = login;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
