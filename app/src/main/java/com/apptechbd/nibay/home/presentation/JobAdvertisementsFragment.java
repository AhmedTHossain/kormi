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
    private boolean hasInitialSpinnerSelectionFired = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobDetailsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String employerId = result.getData().getStringExtra("employerId");
                        boolean isFollowing = result.getData().getBooleanExtra("isFollowing", false);
                        updateEmployerFollowStatus(employerId, isFollowing);
                    }
                }
        );
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

//    private void initRoleSpinner() {
//        String[] roles = getResources().getStringArray(R.array.roles);
//        roleAdapter = new RoleAdapter(requireContext(), new ArrayList<>(Arrays.asList(roles)));
//        binding.spinnerRole.setAdapter(roleAdapter);
//
//        binding.spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View selectedView, int position, long id) {
//                if (selectedView != null) {
//                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.md_theme_primary));
//                }
//            }
//
//            @Override public void onNothingSelected(AdapterView<?> parent) { /* no-op */ }
//        });
//    }

    private void initRoleSpinner() {
        String[] roles = getResources().getStringArray(R.array.roles);
        ArrayList<String> roleList = new ArrayList<>(Arrays.asList(roles));
        roleAdapter = new RoleAdapter(requireContext(), roleList);
        binding.spinnerRole.setAdapter(roleAdapter);

        binding.spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedView, int position, long id) {
                if (!hasInitialSpinnerSelectionFired) {
                    hasInitialSpinnerSelectionFired = true;
                    return; // Skip first trigger on spinner setup
                }

                if (selectedView != null) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.md_theme_primary));
                }

                String selectedRole = String.valueOf(position);

                if (selectedRole == null) return;

                if (selectedRole.equals("10")) {
                    currentSelectedEmployerId = null;
                    startShimmer();
                    homeViewModel.getJobAdvertisements("1");
                } else {
                    currentSelectedEmployerId = null;
                    startShimmer();
                    homeViewModel.getRoleJobAdvertisements("1", selectedRole);
                }
            }


            @Override public void onNothingSelected(AdapterView<?> parent) { /* no-op */ }
        });
    }


    private void initViewModel() {
        startShimmer();

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        currentSelectedEmployerId = null;

        homeViewModel.getJobAdvertisements("1");

        homeViewModel.jobAds.observe(getViewLifecycleOwner(), jobAds -> {
            if (jobAds != null) {
                jobAdAdapter.updateJobAds(jobAds);
                showJobAdsContent();
            }
        });

        homeViewModel.roleBasedJobAds.observe(getViewLifecycleOwner(), roleJobAds -> {
            if (roleJobAds != null) {
                jobAdAdapter.updateJobAds(roleJobAds);
                showJobAdsContent();
            }
        });

        homeViewModel.jobClicked.observe(getViewLifecycleOwner(), jobClicked -> {
            if (jobClicked != null) {
                openJobDetails(jobClicked.getEmployerName(), jobClicked.getId(), jobClicked.getEmployerPhoto());
            }
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
            boolean sameSelected = companyId != null && companyId.equals(currentSelectedEmployerId);
            currentSelectedEmployerId = sameSelected ? null : companyId;

            followedEmployers.forEach(e -> e.setSelected(e.getId().equals(currentSelectedEmployerId)));
            if (followedEmployerAdapter != null) followedEmployerAdapter.notifyDataSetChanged();

            startShimmer();

            if (currentSelectedEmployerId != null) {
                homeViewModel.getCompanyJobAdvertisements("1", currentSelectedEmployerId);
            } else {
                homeViewModel.getJobAdvertisements("1");
            }
        });
    }

    private void updateEmployerFollowStatus(String employerId, boolean isFollowing) {
        boolean listChanged = false;

        if (isFollowing) {
            boolean alreadyFollowed = followedEmployers.stream()
                    .anyMatch(employer -> employer.getId().equals(employerId));

            if (!alreadyFollowed) {
                FollowedEmployer newEmployer = helperClass.getEmployerById(requireContext(), employerId);
                if (newEmployer != null) {
                    followedEmployers.add(newEmployer);
                    listChanged = true;
                }
            }
        } else {
            listChanged = followedEmployers.removeIf(employer -> employer.getId().equals(employerId));

            if (employerId.equals(currentSelectedEmployerId)) {
                currentSelectedEmployerId = null;
                homeViewModel.getJobAdvertisements("1");
            }
        }

        if (listChanged && followedEmployerAdapter != null) {
            followedEmployerAdapter.notifyDataSetChanged();
        }

        toggleFollowedEmployerSection();
    }

    private void toggleFollowedEmployerSection() {
        binding.layoutFollowedEmployer.setVisibility(
                followedEmployers.isEmpty() ? View.GONE : View.VISIBLE
        );
    }

    private void setupFollowedEmployers() {
        if (followedEmployerAdapter == null) {
            followedEmployerAdapter = new FollowedEmployerAdapter(followedEmployers, requireContext(), homeViewModel);
            binding.recyclerviewFollowedCompanies.setLayoutManager(
                    new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerviewFollowedCompanies.setAdapter(followedEmployerAdapter);
        } else {
            followedEmployerAdapter.notifyDataSetChanged();
        }

        toggleFollowedEmployerSection();
    }

    private void startShimmer() {
        binding.layoutJobAdShimmer.setVisibility(View.VISIBLE);
        binding.layoutJobAdShimmer.startShimmerAnimation();
        binding.layoutJobAd.setVisibility(View.GONE);
    }

    private void showJobAdsContent() {
        binding.layoutJobAdShimmer.stopShimmerAnimation();
        binding.layoutJobAdShimmer.setVisibility(View.GONE);
        binding.layoutJobAd.setVisibility(View.VISIBLE);
    }

    private void openJobDetails(String employer, String id, String logo) {
        Intent intent = new Intent(requireContext(), JobAdvertisementDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("employer", employer);
        intent.putExtra("logo", logo);
        jobDetailsLauncher.launch(intent);
    }
}
