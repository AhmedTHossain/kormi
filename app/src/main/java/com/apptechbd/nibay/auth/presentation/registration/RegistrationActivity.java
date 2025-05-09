package com.apptechbd.nibay.auth.presentation.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.apptechbd.nibay.auth.domain.adapter.RegistrationAdapter;
import com.apptechbd.nibay.auth.presentation.landing.LandingActivity;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.databinding.ActivityRegistrationBinding;

import java.util.Locale;

public class RegistrationActivity extends BaseActivity {
    private ActivityRegistrationBinding binding;
    private RegistrationAdapter adapter;
    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false); // Handles padding for system bars

        saveLocale("bn");
        setLocale(new Locale("bn"));

        initViewModel();
        adapter = new RegistrationAdapter(getSupportFragmentManager(), getLifecycle(), viewModel);

        // Handle navigation icon click
        binding.topAppBar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed(); //navigate back
        });

        binding.viewPager2.setAdapter(adapter);

        Log.d("RegistrationActivity", "from otp screen: " + getIntent().getBooleanExtra("fromOtpScreen", false));
        // Load the first fragment
        if (getIntent().getBooleanExtra("fromOtpScreen", false))
            binding.viewPager2.setCurrentItem(1, true);
        else
            binding.viewPager2.setCurrentItem(0, true);


        binding.dotsIndicator.attachTo(binding.viewPager2);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                int currentItem = binding.viewPager2.getCurrentItem();
                if (currentItem == 1) {
                    binding.viewPager2.setCurrentItem(0, true);
                } else {
                    if (currentItem == 0)
                        startActivity(new Intent(RegistrationActivity.this, LandingActivity.class));
                    else {
                        currentItem--;
                        binding.viewPager2.setCurrentItem(currentItem, true);
                    }
                }
            }
        });

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Fragment currentFragment = adapter.createFragment(position);

                if (currentFragment instanceof NameInputFragment) {

                } else if (currentFragment instanceof RoleInputFragment) {

                } else if (currentFragment instanceof ExperienceInputFragment) {

                } else if (currentFragment instanceof LocationInputFragment) {

                } else if (currentFragment instanceof NidInputFragment) {

                } else if (currentFragment instanceof NidUploadFragment) {

                } else if (currentFragment instanceof EducationInputFragment) {
                } else if (currentFragment instanceof EducationTrascriptUploadFragment) {
                } else if (currentFragment instanceof LicenseInputFragment) {
                } else if (currentFragment instanceof LicenseUploadFragment) {

                } else if (currentFragment instanceof ProfilePhotoUploadFragment) {

                }
            }
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        // Observe LiveData for changes
        viewModel.getUserLiveData().observe(this, user -> {
            if (user != null && user.getRole() >=0) {
                adapter.updateFragments(); // Update adapter based on role changes
            }
        });
    }
}