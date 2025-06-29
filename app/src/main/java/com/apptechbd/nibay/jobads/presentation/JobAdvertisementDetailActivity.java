package com.apptechbd.nibay.jobads.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.core.utils.DateConverter;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.ProgressDialog;
import com.apptechbd.nibay.databinding.ActivityJobAdvertisementDetailBinding;
import com.apptechbd.nibay.home.domain.model.FollowedEmployer;
import com.apptechbd.nibay.home.domain.model.JobAdDetails;
import com.apptechbd.nibay.jobads.domain.adapter.RequirementsAdapter;
import com.apptechbd.nibay.jobads.domain.model.Requirement;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class JobAdvertisementDetailActivity extends BaseActivity {

    private ActivityJobAdvertisementDetailBinding binding;
    private JobAdvertisementDetailsViewModel viewModel;
    private JobAdDetails jobAdDetails;
    private AlertDialog alertDialog;
    private final HelperClass helper = new HelperClass();
    private RequirementsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobAdvertisementDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        setupInsets();
        setLocale(new Locale("bn"));

        initViewModel();
        setupListeners();
        handleBackPress();
    }

    private void setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.job_ad_details), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupListeners() {
        binding.topAppBar.setNavigationOnClickListener(v -> finishWithResult());
        binding.textFollowButton.setOnClickListener(v -> handleFollowClick());
        binding.buttonApply.setOnClickListener(v -> {
            if (jobAdDetails != null) {
                viewModel.applyJob(jobAdDetails.getId());
            }
        });
    }

    private void handleFollowClick() {
        if (jobAdDetails == null) {
            helper.showSnackBar(binding.jobAdDetails, getString(R.string.unable_to_load_job_details));
            return;
        }

        boolean isFollowing = jobAdDetails.getIsFollowing();
        alertDialog = new ProgressDialog().showLoadingDialog(
                getString(isFollowing ? R.string.unfollow_progress_dialog_title_text : R.string.follow_progress_dialog_title_text),
                getString(isFollowing ? R.string.unfollow_progress_dialog_body_text : R.string.follow_progress_dialog_body_text),
                this
        );

        if (isFollowing) {
            viewModel.unfollowEmployer(jobAdDetails.getEmployerId());
        } else {
            viewModel.followEmployer(jobAdDetails.getEmployerId());
        }
    }

    private void handleBackPress() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishWithResult();
            }
        });
    }

    private void finishWithResult() {
        if (jobAdDetails != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("employerId", jobAdDetails.getEmployerId());
            resultIntent.putExtra("isFollowing", jobAdDetails.getIsFollowing());
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    private void updateFollowResultIntent(boolean isFollowing) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("employerId", jobAdDetails.getEmployerId());
        resultIntent.putExtra("isFollowing", isFollowing);
        setResult(RESULT_OK, resultIntent);
        // Do NOT call finish() here
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(JobAdvertisementDetailsViewModel.class);
        showLoading();

        viewModel.getJobAdDetails(getIntent().getStringExtra("id"));
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.jobAdDetails.observe(this, details -> {
            if (details != null) {
                jobAdDetails = details;
                updateUI();
            } else {
                helper.showSnackBar(binding.jobAdDetails, getString(R.string.unable_to_load_job_details));
            }
        });

        viewModel.followStatus.observe(this, success -> handleFollowUnfollowResponse(success, true));
        viewModel.unfollowStatus.observe(this, success -> handleFollowUnfollowResponse(success, false));

        viewModel.isApplied.observe(this, isApplied -> {
            int msgResId = isApplied ? R.string.job_applcation_successfull : R.string.job_application_failed;
            helper.showSnackBar(binding.jobAdDetails, getString(msgResId));
            if (isApplied) updateApplyButtonState();
        });
    }

    private void handleFollowUnfollowResponse(Boolean success, boolean isFollow) {
        if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
        if (success == null || !success) {
            int msgResId = isFollow ? R.string.follow_failed : R.string.unfollow_failed;
            helper.showSnackBar(binding.jobAdDetails, getString(msgResId));
            return;
        }

        jobAdDetails.setIsFollowing(isFollow);
        updateFollowButtonUI(isFollow);

        ArrayList<FollowedEmployer> list = helper.getFollowedEmployers(this);
        if (isFollow) {
            FollowedEmployer employer = new FollowedEmployer();
            employer.setId(jobAdDetails.getEmployerId());
            employer.setName(getIntent().getStringExtra("employer"));
            employer.setProfilePhoto(getIntent().getStringExtra("logo"));
            list.add(employer);
        } else {
            list.removeIf(e -> e.getId().equals(jobAdDetails.getEmployerId()));
        }
        helper.saveFollowedEmployers(this, list);
        updateFollowResultIntent(isFollow);
    }

    private void updateUI() {
        binding.textTitle.setText(jobAdDetails.getTitle());
        binding.textEmployer.setText(getIntent().getStringExtra("employer"));
        updateFollowButtonUI(jobAdDetails.getIsFollowing());

        Glide.with(this)
                .load("https://nibay.co/" + getIntent().getStringExtra("logo"))
                .into(binding.imgCompanyLogo);

        binding.textRole.setText(getResources().getStringArray(R.array.roles)[jobAdDetails.getJobRole()]);
        binding.textDescription.setText(jobAdDetails.getShortDescription());
        binding.textResponsibilities.setText(jobAdDetails.getLongDescription());

        if (!"ACTIVE".equals(jobAdDetails.getJobStatus())) {
            updateApplyButtonState();
        }

        createJobRequirements();
        showContent();
    }

    private void createJobRequirements() {
        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(new Requirement(getString(R.string.experience), jobAdDetails.getExperience() + " years"));
        requirements.add(new Requirement(getString(R.string.minimum_academic_qualificaton),
                getResources().getStringArray(R.array.educationQualifications)[jobAdDetails.getMinEducationLevel()]));
        requirements.add(new Requirement(getString(R.string.application_deadline),
                new DateConverter().convertToLocalDate(jobAdDetails.getApplicationDeadline())));
        requirements.add(new Requirement(getString(R.string.location),
                jobAdDetails.getDistrict() + ", " + jobAdDetails.getDivision()));

        adapter = new RequirementsAdapter(requirements, this);
        binding.recyclerRequirements.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerRequirements.setAdapter(adapter);
    }

    private void updateApplyButtonState() {
        binding.buttonApply.setBackgroundColor(getColor(R.color.theme_custom_disabled_button_color));
        binding.buttonApply.setTextColor(getColor(R.color.md_theme_secondary));
        binding.buttonApply.setEnabled(false);

        int resId;
        switch (jobAdDetails.getJobStatus()) {
            case "PENDING":
                resId = R.string.job_application_pending;
                break;
            case "REJECTED":
                resId = R.string.job_application_rejected;
                break;
            case "ACCEPTED":
                resId = R.string.job_application_offered;
                break;
            default:
                resId = R.string.apply;
                break;
        }

        binding.buttonApply.setText(getString(resId));
    }

    private void updateFollowButtonUI(boolean isFollowing) {
        binding.textFollowButton.setText(isFollowing ? R.string.following_company : R.string.follow_company);
    }

    private void showLoading() {
        binding.layoutProgress.setVisibility(View.VISIBLE);
        binding.layoutProgress.startShimmerAnimation();
        binding.layoutJobDetails.setVisibility(View.GONE);
    }

    private void showContent() {
        binding.layoutProgress.stopShimmerAnimation();
        binding.layoutProgress.setVisibility(View.GONE);
        binding.layoutJobDetails.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
