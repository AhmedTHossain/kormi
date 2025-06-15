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
import com.apptechbd.nibay.appmenu.presentation.HelpActivity;
import com.apptechbd.nibay.appmenu.presentation.PrivacyActivity;
import com.apptechbd.nibay.appmenu.presentation.TermsActivity;
import com.apptechbd.nibay.auth.presentation.landing.LandingActivity;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.FragmentNibayAppMenuBinding;

import java.util.Locale;

public class NibayAppMenuFragment extends Fragment {
    private FragmentNibayAppMenuBinding binding;

    public NibayAppMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNibayAppMenuBinding.inflate(inflater, container, false);

        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String language = prefs.getString("language", "bn");

        if (language.equals("en")) {
            binding.switchLanguage.setChecked(true);
            binding.switchLanguage.setText(getString(R.string.en));
        } else {
            binding.switchLanguage.setChecked(false);
            binding.switchLanguage.setText(getString(R.string.bn));
        }

        checkUserPreferredTheme();

        binding.buttonFaq.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), FaqActivity.class));
        });

        binding.buttonTermsAndConditions.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), TermsActivity.class));
        });

        binding.buttonPrivacyPolicy.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), PrivacyActivity.class));
        });

        binding.buttonHelpOrSupport.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), HelpActivity.class));
        });

//        binding.switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                binding.switchLanguage.setText(getString(R.string.en));
//            } else {
//                binding.switchLanguage.setText(getString(R.string.bn));
//            }
//        });

//        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                binding.switchTheme.setText("Dark");
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            } else {
//                binding.switchTheme.setText("Light");
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
//
////            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
//            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("isDarkMode", isChecked);
//            editor.apply();
//        });

//        binding.switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            String language = isChecked ? "en" : "bn";
//            binding.switchLanguage.setText(getString(isChecked ? R.string.en : R.string.bn));
//
//            // Save preference
//            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putString("language", language);
//            editor.apply();
//
//            // Apply and restart
//            ((BaseActivity) requireActivity()).setLocale(new Locale(language));
//            // Recreate activity to apply locale changes
//            requireActivity().recreate();
//
//        });

        binding.switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String languageCode = isChecked ? "en" : "bn";

            // Save language preference to shared preferences
            prefs.edit().putString("language", languageCode).apply();

            // Restart the activity
            requireActivity().recreate();
        });



        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.switchTheme.setText("Dark");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                binding.switchTheme.setText("Light");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            // Save preference consistently
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isDarkMode", isChecked);
            editor.apply();

            // Optional: Restart activity to apply immediately
            requireActivity().recreate();
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