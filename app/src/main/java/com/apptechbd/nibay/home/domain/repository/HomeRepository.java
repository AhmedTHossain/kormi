package com.apptechbd.nibay.home.domain.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.RetrofitInstance;
import com.apptechbd.nibay.home.data.network.HomeAPIService;
import com.apptechbd.nibay.home.domain.model.FollowedEmployer;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

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
}
