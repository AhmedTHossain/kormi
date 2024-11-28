package com.apptechbd.nibay.home.presentation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.databinding.ActivityHomeBinding;

import java.util.Locale;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        binding.bottomnavigationHome.setOnApplyWindowInsetsListener(null);
        binding.bottomnavigationHome.setPadding(0,0,0,0);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveLocale("bn");
        setLocale(new Locale("bn"));

        initViewModel();
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.onBottomNavMenuItemSelect(binding, getSupportFragmentManager());
        viewModel.onFragmentDisplayed(binding, R.id.jobAdvertisementFragment);
        viewModel.setToolbar(binding.topAppBar);
    }
}