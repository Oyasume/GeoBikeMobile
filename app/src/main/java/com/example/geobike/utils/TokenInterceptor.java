package com.example.geobike.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenInterceptor implements Interceptor {

    private String token;

    public TokenInterceptor(String token){
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request origninal = chain.request();

        if((origninal.url().encodedPath().contains("/authenticate") && origninal.method().equals("post"))
            || (origninal.url().encodedPath().contains("/register") && origninal.method().equals("post"))){
            return chain.proceed(origninal);
        }
        HttpUrl originalHttpUrl = origninal.url();
        Request.Builder requestBuilder = origninal.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .url(originalHttpUrl);

        return chain.proceed(requestBuilder.build());
    }
}
