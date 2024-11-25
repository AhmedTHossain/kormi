package com.apptechbd.nibay.core.utils;

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
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        setLocale(getSavedLocale());
    }

    protected Locale getSavedLocale() {
        String lang = sharedPreferences.getString("language", "");
        return lang.equals("bn") ? new Locale("bn") : Locale.ENGLISH;
    }

    protected void saveLocale(String lang) {
        sharedPreferences.edit().putString("language", lang).apply();
    }

    protected void setLocale(Locale locale) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
