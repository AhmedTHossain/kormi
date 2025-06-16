package com.apptechbd.nibay.home.presentation;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.databinding.ActivityHomeBinding;
import com.apptechbd.nibay.home.domain.model.AppliedJobsResponse;
import com.apptechbd.nibay.home.domain.model.EmployerRatingResponseData;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.apptechbd.nibay.home.domain.model.ProfileRsponseData;
import com.apptechbd.nibay.home.domain.repository.HomeRepository;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;
import java.util.Locale;

public class HomeViewModel extends AndroidViewModel {
    private final JobAdvertisementsFragment jobAdvertisementsFragment = new JobAdvertisementsFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();
    private final AppliedJobsFragment appliedJobsFragment = new AppliedJobsFragment();
    private final NibayAppMenuFragment nibayAppMenuFragment = new NibayAppMenuFragment();
    private int currentFragmentId;  // Track the currently displayed fragment ID

    protected MutableLiveData<JobAd> jobClicked = new MutableLiveData<>();
    protected MutableLiveData<String> followedCompanyClicked = new MutableLiveData<>();
    private MaterialToolbar toolbar;
    public LiveData<Boolean> isFollowedEmployersFetched, isJobAdvertisementsFetched, isFollowedEmployerJobAdvertisementsFetched;
    public LiveData<ProfileRsponseData> userProfile;
    private HomeRepository homeRepository;
    public LiveData<EmployerRatingResponseData> employerRating;

    private final MutableLiveData<AppliedJobsResponse> _appliedJobs = new MutableLiveData<>();
    public LiveData<AppliedJobsResponse> appliedJobs = _appliedJobs;

//    public final MutableLiveData<Boolean> _isProfilePhotoUploaded = new MutableLiveData<>();
//    public LiveData<Boolean> isProfilePhotoUploaded = _isProfilePhotoUploaded;

    private final MutableLiveData<Boolean> _isProfilePhotoUploaded = new MutableLiveData<>();
    public LiveData<Boolean> isProfilePhotoUploaded = _isProfilePhotoUploaded;
    private final MutableLiveData<Boolean> _isNidPhotoUploaded = new MutableLiveData<>();
    public LiveData<Boolean> isNidPhotoUploaded = _isNidPhotoUploaded;
    protected MutableLiveData<Boolean> documentClicked = new MutableLiveData<>();
    private String documentTypeToUpdate = "";
    private int documentPosition = -1;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = new HomeRepository(getApplication().getApplicationContext());
    }

    // This method is called when a new fragment is displayed
//    public void onFragmentDisplayed(ActivityHomeBinding binding, int fragmentId) {
//        currentFragmentId = fragmentId;
//        // Select the correct menu item
//        selectBottomNavMenuItem(binding);
//        setToolbarTitle(fragmentId);
//    }

    public void onFragmentDisplayed(ActivityHomeBinding binding, int fragmentId, Context context) {
        currentFragmentId = fragmentId;
        selectBottomNavMenuItem(binding);
        setToolbarTitle(fragmentId, context);
    }


    // Call this method after fragment replacement to sync BottomNavigationView with the displayed fragment
    private void selectBottomNavMenuItem(ActivityHomeBinding binding) {
        binding.bottomnavigationHome.setSelectedItemId(currentFragmentId);
    }

//    public void onBottomNavMenuItemSelect(ActivityHomeBinding binding, FragmentManager supportFragmentManager) {
//        binding.bottomnavigationHome.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            setToolbarTitle(itemId);
//
//            if (itemId == R.id.jobAdvertisementFragment) {
//                replaceFragment(jobAdvertisementsFragment, supportFragmentManager);
//            } else if (itemId == R.id.profileFragment) {
//                replaceFragment(profileFragment, supportFragmentManager);
//            } else if (itemId == R.id.appliedJobsFragment) {
//                replaceFragment(appliedJobsFragment, supportFragmentManager);
//            }
//            else if (itemId == R.id.moreFragment) {
//                replaceFragment(nibayAppMenuFragment, supportFragmentManager);
//            }
//
//            return true;
//        });
//    }

    // Updated: Added Context parameter here
    public void onBottomNavMenuItemSelect(ActivityHomeBinding binding, FragmentManager supportFragmentManager, Context context) {
        binding.bottomnavigationHome.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            setToolbarTitle(itemId, context);

            if (itemId == R.id.jobAdvertisementFragment) {
                replaceFragment(jobAdvertisementsFragment, supportFragmentManager);
            } else if (itemId == R.id.profileFragment) {
                replaceFragment(profileFragment, supportFragmentManager);
            } else if (itemId == R.id.appliedJobsFragment) {
                replaceFragment(appliedJobsFragment, supportFragmentManager);
            } else if (itemId == R.id.moreFragment) {
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

    public void onJobClicked(JobAd id) {
        jobClicked.setValue(id);
    }

    public void onFollowedCompanyClicked(String id) {
        followedCompanyClicked.setValue(id);
    }

    public void setJobClicked(MutableLiveData<JobAd> jobClicked) {
        this.jobClicked = jobClicked;
    }

    public void setFollowedCompanyClicked(MutableLiveData<String> followedCompanyClicked) {
        this.followedCompanyClicked = followedCompanyClicked;
    }

//    private void setToolbarTitle(int fragmentId) {
//        String title = "";
//        if (fragmentId == R.id.jobAdvertisementFragment)
//            title = getApplication().getString(R.string.job_advertisements);
//        else if (fragmentId == R.id.profileFragment)
//            title = getApplication().getString(R.string.profile);
//        else if (fragmentId == R.id.appliedJobsFragment)
//            title = getApplication().getString(R.string.already_applied_jobs);
//        else if (fragmentId == R.id.moreFragment)
//            title = getApplication().getString(R.string.nibay_app_menu);
//
//
//
//        if (toolbar != null) {
//            Log.d("HomeViewModel", "Screen title: "+title + " " + Locale.getDefault().getDisplayLanguage());
//            toolbar.setTitle(title);
//        }
//    }

    private void setToolbarTitle(int fragmentId, Context context) {
        String title = "";
        if (fragmentId == R.id.jobAdvertisementFragment)
            title = context.getString(R.string.job_advertisements);
        else if (fragmentId == R.id.profileFragment)
            title = context.getString(R.string.profile);
        else if (fragmentId == R.id.appliedJobsFragment)
            title = context.getString(R.string.already_applied_jobs);
        else if (fragmentId == R.id.moreFragment)
            title = context.getString(R.string.nibay_app_menu);

        if (toolbar != null) {
            Log.d("HomeViewModel", "Screen title: " + title + " " + Locale.getDefault().getDisplayLanguage());
            toolbar.setTitle(title);
        }
    }


    public void getFollowedEmployers() {
        isFollowedEmployersFetched = homeRepository.getFollowedEmployers();
    }

    public void getJobAdvertisements(String page) {
        isJobAdvertisementsFetched = homeRepository.getJobAdvertisements(page);
    }

    public void getCompanyJobAdvertisements(String page, String id) {
        isFollowedEmployerJobAdvertisementsFetched = homeRepository.getCompanyJobAdvertisements(page,id);
    }

    public void getUserProfile() {
        userProfile = homeRepository.getUserProfile();
    }

    public void getReviews(String applicantId) {
        employerRating = homeRepository.getReviews(applicantId);
    }

    public void getAppliedJobs() {
        appliedJobs = homeRepository.getAppliedJobs();
    }

    public void uploadProfilePhoto(File profilePhoto) {
        // ✅ This triggers your fragment observer
        homeRepository.uploadProfilePhoto(profilePhoto).observeForever(_isProfilePhotoUploaded::postValue);
    }

    public void uploadNIDPhoto(File nidPhoto) {
        // ✅ This triggers your fragment observer
        homeRepository.uploadNIDPhoto(nidPhoto).observeForever(_isNidPhotoUploaded::postValue);
    }

    public void onDocumentClicked(String documentTypeToUpdate, int documentPosition){
        documentClicked.setValue(true);
        this.documentTypeToUpdate = documentTypeToUpdate;
        this.documentPosition = documentPosition;
    }

    public String getDocumentTypeToUpdate() {
        return documentTypeToUpdate;
    }
}
