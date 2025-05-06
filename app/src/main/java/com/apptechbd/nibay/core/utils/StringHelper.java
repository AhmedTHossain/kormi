package com.apptechbd.nibay.core.utils;

import android.content.Context;
import android.content.res.Configuration;

import com.apptechbd.nibay.R;

import java.util.Locale;

public class StringHelper {

    public String[] getRolesInEnglish(Context context){
        // Load English roles
        Configuration englishConfig = new Configuration(context.getResources().getConfiguration());
        englishConfig.setLocale(new Locale("en"));
        Context englishContext = context.createConfigurationContext(englishConfig);

        return englishContext.getResources().getStringArray(R.array.roles);
    }

    public String[] getRolesInBengali(Context context){
        //Load Bengali roles
        Configuration bengaliConfig = new Configuration(context.getResources().getConfiguration());
        bengaliConfig.setLocale(new Locale("bn"));
        Context bengaliContext = context.createConfigurationContext(bengaliConfig);

        return bengaliContext.getResources().getStringArray(R.array.roles);
    }
}
