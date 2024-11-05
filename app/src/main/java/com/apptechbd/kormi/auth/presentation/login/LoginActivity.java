package com.apptechbd.kormi.auth.presentation.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.apptechbd.kormi.R;
import com.apptechbd.kormi.core.utils.BaseActivity;
import com.apptechbd.kormi.core.utils.PhoneNumberFormatter;
import com.apptechbd.kormi.core.utils.PhoneNumberValidator;
import com.apptechbd.kormi.databinding.ActivityLoginBinding;

import java.util.Locale;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveLocale("bn");
        setLocale(new Locale("bn"));

        // Handle navigation icon click
        binding.topAppBar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed(); //navigate back
        });

        PhoneNumberFormatter.formatPhoneNumber(binding.phoneInputText);

        binding.phoneInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Remove error message as soon as user starts typing
                if (s.length() > 0) {
                    binding.phoneInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.pinInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Remove error message as soon as user starts typing
                if (s.length() > 0) {
                    binding.pinInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.buttonGetOtp.setOnClickListener(v -> validateFields());
    }

    private void validateFields() {
        if (binding.phoneInputText.getText().toString().isEmpty()) {
            binding.phoneInputLayout.setError(getString(R.string.error_empty_phone_field));
        } else {
            Log.d("PhoneNumberInput", "Phone number entered: " + binding.phoneInputText.getText().toString());
            // Check if the phone number is valid only if it is not empty
            if (!PhoneNumberValidator.isValidBangladeshiMobileNumber(binding.phoneInputText.getText().toString())) {
                binding.phoneInputLayout.setError(getString(R.string.error_invalid_phone_number));
            }
        }
        if (binding.pinInputText.getText().toString().isEmpty()) {
            binding.pinInputLayout.setError(getString(R.string.error_empty_pin_field));
        } else {
            Log.d("PinInput", "Pin entered: " + binding.pinInputText.getText().toString());
            // Check if the pin is valid only if it is not empty
            if (binding.pinInputText.getText().length() != 5)
                binding.pinInputLayout.setError(getString(R.string.error_invalid_pin_number));
        }
    }
}