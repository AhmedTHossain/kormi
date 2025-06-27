package com.apptechbd.nibay.home.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apptechbd.nibay.core.utils.LocaleUtils;
import com.apptechbd.nibay.databinding.FragmentAppliedJobsBinding;
import com.apptechbd.nibay.home.domain.adapter.JobAdAdapter;
import com.apptechbd.nibay.home.domain.model.AppliedJob;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.apptechbd.nibay.jobads.presentation.JobAdvertisementDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AppliedJobsFragment extends Fragment {

    private FragmentAppliedJobsBinding binding;
    private JobAdAdapter adapter;
    private HomeViewModel homeViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppliedJobsBinding.inflate(inflater, container, false);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        adapter = new JobAdAdapter(requireContext(), homeViewModel, "applied");

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerview.setAdapter(adapter);

        initViewModel();
        setupAnalyticsClickListeners();

        return binding.getRoot();
    }

    private void initViewModel() {
        binding.layoutPlaceholder.startShimmerAnimation();
        binding.layoutPlaceholder.setVisibility(View.VISIBLE);

        homeViewModel.getAppliedJobs();
        homeViewModel.appliedJobs.observe(getViewLifecycleOwner(), appliedJobs -> {
            if (appliedJobs != null && appliedJobs.getData() != null) {
                binding.textNumAppliedJobs.setText(String.valueOf(appliedJobs.getData().size()));
                binding.textNumOfferedJobs.setText(String.valueOf(appliedJobs.getAcceptedApplications()));
                binding.textNumRejectedJobs.setText(String.valueOf(appliedJobs.getRejectedApplications()));

                displayAppliedJobs(appliedJobs.getData());
                binding.layoutNoJobs.setVisibility(View.GONE);
            } else {
                binding.textNumAppliedJobs.setText("0");
                binding.textNumOfferedJobs.setText("0");
                binding.textNumRejectedJobs.setText("0");

                binding.recyclerview.setVisibility(View.GONE);
                binding.layoutAnalytics.setVisibility(View.VISIBLE);
                binding.layoutNoJobs.setVisibility(View.VISIBLE);
            }

            binding.layoutPlaceholder.stopShimmerAnimation();
            binding.layoutPlaceholder.setVisibility(View.GONE);
            binding.recyclerview.setVisibility(View.VISIBLE);
            binding.layoutAnalytics.setVisibility(View.VISIBLE);
        });

        homeViewModel.jobClicked.observe(getViewLifecycleOwner(), jobClicked -> {
            if (jobClicked != null) {
                Intent intent = new Intent(requireContext(), JobAdvertisementDetailActivity.class);
                intent.putExtra("employer", jobClicked.getEmployerName());
                intent.putExtra("id", jobClicked.getId());
                intent.putExtra("logo", jobClicked.getEmployerPhoto());
                startActivity(intent);
            }
        });
    }

    private void displayAppliedJobs(List<AppliedJob> appliedJobs) {
        ArrayList<JobAd> jobAdList = new ArrayList<>();
        Map<Locale, String[]> rolesMap = LocaleUtils.getLocalizedRolesArray(requireContext());
        String[] englishRoles = rolesMap.get(new Locale("en"));
        String[] banglaRoles = rolesMap.get(new Locale("bn"));

        for (AppliedJob job : appliedJobs) {
            JobAd jobAd = new JobAd();
            jobAd.setId(job.getId());
            jobAd.setTitle(job.getTitle());
            jobAd.setEmployerName(job.getEmployerName());
            jobAd.setJobRoleTxtEn(englishRoles[job.getRole()]);
            jobAd.setJobRoleTxtBn(banglaRoles[job.getRole()]);
            jobAd.setDivision(job.getDivision());
            jobAd.setDistrict(job.getDistrict());
            jobAd.setApplicationDeadline(job.getApplicationDeadline());
            jobAd.setApplicationStatus(job.getApplicationStatus());
            jobAd.setEmployerPhoto(job.getEmployerPhoto());

            jobAdList.add(jobAd);
        }

        adapter.submitList(jobAdList);
    }

    private void setupAnalyticsClickListeners() {
        binding.layoutAnalyticsApplied.setOnClickListener(v -> {});
        binding.layoutAnalyticsOffered.setOnClickListener(v -> {});
        binding.layoutAnalyticsRejected.setOnClickListener(v -> {});
    }
}
