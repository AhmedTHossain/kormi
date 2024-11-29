package com.apptechbd.nibay.home.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.databinding.FragmentNibayAppMenuBinding;

public class NibayAppMenuFragment extends Fragment {
    private FragmentNibayAppMenuBinding binding;
    public NibayAppMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNibayAppMenuBinding.inflate(inflater,container,false);

        binding.switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                binding.switchLanguage.setText(getString(R.string.en));
            }else {
                binding.switchLanguage.setText(getString(R.string.bn));
            }
        });

        return binding.getRoot();
    }
}