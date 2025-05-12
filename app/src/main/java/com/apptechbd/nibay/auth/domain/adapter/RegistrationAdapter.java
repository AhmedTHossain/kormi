package com.apptechbd.nibay.auth.domain.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

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
    private ViewPager2 viewPager2;

    public RegistrationAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, RegistrationViewModel registrationViewModel, ViewPager2 viewPager2) {
        super(fragmentManager, lifecycle);
        this.registrationViewModel = registrationViewModel;
        this.viewPager2 = viewPager2;
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
        fragmentList.add(new NameInputFragment(viewPager2));
        fragmentList.add(new RoleInputFragment(viewPager2));
        fragmentList.add(new ExperienceInputFragment(viewPager2));
        fragmentList.add(new LocationInputFragment(viewPager2));
        fragmentList.add(new NidInputFragment(viewPager2));
        fragmentList.add(new NidUploadFragment(viewPager2));
        fragmentList.add(new EducationInputFragment(viewPager2));
        fragmentList.add(new EducationTrascriptUploadFragment(viewPager2));

        // Conditionally add fragments based on the role
        if (registrationViewModel.getUser().getRole() == 3) {
            fragmentList.add(new LicenseInputFragment());
            fragmentList.add(new LicenseUploadFragment());
        }

        fragmentList.add(new ProfilePhotoUploadFragment());

        notifyDataSetChanged(); // Notify ViewPager2 to refresh
    }
}

