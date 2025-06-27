package com.apptechbd.nibay.home.domain.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.RetrofitInstance;
import com.apptechbd.nibay.home.data.network.HomeAPIService;
import com.apptechbd.nibay.home.domain.model.AppliedJobsResponse;
import com.apptechbd.nibay.home.domain.model.EmployerRatingResponse;
import com.apptechbd.nibay.home.domain.model.EmployerRatingResponseData;
import com.apptechbd.nibay.home.domain.model.FollowedEmployer;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.apptechbd.nibay.home.domain.model.ProfileResponse;
import com.apptechbd.nibay.home.domain.model.ProfileRsponseData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {

    private final Context context;
    private final HomeAPIService homeAPIService;
    private final HelperClass helperClass;
    private final Gson gson = new Gson();

    public HomeRepository(Context context) {
        this.context = context.getApplicationContext();
        this.helperClass = new HelperClass();
        this.homeAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1)
                .create(HomeAPIService.class);
    }

    private String getAuthHeader() {
        return "Bearer " + helperClass.getAuthToken(context);
    }

    public LiveData<Boolean> getFollowedEmployers() {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        homeAPIService.getFollowedEmployers(getAuthHeader()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonArray dataArray = response.body().getAsJsonArray("data");
                    List<FollowedEmployer> employers = new ArrayList<>();
                    for (JsonElement element : dataArray) {
                        employers.add(gson.fromJson(element, FollowedEmployer.class));
                    }
                    helperClass.saveFollowedEmployers(context, new ArrayList<>(employers));

                    result.setValue(true);
                } else {
                    result.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                result.setValue(false);
            }
        });
        return result;
    }

    public LiveData<List<JobAd>> getJobAdvertisements(String page) {
        return fetchJobAds(homeAPIService.getJobAdvertisements(getAuthHeader(), page));
    }

    public LiveData<List<JobAd>> getCompanyJobAdvertisements(String page, String companyId) {
        return fetchJobAds(homeAPIService.getCompanyJobAdvertisements(getAuthHeader(), companyId, page));
    }

    public LiveData<List<JobAd>> getRoleJobAdvertisements(String page, String role) {
        return fetchJobAds(homeAPIService.getRoleJobAdvertisements(getAuthHeader(), role, page));
    }

    private LiveData<List<JobAd>> fetchJobAds(Call<JsonObject> call) {
        MutableLiveData<List<JobAd>> result = new MutableLiveData<>();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonArray dataArray = response.body().getAsJsonArray("data");
                    List<JobAd> jobAds = new ArrayList<>();
                    for (JsonElement element : dataArray) {
                        jobAds.add(gson.fromJson(element, JobAd.class));
                    }
                    helperClass.saveFollowedEmployerJobAdvertisementList(context, new ArrayList<>(jobAds));

                    result.setValue(jobAds);
                } else {
                    result.setValue(Collections.emptyList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                result.setValue(Collections.emptyList());
            }
        });
        return result;
    }

    public LiveData<ProfileRsponseData> getUserProfile() {
        MutableLiveData<ProfileRsponseData> result = new MutableLiveData<>();
        homeAPIService.getUserProfile(getAuthHeader()).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                result.setValue(response.isSuccessful() && response.body() != null ? response.body().getData() : null);
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                result.setValue(null);
            }
        });
        return result;
    }

    public LiveData<EmployerRatingResponseData> getReviews(String applicantId) {
        MutableLiveData<EmployerRatingResponseData> result = new MutableLiveData<>();
        homeAPIService.getReviews(getAuthHeader(), applicantId).enqueue(new Callback<EmployerRatingResponse>() {
            @Override
            public void onResponse(@NonNull Call<EmployerRatingResponse> call, @NonNull Response<EmployerRatingResponse> response) {
                result.setValue(response.isSuccessful() && response.body() != null ? response.body().getData() : null);
            }

            @Override
            public void onFailure(@NonNull Call<EmployerRatingResponse> call, @NonNull Throwable t) {
                result.setValue(null);
            }
        });
        return result;
    }

    public LiveData<AppliedJobsResponse> getAppliedJobs() {
        MutableLiveData<AppliedJobsResponse> result = new MutableLiveData<>();
        homeAPIService.getAppliedJobs(getAuthHeader()).enqueue(new Callback<AppliedJobsResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppliedJobsResponse> call, @NonNull Response<AppliedJobsResponse> response) {
                result.setValue(response.isSuccessful() && response.body() != null ? response.body() : null);
            }

            @Override
            public void onFailure(@NonNull Call<AppliedJobsResponse> call, @NonNull Throwable t) {
                result.setValue(null);
            }
        });
        return result;
    }

    public LiveData<Boolean> uploadProfilePhoto(File photo) {
        return uploadPhoto(photo, "profilePhoto", homeAPIService::uploadProfilePhoto);
    }

    public LiveData<Boolean> uploadNIDPhoto(File photo) {
        return uploadPhoto(photo, "nidCopy", homeAPIService::uploadNIDPhoto);
    }

    private LiveData<Boolean> uploadPhoto(File photo, String formKey, PhotoUploadFunction uploadFunction) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), photo);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(formKey, photo.getName(), body);

        uploadFunction.upload(getAuthHeader(), filePart).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(@NonNull Call<JSONObject> call, @NonNull Response<JSONObject> response) {
                result.setValue(response.code() == 200);
            }

            @Override
            public void onFailure(@NonNull Call<JSONObject> call, @NonNull Throwable t) {
                result.setValue(false);
            }
        });
        return result;
    }

    private interface PhotoUploadFunction {
        Call<JSONObject> upload(String authHeader, MultipartBody.Part file);
    }

    public LiveData<List<JobAd>> getCompanyRoleJobAdvertisements(String page, String employerId, String role) {
        return fetchJobAds(homeAPIService.getCompanyRoleJobAdvertisements(getAuthHeader(), employerId, role, page));
    }
}