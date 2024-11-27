package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.databinding.FragmentLicenseInputBinding;

public class LicenseInputFragment extends Fragment {
    private FragmentLicenseInputBinding binding;
    public LicenseInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLicenseInputBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }
}