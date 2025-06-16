package com.apptechbd.nibay.home.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.presentation.landing.LandingActivity;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.LocaleUtils; // Import LocaleUtils
import com.apptechbd.nibay.databinding.FragmentNibayAppMenuBinding;

// Removed java.util.Locale import as it's no longer directly used here for new Locale creation
// Removed android.content.res.Configuration import for the same reason
// Removed android.preference.PreferenceManager

public class NibayAppMenuFragment extends Fragment {
    private FragmentNibayAppMenuBinding binding;
    private static final String PREF_NAME = "UserPrefs";
    private static final String PREF_LANGUAGE_KEY = "language";
    private static final String PREF_THEME_KEY = "isDarkMode"; // Define key for theme
    private static final String LANGUAGE_ENGLISH = "en";
    private static final String LANGUAGE_BANGLA = "bn";

    public NibayAppMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNibayAppMenuBinding.inflate(inflater, container, false);

        checkUserPreferredTheme();
        loadAndSetLanguagePreference();

        binding.switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String languageToLoad;
            if (isChecked) {
                languageToLoad = LANGUAGE_ENGLISH;
                binding.switchLanguage.setText(getString(R.string.en));
            } else {
                languageToLoad = LANGUAGE_BANGLA;
                binding.switchLanguage.setText(getString(R.string.bn));
            }
            saveLanguagePreference(languageToLoad);
            LocaleUtils.setLocale(requireContext(), languageToLoad); // Use LocaleUtils
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

            SharedPreferences prefs = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE); // Use consistent SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(PREF_THEME_KEY, isChecked); // Use defined key
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
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean(PREF_THEME_KEY, false); // Use defined key
        if (isDarkMode) {
            binding.switchTheme.setText("Dark");
            binding.switchTheme.setChecked(true);
        } else {
            binding.switchTheme.setText("Light");
            binding.switchTheme.setChecked(false);
        }

    }

    private void loadAndSetLanguagePreference() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String currentLanguage = prefs.getString(PREF_LANGUAGE_KEY, LANGUAGE_ENGLISH); // Default to English

        if (LANGUAGE_BANGLA.equals(currentLanguage)) {
            binding.switchLanguage.setChecked(false);
            binding.switchLanguage.setText(getString(R.string.bn));
        } else {
            binding.switchLanguage.setChecked(true);
            binding.switchLanguage.setText(getString(R.string.en));
        }
        LocaleUtils.setLocale(requireContext(), currentLanguage); // Set initial locale
    }

    private void saveLanguagePreference(String language) {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_LANGUAGE_KEY, language);
        editor.apply();
    }

    // Removed private setLocale(String languageCode) method
}