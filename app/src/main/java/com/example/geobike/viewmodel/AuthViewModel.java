package com.example.geobike.viewmodel;

import android.app.Application;
import android.util.Patterns;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.geobike.R;
import com.example.geobike.models.JwtToken;
import com.example.geobike.models.Login;
import com.example.geobike.models.User;
import com.example.geobike.repositories.AuthRepository;

public class AuthViewModel extends AndroidViewModel {

    private LiveData<JwtToken> token;
    private MutableLiveData<AuthFormState> authFormState ;
    private AuthRepository authRepository;

    public AuthViewModel(Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        token = new MutableLiveData<>();
        authFormState = new MutableLiveData<>();
    }

    public LiveData<User> login(Login credentials){


        authRepository.login(credentials);
        return authRepository.getAccount();


//        if (result instanceof Result.Success) {
//            com.example.geobike.activities.data.model.LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//            loginResult.setValue(new com.example.geobike.activities.ui.login.LoginResult(new com.example.geobike.activities.ui.login.LoggedInUserView(data.getDisplayName())));
//        } else {
//            loginResult.setValue(new com.example.geobike.activities.ui.login.LoginResult(R.string.login_failed));
//        }
    }


    public LiveData<AuthFormState> getAuthFormState() {
        return authFormState;
    }

    public void loginDataChanged(Login credentials) {
        if (!isUserNameValid(credentials.getUsername())) {
            authFormState.setValue(new AuthFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(credentials.getPassword())) {
            authFormState.setValue(new AuthFormState(null, R.string.invalid_password));
        } else {
            authFormState.setValue(new AuthFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String login) {
        if (login == null) {
            return false;
        }
        if (login.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(login).matches();
        } else {
            return !login.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public LiveData<User> getAccount(){
        return authRepository.getAccount();
    }


}
