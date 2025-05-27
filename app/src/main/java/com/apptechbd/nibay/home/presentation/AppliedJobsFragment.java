package com.apptechbd.nibay.home.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.LocaleUtils;
import com.apptechbd.nibay.databinding.FragmentAppliedJobsBinding;
import com.apptechbd.nibay.home.domain.adapter.JobAdAdapter;
import com.apptechbd.nibay.home.domain.model.AppliedJob;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.apptechbd.nibay.home.domain.model.JobAdDetails;
import com.apptechbd.nibay.jobads.presentation.JobAdvertisementDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AppliedJobsFragment extends Fragment {
    private FragmentAppliedJobsBinding binding;
    private JobAdAdapter adapter;
    private ArrayList<JobAd> jobAds = new ArrayList<>();
    private HomeViewModel homeViewModel;

    public AppliedJobsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppliedJobsBinding.inflate(inflater, container, false);
        initViewModel();

        adapter = new JobAdAdapter(jobAds, requireContext(), homeViewModel, "applied");
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerview.setLayoutManager(layoutManager);
        binding.recyclerview.setAdapter(adapter);


        return binding.getRoot();
    }

    private void initViewModel() {
        binding.layoutPlaceholder.startShimmerAnimation();
        binding.layoutPlaceholder.setVisibility(View.VISIBLE);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getAppliedJobs();
        homeViewModel.appliedJobs.observe(getViewLifecycleOwner(), appliedJobs -> {
            if (appliedJobs != null) {
                binding.textNumAppliedJobs.setText(String.valueOf(appliedJobs.getData().size()));
                binding.textNumOfferedJobs.setText(String.valueOf(appliedJobs.getAcceptedApplications()));
                binding.textNumRejectedJobs.setText(String.valueOf(appliedJobs.getRejectedApplications()));

                displayAppliedJobs(appliedJobs.getData());
            }

            binding.layoutPlaceholder.stopShimmerAnimation();
            binding.layoutPlaceholder.setVisibility(View.GONE);
            binding.layoutContent.setVisibility(View.VISIBLE);
        });
        homeViewModel.jobClicked.observe(getViewLifecycleOwner(), jobClicked -> {
            if (jobClicked != null) {
                Intent intent = new Intent(requireContext(), JobAdvertisementDetailActivity.class);
                intent.putExtra("employer",jobClicked.getEmployerName());
                intent.putExtra("id",jobClicked.getId());
                intent.putExtra("logo",jobClicked.getEmployerPhoto());
                startActivity(intent);
            }
        });
    }

    private void displayAppliedJobs(List<AppliedJob> appliedJobs) {
        ArrayList<JobAd> jobAdDetailsList = new ArrayList<>();
        for (AppliedJob job : appliedJobs) {
            JobAd jobAd = new JobAd();
            jobAd.setId(job.getId());
            jobAd.setTitle(job.getTitle());
            jobAd.setEmployerName(job.getEmployerName());

            Map<Locale, String[]> rolesMap = LocaleUtils.getLocalizedRolesArray(requireContext());
            String[] englishRoles = rolesMap.get(new Locale("en"));
            String[] banglaRoles = rolesMap.get(new Locale("bn"));


            jobAd.setJobRoleTxtEn(englishRoles[job.getRole()]);
            jobAd.setJobRoleTxtBn(banglaRoles[job.getRole()]);

            jobAd.setDivision(job.getDivision());
            jobAd.setDistrict(job.getDistrict());
            jobAd.setApplicationDeadline(job.getApplicationDeadline());

            jobAd.setApplicationStatus(job.getApplicationStatus());
            jobAd.setEmployerPhoto(job.getEmployerPhoto());

            jobAdDetailsList.add(jobAd);
        }

        jobAds.clear();
        jobAds.addAll(jobAdDetailsList);
        adapter.notifyDataSetChanged();
//        binding.layoutProgress.setVisibility(View.GONE);
    }

    //ToDo: Create a dummy job ads later on when apis are integrated fetch job advertisements from server
//    private void createDummyJobAds(){
//        jobAds.clear();
//
//        // Add job ads with sample data
//        jobAds.add(new JobAd("Driver", "BRAC", "Dhaka", "2023-12-31", "Applied", R.drawable.employer_logo_placeholder));
//        jobAds.add(new JobAd("Mechanic/Mistry", "Shohag Paribahan (PVT) Ltd.", "Sylhet", "2024-01-15", "Applied", R.drawable.employer_logo_placeholder_1));
//        jobAds.add(new JobAd("Driver", "Driver for private car", "Chittagong", "2024-02-29", "Rejected", R.drawable.ic_driver_private_placeholder));
//        jobAds.add(new JobAd("Data Scientist", "Ena Transport Ltd.", "Mymensingh", "2024-03-31", "Applied", R.drawable.employer_logo_placeholder_2));
//    }
}