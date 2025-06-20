package com.apptechbd.nibay.home.presentation;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.apptechbd.nibay.home.presentation.AppliedJobsFragment;
import com.apptechbd.nibay.home.presentation.JobAdvertisementsFragment;
import com.apptechbd.nibay.home.presentation.NibayAppMenuFragment;
import com.apptechbd.nibay.home.presentation.ProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class HomeViewModel extends AndroidViewModel {

    // Fragments
    private final JobAdvertisementsFragment jobAdvertisementsFragment = new JobAdvertisementsFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();
    private final AppliedJobsFragment appliedJobsFragment = new AppliedJobsFragment();
    private final NibayAppMenuFragment nibayAppMenuFragment = new NibayAppMenuFragment();

    private final HomeRepository homeRepository;
    private int currentFragmentId;
    private MaterialToolbar toolbar;

    // LiveData - Navigation & UI Events
    private final MutableLiveData<JobAd> _jobClicked = new MutableLiveData<>();
    public LiveData<JobAd> jobClicked = _jobClicked;

    private final MutableLiveData<String> _followedCompanyClicked = new MutableLiveData<>();
    public LiveData<String> followedCompanyClicked = _followedCompanyClicked;

    private final MutableLiveData<Boolean> _documentClicked = new MutableLiveData<>();
    public LiveData<Boolean> documentClicked = _documentClicked;

    // LiveData - Data
    private final MutableLiveData<List<JobAd>> _jobAds = new MutableLiveData<>();
    public LiveData<List<JobAd>> jobAds = _jobAds;

    private final MutableLiveData<List<JobAd>> _companyJobAds = new MutableLiveData<>();
    public LiveData<List<JobAd>> companyJobAds = _companyJobAds;

    private final MutableLiveData<Boolean> _isJobAdvertisementsFetched = new MutableLiveData<>();
    public LiveData<Boolean> isJobAdvertisementsFetched = _isJobAdvertisementsFetched;

    private final MutableLiveData<List<JobAd>> _roleBasedJobAds = new MutableLiveData<>();
    public LiveData<List<JobAd>> roleBasedJobAds = _roleBasedJobAds;

    private final MutableLiveData<Boolean> _isRoleJobAdvertisementsFetched = new MutableLiveData<>();
    public LiveData<Boolean> isRoleJobAdvertisementsFetched = _isRoleJobAdvertisementsFetched;

    private final MutableLiveData<Boolean> _isFollowedEmployerJobAdvertisementsFetched = new MutableLiveData<>();
    public LiveData<Boolean> isFollowedEmployerJobAdvertisementsFetched = _isFollowedEmployerJobAdvertisementsFetched;

    private final MutableLiveData<AppliedJobsResponse> _appliedJobs = new MutableLiveData<>();
    public LiveData<AppliedJobsResponse> appliedJobs = _appliedJobs;

    private final MutableLiveData<Boolean> _isProfilePhotoUploaded = new MutableLiveData<>();
    public LiveData<Boolean> isProfilePhotoUploaded = _isProfilePhotoUploaded;

    private final MutableLiveData<Boolean> _isNidPhotoUploaded = new MutableLiveData<>();
    public LiveData<Boolean> isNidPhotoUploaded = _isNidPhotoUploaded;

    public LiveData<Boolean> isFollowedEmployersFetched;
    public LiveData<ProfileRsponseData> userProfile;
    public LiveData<EmployerRatingResponseData> employerRating;

    // Document handling
    private String documentTypeToUpdate = "";
    private int documentPosition = -1;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = new HomeRepository(application.getApplicationContext());
    }

    // Navigation logic
    public void onFragmentDisplayed(ActivityHomeBinding binding, int fragmentId, Context context) {
        currentFragmentId = fragmentId;
        binding.bottomnavigationHome.setSelectedItemId(fragmentId);
        updateToolbarTitle(fragmentId, context);
    }

    public void setToolbar(MaterialToolbar toolbar) {
        this.toolbar = toolbar;
    }

    private void updateToolbarTitle(int fragmentId, Context context) {
        String title = "";

        if (fragmentId == R.id.jobAdvertisementFragment) {
            title = context.getString(R.string.job_advertisements);
        } else if (fragmentId == R.id.profileFragment) {
            title = context.getString(R.string.profile);
        } else if (fragmentId == R.id.appliedJobsFragment) {
            title = context.getString(R.string.already_applied_jobs);
        } else if (fragmentId == R.id.moreFragment) {
            title = context.getString(R.string.nibay_app_menu);
        }

        if (toolbar != null) {
            Log.d("HomeViewModel", "Screen title: " + title + " " + Locale.getDefault().getDisplayLanguage());
            toolbar.setTitle(title);
        }
    }

    public void onBottomNavMenuItemSelect(ActivityHomeBinding binding, FragmentManager fm, Context context) {
        binding.bottomnavigationHome.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            updateToolbarTitle(itemId, context);

            if (itemId == R.id.jobAdvertisementFragment) {
                replaceFragment(jobAdvertisementsFragment, fm);
            } else if (itemId == R.id.profileFragment) {
                replaceFragment(profileFragment, fm);
            } else if (itemId == R.id.appliedJobsFragment) {
                replaceFragment(appliedJobsFragment, fm);
            } else if (itemId == R.id.moreFragment) {
                replaceFragment(nibayAppMenuFragment, fm);
            }

            return true;
        });
    }


    private void replaceFragment(Fragment fragment, FragmentManager fm) {
        fm.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    // Event triggers
    public void onJobClicked(JobAd jobAd) {
        _jobClicked.setValue(jobAd);
    }

    public void onFollowedCompanyClicked(String companyId) {
        _followedCompanyClicked.setValue(companyId);
    }

    public void onDocumentClicked(String type, int position) {
        documentTypeToUpdate = type;
        documentPosition = position;
        _documentClicked.setValue(true);
    }

    public String getDocumentTypeToUpdate() {
        return documentTypeToUpdate;
    }

    // Data fetching
    public void getFollowedEmployers() {
        isFollowedEmployersFetched = homeRepository.getFollowedEmployers();
    }

    public void getJobAdvertisements(String page) {
        homeRepository.getJobAdvertisements(page).observeForever(jobAds -> {
            _jobAds.postValue(jobAds);
            _isJobAdvertisementsFetched.postValue(true);
        });
    }

    public void getCompanyJobAdvertisements(String page, String companyId) {
        homeRepository.getCompanyJobAdvertisements(page, companyId).observeForever(jobAds -> {
            _companyJobAds.postValue(jobAds);
            _isFollowedEmployerJobAdvertisementsFetched.postValue(true);
        });
    }

    public void getRoleJobAdvertisements(String page, String jobRole) {
        homeRepository.getRoleJobAdvertisements(page, jobRole).observeForever(jobAds -> {
            _roleBasedJobAds.postValue(jobAds);
            _isRoleJobAdvertisementsFetched.postValue(true);
        });
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

    // Uploading
    public void uploadProfilePhoto(File photo) {
        homeRepository.uploadProfilePhoto(photo).observeForever(_isProfilePhotoUploaded::postValue);
    }

    public void uploadNIDPhoto(File photo) {
        homeRepository.uploadNIDPhoto(photo).observeForever(_isNidPhotoUploaded::postValue);
    }
}
