package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.FragmentLicenseInputBinding;

public class LicenseInputFragment extends Fragment {
    private FragmentLicenseInputBinding binding;
    private RegistrationViewModel viewModel;
    private ViewPager2 viewPager2;

    public LicenseInputFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLicenseInputBinding.inflate(inflater, container, false);
        initViewModel();

        binding.nidInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() > 0)
                    binding.nidInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(binding.nidInputText.getText())) {
                    RegisterUserModel user = viewModel.getUser();
                    user.setDrivingLicenseNumber(binding.nidInputText.getText().toString());

                    int currentFragment = viewPager2.getCurrentItem();
                    viewModel.goToNextPage(currentFragment);
                } else {
                    binding.nidInputLayout.setError(getString(R.string.error_invalid_license_number));
//                    new HelperClass().showSnackBar(binding.layoutLicenseInputLayout, getString(R.string.error_invalid_license_number));
                }
            }
        });

        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }
}