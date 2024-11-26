package com.apptechbd.nibay.auth.presentation.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.apptechbd.nibay.auth.domain.models.RegisterUserModel;

public class RegistrationViewModel extends AndroidViewModel {
    private RegisterUserModel user = new RegisterUserModel();

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }

    public RegisterUserModel getUser() {
        return user;
    }

    public void setUser(RegisterUserModel registerUserModelLiveData) {
        this.user = registerUserModelLiveData;
    }
}
