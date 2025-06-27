package com.apptechbd.nibay.home.presentation;

import android.app.Application;
import android.content.Context;

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
import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HomeViewModel extends AndroidViewModel {

    private final JobAdvertisementsFragment jobAdvertisementsFragment = new JobAdvertisementsFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();
    private final AppliedJobsFragment appliedJobsFragment = new AppliedJobsFragment();
    private final NibayAppMenuFragment nibayAppMenuFragment = new NibayAppMenuFragment();

    private final HomeRepository homeRepository;
    private MaterialToolbar toolbar;

    private final MutableLiveData<JobAd> _jobClicked = new MutableLiveData<>();
    public final LiveData<JobAd> jobClicked = _jobClicked;

    private final MutableLiveData<String> _followedCompanyClicked = new MutableLiveData<>();
    public final LiveData<String> followedCompanyClicked = _followedCompanyClicked;

    private final MutableLiveData<Boolean> _documentClicked = new MutableLiveData<>();
    public final LiveData<Boolean> documentClicked = _documentClicked;

    private final MutableLiveData<List<JobAd>> _displayedJobAds = new MutableLiveData<>();
    public final LiveData<List<JobAd>> displayedJobAds = _displayedJobAds;

    private final MutableLiveData<Boolean> _isProfilePhotoUploaded = new MutableLiveData<>();
    public final LiveData<Boolean> isProfilePhotoUploaded = _isProfilePhotoUploaded;

    private final MutableLiveData<Boolean> _isNidPhotoUploaded = new MutableLiveData<>();
    public final LiveData<Boolean> isNidPhotoUploaded = _isNidPhotoUploaded;

    private final MutableLiveData<Boolean> _isFollowedEmployersFetched = new MutableLiveData<>();
    public final LiveData<Boolean> isFollowedEmployersFetched = _isFollowedEmployersFetched;

    public LiveData<AppliedJobsResponse> appliedJobs;
    public LiveData<ProfileRsponseData> userProfile;
    public LiveData<EmployerRatingResponseData> employerRating;

    private String documentTypeToUpdate = "";
    private int documentPosition = -1;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = new HomeRepository(application.getApplicationContext());
    }

    public void setToolbar(MaterialToolbar toolbar) {
        this.toolbar = toolbar;
    }

    public void onFragmentDisplayed(ActivityHomeBinding binding, int fragmentId, Context context) {
        binding.bottomnavigationHome.setSelectedItemId(fragmentId);
        updateToolbarTitle(fragmentId, context);
    }

    private void updateToolbarTitle(int fragmentId, Context context) {
        int titleResId;
        if (fragmentId == R.id.jobAdvertisementFragment) {
            titleResId = R.string.job_advertisements;
        } else if (fragmentId == R.id.profileFragment) {
            titleResId = R.string.profile;
        } else if (fragmentId == R.id.appliedJobsFragment) {
            titleResId = R.string.already_applied_jobs;
        } else if (fragmentId == R.id.moreFragment) {
            titleResId = R.string.nibay_app_menu;
        } else {
            titleResId = R.string.app_name;
        }

        if (toolbar != null) {
            toolbar.setTitle(context.getString(titleResId));
        }
    }

    public void onBottomNavMenuItemSelect(ActivityHomeBinding binding, FragmentManager fm, Context context) {
        binding.bottomnavigationHome.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            updateToolbarTitle(itemId, context);
            Fragment selectedFragment = null;
            if (itemId == R.id.jobAdvertisementFragment) {
                selectedFragment = jobAdvertisementsFragment;
            } else if (itemId == R.id.profileFragment) {
                selectedFragment = profileFragment;
            } else if (itemId == R.id.appliedJobsFragment) {
                selectedFragment = appliedJobsFragment;
            } else if (itemId == R.id.moreFragment) {
                selectedFragment = nibayAppMenuFragment;
            }
            if (selectedFragment != null) {
                replaceFragment(selectedFragment, fm);
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment, FragmentManager fm) {
        fm.beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }

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

    public int getDocumentPosition() {
        return documentPosition;
    }

    public void loadInitialJobAdvertisements() {
        homeRepository.getJobAdvertisements("1").observeForever(_displayedJobAds::postValue);
    }

    public void loadCompanyRoleJobAdvertisements(String page, String employerId, String jobRole) {
        homeRepository.getCompanyJobAdvertisements(page, employerId).observeForever(companyAds -> {
            if (companyAds == null || companyAds.isEmpty()) {
                _displayedJobAds.setValue(Collections.emptyList());
                return;
            }

            homeRepository.getRoleJobAdvertisements(page, jobRole).observeForever(roleAds -> {
                if (roleAds == null || roleAds.isEmpty()) {
                    _displayedJobAds.setValue(Collections.emptyList());
                    return;
                }

                List<JobAd> filteredAds = roleAds.stream()
                        .filter(ad -> ad.getUser().equals(employerId))
                        .collect(Collectors.toList());

                _displayedJobAds.setValue(filteredAds);
            });
        });
    }

    public void loadCompanyJobAdvertisements(String page, String companyId) {
        homeRepository.getCompanyJobAdvertisements(page, companyId).observeForever(_displayedJobAds::postValue);
    }

    public void loadRoleBasedJobAdvertisements(String page, String jobRole) {
        homeRepository.getRoleJobAdvertisements(page, jobRole).observeForever(_displayedJobAds::postValue);
    }

    public void getFollowedEmployers() {
        homeRepository.getFollowedEmployers().observeForever(_isFollowedEmployersFetched::postValue);
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

    public void uploadProfilePhoto(File photo) {
        homeRepository.uploadProfilePhoto(photo).observeForever(_isProfilePhotoUploaded::postValue);
    }

    public void uploadNIDPhoto(File photo) {
        homeRepository.uploadNIDPhoto(photo).observeForever(_isNidPhotoUploaded::postValue);
    }
}