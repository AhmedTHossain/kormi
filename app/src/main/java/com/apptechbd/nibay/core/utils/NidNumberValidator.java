package com.apptechbd.nibay.core.utils;

public class NidNumberValidator {
    public  static boolean isValidBangladeshiNidNumber(String nidNumber) {
        // Trim any leading or trailing spaces from the input
        nidNumber = nidNumber != null ? nidNumber.trim() : null;
        return nidNumber != null && nidNumber.length() == 12;
    }
}
