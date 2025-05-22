package com.apptechbd.nibay.jobads.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apptechbd.nibay.home.domain.model.JobAdDetails;
import com.apptechbd.nibay.jobads.domain.repository.JobDetailsRepository;

public class JobAdvertisementDetailsViewModel extends AndroidViewModel {
    private final JobDetailsRepository jobDetailsRepository;
    public LiveData<JobAdDetails> jobAdDetails;

    public JobAdvertisementDetailsViewModel(@NonNull Application application) {
        super(application);
        jobDetailsRepository = new JobDetailsRepository(application.getApplicationContext());
    }

    public void getJobAdDetails(String jobAdId) {
        jobAdDetails = jobDetailsRepository.getJobDetails(jobAdId);
    }
}
