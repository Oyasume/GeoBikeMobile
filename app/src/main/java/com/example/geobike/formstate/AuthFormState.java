package com.example.geobike.formstate;

import androidx.annotation.Nullable;

public class AuthFormState {
    @Nullable
    private Integer loginError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    public AuthFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.loginError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    public AuthFormState(boolean isDataValid) {
        this.loginError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getUsernameError() {
        return loginError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
