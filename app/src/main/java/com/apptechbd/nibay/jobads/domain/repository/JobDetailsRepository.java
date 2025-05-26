package com.apptechbd.nibay.jobads.domain.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.RetrofitInstance;
import com.apptechbd.nibay.home.domain.model.JobAdDetails;
import com.apptechbd.nibay.jobads.data.network.JobAPIService;
import com.apptechbd.nibay.jobads.domain.model.JobDetailsResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public JobDetailsRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<JobAdDetails> getJobDetails(String jobId) {
        MutableLiveData<JobAdDetails> jobDetails = new MutableLiveData<>();
        JobAPIService jobAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(JobAPIService.class);
        Call<JobDetailsResponse> call = jobAPIService.getJobDetails("Bearer " + helperClass.getAuthToken(context), jobId);
        call.enqueue(new Callback<JobDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<JobDetailsResponse> call, @NonNull Response<JobDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    jobDetails.setValue(response.body().getData());
                else
                    jobDetails.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<JobDetailsResponse> call, @NonNull Throwable t) {

            }
        });
        return jobDetails;
    }

    public MutableLiveData<Boolean> followEmployer(String employerId){
        MutableLiveData<Boolean> isFollowed = new MutableLiveData<>();
        JobAPIService jobAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(JobAPIService.class);
        Call<JSONObject> call = jobAPIService.followEmployer("Bearer " + helperClass.getAuthToken(context),employerId);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(@NonNull Call<JSONObject> call, @NonNull Response<JSONObject> response) {
                if (response.isSuccessful() && response.body() != null)
                    isFollowed.setValue(true);
                else
                    isFollowed.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<JSONObject> call, @NonNull Throwable t) {
                isFollowed.setValue(false);
            }
        });
        return isFollowed;
    }

    public MutableLiveData<Boolean> unfollowEmployer(String employerId){
        MutableLiveData<Boolean> isUnfollowed = new MutableLiveData<>();
        JobAPIService jobAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(JobAPIService.class);
        Call<JSONObject> call = jobAPIService.unFollowEmployer("Bearer " + helperClass.getAuthToken(context),employerId);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(@NonNull Call<JSONObject> call, @NonNull Response<JSONObject> response) {
                if (response.isSuccessful() && response.body() != null)
                    isUnfollowed.setValue(true);
                else
                    isUnfollowed.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<JSONObject> call, Throwable t) {
                isUnfollowed.setValue(false);
            }
        });
        return isUnfollowed;
    }

    public MutableLiveData<Boolean> applyJob(String jobId){
        MutableLiveData<Boolean> isApplied = new MutableLiveData<>();
        JobAPIService jobAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(JobAPIService.class);
        Call<JSONObject> call = jobAPIService.applyJob("Bearer " + helperClass.getAuthToken(context),jobId);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(@NonNull Call<JSONObject> call, @NonNull Response<JSONObject> response) {
                if (response.isSuccessful() && response.body() != null)
                    isApplied.setValue(true);
                else
                    isApplied.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<JSONObject> call, @NonNull Throwable t) {
                isApplied.setValue(false);
            }
        });
        return isApplied;
    }
}
