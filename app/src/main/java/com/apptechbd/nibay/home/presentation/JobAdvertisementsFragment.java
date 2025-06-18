package com.apptechbd.nibay.home.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.adapter.RoleAdapter;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.FragmentJobAdvertisementsBinding;
import com.apptechbd.nibay.home.domain.adapter.FollowedEmployerAdapter;
import com.apptechbd.nibay.home.domain.adapter.JobAdAdapter;
import com.apptechbd.nibay.home.domain.model.FollowedEmployer;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.apptechbd.nibay.jobads.presentation.JobAdvertisementDetailActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class JobAdvertisementsFragment extends Fragment {

    private FragmentJobAdvertisementsBinding binding;
    private RoleAdapter roleAdapter;
    private FollowedEmployerAdapter followedEmployerAdapter;
    private JobAdAdapter jobAdAdapter;
    private final ArrayList<FollowedEmployer> followedEmployers = new ArrayList<>();
    private HomeViewModel homeViewModel;
    private final HelperClass helperClass = new HelperClass();
    private String currentSelectedEmployerId;

    private ActivityResultLauncher<Intent> jobDetailsLauncher;

    private void updateEmployerFollowStatus(String employerId, boolean isFollowing) {
        // Always re-fetch followed employers from the source of truth
        followedEmployers.clear();
        followedEmployers.addAll(helperClass.getFollowedEmployers(requireContext()));

        // Update adapter
        if (followedEmployerAdapter != null) {
            followedEmployerAdapter.notifyDataSetChanged();
        } else {
            setupFollowedEmployers();
        }

        // Show/hide followed section
        if (followedEmployers.isEmpty()) {
            binding.layoutFollowedEmployer.setVisibility(View.GONE);
        } else {
            binding.layoutFollowedEmployer.setVisibility(View.VISIBLE);
        }

        // If user unfollowed the selected employer, clear selection and show all jobs
        if (!isFollowing && employerId.equals(currentSelectedEmployerId)) {
            currentSelectedEmployerId = null;
            homeViewModel.getJobAdvertisements("1");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobDetailsLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String employerId = result.getData().getStringExtra("employerId");
                        boolean isFollowing = result.getData().getBooleanExtra("isFollowing", false);

                        // Update local state and UI
                        updateEmployerFollowStatus(employerId, isFollowing);
                    }
                });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJobAdvertisementsBinding.inflate(inflater, container, false);

        initViewModel();
        initRecyclerViews();
        initRoleSpinner();

        Log.d("JobAdvertisementsFragment", "Device ID: " + helperClass.getAndroidId(requireContext()));

        return binding.getRoot();
    }

    private void initRecyclerViews() {
        jobAdAdapter = new JobAdAdapter(new ArrayList<>(), requireContext(), homeViewModel, "all");
        binding.recyclerviewJobAds.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewJobAds.setAdapter(jobAdAdapter);
    }

    private void initRoleSpinner() {
        String[] roles = getResources().getStringArray(R.array.roles);
        roleAdapter = new RoleAdapter(requireContext(), new ArrayList<>(Arrays.asList(roles)));
        binding.spinnerRole.setAdapter(roleAdapter);

        binding.spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedView, int position, long id) {
                if (selectedView != null) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.md_theme_primary));
                }
            }

            @Override public void onNothingSelected(AdapterView<?> parent) { /* no-op */ }
        });
    }

    private void initViewModel() {
        binding.layoutJobAdShimmer.setVisibility(View.VISIBLE);
        binding.layoutJobAdShimmer.startShimmerAnimation();

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        currentSelectedEmployerId = null;

        homeViewModel.getJobAdvertisements("1");

        homeViewModel.jobAds.observe(getViewLifecycleOwner(), jobAdsList -> {
            if (jobAdsList != null) {
                jobAdAdapter.updateJobAds(jobAdsList);
                showJobAdsContent();
            }
        });

        homeViewModel.jobClicked.observe(getViewLifecycleOwner(), jobClicked -> {
            if (jobClicked != null) openJobDetails(jobClicked.getEmployerName(), jobClicked.getId(), jobClicked.getEmployerPhoto());
        });

        homeViewModel.getFollowedEmployers();
        homeViewModel.isFollowedEmployersFetched.observe(getViewLifecycleOwner(), isFetched -> {
            if (Boolean.TRUE.equals(isFetched)) {
                followedEmployers.clear();
                followedEmployers.addAll(helperClass.getFollowedEmployers(requireContext()));
                setupFollowedEmployers();
            } else {
                helperClass.showSnackBar(binding.getRoot(), getString(R.string.no_employers_followed_disclaimer_text));
            }
        });

        homeViewModel.companyJobAds.observe(getViewLifecycleOwner(), jobAds -> {
            if (jobAds != null) {
                jobAdAdapter.updateJobAds(jobAds);
                showJobAdsContent();
            }
        });

        homeViewModel.followedCompanyClicked.observe(getViewLifecycleOwner(), companyId -> {
            Log.d("JobAdFragment", "Selected companyId: " + companyId);

            boolean sameCompanySelected = companyId != null && companyId.equals(currentSelectedEmployerId);
            currentSelectedEmployerId = sameCompanySelected ? null : companyId;

            followedEmployers.forEach(employer -> employer.setSelected(employer.getId().equals(currentSelectedEmployerId)));
            if (followedEmployerAdapter != null) followedEmployerAdapter.notifyDataSetChanged();

            binding.layoutJobAdShimmer.setVisibility(View.VISIBLE);
            binding.layoutJobAdShimmer.startShimmerAnimation();

            if (currentSelectedEmployerId != null) {
                homeViewModel.getCompanyJobAdvertisements("1", currentSelectedEmployerId);
            } else {
                homeViewModel.getJobAdvertisements("1");
            }
        });
    }

    private void setupFollowedEmployers() {
        if (followedEmployerAdapter == null) {
            followedEmployerAdapter = new FollowedEmployerAdapter(followedEmployers, requireContext(), homeViewModel);
            binding.recyclerviewFollowedCompanies.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerviewFollowedCompanies.setAdapter(followedEmployerAdapter);
        } else {
            followedEmployerAdapter.notifyDataSetChanged();
        }
        binding.layoutFollowedEmployer.setVisibility(View.VISIBLE);
    }

    private void showJobAdsContent() {
        binding.layoutJobAdShimmer.stopShimmerAnimation();
        binding.layoutJobAdShimmer.setVisibility(View.GONE);
        binding.layoutJobAd.setVisibility(View.VISIBLE);
    }

    private void openJobDetails(String employer, String id, String logo) {
        Intent intent = new Intent(requireContext(), JobAdvertisementDetailActivity.class);
        intent.putExtra("id", id); // jobAd ID
        intent.putExtra("employer", employer);
        intent.putExtra("logo", logo);
        jobDetailsLauncher.launch(intent);

    }
}
