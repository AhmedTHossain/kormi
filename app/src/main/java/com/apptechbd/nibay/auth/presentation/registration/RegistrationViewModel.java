package com.apptechbd.nibay.auth.presentation.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.auth.domain.repository.AuthRepository;

public class RegistrationViewModel extends AndroidViewModel {
    private final MutableLiveData<RegisterUserModel> userLiveData = new MutableLiveData<>();
    public LiveData<Boolean> ifOtpSent;
    private AuthRepository authRepository;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        // Initialize with a default user instance
        userLiveData.setValue(new RegisterUserModel());
        authRepository = new AuthRepository(getApplication().getApplicationContext());
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

    public void getOtp(String phone){
        ifOtpSent = authRepository.getOtp(phone);
    }
}
