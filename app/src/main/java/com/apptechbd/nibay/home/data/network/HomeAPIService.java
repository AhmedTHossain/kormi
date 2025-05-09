package com.apptechbd.nibay.home.data.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface HomeAPIService {
    @GET("mobile/follow/get-followed-employers")
    Call<JsonObject> getFollowedEmployers(@Header("Authorization") String authToken);

    @GET("mobile/jobs/get-jobs")
    Call<JsonObject> getJobAdvertisements(@Header("Authorization") String authToken, @Query("page") String page);
}
