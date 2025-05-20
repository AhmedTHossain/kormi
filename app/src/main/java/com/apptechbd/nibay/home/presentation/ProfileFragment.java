package com.apptechbd.nibay.home.presentation;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.GridSpacingItemDecoration;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.FragmentProfileBinding;
import com.apptechbd.nibay.home.domain.adapter.EmployerRatingAdapter;
import com.apptechbd.nibay.home.domain.adapter.ProfileDocumentsAdapter;
import com.apptechbd.nibay.home.domain.model.EmployerRating;
import com.apptechbd.nibay.home.domain.model.ProfileDocument;
import com.apptechbd.nibay.home.domain.model.ProfileRsponseData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private EmployerRatingAdapter adapter;
    private ProfileDocumentsAdapter documentsAdapter;
    private ArrayList<EmployerRating> employerRatings = new ArrayList<>();
    private HomeViewModel homeViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);
        String[] educationQualification = requireContext().getResources().getStringArray(R.array.educationQualifications);

        initViewModel(roles, educationQualification);
        return binding.getRoot();
    }

    private void initViewModel(String[] roles, String[] educationQualification) {
        binding.layoutProfileLoading.startShimmerAnimation();
        binding.layoutProfileLoading.setVisibility(VISIBLE);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getUserProfile();
        homeViewModel.userProfile.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                homeViewModel.getReviews(response.getId());
                homeViewModel.employerRating.observe(getViewLifecycleOwner(), employerRatingResponseData -> {
                    if (employerRatingResponseData != null) {
                        int averageRating = employerRatingResponseData.getAverageRating();
                        binding.ratingBar.setRating(averageRating);

                        employerRatings.clear();
                        employerRatings.addAll(employerRatingResponseData.getReviews());
                        setReviews();
                        setDocuments(response);
                    }
                });

                binding.textName.setText(response.getName());
                binding.textRole.setText(roles[response.getRole()]);

                String location = response.getDistrict() + ", " + response.getDivision();
                binding.textLocation.setText(location);
                binding.textNid.setText(response.getNidNumber());
                binding.textDrivingLicense.setText(response.getDrivingLicense());

                String experience = response.getYearsOfExperience() + " years";
                binding.textExperience.setText(experience);
                binding.textMobile.setText(response.getPhone());

                binding.textDegree.setText(educationQualification[response.getMaxEducationLevel()]);

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

//                    binding.layoutProfileLoading.stopShimmerAnimation();
//                    binding.layoutProfileLoading.setVisibility(View.GONE);
//                    binding.layoutProfile.setVisibility(VISIBLE);
                }
            } else {
                binding.layoutProfileLoading.stopShimmerAnimation();
                binding.layoutProfileLoading.setVisibility(View.GONE);
                // Handle error
                new HelperClass().showSnackBar(binding.profile, getString(R.string.disclaimer_profile_load_failed));
            }
        });
    }

    private void setReviews() {
        if (!employerRatings.isEmpty()) {
            adapter = new EmployerRatingAdapter(employerRatings, requireContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
            binding.recyclerview.setLayoutManager(layoutManager);
            binding.recyclerview.setAdapter(adapter);

            binding.recyclerview.setVisibility(VISIBLE);
            binding.layoutNoReview.setVisibility(View.GONE);
        } else {
            binding.layoutNoReview.setVisibility(View.VISIBLE);
            binding.recyclerview.setVisibility(View.GONE);
        }

        binding.layoutProfileLoading.stopShimmerAnimation();
        binding.layoutProfileLoading.setVisibility(View.GONE);
        binding.layoutProfile.setVisibility(VISIBLE);
    }

    private void setDocuments(ProfileRsponseData response) {
        ArrayList<ProfileDocument> documents = new ArrayList<>();

        if (response.getNidCopy() != null) {
            ProfileDocument nidDocument = new ProfileDocument("NID", response.getNidCopy());
            documents.add(nidDocument);
        }

        if (response.getDrivingLicenseCopy() != null) {
            ProfileDocument drivingLicenseDocument = new ProfileDocument("Driving License", response.getDrivingLicenseCopy());
            documents.add(drivingLicenseDocument);
        }

        if (response.getChairmanCertificateCopy() != null) {
            ProfileDocument chairmanCertificateDocument = new ProfileDocument("Birth Certificate", response.getChairmanCertificateCopy());
            documents.add(chairmanCertificateDocument);
        }

        if (response.getPortEntryPermit() != null) {
            ProfileDocument portEntryPermitDocument = new ProfileDocument("Port Entry Permit", response.getPortEntryPermit());
            documents.add(portEntryPermitDocument);
        }

        if (response.getBirthCertificate() != null) {
            ProfileDocument educationCertificateDocument = new ProfileDocument("Degree Certificate", response.getBirthCertificate());
            documents.add(educationCertificateDocument);
        }

        int spanCount = 2; // Number of columns
        int spacing = 25; // Spacing in pixels (or use getResources().getDimensionPixelSize(R.dimen.spacing))
        boolean includeEdge = false;

        documentsAdapter = new ProfileDocumentsAdapter(requireContext(),documents);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);

        binding.recyclerviewDocuments.setLayoutManager(layoutManager);
        binding.recyclerviewDocuments.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        binding.recyclerviewDocuments.setAdapter(documentsAdapter);
    }
}