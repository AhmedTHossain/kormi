package com.apptechbd.nibay.auth.domain.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.data.network.AuthAPIService;
import com.apptechbd.nibay.auth.domain.model.GetLoginResponseModel;
import com.apptechbd.nibay.auth.domain.model.LoginResult;
import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.auth.domain.model.RegistrationResponse;
import com.apptechbd.nibay.auth.domain.model.RegistrationResponseUser;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.RetrofitInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public AuthRepository(Context context) {
        this.context = context;
    }

    //GET OTP
    public MutableLiveData<Boolean> getOtp(String phone) {
        MutableLiveData<Boolean> isOtpSent = new MutableLiveData<>();

        AuthAPIService authAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(AuthAPIService.class);
        Call<JSONObject> call = authAPIService.getOtp(phone, helperClass.getAndroidId(context));
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

    //LOGIN
    public MutableLiveData<LoginResult> login(String phone, String otpCode) {
        MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

        AuthAPIService authAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(AuthAPIService.class);
        Call<GetLoginResponseModel> call = authAPIService.login(phone, helperClass.getAndroidId(context), otpCode);
        Log.d("AuthRepository", "android id = " + helperClass.getAndroidId(context));
        call.enqueue(new Callback<GetLoginResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<GetLoginResponseModel> call, @NonNull Response<GetLoginResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    helperClass.setAuthToken(context, response.body().getToken());
                    Log.d("AuthRepository", "token = " + response.body().getToken());
                    loginResult.setValue(new LoginResult(true,response.body().getMessage()));
                } else if (response.errorBody() != null) {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String message = jsonObject.getString("message");

                        loginResult.setValue(new LoginResult(false,message));
                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetLoginResponseModel> call, @NonNull Throwable t) {
                helperClass.setAuthToken(context, null);
                loginResult.setValue(new LoginResult(false,"Something went wrong. Please try again later."));
            }
        });
        return loginResult;
    }

    public MutableLiveData<RegistrationResponseUser> register(RegisterUserModel registerUser){
        final MutableLiveData<RegistrationResponseUser>[] user = new MutableLiveData[]{new MutableLiveData<>()};

        AuthAPIService authAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(AuthAPIService.class);

        // Convert form fields
        RequestBody mobileNumber = toRequestBody(registerUser.getMobileNumber());
        RequestBody fullName = toRequestBody(registerUser.getFullName());
        RequestBody nidNumber = toRequestBody(registerUser.getNidNumber());
        RequestBody drivingLicenseNumber = toRequestBody(registerUser.getDrivingLicenseNumber());
        RequestBody divisionName = toRequestBody(registerUser.getDivisionName());
        RequestBody districtName = toRequestBody(registerUser.getDistrictName());
        RequestBody yearsOfExperience = toRequestBody(registerUser.getYearsOfExperience());
        RequestBody role = toRequestBody(registerUser.getRole());
        RequestBody maxEducationLevel = toRequestBody(registerUser.getMaxEducationLevel());
        RequestBody deviceID = toRequestBody(registerUser.getDeviceID());

        MultipartBody.Part nidImage = toMultipart("nidPhoto", registerUser.getNidImage());
        MultipartBody.Part drivingLicenseImage = toMultipart("drivingLicensePhoto", registerUser.getDrivingLicenseImage());
        MultipartBody.Part certificateImage = toMultipart("maxEducationLevelCopy", registerUser.getCertificateImage());
        MultipartBody.Part profilePhotoImage = toMultipart("profilePhoto", registerUser.getProfilePhotoImage());

        // Make the call
        Call<RegistrationResponse> call = authAPIService.register(
                mobileNumber, fullName, nidNumber, drivingLicenseNumber,
                divisionName, districtName, yearsOfExperience, role, maxEducationLevel,deviceID,
                nidImage, drivingLicenseImage, certificateImage, profilePhotoImage
        );

        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegistrationResponse> call, @NonNull Response<RegistrationResponse> response) {
                if (response.isSuccessful() && response.body()!=null)
                    user[0].setValue(response.body().getData().getUser());
                else
                    user[0] = null;
            }

            @Override
            public void onFailure(@NonNull Call<RegistrationResponse> call, @NonNull Throwable t) {
                user[0] = null;
            }
        });
        return user[0];
    }

    private RequestBody toRequestBody(String value) {
        return RequestBody.create(value != null ? value : "", MediaType.parse("text/plain"));
    }

    private RequestBody toRequestBody(int value) {
        return RequestBody.create(String.valueOf(value), MediaType.parse("text/plain"));
    }

    private MultipartBody.Part toMultipart(String key, File file) {
        if (file == null) {
            // Send an empty part if file is null to avoid NullPointerException
            return MultipartBody.Part.createFormData(key, "",
                    RequestBody.create(new byte[0], MediaType.parse("application/octet-stream")));
        }
        RequestBody reqFile = RequestBody.create(file, MediaType.parse("image/*"));
        return MultipartBody.Part.createFormData(key, file.getName(), reqFile);
    }

}
