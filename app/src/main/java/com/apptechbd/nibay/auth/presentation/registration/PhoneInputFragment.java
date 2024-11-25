package com.apptechbd.nibay.auth.presentation.registration;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.presentation.login.OtpActivity;
import com.apptechbd.nibay.core.utils.PhoneNumberFormatter;
import com.apptechbd.nibay.core.utils.PhoneNumberValidator;
import com.apptechbd.nibay.core.utils.ProgressDialog;
import com.apptechbd.nibay.databinding.FragmentPhoneInputBinding;

public class PhoneInputFragment extends Fragment {

    private FragmentPhoneInputBinding binding;
    private AlertDialog alertDialog;

    public PhoneInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhoneInputBinding.inflate(inflater, container, false);

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
                    drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_correct_input);
                } else {
                    drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_alert);
                }
                binding.phoneInputText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
        });

        binding.buttonGetOtp.setOnClickListener(v -> validateFields());

        // Inflate the layout for this fragment
        return binding.getRoot();
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
            alertDialog = new ProgressDialog().showLoadingDialog(getResources().getString(R.string.registering_phone_progress_dialog_title_text), getResources().getString(R.string.registering_phone_progress_dialog_disclaimer_text), requireContext());

            //ToDo: for now a delay has been used just to demo the alert dialog loading.
            // Later when API will be integrated the delay will be removed and the dialog will only be shown
            // till a response is received.
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // All fields are valid, navigate to OtpActivity
                    Intent intent = new Intent(requireActivity(), OtpActivity.class);
                    // Add any necessary data to the intent, e.g., phone number, pin
                    intent.putExtra("phoneNumber", phoneNumber);
                    intent.putExtra("forgotPassword", false);
                    intent.putExtra("from", "registration");
                    startActivity(intent);
                    alertDialog.dismiss(); // Dismiss the loading dialog
                }
            },2000);
        }
    }
}