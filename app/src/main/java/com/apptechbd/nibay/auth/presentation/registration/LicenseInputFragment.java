package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentFragment = viewPager2.getCurrentItem();
                viewModel.goToNextPage(currentFragment);
            }
        });

        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }
}