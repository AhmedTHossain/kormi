package com.apptechbd.nibay.home.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.appmenu.presentation.FaqActivity;
import com.apptechbd.nibay.appmenu.presentation.TermsActivity;
import com.apptechbd.nibay.auth.presentation.landing.LandingActivity;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.FragmentNibayAppMenuBinding;

public class NibayAppMenuFragment extends Fragment {
    private FragmentNibayAppMenuBinding binding;

    public NibayAppMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNibayAppMenuBinding.inflate(inflater, container, false);

        checkUserPreferredTheme();

        binding.buttonFaq.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), FaqActivity.class));
        });

        binding.buttonTermsAndConditions.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), TermsActivity.class));
        });

        binding.switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.switchLanguage.setText(getString(R.string.en));
            } else {
                binding.switchLanguage.setText(getString(R.string.bn));
            }
        });

        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.switchTheme.setText("Dark");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                binding.switchTheme.setText("Light");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isDarkMode", isChecked);
            editor.apply();
        });

        binding.buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HelperClass().setAuthToken(requireContext(),null);
                startActivity(new Intent(requireContext(), LandingActivity.class));
                requireActivity().finish();
            }
        });


        return binding.getRoot();
    }

    private void checkUserPreferredTheme() {
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        boolean isDarkMode = prefs.getBoolean("isDarkMode", false);
        if (isDarkMode) {
            binding.switchTheme.setText("Dark");
            binding.switchTheme.setChecked(true);
        } else {
            binding.switchTheme.setText("Light");
            binding.switchTheme.setChecked(false);
        }

    }
}