package com.apptechbd.nibay.jobads.presentation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.databinding.ActivityJobAdvertisementDetailBinding;
import com.apptechbd.nibay.jobads.domain.adapter.RequirementsAdapter;
import com.apptechbd.nibay.jobads.domain.model.Requirement;

import java.util.ArrayList;
import java.util.Locale;

public class JobAdvertisementDetailActivity extends BaseActivity {
    private ActivityJobAdvertisementDetailBinding binding;
    private ArrayList<Requirement> requirements = new ArrayList<>();
    private RequirementsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobAdvertisementDetailBinding.inflate(getLayoutInflater());

        createDummyJobRequirements();

        adapter = new RequirementsAdapter(requirements, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2); // Adjust the span count as needed
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerRequirements.setLayoutManager(layoutManager);
        binding.recyclerRequirements.setAdapter(adapter);

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveLocale("bn");
        setLocale(new Locale("bn"));

        // Handle navigation icon click
        binding.topAppBar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed(); //navigate back
        });
    }

    //ToDo: Create a dummy requirements list later on when apis are integrated fetch job requirements from server
    private void createDummyJobRequirements() {
        // Clear existing data
        requirements.clear();

        // Add job ads with sample data
        requirements.add(new Requirement(getString(R.string.experience), getString(R.string.dummy_experience_requirement)));
        requirements.add(new Requirement(getString(R.string.minimum_academic_qualificaton), getString(R.string.dummy_minimum_education_requirement)));
        requirements.add(new Requirement(getString(R.string.application_deadline), getString(R.string.dummy_application_deadline_requirement)));
        requirements.add(new Requirement(getString(R.string.location),getString(R.string.dhaka_dhaka)));
    }
}