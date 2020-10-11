package com.example.geobike.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.geobike.R;
import com.example.geobike.models.JwtToken;
import com.example.geobike.models.Login;
import com.example.geobike.models.User;
import com.example.geobike.formstate.AuthFormState;
import com.example.geobike.viewmodel.AuthViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.Subject;

public class LoginActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private ProgressBar loadingProgressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AuthViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        authViewModel.getAuthFormState().observe(this, new Observer<AuthFormState>() {
            @Override
            public void onChanged(AuthFormState authFormState) {
                if (authFormState == null) {
                    return;
                }
                loginButton.setEnabled(authFormState.isDataValid());
                if (authFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(authFormState.getUsernameError()));
                }
                if (authFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(authFormState.getPasswordError()));
                }
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                Login credentials = new Login("diego", "diego", false);
                authViewModel.loginDataChanged(credentials);
//                usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString()
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Login credentials = new Login("diego", "diego", false);
                    login(credentials);
//                    (usernameEditText.getText().toString(),
//                            passwordEditText.getText().toString()
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                Login credentials = new Login("diego", "diego", false);
                login(credentials);

            }
        });
    }

    public void login(Login credentials) {
        authViewModel.login(credentials)
                .flatMap(jwtToken -> authViewModel.account())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.rxjava3.core.Observer<User>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull User user) {
                        if (user == null) {
                            return;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        loadingProgressBar.setVisibility(View.GONE);
                        setResult(Activity.RESULT_OK);
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                    }
                });
    }

    private void updateUiWithUser() { //LoggedInUserView model
        String welcome = getString(R.string.welcome); //+ model.getDisplayName()
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
