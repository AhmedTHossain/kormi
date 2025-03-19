package com.apptechbd.nibay.auth.presentation.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;

public class RegistrationViewModel extends AndroidViewModel {
    private final MutableLiveData<RegisterUserModel> userLiveData = new MutableLiveData<>();

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        // Initialize with a default user instance
        userLiveData.setValue(new RegisterUserModel());
    }

    public LiveData<RegisterUserModel> getUserLiveData() {
        return userLiveData;
    }

    public RegisterUserModel getUser() {
        return userLiveData.getValue();
    }

    public void setUser(RegisterUserModel registerUserModel) {
        if (registerUserModel != null) {
            userLiveData.setValue(registerUserModel);
        }
    }

    public void updateUserRole(String role) {
        if (userLiveData.getValue() != null) {
            RegisterUserModel currentUser = userLiveData.getValue();
            currentUser.setRole(role);
            userLiveData.setValue(currentUser);
        }
    }
}
