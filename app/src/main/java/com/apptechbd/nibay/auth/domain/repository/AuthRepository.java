package com.apptechbd.nibay.auth.domain.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.auth.data.network.AuthAPIService;
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
}
