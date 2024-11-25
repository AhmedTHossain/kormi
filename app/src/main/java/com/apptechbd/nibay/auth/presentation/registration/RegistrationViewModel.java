package com.apptechbd.nibay.auth.presentation.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.apptechbd.nibay.auth.domain.models.RegisterUserModel;

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
