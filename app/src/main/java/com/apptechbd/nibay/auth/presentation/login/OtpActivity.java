package com.apptechbd.nibay.auth.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.presentation.registration.RegistrationActivity;
import com.apptechbd.nibay.auth.presentation.viewmodel.OtpViewModel;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.ProgressDialog;
import com.apptechbd.nibay.databinding.ActivityOtpBinding;
import com.apptechbd.nibay.home.presentation.HomeActivity;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class OtpActivity extends BaseActivity {
    private ActivityOtpBinding binding;
    private AlertDialog alertDialog;
    private OtpViewModel viewModel;
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

        initViewModel();

        // Handle navigation icon click
        binding.topAppBar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed(); //navigate back
        });

        binding.textPhoneNumber.setText(getIntent().getStringExtra("phoneNumber"));

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

        binding.buttonVerifyOtp.setOnClickListener(v -> validateOtpFields());

        binding.buttonResendOtp.setOnClickListener(v -> {
            if (binding.textTimer.getText().equals(getString(R.string.text_countdown_end))) {
                //Todo: call resend otp api
                startOtpTimer();
                Snackbar.make(binding.otp, getString(R.string.verify_otp_page_new_otp_disclaimer_text), Snackbar.LENGTH_SHORT).show();
            } else
                Snackbar.make(binding.otp, getString(R.string.verify_otp_page_resend_otp_disclaimer_text), Snackbar.LENGTH_SHORT).show();
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(OtpViewModel.class);
    }

    private void validateOtpFields() {
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

        if (allFilled) {

            alertDialog = new ProgressDialog().showLoadingDialog(getResources().getString(R.string.verifying_progress_dialog_title_text), getResources().getString(R.string.verifying_progress_dialog_disclaimer_text), this);

            //Todo: call otp verification api
            //ToDo: for now a delay has been used just to demo the alert dialog loading.
            // Later when API will be integrated the delay will be removed and the dialog will only be shown
            // till a response is received.

            String otpCode = "";
            for (EditText otpBox : otpBoxes) {
                if (otpBox != null) {
                    otpCode += otpBox.getText().toString().trim();
                }
            }

            viewModel.login(getIntent().getStringExtra("phoneNumber"), otpCode);
            viewModel.ifLoginSuccessful.observe(this, ifLoginSuccessful -> {
                if (ifLoginSuccessful) {
                    Intent intent;
                    if (Objects.requireNonNull(getIntent().getStringExtra("from")).equals("login"))
                        intent = new Intent(OtpActivity.this, HomeActivity.class);
                    else {
                        intent = new Intent(OtpActivity.this, RegistrationActivity.class);
                        intent.putExtra("fromOtpScreen", true);
                    }

                    startActivity(intent);
                    alertDialog.dismiss();
                    finish();
                } else {
                    new HelperClass().showSnackBar(binding.otp, getString(R.string.failed_login_disclaimer_text));
                    alertDialog.dismiss();
                }
            });
        }

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