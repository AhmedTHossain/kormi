package com.apptechbd.nibay.auth.presentation.login;

import static android.view.View.GONE;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
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
    private static final int SMS_CONSENT_REQUEST = 200;


    // 1. Define the ActivityResultLauncher for consent activity
    private final ActivityResultLauncher<Intent> consentActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    String message = result.getData().getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                    Log.d("OtpActivity", "SMS Message: " + message);

                    if (message != null) {
                        String otp = extractOtpFromMessage(message); // Extract OTP (assuming 4-digit code)
                        autofillOtpBoxes(otp);
                    }
                }
            });

    // 2. In your BroadcastReceiver, launch the consent intent with the ActivityResultLauncher
    private final BroadcastReceiver smsConsentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                Bundle extras = intent.getExtras();
                Status status = null;
                if (extras != null) {
                    status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
                    if (status != null && status.getStatusCode() == CommonStatusCodes.SUCCESS) {
                        Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                        if (consentIntent != null) {
                            try {
                                // 3. Use the ActivityResultLauncher to start the consent activity
                                consentActivityResultLauncher.launch(consentIntent);
                            } catch (ActivityNotFoundException e) {
                                // Handle the exception
                            }
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsConsentReceiver, intentFilter, Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsConsentReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());

        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null); // Pass sender phone number or `null` for any

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
            viewModel.loginResult.observe(this, loginResult -> {
                if (loginResult.isSuccess()) {
                    Intent intent;
                    if (Objects.requireNonNull(getIntent().getStringExtra("from")).equals("login"))
                        intent = new Intent(OtpActivity.this, HomeActivity.class);
                    else {
                        intent = new Intent(OtpActivity.this, RegistrationActivity.class);
                        intent.putExtra("fromOtpScreen", true);
                        intent.putExtra("phoneNumber", getIntent().getStringExtra("phoneNumber"));
                    }

                    startActivity(intent);
                    alertDialog.dismiss();
                    finish();
                } else {
                    if (Objects.requireNonNull(getIntent().getStringExtra("from")).equals("login"))
                        new HelperClass().showSnackBar(binding.otp, loginResult.getErrorMessage());
                    else {
                        Intent intent = new Intent(this, RegistrationActivity.class);
                        intent.putExtra("fromOtpScreen", true);
                        startActivity(intent);
                    }

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
                binding.textTimer.setVisibility(GONE);
            }
        }.start();
    }

    private String extractOtpFromMessage(String message) {
        // Simple regex to extract a 4-digit OTP. Adjust if you use 6-digit or different format.
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\b\\d{4}\\b");
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    private void autofillOtpBoxes(String otp) {
        EditText[] otpBoxes = {
                binding.otpBox1.getRoot().getEditText(),
                binding.otpBox2.getRoot().getEditText(),
                binding.otpBox3.getRoot().getEditText(),
                binding.otpBox4.getRoot().getEditText()
        };

        for (int i = 0; i < otp.length() && i < otpBoxes.length; i++) {
            if (otpBoxes[i] != null) {
                otpBoxes[i].setText(String.valueOf(otp.charAt(i)));
            }
        }
    }

}