package com.apptechbd.nibay.jobads.domain.model;

import com.apptechbd.nibay.home.domain.model.JobAdDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobDetailsResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private JobAdDetails jobAdDetails;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JobAdDetails getData() {
        return jobAdDetails;
    }

    public void setData(JobAdDetails jobAdDetails) {
        this.jobAdDetails = jobAdDetails;
    }
}
