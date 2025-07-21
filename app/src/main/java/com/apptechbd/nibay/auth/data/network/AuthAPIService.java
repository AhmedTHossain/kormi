package com.apptechbd.nibay.auth.data.network;

import com.apptechbd.nibay.auth.domain.model.GetLoginResponseModel;
import com.apptechbd.nibay.auth.domain.model.RegistrationResponse;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthAPIService {

    //for getting OTP
    @FormUrlEncoded
    @POST("mobile/get-otp")
    Call<JSONObject> getOtp(@Field("phone") String phone, @Field("deviceID") String deviceID);

    //for login user
    @FormUrlEncoded
    @POST("mobile/jobseekers/signin")
    Call<GetLoginResponseModel> login(@Field("phone") String phone, @Field("deviceID") String deviceID, @Field("otpCode") String otpCode);

    //for registering new user
    @Multipart
    @POST("mobile/jobseekers/register")
    Call<RegistrationResponse> register(
            @Part("phone") RequestBody phone,
            @Part("name") RequestBody name,
            @Part("nidNumber") RequestBody nidNumber,
            @Part("drivingLicense") RequestBody drivingLicense,
            @Part("division") RequestBody division,
            @Part("district") RequestBody district,
            @Part("yearsOfExperience") RequestBody yearsOfExperience,
            @Part("role") RequestBody role,
            @Part("maxEducationLevel") RequestBody maxEducationLevel,
            @Part("deviceID") RequestBody deviceID,
            @Part MultipartBody.Part nidCopy,
            @Part MultipartBody.Part drivingLicenseCopy,
            @Part MultipartBody.Part maxEducationLevelCertificateCopy,
            @Part MultipartBody.Part profilePhoto
    );

}
