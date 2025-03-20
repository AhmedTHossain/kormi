package com.apptechbd.nibay.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {

    public String convertToLocalDate(String isoDate) {
        try {
            // Parse the input ISO 8601 date
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // The input is in UTC

            Date date = isoFormat.parse(isoDate); // Convert string to Date object

            // Format the date in the device's local timezone
            SimpleDateFormat localFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            localFormat.setTimeZone(TimeZone.getDefault()); // Set the local timezone

            return localFormat.format(date); // Return formatted date
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null in case of error
        }
    }

}
