package com.apptechbd.nibay.auth.presentation.registration;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.PhoneNumberFormatter;
import com.apptechbd.nibay.core.utils.PhoneNumberValidator;
import com.apptechbd.nibay.core.utils.ProgressDialog;
import com.apptechbd.nibay.databinding.FragmentPhoneInputBinding;

public class PhoneInputFragment extends Fragment {

    private FragmentPhoneInputBinding binding;
    private AlertDialog alertDialog;
    private RegistrationViewModel viewModel;
    private ViewPager2 viewPager2;

    public PhoneInputFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhoneInputBinding.inflate(inflater, container, false);

        initViewModel();

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

        binding.buttonNext.setOnClickListener(v -> validateFields());

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
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
//            alertDialog = new ProgressDialog().showLoadingDialog(getResources().getString(R.string.registering_phone_progress_dialog_title_text), getResources().getString(R.string.registering_phone_progress_dialog_disclaimer_text), requireContext());

            RegisterUserModel user = viewModel.getUser();
            user.setMobileNumber(binding.phoneInputText.getText().toString());
            user.setDeviceID(new HelperClass().getAndroidId(requireContext()));
            viewModel.setUser(user);

            int currentFragment = viewPager2.getCurrentItem();
            viewModel.goToNextPage(currentFragment);
        }
    }
}