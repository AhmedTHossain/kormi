package com.apptechbd.nibay.auth.presentation.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.presentation.viewmodel.LoginViewModel;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.PhoneNumberFormatter;
import com.apptechbd.nibay.core.utils.PhoneNumberValidator;
import com.apptechbd.nibay.core.utils.ProgressDialog;
import com.apptechbd.nibay.databinding.ActivityLoginBinding;

import java.util.Locale;
import java.util.Objects;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private AlertDialog alertDialog;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false); // Handles padding for system bars

        saveLocale("bn");
        setLocale(new Locale("bn"));

        initViewModel();

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
                    drawable = ContextCompat.getDrawable(LoginActivity.this, R.drawable.ic_correct_input);
                } else {
                    drawable = ContextCompat.getDrawable(LoginActivity.this, R.drawable.ic_alert);
                }
                binding.phoneInputText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
        });

//        binding.pinInputText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // Remove error message as soon as user starts typing
//                if (s.length() > 0)
//                    binding.pinInputLayout.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        binding.buttonGetOtp.setOnClickListener(v -> validateFields());
//        binding.buttonForgotPassword.setOnClickListener(v -> {
//            startActivity(new Intent(this, ForgotPinActivity.class));
//        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void validateFields() {
        String phoneNumber = binding.phoneInputText.getText().toString().trim();
//        String pin = binding.pinInputText.getText().toString().trim();

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

        // PIN validation
//        if (pin.isEmpty()) {
//            binding.pinInputLayout.setError(getString(R.string.error_empty_pin_field));
//            isValid = false;
//        } else if (pin.length() != 5) {
//            binding.pinInputLayout.setError(getString(R.string.error_invalid_pin_number));
//            isValid = false;
//        } else {
//            binding.pinInputLayout.setError(null); // Clear error if valid
//        }

        if (isValid) {
            alertDialog = new ProgressDialog().showLoadingDialog(getResources().getString(R.string.loging_progress_dialog_title_text), getResources().getString(R.string.loging_progress_dialog_disclaimer_text), this);

            String phone = new HelperClass().formatPhoneNumber(binding.phoneInputText.getText().toString().trim());
            Log.d("LoginActivity", "phone number sent for OTP = " + phone);

            viewModel.getOtp(phone);
            viewModel.ifOtpSent.observe(this, ifOtpSent -> {
                if (ifOtpSent) {
                    // All fields are valid, navigate to OtpActivity
                    Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                    // Add any necessary data to the intent, e.g., phone number, pin
                    intent.putExtra("phoneNumber", Objects.requireNonNull(binding.phoneInputText.getText()).toString().trim());
                    intent.putExtra("from", "login");
                    startActivity(intent);
                    alertDialog.dismiss(); // Dismiss the loading dialog
                } else {
                    new HelperClass().showSnackBar(binding.login, getString(R.string.failed_to_send_otp_disclaimer_text));
                    alertDialog.dismiss();
                }
            });
        }
    }
}