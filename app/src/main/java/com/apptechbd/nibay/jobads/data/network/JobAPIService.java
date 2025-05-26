package com.apptechbd.nibay.jobads.data.network;

import com.apptechbd.nibay.jobads.domain.model.JobDetailsResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface JobAPIService {
//    @POST("mobile/follow/follow-employer")
//    Call<>

    //Get job ad details
    @GET("mobile/jobs/get-jobs/{jobId}")
    Call<JobDetailsResponse> getJobDetails(@Header("Authorization") String authToken, @Path("jobId") String jobId);

    //follow company/employer
    @FormUrlEncoded
    @POST("mobile/follow/follow-employer")
    Call<JSONObject> followEmployer(@Header("Authorization") String authToken, @Field("employerId") String employerId);

    //un-follow company/employer
    @FormUrlEncoded
    @POST("mobile/follow/unfollow-employer")
    Call<JSONObject> unFollowEmployer(@Header("Authorization") String authToken, @Field("employerId") String employerId);

    //apply job
    @FormUrlEncoded
    @POST("mobile/jobs/apply")
    Call<JSONObject> applyJob(@Header("Authorization") String authToken, @Field("jobId") String jobId);
}
