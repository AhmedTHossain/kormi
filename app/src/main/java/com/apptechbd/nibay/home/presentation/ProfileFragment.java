package com.apptechbd.nibay.home.presentation;

import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.GridSpacingItemDecoration;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.ImageUtils;
import com.apptechbd.nibay.core.utils.ProgressDialog;
import com.apptechbd.nibay.databinding.FragmentProfileBinding;
import com.apptechbd.nibay.home.domain.adapter.EmployerRatingAdapter;
import com.apptechbd.nibay.home.domain.adapter.ProfileDocumentsAdapter;
import com.apptechbd.nibay.home.domain.model.EmployerRating;
import com.apptechbd.nibay.home.domain.model.ProfileDocument;
import com.apptechbd.nibay.home.domain.model.ProfileRsponseData;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private EmployerRatingAdapter adapter;
    private ProfileDocumentsAdapter documentsAdapter;
    private ArrayList<EmployerRating> employerRatings = new ArrayList<>();
    private HomeViewModel homeViewModel;

    private File imageFile;
    private Uri resultUri;
    private String documentType;

    private AlertDialog alertDialog;
    private final ActivityResultLauncher<Intent> cropImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    final Intent data = result.getData();
                    resultUri = UCrop.getOutput(data);
                    if (resultUri != null) {
                        imageFile = new ImageUtils().rotateImage(resultUri, requireContext());

                        Log.d("ProfileFragment", "image file cropped = " + imageFile);

//                        Glide.with(requireContext())
//                                .load(imageFile)
//                                .placeholder(R.drawable.img_profile_photo_placeholder)
//                                .error(R.drawable.img_profile_photo_placeholder)
//                                .circleCrop()
//                                .into(binding.circleImageView);
                        switch (documentType) {
                            case "NID":
                                alertDialog = new ProgressDialog().showLoadingDialog(getResources().getString(R.string.uploading_nid_progress_dialog_title_text), getResources().getString(R.string.uploading_nid_progress_dialog_body_text), requireContext());
                                homeViewModel.uploadNIDPhoto(imageFile);
                                break;
                            default:
                                alertDialog = new ProgressDialog().showLoadingDialog(getResources().getString(R.string.uploading_photo_progress_dialog_title_text), getResources().getString(R.string.uploading_photo_progress_dialog_body_text), requireContext());
                                homeViewModel.uploadProfilePhoto(imageFile);
                                break;
                        }
                    }
                } else if (result.getResultCode() == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(result.getData());
                    Toast.makeText(requireContext(), "Crop failed: " + cropError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                Uri destinationUri = Uri.fromFile(new File(requireContext().getCacheDir(), "cropped_image.jpg"));

                if (uri != null) {
                    Intent uCropIntent = UCrop.of(uri, destinationUri)
                            .withAspectRatio(1, 1)
                            .withMaxResultSize(400, 400)
                            .getIntent(requireContext());

                    cropImageLauncher.launch(uCropIntent);
                }
            });

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);
        String[] educationQualification = requireContext().getResources().getStringArray(R.array.educationQualifications);

        binding.buttonEditePhoto.setOnClickListener(v -> onPhotoEdit());

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
                if (response.getDrivingLicense() != null) {
                    binding.textDrivingLicenseFieldName.setVisibility(View.VISIBLE);
                    binding.textDrivingLicense.setText(response.getDrivingLicense());
                } else {
                    binding.textDrivingLicense.setVisibility(View.GONE);
                    binding.textDrivingLicenseFieldName.setVisibility(View.GONE);
                }

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
//        homeViewModel.isProfilePhotoUploaded.observe(getViewLifecycleOwner(), isUploaded -> {
//            Log.d("ProfileFragment", "isUploaded called = " + "YES");
//            if (isUploaded)
//                new HelperClass().showSnackBar(binding.profile, getString(R.string.photo_uploaded_successfully));
//            else
//                new HelperClass().showSnackBar(binding.profile, getString(R.string.photo_upload_failed));
//            alertDialog.dismiss();
//        });

        homeViewModel.isProfilePhotoUploaded.observe(getViewLifecycleOwner(), isUploaded -> {
            Log.d("ProfileFragment", "isUploaded called = YES");
            if (isUploaded) {
                binding.circleImageView.setImageURI(resultUri);
                new HelperClass().showSnackBar(binding.profile, getString(R.string.photo_uploaded_successfully));
            } else
                new HelperClass().showSnackBar(binding.profile, getString(R.string.photo_upload_failed));
            alertDialog.dismiss();
        });

//        homeViewModel.isNidPhotoUploaded.observe(getViewLifecycleOwner(), isUploaded -> {
//            Log.d("ProfileFragment", "isUploaded called = YES");
//            if (isUploaded) {
//                // Update adapter with local URI (converted to String)
//                documentsAdapter.updateDocument("NID", resultUri.toString());
//                new HelperClass().showSnackBar(binding.profile, getString(R.string.photo_uploaded_successfully));
//            }
//            else
//                new HelperClass().showSnackBar(binding.profile, getString(R.string.photo_upload_failed));
//        });

        homeViewModel.isNidPhotoUploaded.observe(getViewLifecycleOwner(), isUploaded -> {
            alertDialog.dismiss();
            if (isUploaded) {
                documentsAdapter.updateDocument("NID", resultUri.toString());
                new HelperClass().showSnackBar(binding.profile, getString(R.string.photo_uploaded_successfully));
            } else {
                new HelperClass().showSnackBar(binding.profile, getString(R.string.photo_upload_failed));
            }
        });

        homeViewModel.documentClicked.observe(getViewLifecycleOwner(), documentClicked -> {
            if (documentClicked) {
                documentType = homeViewModel.getDocumentTypeToUpdate();
                onPhotoEdit();
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

        if (response.getDrivingLicense() != null) {
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

        if (response.getMaxEducationLevelCertificateCopy() != null) {
            ProfileDocument educationCertificateDocument = new ProfileDocument("Degree Certificate", response.getMaxEducationLevelCertificateCopy());
            documents.add(educationCertificateDocument);
        }

        int spanCount = 2; // Number of columns
        int spacing = 25; // Spacing in pixels (or use getResources().getDimensionPixelSize(R.dimen.spacing))
        boolean includeEdge = false;

        documentsAdapter = new ProfileDocumentsAdapter(requireContext(), documents, homeViewModel);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);

        binding.recyclerviewDocuments.setLayoutManager(layoutManager);
        binding.recyclerviewDocuments.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        binding.recyclerviewDocuments.setAdapter(documentsAdapter);
    }

    private void onPhotoEdit() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)  //app compiles and run properly even after this error
                .build());
    }
}