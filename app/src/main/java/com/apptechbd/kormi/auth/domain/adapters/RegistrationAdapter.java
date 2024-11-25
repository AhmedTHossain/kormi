package com.apptechbd.kormi.auth.domain.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.apptechbd.kormi.auth.presentation.registration.ExperienceInputFragment;
import com.apptechbd.kormi.auth.presentation.registration.LocationInputFragment;
import com.apptechbd.kormi.auth.presentation.registration.NameInputFragment;
import com.apptechbd.kormi.auth.presentation.registration.PhoneInputFragment;
import com.apptechbd.kormi.auth.presentation.registration.RoleInputFragment;

public class RegistrationAdapter extends FragmentStateAdapter {
    public RegistrationAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PhoneInputFragment();
            case 1:
                return new NameInputFragment();
            case 2:
                return new RoleInputFragment();
            case 3:
                return new ExperienceInputFragment();
            case 4:
                return new LocationInputFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
