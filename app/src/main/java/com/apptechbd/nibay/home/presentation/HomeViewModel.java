package com.apptechbd.nibay.home.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.databinding.ActivityHomeBinding;

public class HomeViewModel extends AndroidViewModel {
    private final JobAdvertisementsFragment jobAdvertisementsFragment = new JobAdvertisementsFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();
    private final AppliedJobsFragment appliedJobsFragment = new AppliedJobsFragment();
    private final NibayAppMenuFragment nibayAppMenuFragment = new NibayAppMenuFragment();
    private int currentFragmentId;  // Track the currently displayed fragment ID

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    // This method is called when a new fragment is displayed
    public void onFragmentDisplayed(ActivityHomeBinding binding, int fragmentId) {
        currentFragmentId = fragmentId;
        // Select the correct menu item
        selectBottomNavMenuItem(binding);
    }

    // Call this method after fragment replacement to sync BottomNavigationView with the displayed fragment
    private void selectBottomNavMenuItem(ActivityHomeBinding binding) {
        binding.bottomnavigationHome.setSelectedItemId(currentFragmentId);
    }

    public void onBottomNavMenuItemSelect(ActivityHomeBinding binding, FragmentManager supportFragmentManager) {
        binding.bottomnavigationHome.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.jobAdvertisementFragment) {
                replaceFragment(jobAdvertisementsFragment, supportFragmentManager);
            } else if (itemId == R.id.profileFragment) {
                replaceFragment(profileFragment, supportFragmentManager);
            } else if (itemId == R.id.appliedJobsFragment) {
                replaceFragment(appliedJobsFragment, supportFragmentManager);
            }
            else if (itemId == R.id.moreFragment) {
                replaceFragment(nibayAppMenuFragment, supportFragmentManager);
            }

            return true;
        });
    }

    public void replaceFragment(Fragment fragment, FragmentManager supportFragmentManager) {
        FragmentManager fragmentManager = supportFragmentManager;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
