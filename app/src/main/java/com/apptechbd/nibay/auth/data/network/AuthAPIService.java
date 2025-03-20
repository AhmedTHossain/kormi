package com.apptechbd.nibay.auth.data.network;

import com.apptechbd.nibay.auth.domain.model.GetLoginResponseModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthAPIService {

    //for getting OTP
    @FormUrlEncoded
    @POST("mobile/get-otp")
    Call<JSONObject> getOtp(@Field("phone") String phone, @Field("deviceID") String deviceID);

    @FormUrlEncoded
    @POST("mobile/jobseekers/signin")
    Call<GetLoginResponseModel> login(@Field("phone") String phone, @Field("deviceID") String deviceID, @Field("otpCode") String otpCode);
}
