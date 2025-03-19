package com.apptechbd.nibay.core.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class HelperClass {
    public String BASE_URL_V1 = "https://nibay.co/api/v1/";

    //there's no v2 of the APIs at present just keeping it for future iteration
    public String BASE_URL_V2 = "https://nibay.co/api/v2/";

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
                Snackbar.LENGTH_SHORT
        );

        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_FADE);

        // Set background color to white (#ffffff)
        snackbar.getView().setBackgroundColor(Color.parseColor("#000000"));

        // Set text color to black (#000000)
        TextView snackbarTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        snackbarTextView.setTextSize(16);
        snackbarTextView.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.show();
    }
}
