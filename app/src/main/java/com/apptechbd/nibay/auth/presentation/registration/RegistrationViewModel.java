package com.apptechbd.nibay.auth.presentation.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.auth.domain.model.LoginResult;
import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.auth.domain.model.RegistrationResponseUser;
import com.apptechbd.nibay.auth.domain.repository.AuthRepository;
import com.apptechbd.nibay.home.domain.repository.HomeRepository;

import java.io.File;

public class RegistrationViewModel extends AndroidViewModel {
    private final MutableLiveData<RegisterUserModel> userLiveData = new MutableLiveData<>();
    public LiveData<String> ifOtpSent;
    private AuthRepository authRepository;
    private final HomeRepository homeRepository;
    public LiveData<String> isRegistrationSuccessful;
    public LiveData<String> isInitialAccountCreationSuccessful;
    public final MutableLiveData<Integer> nextPageRequest = new MutableLiveData<>();

    private final MutableLiveData<Boolean> _isProfilePhotoUploaded = new MutableLiveData<>();
    public final LiveData<Boolean> isProfilePhotoUploaded = _isProfilePhotoUploaded;

    private final MutableLiveData<Boolean> _isCertificatePhotoUploaded = new MutableLiveData<>();
    public final LiveData<Boolean> isCertificatePhotoUploaded = _isCertificatePhotoUploaded;

    public LiveData<LoginResult> loginResult;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        // Initialize with a default user instance
        userLiveData.setValue(new RegisterUserModel());
        authRepository = new AuthRepository(getApplication().getApplicationContext());
        homeRepository = new HomeRepository(getApplication().getApplicationContext());
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

    public void getOtp(String phone){
        ifOtpSent = authRepository.getOtp(phone);
    }

    public void goToNextPage(int currentPageIndex) {
        nextPageRequest.setValue(currentPageIndex + 1);
    }

    public void registerUser(RegisterUserModel user){
        isRegistrationSuccessful = authRepository.register(user);
    }

    public void createInitialAccountForUser(RegisterUserModel user){
        isInitialAccountCreationSuccessful = authRepository.createInitialAccountForUser(user);
    }

    public void login(String phone, String otpCode){
        loginResult = authRepository.login(phone, otpCode);
    }

    public void uploadProfilePhoto(File photo) {
        homeRepository.uploadProfilePhoto(photo).observeForever(_isProfilePhotoUploaded::postValue);
    }

    public void uploadCertificatePhoto(File photo) {
        homeRepository.uploadCertificatePhoto(photo).observeForever(_isCertificatePhotoUploaded::postValue);
    }
}
