package com.apptechbd.nibay.home.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.adapter.RoleAdapter;
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
    private RoleAdapter adapter;
    private FollowedEmployerAdapter followedEmployerAdapter;
    private JobAdAdapter jobAdAdapter;
    private ArrayList<FollowedEmployer> followedEmployers = new ArrayList<>();
    private ArrayList<JobAd> jobAds = new ArrayList<>();
    private HomeViewModel homeViewModel;

    public JobAdvertisementsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJobAdvertisementsBinding.inflate(inflater, container, false);

        initViewModel();

        createDummyEmployerRatings();
        createDummyJobAds();

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);
        ArrayList<String> roleList = new ArrayList<>(Arrays.asList(roles));

        adapter = new RoleAdapter(requireContext(), roleList);
        jobAdAdapter = new JobAdAdapter(jobAds, requireContext(), homeViewModel);

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
                if (selectedItemView != null)
                    ((TextView) parentView.getChildAt(0)).setTextColor(getResources().getColor(R.color.md_theme_onPrimary));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       return binding.getRoot();
    }

    private void initViewModel() {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
//        homeViewModel.onJobClicked();
        homeViewModel.isJobClicked.observe(getViewLifecycleOwner(), isJobClicked -> {
            if (isJobClicked) {
                startActivity(new Intent(requireContext(), JobAdvertisementDetailActivity.class));
            }
        });
    }

    //ToDo: Create a dummy followed employers list later on when apis are integrated fetch job advertisements from server
    private void createDummyEmployerRatings() {
        // Clear existing data
        followedEmployers.clear();

        // Add job ads with sample data
        followedEmployers.add(new FollowedEmployer("এনা", "https://drive.google.com/uc?id=1OrUIr35ocmqQv8_PbQ0Qnx67TOsy1Buw", true, 1));
        followedEmployers.add(new FollowedEmployer("সোহাগ", "https://drive.google.com/uc?id=1I_rAFH5XiB-PLGXvAepQMEaSrdqnG1D5", true, 2));
        followedEmployers.add(new FollowedEmployer("ব্র্যাক", "https://drive.google.com/uc?id=1Mbs0VHhUrbKHZn0-2jxNAEZQUcvmyJG7", true, 3));
        followedEmployers.add(new FollowedEmployer("BRTC", "https://drive.google.com/uc?id=1b1MWNq3FrMAjxgSUv0Tc8tE3EkxtMSUN", true, 4));
    }

    //ToDo: Create a dummy job ads later on when apis are integrated fetch job advertisements from server
    private void createDummyJobAds(){
        jobAds.clear();

        // Add job ads with sample data
        jobAds.add(new JobAd("ড্রাইভার", "ব্র্যাক", "ঢাকা", "২০২৩-১২-৩১", "আবেদন করা হয়েছে", R.drawable.employer_logo_placeholder));
        jobAds.add(new JobAd("মেকানিক/মিস্ত্রি", "সোহাগ পরিবহন (প্রা:) লিমিটেড", "সিলেট", "২০২৪-০১-১৫", null, R.drawable.employer_logo_placeholder_1));
        jobAds.add(new JobAd("ড্রাইভার", "প্রাইভেট কারের জন্য ড্রাইভার", "চট্টগ্রাম", "২০২৪-০২-২৯", "নাকচ করা হয়েছে", R.drawable.ic_driver_private_placeholder));
        jobAds.add(new JobAd("ড্রাইভার", "এনা ট্রান্সপোর্ট লিমিটেড", "ময়মনসিংহ", "২০২৪-০৩-৩১", null, R.drawable.employer_logo_placeholder_2));
    }
}