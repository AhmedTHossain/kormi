package com.apptechbd.nibay.home.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.adapters.RoleAdapter;
import com.apptechbd.nibay.databinding.FragmentJobAdvertisementsBinding;
import com.apptechbd.nibay.home.domain.adapters.EmployerRatingAdapter;
import com.apptechbd.nibay.home.domain.adapters.FollowedEmployerAdapter;
import com.apptechbd.nibay.home.domain.adapters.JobAdAdapter;
import com.apptechbd.nibay.home.domain.models.FollowedEmployer;
import com.apptechbd.nibay.home.domain.models.JobAd;

import java.util.ArrayList;
import java.util.Arrays;

public class JobAdvertisementsFragment extends Fragment {
    private FragmentJobAdvertisementsBinding binding;
    private RoleAdapter adapter;
    private FollowedEmployerAdapter followedEmployerAdapter;
    private JobAdAdapter jobAdAdapter;
    private ArrayList<FollowedEmployer> followedEmployers = new ArrayList<>();
    private ArrayList<JobAd> jobAds = new ArrayList<>();

    public JobAdvertisementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJobAdvertisementsBinding.inflate(inflater, container, false);

        createDummyEmployerRatings();
        createDummyJobAds();

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);
        ArrayList<String> roleList = new ArrayList<>(Arrays.asList(roles));

        adapter = new RoleAdapter(requireContext(), roleList);
        jobAdAdapter = new JobAdAdapter(jobAds, requireContext());

        followedEmployerAdapter = new FollowedEmployerAdapter(followedEmployers, requireContext());


        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerviewFollowedCompanies.setLayoutManager(layoutManager);
        binding.recyclerviewFollowedCompanies.setAdapter(followedEmployerAdapter);


        LinearLayoutManager layoutManagerAds = new LinearLayoutManager(requireContext());
        binding.recyclerviewJobAds.setLayoutManager(layoutManagerAds);
        binding.recyclerviewJobAds.setAdapter(jobAdAdapter);

        binding.spinnerRole.setAdapter(adapter);

        binding.spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextColor(getResources().getColor(R.color.md_theme_onPrimary));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       return binding.getRoot();
    }

    //ToDo: Create a dummy followed employers list later on when apis are integrated fetch job advertisements from server
    private void createDummyEmployerRatings() {
        // Clear existing data
        followedEmployers.clear();

        // Add job ads with sample data
        followedEmployers.add(new FollowedEmployer("Ena", "https://drive.google.com/uc?id=1OrUIr35ocmqQv8_PbQ0Qnx67TOsy1Buw", true, 1));
        followedEmployers.add(new FollowedEmployer("Shohag", "https://drive.google.com/uc?id=1I_rAFH5XiB-PLGXvAepQMEaSrdqnG1D5", true, 2));
        followedEmployers.add(new FollowedEmployer("BRAC", "https://drive.google.com/uc?id=1Mbs0VHhUrbKHZn0-2jxNAEZQUcvmyJG7", true, 3));
        followedEmployers.add(new FollowedEmployer("BRTC", "https://drive.google.com/uc?id=1b1MWNq3FrMAjxgSUv0Tc8tE3EkxtMSUN", true, 4));
    }

    //ToDo: Create a dummy job ads later on when apis are integrated fetch job advertisements from server
    private void createDummyJobAds(){
        jobAds.clear();

        // Add job ads with sample data
        jobAds.add(new JobAd("Driver", "BRAC", "Dhaka", "2023-12-31", "Applied", R.drawable.employer_logo_placeholder));
        jobAds.add(new JobAd("Mechanic/Mistry", "Shohag Paribahan (PVT) Ltd.", "Sylhet", "2024-01-15", null, R.drawable.employer_logo_placeholder_1));
        jobAds.add(new JobAd("Driver", "Driver for private car", "Chittagong", "2024-02-29", "Rejected", R.drawable.ic_driver_private_placeholder));
        jobAds.add(new JobAd("Manager", "Ena Transport Ltd.", "Mymensingh", "2024-03-31", null, R.drawable.employer_logo_placeholder_2));
    }
}