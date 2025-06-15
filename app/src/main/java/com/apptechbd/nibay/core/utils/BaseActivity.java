package com.apptechbd.nibay.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);

        setLocale(getSavedLocale());
    }

    protected Locale getSavedLocale() {
        String lang = sharedPreferences.getString("language", "");
        return lang.equals("bn") ? new Locale("bn") : Locale.ENGLISH;
    }

    protected void saveLocale(String lang) {
        sharedPreferences.edit().putString("language", lang).apply();
    }

    public void setLocale(Locale locale) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private static Context updateBaseContextLocale(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return context.createConfigurationContext(configuration);
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        SharedPreferences prefs = newBase.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//        String language = prefs.getString("language", "bn"); // default to Bangla
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//
//        Context context = updateBaseContextLocale(newBase, locale);
//        super.attachBaseContext(context);
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = newBase.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String language = prefs.getString("language", "bn");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = newBase.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        config.setLayoutDirection(locale);

        Context context = newBase.createConfigurationContext(config);
        super.attachBaseContext(context);
    }


}
