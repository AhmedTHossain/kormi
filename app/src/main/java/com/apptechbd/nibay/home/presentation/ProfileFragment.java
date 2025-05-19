package com.apptechbd.nibay.home.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.FragmentProfileBinding;
import com.apptechbd.nibay.home.domain.adapter.EmployerRatingAdapter;
import com.apptechbd.nibay.home.domain.model.EmployerRating;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private EmployerRatingAdapter adapter;
    private ArrayList<EmployerRating> employerRatings = new ArrayList<>();
    private HomeViewModel homeViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        createDummyEmployerRatings();

        adapter = new EmployerRatingAdapter(employerRatings, requireContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerview.setLayoutManager(layoutManager);
        binding.recyclerview.setAdapter(adapter);

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);
        initViewModel(roles);
        return binding.getRoot();
    }

    private void initViewModel(String[] roles) {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getUserProfile();
        homeViewModel.userProfile.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                binding.textName.setText(response.getName());
                binding.textRole.setText(roles[response.getRole()]);

                String location = response.getDistrict() + ", " + response.getDivision();
                binding.textLocation.setText(location);
                binding.textNid.setText(response.getNidNumber());
                binding.textDrivingLicense.setText(response.getDrivingLicense());
                binding.textExperience.setText(response.getYearsOfExperience());
                binding.textMobile.setText(response.getPhone());

                // ✅ Load profile image from URL
                String imageURLInResponse = response.getProfilePhoto();
                if (imageURLInResponse != null && !imageURLInResponse.isEmpty()) {
                    String fullURLToImage = "https://nibay.co" + imageURLInResponse;
                    Log.d("ProfileFragment", "Full URL to Image: " + fullURLToImage);

                    Glide.with(requireContext())
                            .load(fullURLToImage)
                            .placeholder(R.drawable.img_profile_photo_placeholder) // fallback while loading
                            .error(R.drawable.img_profile_photo_placeholder) // fallback on error
                            .circleCrop() // optional: make it circular
                            .into(binding.circleImageView);
                }
            } else {
                // Handle error
                new HelperClass().showSnackBar(binding.profile, getString(R.string.disclaimer_profile_load_failed));
            }
        });
    }

    private void createDummyEmployerRatings() {
        // Clear existing data
        employerRatings.clear();

        // Add job ads with sample data
        employerRatings.add(new EmployerRating("এনা ট্রান্সপোর্ট লিমিটেড", "লোরেম ইপসাম ডলর সিট আমেট, কনসেকটেটুর অ্যাডিপিসিং এলিট। সেড ডো ইইউসিমড টেম্পোর ইনসিডিডুন্ট ইউট ল্যাবোর এ ডোলোর ম্যাগনা অ্যালিকুয়া।", "https://drive.google.com/uc?id=1OrUIr35ocmqQv8_PbQ0Qnx67TOsy1Buw", 4));
        employerRatings.add(new EmployerRating("সৈয়দ আবদুল্লাহ", "লোরেম ইপসাম ডলর সিট আমেট, কনসেকটেটুর অ্যাডিপিসিং এলিট।", null, 5));
    }
}