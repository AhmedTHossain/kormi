package com.apptechbd.kormi.auth.presentation.landing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.apptechbd.kormi.R;
import com.apptechbd.kormi.auth.presentation.login.LoginActivity;
import com.apptechbd.kormi.auth.presentation.registration.RegistrationActivity;
import com.apptechbd.kormi.core.utils.BaseActivity;
import com.apptechbd.kormi.databinding.ActivityLandingBinding;

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
    }
}