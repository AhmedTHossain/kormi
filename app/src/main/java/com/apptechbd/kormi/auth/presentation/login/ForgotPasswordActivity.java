package com.apptechbd.kormi.auth.presentation.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

import com.apptechbd.kormi.R;
import com.apptechbd.kormi.core.utils.BaseActivity;
import com.apptechbd.kormi.core.utils.PhoneNumberFormatter;
import com.apptechbd.kormi.core.utils.PhoneNumberValidator;
import com.apptechbd.kormi.core.utils.ProgressDialog;
import com.apptechbd.kormi.databinding.ActivityForgotPasswordBinding;

import java.util.Locale;

public class ForgotPasswordActivity extends BaseActivity {
    private ActivityForgotPasswordBinding binding;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false); // Handles padding for system bars

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
                if (s.length() > 0)
                    binding.phoneInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) return;
                Drawable drawable;
                if (PhoneNumberValidator.isValidBangladeshiMobileNumber(s.toString())) {
                    drawable = ContextCompat.getDrawable(ForgotPasswordActivity.this, R.drawable.ic_correct_input);
                } else {
                    drawable = ContextCompat.getDrawable(ForgotPasswordActivity.this, R.drawable.ic_alert);
                }
                binding.phoneInputText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
        });

        binding.buttonGetOtp.setOnClickListener(v -> validateFields());
    }

    private void validateFields() {
        Log.d("ForgotPasswordActivity", "validateFields() called");

        String phoneNumber = binding.phoneInputText.getText().toString().trim();

        boolean isValid = true;

        // Phone number validation
        if (phoneNumber.isEmpty()) {
            binding.phoneInputLayout.setError(getString(R.string.error_empty_phone_field));
            isValid = false;
        } else if (!PhoneNumberValidator.isValidBangladeshiMobileNumber(phoneNumber)) {
            binding.phoneInputLayout.setError(getString(R.string.error_invalid_phone_number));
            isValid = false;
        } else {
            binding.phoneInputLayout.setError(null); // Clear error if valid
        }

        if (isValid) {
            alertDialog = new ProgressDialog().showLoadingDialog(getResources().getString(R.string.recover_password_progress_dialog_title_text), getResources().getString(R.string.recover_password_progress_dialog_disclaimer_text), this);

            //ToDo: for now a delay has been used just to demo the alert dialog loading.
            // Later when API will be integrated the delay will be removed and the dialog will only be shown
            // till a response is received.
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // All fields are valid, navigate to OtpActivity
                    Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                    // Add any necessary data to the intent, e.g., phone number, pin
                    intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent);
                    alertDialog.dismiss(); // Dismiss the loading dialog
                }
            },2000);
        }
    }
}