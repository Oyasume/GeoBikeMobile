package com.example.geobike.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.geobike.models.User;
import com.example.geobike.service.AccountService;
import com.example.geobike.utils.RetrofitService;
import com.example.geobike.models.JwtToken;
import com.example.geobike.models.Login;
import com.example.geobike.utils.SessionManager;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;

public class AuthRepository {
    private AccountService authService;
    private LiveData<JwtToken> token;
    private static SessionManager sessionManager;
    private User user;

    public AuthRepository(Application application) {
         sessionManager = new SessionManager(application);
        Retrofit retrofitService = RetrofitService.getInstance(sessionManager);
        this.authService = retrofitService.create(AccountService.class);
    }

    public LiveData<JwtToken> loginOld(Login credentials){
//        MutableLiveData<JwtToken> tokenMutableLiveData = new MutableLiveData<>();
//        authService.authenticate(credentials).enqueue(new Callback<JwtToken>() {
//            @Override
//            public void onResponse(Call<JwtToken> call, Response<JwtToken> response) {
//                if (response.isSuccessful()) {
//                    Log.i("TEST AUTH SUCCESS", response.body().getId_token());
////                    authenticateSuccess(response.body(),credentials.getRememberMe());
//                    authenticateSuccess(response.body(), false);
//                    tokenMutableLiveData.setValue(response.body());
//                }else{
//                    Log.e("TEST AUTH ERROR", "no status code 200");
//                    this.onFailure(call, new Throwable());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JwtToken> call, Throwable t) {
//                Log.e("TEST AUTH ERROR", "Failure");
//                tokenMutableLiveData.setValue(null);
//            }
//        });
//        return tokenMutableLiveData;
        return null;
    }

    public Observable<JwtToken> login(Login credentials){
        return authService.authenticate(credentials)
                .doOnNext(jwtToken -> authenticateSuccess(jwtToken, false));
    }

    public Observable<User> getAccount(){
        return authService.getAccount();
    }

    public LiveData<User> getAccountOld(){
//        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
//        authService.getAccount().enqueue(new Callback<User>(){
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if(response.isSuccessful()){
//                    userMutableLiveData.setValue(response.body());
//                    Log.i("TEST ACCOUNT SUCCESS", response.body().getEmail());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                userMutableLiveData.setValue(null);
//            }
//        });
//        return userMutableLiveData;
        return null;
    }

    private static void authenticateSuccess(JwtToken response, boolean rememberMe){
        Log.i("AuthRepository", "saveAuthToken: " + response.getId_token());
        if(rememberMe){
            sessionManager.saveAuthToken(response.getId_token());
        }else{
            sessionManager.saveAuthToken(response.getId_token());
        }
    }

    private void setUser(User user){
        this.user = user;
    }
}
