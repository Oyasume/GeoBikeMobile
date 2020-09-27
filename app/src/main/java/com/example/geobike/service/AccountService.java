package com.example.geobike.service;

import com.example.geobike.models.JwtToken;
import com.example.geobike.models.Login;
import com.example.geobike.models.User;
import com.example.geobike.utils.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountService extends Api {
    @POST("authenticate")
    Call<JwtToken> authenticate(@Body Login credential);

    @GET("account")
    Call<User> getAccount();


//    @POST("register")
//    Call<JwtToken> authenticate(@Body Login credential);
}
