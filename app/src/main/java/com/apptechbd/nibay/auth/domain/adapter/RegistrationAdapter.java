package com.apptechbd.nibay.auth.domain.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.apptechbd.nibay.auth.presentation.registration.EducationInputFragment;
import com.apptechbd.nibay.auth.presentation.registration.EducationTrascriptUploadFragment;
import com.apptechbd.nibay.auth.presentation.registration.ExperienceInputFragment;
import com.apptechbd.nibay.auth.presentation.registration.LicenseInputFragment;
import com.apptechbd.nibay.auth.presentation.registration.LicenseUploadFragment;
import com.apptechbd.nibay.auth.presentation.registration.LocationInputFragment;
import com.apptechbd.nibay.auth.presentation.registration.NameInputFragment;
import com.apptechbd.nibay.auth.presentation.registration.NidInputFragment;
import com.apptechbd.nibay.auth.presentation.registration.NidUploadFragment;
import com.apptechbd.nibay.auth.presentation.registration.PhoneInputFragment;
import com.apptechbd.nibay.auth.presentation.registration.ProfilePhotoUploadFragment;
import com.apptechbd.nibay.auth.presentation.registration.RegistrationViewModel;
import com.apptechbd.nibay.auth.presentation.registration.RoleInputFragment;

import java.util.ArrayList;
import java.util.List;

public class RegistrationAdapter extends FragmentStateAdapter {
    private final RegistrationViewModel registrationViewModel;
    private final List<Fragment> fragmentList = new ArrayList<>();

    public RegistrationAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, RegistrationViewModel registrationViewModel) {
        super(fragmentManager, lifecycle);
        this.registrationViewModel = registrationViewModel;
        updateFragments(); // Initial fragment setup
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public void updateFragments() {
        fragmentList.clear(); // Clear previous fragments

        // Add base fragments
        fragmentList.add(new PhoneInputFragment());
        fragmentList.add(new NameInputFragment());
        fragmentList.add(new RoleInputFragment());
        fragmentList.add(new ExperienceInputFragment());
        fragmentList.add(new LocationInputFragment());
        fragmentList.add(new NidInputFragment());
        fragmentList.add(new NidUploadFragment());
        fragmentList.add(new EducationInputFragment());
        fragmentList.add(new EducationTrascriptUploadFragment());

        // Conditionally add fragments based on the role
        if (registrationViewModel.getUser().getRole() != null && registrationViewModel.getUser().getRole().equals("Driver")) {
            fragmentList.add(new LicenseInputFragment());
            fragmentList.add(new LicenseUploadFragment());
        }

        fragmentList.add(new ProfilePhotoUploadFragment());

        notifyDataSetChanged(); // Notify ViewPager2 to refresh
    }
}

