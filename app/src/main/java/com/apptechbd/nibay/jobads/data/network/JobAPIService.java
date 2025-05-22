package com.apptechbd.nibay.jobads.data.network;

import com.apptechbd.nibay.jobads.domain.model.JobDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JobAPIService {
//    @POST("mobile/follow/follow-employer")
//    Call<>

    //Get job ad details
    @GET("mobile/jobs/get-jobs/{jobId}")
    Call<JobDetailsResponse> getJobDetails(@Header("Authorization") String authToken, @Path("jobId") String jobId);
}
