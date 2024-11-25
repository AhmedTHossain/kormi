package com.apptechbd.nibay.core.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;

public class NidNumberFormatter {

    public static void formatNidNumber(final TextInputEditText editText) {

        // Set the maximum length of the phone number with hyphens
        final int maxLength = 12;
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        editText.addTextChangedListener(new TextWatcher() {

            boolean isFormatting;
            String previousText = "";
            boolean deletingHyphen = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousText = s.toString();
                // Detect if a hyphen is being deleted
                deletingHyphen = count == 1 && after == 0 && s.charAt(start) == '-';
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing on text changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) {
                    return;
                }

                isFormatting = true;

                String input = s.toString();

                // Remove hyphens from the previous input
                input = input.replace("-", "");

                StringBuilder formatted = new StringBuilder();
                int digitCount = 0;

                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (Character.isDigit(c)) {
                        formatted.append(c);
                        digitCount++;
                        if (digitCount == 3 || digitCount == 6) {
                            formatted.append("-");
                        }
                    }
                }

                // Handle deletion of a hyphen: Remove the previous digit if necessary
                if (deletingHyphen && formatted.length() > 0) {
                    formatted.deleteCharAt(formatted.length() - 1);
                }

                // Update the EditText with the formatted phone number
                editText.removeTextChangedListener(this);
                editText.setText(formatted.toString());
                editText.setSelection(formatted.length());
                editText.addTextChangedListener(this);

                isFormatting = false;
            }
        });
    }
}