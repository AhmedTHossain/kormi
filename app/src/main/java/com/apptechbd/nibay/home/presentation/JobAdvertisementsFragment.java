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
import com.apptechbd.nibay.home.domain.models.FollowedEmployer;

import java.util.ArrayList;
import java.util.Arrays;

public class JobAdvertisementsFragment extends Fragment {
    private FragmentJobAdvertisementsBinding binding;
    private RoleAdapter adapter;
    private FollowedEmployerAdapter followedEmployerAdapter;
    private ArrayList<FollowedEmployer> followedEmployers = new ArrayList<>();

    public JobAdvertisementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJobAdvertisementsBinding.inflate(inflater, container, false);

        createDummyEmployerRatings();

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);
        ArrayList<String> roleList = new ArrayList<>(Arrays.asList(roles));

        adapter = new RoleAdapter(requireContext(), roleList);
        followedEmployerAdapter = new FollowedEmployerAdapter(followedEmployers, requireContext());


        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerviewFollowedCompanies.setLayoutManager(layoutManager);
        binding.recyclerviewFollowedCompanies.setAdapter(followedEmployerAdapter);

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

    private void createDummyEmployerRatings() {
        // Clear existing data
        followedEmployers.clear();

        // Add job ads with sample data
        followedEmployers.add(new FollowedEmployer("Ena", "https://drive.google.com/uc?id=1OrUIr35ocmqQv8_PbQ0Qnx67TOsy1Buw", true, 1));
        followedEmployers.add(new FollowedEmployer("Shohag", "https://drive.google.com/uc?id=1I_rAFH5XiB-PLGXvAepQMEaSrdqnG1D5", true, 2));
        followedEmployers.add(new FollowedEmployer("BRAC", "https://drive.google.com/uc?id=1Mbs0VHhUrbKHZn0-2jxNAEZQUcvmyJG7", true, 3));
        followedEmployers.add(new FollowedEmployer("BRTC", "https://drive.google.com/uc?id=1b1MWNq3FrMAjxgSUv0Tc8tE3EkxtMSUN", true, 4));
    }
}