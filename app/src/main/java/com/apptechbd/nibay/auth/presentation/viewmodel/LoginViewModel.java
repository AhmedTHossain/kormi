package com.apptechbd.nibay.auth.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apptechbd.nibay.auth.domain.repository.AuthRepository;

public class LoginViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    public LiveData<Boolean> ifOtpSent;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(getApplication().getApplicationContext());
    }

    public void getOtp(String phone){
        ifOtpSent = authRepository.getOtp(phone);
    }
}
