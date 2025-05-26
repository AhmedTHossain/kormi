package com.apptechbd.nibay.core.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apptechbd.nibay.home.domain.model.FollowedEmployer;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HelperClass {
    public String BASE_URL_V1 = "https://nibay.co/api/v1/";

    //there's no v2 of the APIs at present just keeping it for future iteration
    public String BASE_URL_V2 = "https://nibay.co/api/v2/";

    private Gson gson = new Gson();

    @SuppressLint("HardwareIds")
    public String getAndroidId(Context context) {
        String deviceID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("HelperClass","Device ID Fetched = "+ deviceID);

        return deviceID;
    }

    public String formatPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("-", "");
    }

    public void showSnackBar(View layout, String msg) {
        Snackbar snackbar = Snackbar.make(
                layout,
                msg,
                Snackbar.LENGTH_LONG
        );

        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_FADE);

        // Set background color to white (#ffffff)
//        snackbar.getView().setBackgroundColor(Color.parseColor("#000000"));

        // Set text color to black (#000000)
        TextView snackbarTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        snackbarTextView.setTextSize(16);
//        snackbarTextView.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.show();
    }

    //Store and retrieve logged in user's auth token to and from local
    public void setAuthToken(Context context, String authToken) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE).edit();
        editor.putString("AUTH_TOKEN", authToken);
        editor.apply();
    }
    public String getAuthToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE);
        String authToken = prefs.getString("AUTH_TOKEN", null);
        return authToken;
    }

    // ✅ Save Followed Employers List to SharedPreferences
    public void saveFollowedEmployers(Context context, ArrayList<FollowedEmployer> followedEmployers) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE).edit();
        String json = gson.toJson(followedEmployers); // Convert list to JSON
        editor.putString("FOLLOWED_EMPLOYERS", json);
        editor.apply(); // Save changes asynchronously
    }

    // ✅ Retrieve Followed Employers List from SharedPreferences
    public ArrayList<FollowedEmployer> getFollowedEmployers(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE);
        String json = prefs.getString("FOLLOWED_EMPLOYERS", null);
        if (json == null) {
            return new ArrayList<>(); // Return empty list if nothing is saved
        }

        Type type = new TypeToken<ArrayList<FollowedEmployer>>() {}.getType();
        return gson.fromJson(json, type); // Convert JSON back to ArrayList
    }

    // ✅ Save Job Advertisement List to SharedPreferences
    public void saveJobAdvertisementList(Context context, ArrayList<JobAd> followedEmployers) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE).edit();
        String json = gson.toJson(followedEmployers); // Convert list to JSON
        editor.putString("JOB_ADVERTISEMENT_LIST", json);
        editor.apply(); // Save changes asynchronously
    }

    // ✅ Retrieve Job Advertisement List from SharedPreferences
    public ArrayList<JobAd> getJobAdvertisementList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE);
        String json = prefs.getString("JOB_ADVERTISEMENT_LIST", null);
        if (json == null) {
            return new ArrayList<>(); // Return empty list if nothing is saved
        }

        Type type = new TypeToken<ArrayList<JobAd>>() {}.getType();
        return gson.fromJson(json, type); // Convert JSON back to ArrayList
    }
}
