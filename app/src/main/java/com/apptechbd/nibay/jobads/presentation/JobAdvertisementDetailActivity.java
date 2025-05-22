package com.apptechbd.nibay.jobads.presentation;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.core.utils.DateConverter;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.ActivityJobAdvertisementDetailBinding;
import com.apptechbd.nibay.home.domain.model.JobAdDetails;
import com.apptechbd.nibay.jobads.domain.adapter.RequirementsAdapter;
import com.apptechbd.nibay.jobads.domain.model.Requirement;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class JobAdvertisementDetailActivity extends BaseActivity {
    private ActivityJobAdvertisementDetailBinding binding;
    private ArrayList<Requirement> requirements = new ArrayList<>();
    private RequirementsAdapter adapter;
    private JobAdvertisementDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobAdvertisementDetailBinding.inflate(getLayoutInflater());

        initViewModel();

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.job_ad_details), (v, insets) -> {
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

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(JobAdvertisementDetailsViewModel.class);

        viewModel.getJobAdDetails(getIntent().getStringExtra("id"));
        viewModel.jobAdDetails.observe(this, jobAdDetails -> {
            if (jobAdDetails != null) {
                binding.textRole.setText(jobAdDetails.getTitle());
                binding.textEmployer.setText(getIntent().getStringExtra("employer"));
                if (jobAdDetails.getIsFollowing())
                    binding.textFollowButton.setText(R.string.following_company);
                else
                    binding.textFollowButton.setText(R.string.follow_company);
                String completeUrl = "https://nibay.co/" + getIntent().getStringExtra("logo");

                Log.d("FollowedEmployerAdapter", "company logo: " + completeUrl);
                Glide.with(this).load(completeUrl).into(binding.imgCompanyLogo);
                createJobRequirements(jobAdDetails);
            } else
                new HelperClass().showSnackBar(binding.jobAdDetails, getString(R.string.unable_to_load_job_details));
        });
    }

    //ToDo: Create a dummy requirements list later on when apis are integrated fetch job requirements from server
    private void createJobRequirements(JobAdDetails jobAdDetails) {
        // Clear existing data
        requirements.clear();

        // Add job ads with sample data
        String experienceText = jobAdDetails.getExperience()+" years";
        requirements.add(new Requirement(getString(R.string.experience), experienceText));
        String[] educationQualification = getResources().getStringArray(R.array.educationQualifications);
        String educationText = educationQualification[jobAdDetails.getMinEducationLevel()];
        requirements.add(new Requirement(getString(R.string.minimum_academic_qualificaton), educationText));
        requirements.add(new Requirement(getString(R.string.application_deadline), new DateConverter().convertToLocalDate(jobAdDetails.getApplicationDeadline())));
        String locationText = jobAdDetails.getDistrict() + ", " + jobAdDetails.getDivision();
        requirements.add(new Requirement(getString(R.string.location), locationText));

        adapter = new RequirementsAdapter(requirements, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2); // Adjust the span count as needed
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerRequirements.setLayoutManager(layoutManager);
        binding.recyclerRequirements.setAdapter(adapter);
    }
}