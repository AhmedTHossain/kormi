package com.apptechbd.nibay.home.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.FragmentJobAdvertisementsBinding;
import com.apptechbd.nibay.home.domain.adapter.FollowedEmployerAdapter;
import com.apptechbd.nibay.home.domain.adapter.JobAdAdapter;
import com.apptechbd.nibay.home.domain.model.FollowedEmployer;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.apptechbd.nibay.jobads.presentation.JobAdvertisementDetailActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

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
    private HelperClass helperClass = new HelperClass();
    private int pageNumber = 1;
    private ShimmerFrameLayout shimmerFrameLayout;

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

        shimmerFrameLayout = binding.layoutJobAdShimmer;

        initViewModel();
        Log.d("JobAdvertisementsFragment", "device id: " + helperClass.getAndroidId(requireContext()));

//        createDummyJobAds();

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);
        ArrayList<String> roleList = new ArrayList<>(Arrays.asList(roles));

        adapter = new RoleAdapter(requireContext(), roleList);
        jobAdAdapter = new JobAdAdapter(jobAds, requireContext(), homeViewModel);

        binding.spinnerRole.setAdapter(adapter);

        binding.spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (selectedItemView != null)
                    ((TextView) parentView.getChildAt(0)).setTextColor(getResources().getColor(R.color.md_theme_primary));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initViewModel() {
        binding.layoutJobAdShimmer.startShimmerAnimation();
        binding.layoutJobAdShimmer.setVisibility(View.VISIBLE);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
//        homeViewModel.onJobClicked();
        homeViewModel.jobClicked.observe(getViewLifecycleOwner(), jobClicked -> {
            if (jobClicked != null) {
                Intent intent = new Intent(requireContext(), JobAdvertisementDetailActivity.class);
                intent.putExtra("employer",jobClicked.getEmployerName());
                intent.putExtra("id",jobClicked.getId());
                intent.putExtra("logo",jobClicked.getEmployerPhoto());
                startActivity(intent);
            }
        });

        homeViewModel.getFollowedEmployers();
        homeViewModel.isFollowedEmployersFetched.observe(getViewLifecycleOwner(), isFollowedEmployersFetched -> {
            if (isFollowedEmployersFetched) {
                followedEmployers.clear();
                followedEmployers.addAll(helperClass.getFollowedEmployers(requireContext()));
                setFollowedEmployerList();
            } else
                helperClass.showSnackBar(binding.jobAdvertisementFragment, getString(R.string.no_employers_followed_disclaimer_text));
        });

        homeViewModel.getJobAdvertisements(String.valueOf(pageNumber));
        homeViewModel.isJobAdvertisementsFetched.observe(getViewLifecycleOwner(), isJobAdvertisementsFetched -> {
            if (isJobAdvertisementsFetched) {
                if (pageNumber == 1)
                    jobAds.clear();
                jobAds.addAll(helperClass.getJobAdvertisementList(requireContext()));
                setJobAdvertisementList();
            } else
                helperClass.showSnackBar(binding.jobAdvertisementFragment, getString(R.string.no_job_advertisements_disclaimer_text));

            binding.layoutJobAdShimmer.stopShimmerAnimation();
            binding.layoutJobAdShimmer.setVisibility(View.GONE);
        });
    }

    private void setFollowedEmployerList() {
        followedEmployerAdapter = new FollowedEmployerAdapter(followedEmployers, requireContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerviewFollowedCompanies.setLayoutManager(layoutManager);
        binding.recyclerviewFollowedCompanies.setAdapter(followedEmployerAdapter);
        binding.layoutFollowedEmployer.setVisibility(View.VISIBLE);
    }

    private void setJobAdvertisementList() {
        LinearLayoutManager layoutManagerAds = new LinearLayoutManager(requireContext());
        binding.recyclerviewJobAds.setLayoutManager(layoutManagerAds);
        binding.recyclerviewJobAds.setAdapter(jobAdAdapter);

        binding.layoutJobAd.setVisibility(View.VISIBLE);
    }

}