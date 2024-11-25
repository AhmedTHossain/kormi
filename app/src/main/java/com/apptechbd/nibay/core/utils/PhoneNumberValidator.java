package com.apptechbd.nibay.core.utils;

public class PhoneNumberValidator {

    public static boolean isValidBangladeshiMobileNumber(String phoneNumber) {
        // Trim any leading or trailing spaces from the input
        phoneNumber = phoneNumber != null ? phoneNumber.trim() : null;

        // Adjusted regex pattern to allow both 3 and 4 digits after '0' for valid prefixes
        String regex = "^(0(13|14|15|16|17|18|19)\\d?)-\\d{3}-\\d{4}$";

        // Return true if the phone number matches the regex, otherwise return false
        return phoneNumber != null && phoneNumber.matches(regex);
    }

}
