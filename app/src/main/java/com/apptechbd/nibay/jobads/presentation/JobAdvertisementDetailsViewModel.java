package com.apptechbd.nibay.jobads.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.nibay.home.domain.model.JobAdDetails;
import com.apptechbd.nibay.jobads.domain.repository.JobDetailsRepository;

public class JobAdvertisementDetailsViewModel extends AndroidViewModel {
    private final JobDetailsRepository jobDetailsRepository;

    //    public LiveData<JobAdDetails> jobAdDetails;
//    public LiveData<Boolean> followUnfollowSatuts;
    private final MutableLiveData<JobAdDetails> _jobAdDetails = new MutableLiveData<>();
    public LiveData<JobAdDetails> jobAdDetails = _jobAdDetails;

    private final MutableLiveData<Boolean> _followStatus = new MutableLiveData<>();
    public LiveData<Boolean> followStatus = _followStatus;

    private final MutableLiveData<Boolean> _unfollowStatus = new MutableLiveData<>();
    public LiveData<Boolean> unfollowStatus = _unfollowStatus;

    public JobAdvertisementDetailsViewModel(@NonNull Application application) {
        super(application);
        jobDetailsRepository = new JobDetailsRepository(application.getApplicationContext());
    }

//    public void getJobAdDetails(String jobAdId) {
//        jobAdDetails = jobDetailsRepository.getJobDetails(jobAdId);
//    }
//
//    public void followEmployer(String employerId) {
//        followUnfollowSatuts = jobDetailsRepository.followEmployer(employerId);
//    }
//
//    public void unfollowEmployer(String employerId) {
//        followUnfollowSatuts = jobDetailsRepository.unfollowEmployer(employerId);
//    }

    public void getJobAdDetails(String jobAdId) {
        jobDetailsRepository.getJobDetails(jobAdId).observeForever(_jobAdDetails::postValue);
    }

    public void setJobAdDetails(JobAdDetails jobAdDetails) {
        this._jobAdDetails.setValue(jobAdDetails);
    }

    public void followEmployer(String employerId){
        jobDetailsRepository.followEmployer(employerId).observeForever(status -> {
            _followStatus.postValue(status);
        });
    }

    public void unfollowEmployer(String employerId){
        jobDetailsRepository.unfollowEmployer(employerId).observeForever(status -> {
            _unfollowStatus.postValue(status);
        });
    }

}
