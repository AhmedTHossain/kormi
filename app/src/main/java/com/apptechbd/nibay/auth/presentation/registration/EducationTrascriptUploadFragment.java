package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.databinding.FragmentEducationTrascriptUploadBinding;

public class EducationTrascriptUploadFragment extends Fragment {
    private FragmentEducationTrascriptUploadBinding binding;
    private RegistrationViewModel registrationViewModel;
    public EducationTrascriptUploadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEducationTrascriptUploadBinding.inflate(inflater,container,false);

        registrationViewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        String[] educationLevels = requireContext().getResources().getStringArray(R.array.educationQualifications);

        return binding.getRoot();
    }
}