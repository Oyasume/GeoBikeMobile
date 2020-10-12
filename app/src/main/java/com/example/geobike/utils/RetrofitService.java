package com.example.geobike.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
//    private AuthService authService;
//
//    public AuthService getAuthService(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        authService = retrofit.create(AuthService.class);
//        return authService;
//    }

    public static Retrofit retrofit;

    public static Retrofit getInstance(SessionManager sessionManager){
        if( retrofit == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .addInterceptor(interceptor)
                    .addInterceptor(new TokenInterceptor(sessionManager.fetchAuthToken()))
                    .build();

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    .create();

            retrofit =  new retrofit2.Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .baseUrl(Constants.API_BASE_URL)
                    .client(client)
                    .build();
        }

        return retrofit;
    }
}
