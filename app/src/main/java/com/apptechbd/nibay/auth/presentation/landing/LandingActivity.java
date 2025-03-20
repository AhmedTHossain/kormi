package com.apptechbd.nibay.auth.presentation.landing;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.presentation.login.LoginActivity;
import com.apptechbd.nibay.auth.presentation.registration.RegistrationActivity;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.ActivityLandingBinding;
import com.apptechbd.nibay.home.presentation.HomeActivity;

import java.util.Locale;

public class LandingActivity extends BaseActivity {
    private ActivityLandingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveLocale("bn");
        setLocale(new Locale("bn"));

        binding.buttonLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        binding.buttonCreateAccount.setOnClickListener(v -> startActivity(new Intent(this, RegistrationActivity.class)));

        if (new HelperClass().getAuthToken(this) != null)
            startActivity(new Intent(this, HomeActivity.class));
    }
}