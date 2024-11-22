package com.apptechbd.kormi.auth.presentation.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apptechbd.kormi.auth.domain.models.RegisterUserModel;

public class RegistrationViewModel extends AndroidViewModel {
    private RegisterUserModel registerUserModelLiveData;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }

    public RegisterUserModel getRegisterUserModelLiveData() {
        return registerUserModelLiveData;
    }

    public void setRegisterUserModelLiveData(RegisterUserModel registerUserModelLiveData) {
        this.registerUserModelLiveData = registerUserModelLiveData;
    }
}
