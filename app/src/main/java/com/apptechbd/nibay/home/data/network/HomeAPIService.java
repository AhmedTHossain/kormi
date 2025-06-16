package com.apptechbd.nibay.home.data.network;

import com.apptechbd.nibay.home.domain.model.AppliedJobsResponse;
import com.apptechbd.nibay.home.domain.model.EmployerRating;
import com.apptechbd.nibay.home.domain.model.EmployerRatingResponse;
import com.apptechbd.nibay.home.domain.model.ProfileResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeAPIService {
    @GET("mobile/follow/get-followed-employers")
    Call<JsonObject> getFollowedEmployers(@Header("Authorization") String authToken);

    @GET("mobile/jobs/get-jobs")
    Call<JsonObject> getJobAdvertisements(@Header("Authorization") String authToken, @Query("page") String page);

    @GET("mobile/jobs/get-jobs")
    Call<JsonObject> getCompanyJobAdvertisements(@Header("Authorization") String authToken, @Query("employerId") String employerId, @Query("page") String page);

    @GET("mobile/profile")
    Call<ProfileResponse> getUserProfile(@Header("Authorization") String authToken);

    @GET("reviews/{applicantId}")
    Call<EmployerRatingResponse> getReviews(@Header("Authorization") String authToken, @Path("applicantId") String applicantId);

    @GET("mobile/jobs/get-applied-jobs")
    Call<AppliedJobsResponse> getAppliedJobs(@Header("Authorization") String authToken);

    @Multipart
    @PATCH("mobile/profile")
    Call<JSONObject> uploadProfilePhoto(@Header("Authorization") String authToken, @Part MultipartBody.Part profilePhoto);
}
