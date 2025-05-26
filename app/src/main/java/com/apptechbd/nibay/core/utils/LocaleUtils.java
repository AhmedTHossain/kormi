package com.apptechbd.nibay.core.utils;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.apptechbd.nibay.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocaleUtils {

    public static Map<Locale, String[]> getLocalizedRolesArray(Context context) {
        Locale[] supportedLocales = {
                new Locale("en"),  // English
                new Locale("bn")   // Bangla
        };

        Map<Locale, String[]> rolesMap = new HashMap<>();

        for (Locale locale : supportedLocales) {
            Configuration config = new Configuration(context.getResources().getConfiguration());
            config.setLocale(locale);
            Context localizedContext = context.createConfigurationContext(config);
            Resources localizedResources = localizedContext.getResources();
            String[] roles = localizedResources.getStringArray(R.array.roles);
            rolesMap.put(locale, roles);
        }

        return rolesMap;
    }
}

