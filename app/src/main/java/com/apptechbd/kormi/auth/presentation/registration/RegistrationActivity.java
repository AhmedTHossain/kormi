package com.apptechbd.kormi.auth.presentation.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.apptechbd.kormi.R;
import com.apptechbd.kormi.auth.domain.adapters.RegistrationAdapter;
import com.apptechbd.kormi.auth.presentation.landing.LandingActivity;
import com.apptechbd.kormi.core.utils.BaseActivity;
import com.apptechbd.kormi.databinding.ActivityRegistrationBinding;

import java.util.Locale;

public class RegistrationActivity extends BaseActivity {
    private ActivityRegistrationBinding binding;
    private RegistrationAdapter adapter = new RegistrationAdapter(getSupportFragmentManager(), getLifecycle());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false); // Handles padding for system bars

        saveLocale("bn");
        setLocale(new Locale("bn"));

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
    }
}