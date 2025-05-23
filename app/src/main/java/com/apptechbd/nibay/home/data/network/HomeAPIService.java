package com.apptechbd.nibay.home.data.network;

import com.apptechbd.nibay.home.domain.model.EmployerRating;
import com.apptechbd.nibay.home.domain.model.EmployerRatingResponse;
import com.apptechbd.nibay.home.domain.model.ProfileResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeAPIService {
    @GET("mobile/follow/get-followed-employers")
    Call<JsonObject> getFollowedEmployers(@Header("Authorization") String authToken);

    @GET("mobile/jobs/get-jobs")
    Call<JsonObject> getJobAdvertisements(@Header("Authorization") String authToken, @Query("page") String page);

    @GET("mobile/profile")
    Call<ProfileResponse> getUserProfile(@Header("Authorization") String authToken);

    @GET("reviews/{applicantId}")
    Call<EmployerRatingResponse> getReviews(@Header("Authorization") String authToken, @Path("applicantId") String applicantId);
}
