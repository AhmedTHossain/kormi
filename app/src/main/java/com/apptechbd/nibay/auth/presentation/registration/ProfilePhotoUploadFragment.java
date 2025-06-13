package com.apptechbd.nibay.auth.presentation.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.auth.presentation.landing.LandingActivity;
import com.apptechbd.nibay.auth.presentation.login.LoginActivity;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.ImageUtils;
import com.apptechbd.nibay.databinding.FragmentProfilePhotoUploadBinding;
import com.apptechbd.nibay.home.presentation.HomeActivity;

import java.io.File;

public class ProfilePhotoUploadFragment extends Fragment {
    private FragmentProfilePhotoUploadBinding binding;
    private Uri selectedImageUri;
    private File imageFile;
    private boolean isImagePicked;
    private RegistrationViewModel viewModel;
    private ViewPager2 viewPager2;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;

                    imageFile = new ImageUtils().rotateImage(uri, requireContext());
                    binding.circleImageView.setImageURI(uri);
                    isImagePicked = true;
                    updateButtonState();
                }
            });

    public ProfilePhotoUploadFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilePhotoUploadBinding.inflate(inflater, container, false);
        initViewModel();

        binding.buttonSelectPhoto.setOnClickListener(v -> openImagePicker());
        binding.buttonNext.setOnClickListener(v -> {
            RegisterUserModel user = viewModel.getUser();
            user.setProfilePhotoImage(imageFile);

            int currentFragment = viewPager2.getCurrentItem();
            viewModel.goToNextPage(currentFragment);

            Log.d("ProfilePhotoUploadFragment", "Last screen of Registration has the user profile = " + viewModel.getUser().toString());

            viewModel.registerUser(user);

//            viewModel.registeredUser.observe(requireActivity(), responseUser -> {
//                if (responseUser != null) {
//                    startActivity(new Intent(requireActivity(), LoginActivity.class));
//                    requireActivity().finish();
//                } else
//                    new HelperClass().showSnackBar(binding.getRoot(), "Something went wrong");
//            });

//            startActivity(new Intent(requireActivity(), LandingActivity.class));
//            requireActivity().finish();
        });

        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        viewModel.registeredUser.observe(requireActivity(), responseUser -> {
            if (responseUser != null) {
                startActivity(new Intent(requireActivity(), LoginActivity.class));
                requireActivity().finish();
            } else
                new HelperClass().showSnackBar(binding.getRoot(), "Something went wrong");
        });
    }

    private void openImagePicker() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
    }

    private void updateButtonState() {
        if (isImagePicked) {
            binding.buttonNext.setBackgroundColor(requireContext().getColor(R.color.md_theme_secondary));
            binding.buttonNext.setTextColor(requireContext().getColor(R.color.md_theme_background));
            binding.buttonNext.setEnabled(true);
        }
    }
}