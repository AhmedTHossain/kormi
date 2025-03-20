package com.apptechbd.nibay.auth.domain.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.auth.data.network.AuthAPIService;
import com.apptechbd.nibay.auth.domain.model.GetLoginResponseModel;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.RetrofitInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public AuthRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<Boolean> getOtp(String phone){
        MutableLiveData<Boolean> isOtpSent = new MutableLiveData<>();

        AuthAPIService authAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(AuthAPIService.class);
        Call<JSONObject> call = authAPIService.getOtp(phone,helperClass.getAndroidId(context));
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(@NonNull Call<JSONObject> call, @NonNull Response<JSONObject> response) {
                if (response.isSuccessful())
                    isOtpSent.setValue(true);
                else
                    isOtpSent.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<JSONObject> call, @NonNull Throwable t) {
                isOtpSent.setValue(false);
            }
        });
        return isOtpSent;
    }

    public MutableLiveData<Boolean> login(String phone, String otpCode) {
        MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();

        AuthAPIService authAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(AuthAPIService.class);
        Call<GetLoginResponseModel> call = authAPIService.login(phone, helperClass.getAndroidId(context), otpCode);
        call.enqueue(new Callback<GetLoginResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<GetLoginResponseModel> call, @NonNull Response<GetLoginResponseModel> response) {
                if (response.isSuccessful())
                    if (response.body() != null) {
                        helperClass.setAuthToken(context, response.body().getToken());
                        isLoginSuccessful.setValue(true);
                    } else helperClass.setAuthToken(context, null);
                else {
                    helperClass.setAuthToken(context, null);
                    isLoginSuccessful.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetLoginResponseModel> call, @NonNull Throwable t) {
                helperClass.setAuthToken(context, null);
                isLoginSuccessful.setValue(false);
            }
        });
        return isLoginSuccessful;
    }
}
