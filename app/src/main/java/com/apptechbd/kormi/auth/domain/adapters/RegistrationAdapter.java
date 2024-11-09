package com.apptechbd.kormi.auth.domain.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.apptechbd.kormi.auth.presentation.registration.NameInputFragment;
import com.apptechbd.kormi.auth.presentation.registration.PhoneInputFragment;

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
            // Add more cases for other fragments if needed
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
