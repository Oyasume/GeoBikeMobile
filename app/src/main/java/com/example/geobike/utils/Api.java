package com.example.geobike.utils;

import com.example.geobike.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface Api {


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


}
