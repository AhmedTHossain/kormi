package com.apptechbd.kormi.auth.presentation.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.apptechbd.kormi.R;
import com.apptechbd.kormi.core.utils.BaseActivity;
import com.apptechbd.kormi.databinding.ActivityOtpBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OtpActivity extends BaseActivity {
    private ActivityOtpBinding binding;
    private static final long ONE_MINUTE_IN_MILLIS = 60000; // 1 minute in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false); // Handles padding for system bars

        saveLocale("bn");
        setLocale(new Locale("bn"));

        // Handle navigation icon click
        binding.topAppBar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed(); //navigate back
        });

        startOtpTimer();

        EditText[] otpBoxes = {
                binding.otpBox1.getRoot().getEditText(),
                binding.otpBox2.getRoot().getEditText(),
                binding.otpBox3.getRoot().getEditText(),
                binding.otpBox4.getRoot().getEditText()
        };

        // Focus on the first box initially
        if (otpBoxes[0] != null) otpBoxes[0].requestFocus();

        // Add a single TextWatcher instance to each box
        for (int i = 0; i < otpBoxes.length; i++) {
            final int currentIndex = i;
            otpBoxes[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0 && currentIndex < otpBoxes.length - 1) {
                        otpBoxes[currentIndex + 1].requestFocus();
                    } else if (s.length() == 0 && currentIndex > 0) {
                        otpBoxes[currentIndex - 1].requestFocus();
                    }
                }
            });
        }

        binding.buttonGetOtp.setOnClickListener(v -> {
            if (validateOtpFields()) {
                //Todo: call otp verification api

            }
        });

        binding.buttonResendOtp.setOnClickListener(v -> {
            if (binding.textTimer.getText().equals(getString(R.string.text_countdown_end))) {
                //Todo: call resend otp api
                startOtpTimer();
                Snackbar.make(binding.otp, getString(R.string.verify_otp_page_new_otp_disclaimer_text), Snackbar.LENGTH_SHORT).show();
            } else
                Snackbar.make(binding.otp, getString(R.string.verify_otp_page_resend_otp_disclaimer_text), Snackbar.LENGTH_SHORT).show();
        });
    }

    private boolean validateOtpFields() {
        EditText[] otpBoxes = {
                binding.otpBox1.getRoot().getEditText(),
                binding.otpBox2.getRoot().getEditText(),
                binding.otpBox3.getRoot().getEditText(),
                binding.otpBox4.getRoot().getEditText()
        };

        boolean allFilled = true;
        for (EditText otpBox : otpBoxes) {
            if (otpBox != null && otpBox.getText().toString().trim().isEmpty()) {
                otpBox.setError(getString(R.string.verify_otp_page_error_text));
                allFilled = false;
            } else if (otpBox != null) {
                otpBox.setError(null); // Clear any previous error
            }
        }

        return allFilled;
    }


    private void startOtpTimer() {
        // Display the current time on the text_timer initially
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
        String currentTime = timeFormat.format(new Date());

        String countDownText = "(" + currentTime + ")";
        binding.textTimer.setText(countDownText);

        // Start a 1-minute countdown timer
        new CountDownTimer(ONE_MINUTE_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;

                binding.textTimer.setText(String.format(Locale.getDefault(), "(%02d:%02d)", secondsRemaining / 60, secondsRemaining % 60));
            }

            @Override
            public void onFinish() {
                binding.textTimer.setText(R.string.text_countdown_end);
            }
        }.start();
    }
}