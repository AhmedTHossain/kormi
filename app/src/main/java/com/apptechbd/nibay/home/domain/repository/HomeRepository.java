package com.apptechbd.nibay.home.domain.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.RetrofitInstance;
import com.apptechbd.nibay.home.data.network.HomeAPIService;
import com.apptechbd.nibay.home.domain.model.AppliedJobsResponse;
import com.apptechbd.nibay.home.domain.model.EmployerRating;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public HomeRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<Boolean> getFollowedEmployers() {
        MutableLiveData<Boolean> isFollowedEmployersFetched = new MutableLiveData<>();

        HomeAPIService homeAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(HomeAPIService.class);
        Call<JsonObject> call = homeAPIService.getFollowedEmployers("Bearer " + helperClass.getAuthToken(context));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    isFollowedEmployersFetched.setValue(true);

                    JsonObject responseObj = response.body(); // ✅ Correct way to handle JSON
                    JsonArray dataArray = responseObj.getAsJsonArray("data"); // ✅ Extract "data" array

                    ArrayList<FollowedEmployer> followedEmployers = new ArrayList<>();
                    Gson gson = new Gson();

                    // Convert each JSON object in "data" array into a FollowedEmployer object
                    for (JsonElement element : dataArray) {
                        FollowedEmployer employer = gson.fromJson(element, FollowedEmployer.class);
                        followedEmployers.add(employer);
                    }
                    helperClass.saveFollowedEmployers(context, followedEmployers);
                } else
                    isFollowedEmployersFetched.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                isFollowedEmployersFetched.setValue(false);
            }
        });
        return isFollowedEmployersFetched;
    }

    public MutableLiveData<Boolean> getJobAdvertisements(String page) {
        MutableLiveData<Boolean> isJobAdvertisementsFetched = new MutableLiveData<>();
        HomeAPIService homeAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(HomeAPIService.class);
        Log.d("HomeRepository", "get bearer token sent: " + helperClass.getAuthToken(context));
        Call<JsonObject> call = homeAPIService.getJobAdvertisements("Bearer " + helperClass.getAuthToken(context), page);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    isJobAdvertisementsFetched.setValue(true);

                    JsonObject responseObj = response.body(); // ✅ Correct way to handle JSON
                    JsonArray dataArray = responseObj.getAsJsonArray("data"); // ✅ Extract "data" array

                    ArrayList<JobAd> jobAdvertisements = new ArrayList<>();
                    Gson gson = new Gson();

                    // Convert each JSON object in "data" array into a FollowedEmployer object
                    for (JsonElement element : dataArray) {
                        JobAd jobAd = gson.fromJson(element, JobAd.class);
                        jobAdvertisements.add(jobAd);
                    }
                    helperClass.saveJobAdvertisementList(context, jobAdvertisements);
                } else
                    isJobAdvertisementsFetched.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                isJobAdvertisementsFetched.setValue(false);
            }
        });
        return isJobAdvertisementsFetched;
    }

    public MutableLiveData<Boolean> getCompanyJobAdvertisements(String page, String id) {
        MutableLiveData<Boolean> isJobAdvertisementsFetched = new MutableLiveData<>();
        HomeAPIService homeAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(HomeAPIService.class);
        Log.d("HomeRepository", "get bearer token sent: " + helperClass.getAuthToken(context));
        Call<JsonObject> call = homeAPIService.getCompanyJobAdvertisements("Bearer " + helperClass.getAuthToken(context), id, page);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    isJobAdvertisementsFetched.setValue(true);

                    JsonObject responseObj = response.body(); // ✅ Correct way to handle JSON
                    JsonArray dataArray = responseObj.getAsJsonArray("data"); // ✅ Extract "data" array

                    ArrayList<JobAd> jobAdvertisements = new ArrayList<>();
                    Gson gson = new Gson();

                    // Convert each JSON object in "data" array into a FollowedEmployer object
                    for (JsonElement element : dataArray) {
                        JobAd jobAd = gson.fromJson(element, JobAd.class);
                        jobAdvertisements.add(jobAd);
                    }
                    helperClass.saveFollowedEmployerJobAdvertisementList(context, jobAdvertisements);
                } else
                    isJobAdvertisementsFetched.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                isJobAdvertisementsFetched.setValue(false);
            }
        });
        return isJobAdvertisementsFetched;
    }

    public MutableLiveData<ProfileRsponseData> getUserProfile() {
        MutableLiveData<ProfileRsponseData> userProfileFetched = new MutableLiveData<>();
        HomeAPIService homeAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(HomeAPIService.class);
        Call<ProfileResponse> call = homeAPIService.getUserProfile("Bearer " + helperClass.getAuthToken(context));
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    userProfileFetched.setValue(response.body().getData());
                else
                    userProfileFetched.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                userProfileFetched.setValue(null);
            }
        });
        return userProfileFetched;
    }

    public MutableLiveData<EmployerRatingResponseData> getReviews(String applicantId) {
        MutableLiveData<EmployerRatingResponseData> reviews = new MutableLiveData<>();
        HomeAPIService homeAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(HomeAPIService.class);
        Call<EmployerRatingResponse> call = homeAPIService.getReviews("Bearer " + helperClass.getAuthToken(context), applicantId);
        call.enqueue(new Callback<EmployerRatingResponse>() {
            @Override
            public void onResponse(@NonNull Call<EmployerRatingResponse> call, @NonNull Response<EmployerRatingResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    reviews.setValue(response.body().getData());
                else
                    reviews.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<EmployerRatingResponse> call, @NonNull Throwable t) {
                reviews.setValue(null);
            }
        });
        return reviews;
    }

    public MutableLiveData<AppliedJobsResponse> getAppliedJobs() {
        MutableLiveData<AppliedJobsResponse> appliedJobs = new MutableLiveData<>();
        HomeAPIService homeAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(HomeAPIService.class);
        Call<AppliedJobsResponse> call = homeAPIService.getAppliedJobs("Bearer " + helperClass.getAuthToken(context));
        call.enqueue(new Callback<AppliedJobsResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppliedJobsResponse> call, @NonNull Response<AppliedJobsResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    appliedJobs.setValue(response.body());
                else
                    appliedJobs.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<AppliedJobsResponse> call, @NonNull Throwable t) {
                appliedJobs.setValue(null);
            }
        });
        return appliedJobs;
    }

    public MutableLiveData<Boolean> uploadProfilePhoto(File profilePhoto) {
        MutableLiveData<Boolean> isProfilePhotoUploaded = new MutableLiveData<>();
        HomeAPIService homeAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(HomeAPIService.class);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("profilePhoto", profilePhoto.getName(), RequestBody.create(MediaType.parse("image/*"), profilePhoto));

        Call<JSONObject> call = homeAPIService.uploadProfilePhoto("Bearer " + helperClass.getAuthToken(context), filePart);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(@NonNull Call<JSONObject> call, @NonNull Response<JSONObject> response) {
                if (response.code() == 200)
                    isProfilePhotoUploaded.setValue(true);
                else
                    isProfilePhotoUploaded.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<JSONObject> call, @NonNull Throwable t) {
                isProfilePhotoUploaded.setValue(false);
            }
        });
        return isProfilePhotoUploaded;
    }

    public MutableLiveData<Boolean> uploadNIDPhoto(File nidPhoto) {
        MutableLiveData<Boolean> isNidPhotoUploaded = new MutableLiveData<>();
        HomeAPIService homeAPIService = RetrofitInstance.getRetrofitClient(helperClass.BASE_URL_V1).create(HomeAPIService.class);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("nidCopy", nidPhoto.getName(), RequestBody.create(MediaType.parse("image/*"), nidPhoto));

        Call<JSONObject> call = homeAPIService.uploadNIDPhoto("Bearer " + helperClass.getAuthToken(context), filePart);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(@NonNull Call<JSONObject> call, @NonNull Response<JSONObject> response) {
                if (response.code() == 200)
                    isNidPhotoUploaded.setValue(true);
                else
                    isNidPhotoUploaded.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<JSONObject> call, @NonNull Throwable t) {
                isNidPhotoUploaded.setValue(false);
            }
        });
        return isNidPhotoUploaded;
    }
}
