package com.apptechbd.nibay.core.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

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
}
