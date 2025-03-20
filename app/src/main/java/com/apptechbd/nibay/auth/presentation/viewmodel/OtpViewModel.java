package com.apptechbd.nibay.auth.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apptechbd.nibay.auth.domain.repository.AuthRepository;

public class OtpViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    public LiveData<Boolean> ifLoginSuccessful;

    public OtpViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(getApplication().getApplicationContext());
    }

    public void login(String phone, String otpCode){
        ifLoginSuccessful = authRepository.login(phone, otpCode);
    }
}
