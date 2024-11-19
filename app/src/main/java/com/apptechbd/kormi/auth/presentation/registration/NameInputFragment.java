package com.apptechbd.kormi.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.kormi.R;
import com.apptechbd.kormi.databinding.FragmentNameInputBinding;

public class NameInputFragment extends Fragment {
    private FragmentNameInputBinding binding;

    public NameInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNameInputBinding.inflate(inflater, container, false);

        binding.buttonNext.setOnClickListener(v -> {
            
        });

        return binding.getRoot();
    }
}