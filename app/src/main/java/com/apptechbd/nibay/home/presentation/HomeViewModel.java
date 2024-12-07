package com.apptechbd.nibay.home.presentation;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.databinding.ActivityHomeBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Locale;

public class HomeViewModel extends AndroidViewModel {
    private final JobAdvertisementsFragment jobAdvertisementsFragment = new JobAdvertisementsFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();
    private final AppliedJobsFragment appliedJobsFragment = new AppliedJobsFragment();
    private final NibayAppMenuFragment nibayAppMenuFragment = new NibayAppMenuFragment();
    private int currentFragmentId;  // Track the currently displayed fragment ID

    private LiveData<Boolean> isJobClicked;
    private MaterialToolbar toolbar;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    // This method is called when a new fragment is displayed
    public void onFragmentDisplayed(ActivityHomeBinding binding, int fragmentId) {
        currentFragmentId = fragmentId;
        // Select the correct menu item
        selectBottomNavMenuItem(binding);
        setToolbarTitle(fragmentId);
    }

    // Call this method after fragment replacement to sync BottomNavigationView with the displayed fragment
    private void selectBottomNavMenuItem(ActivityHomeBinding binding) {
        binding.bottomnavigationHome.setSelectedItemId(currentFragmentId);
    }

    public void onBottomNavMenuItemSelect(ActivityHomeBinding binding, FragmentManager supportFragmentManager) {
        binding.bottomnavigationHome.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            setToolbarTitle(itemId);

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

    public void setToolbar(MaterialToolbar toolbar) {
        this.toolbar = toolbar;
    }

    public LiveData<Boolean> getIsJobClicked() {
        return isJobClicked;
    }

    public void setIsJobClicked(LiveData<Boolean> isJobClicked) {
        this.isJobClicked = isJobClicked;
    }

    private void setToolbarTitle(int fragmentId) {
        String title = "";
        if (fragmentId == R.id.jobAdvertisementFragment)
            title = getApplication().getString(R.string.job_advertisements);
        else if (fragmentId == R.id.profileFragment)
            title = getApplication().getString(R.string.profile);
        else if (fragmentId == R.id.appliedJobsFragment)
            title = getApplication().getString(R.string.already_applied_jobs);
        else if (fragmentId == R.id.moreFragment)
            title = getApplication().getString(R.string.nibay_app_menu);



        if (toolbar != null) {
            Log.d("HomeViewModel", "Screen title: "+title + " " + Locale.getDefault().getDisplayLanguage());
            toolbar.setTitle(title);
        }
    }
}
