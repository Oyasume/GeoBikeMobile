package com.example.geobike.models;

import com.google.gson.annotations.SerializedName;

public class JwtToken {

    @SerializedName("id_token")
    private String id_token;

    public JwtToken(String id_token) {
        this.id_token = id_token;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }
}
