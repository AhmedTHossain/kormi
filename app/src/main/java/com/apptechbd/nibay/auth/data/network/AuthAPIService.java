package com.apptechbd.nibay.auth.data.network;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthAPIService {

    //for getting OTP
    @FormUrlEncoded
    @POST("mobile/get-otp")
    Call<JSONObject> getOtp(@Field("phone") String phone, @Field("deviceID") String deviceID);
}
