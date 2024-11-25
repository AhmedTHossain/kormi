package com.apptechbd.nibay.auth.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.WindowCompat;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.core.utils.ProgressDialog;
import com.apptechbd.nibay.databinding.ActivityCreatePinBinding;

import java.util.Locale;

public class CreatePinActivity extends BaseActivity {
    private ActivityCreatePinBinding binding;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreatePinBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false); // Handles padding for system bars

        saveLocale("bn");
        setLocale(new Locale("bn"));

        // Handle navigation icon click
        binding.topAppBar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed(); //navigate back
        });

        binding.buttonSetPin.setOnClickListener(v -> {
            validateFields();
        });
    }

    private void validateFields() {
        String pin = binding.pinInputText.getText().toString().trim();
        String confirmPin = binding.pinConfirmInputText.getText().toString().trim();
        boolean isValid = true;

        // PIN validation
        if (pin.isEmpty()) {
            binding.pinInputLayout.setError(getString(R.string.error_empty_pin_field));
            isValid = false;
        } else if (pin.length() != 5) {
            binding.pinInputLayout.setError(getString(R.string.error_invalid_pin));
            isValid = false;
        } else {
            binding.pinInputLayout.setError(null); // Clear error if valid
        }

        // PIN validation
        if (confirmPin.isEmpty()) {
            binding.pinConfirmInputLayout.setError(getString(R.string.error_empty_confirm_pin_field));
            isValid = false;
        } else if (!pin.equals(confirmPin)) {
            binding.pinConfirmInputLayout.setError(getString(R.string.error_pin_mismatched));
            isValid = false;
        } else {
            binding.pinConfirmInputLayout.setError(null); // Clear error if valid
        }

        if (isValid) {
            alertDialog = new ProgressDialog().showLoadingDialog(
                    getResources().getString(R.string.pin_creation_progress_dialog_title_text),
                    getResources().getString(R.string.pin_creation_progress_dialog_disclaimer_text),
                    this);

            //ToDo: for now a delay has been used just to demo the alert dialog loading.
            // Later when API will be integrated the delay will be removed and the dialog will only be shown
            // till a response is received.
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // All fields are valid, navigate to OtpActivity
                    Intent intent = new Intent(CreatePinActivity.this, LoginActivity.class);
                    // Add any necessary data to the intent, e.g., phone number, pin
//                    intent.putExtra("phoneNumber", phoneNumber);
//                    intent.putExtra("pin", pin);
//                    intent.putExtra("forgotPassword", false);
                    startActivity(intent);
                    alertDialog.dismiss(); // Dismiss the loading dialog
                }
            },2000);
        }
    }
}