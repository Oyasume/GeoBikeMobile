package com.example.geobike.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.geobike.R;

public class SessionManager {
    private static SharedPreferences prefs;
    private static SessionManager sessionManager;

    private static final String USER_TOKEN = "user_token";


    public SessionManager(Context context ){
        prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    /**
     * Fonction to save auth token
     * @param token
     */
    public void saveAuthToken(String token){
        prefs.edit().putString(USER_TOKEN, token).apply();
    }

    /**
     * Fonction to fetch auth token
     * @return
     */
    public String fetchAuthToken(){
        return prefs.getString(USER_TOKEN, "");
    }

}
