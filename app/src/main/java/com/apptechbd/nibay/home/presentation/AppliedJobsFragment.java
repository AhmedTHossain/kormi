package com.apptechbd.nibay.home.presentation;

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
import com.apptechbd.nibay.databinding.FragmentAppliedJobsBinding;
import com.apptechbd.nibay.home.domain.adapter.JobAdAdapter;
import com.apptechbd.nibay.home.domain.model.JobAd;

import java.util.ArrayList;

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
        binding = FragmentAppliedJobsBinding.inflate(inflater,container,false);

        createDummyJobAds();

        adapter = new JobAdAdapter(jobAds, requireContext(), homeViewModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerview.setLayoutManager(layoutManager);
        binding.recyclerview.setAdapter(adapter);


        return binding.getRoot();
    }

    //ToDo: Create a dummy job ads later on when apis are integrated fetch job advertisements from server
    private void createDummyJobAds(){
        jobAds.clear();

        // Add job ads with sample data
        jobAds.add(new JobAd("Driver", "BRAC", "Dhaka", "2023-12-31", "Applied", R.drawable.employer_logo_placeholder));
        jobAds.add(new JobAd("Mechanic/Mistry", "Shohag Paribahan (PVT) Ltd.", "Sylhet", "2024-01-15", "Applied", R.drawable.employer_logo_placeholder_1));
        jobAds.add(new JobAd("Driver", "Driver for private car", "Chittagong", "2024-02-29", "Rejected", R.drawable.ic_driver_private_placeholder));
        jobAds.add(new JobAd("Data Scientist", "Ena Transport Ltd.", "Mymensingh", "2024-03-31", "Applied", R.drawable.employer_logo_placeholder_2));
    }
}