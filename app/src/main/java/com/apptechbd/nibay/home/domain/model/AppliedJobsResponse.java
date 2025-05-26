package com.apptechbd.nibay.home.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppliedJobsResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<AppliedJob> data;
    @SerializedName("acceptedApplications")
    @Expose
    private Integer acceptedApplications;
    @SerializedName("rejectedApplications")
    @Expose
    private Integer rejectedApplications;

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

    public List<AppliedJob> getData() {
        return data;
    }

    public void setData(List<AppliedJob> data) {
        this.data = data;
    }

    public Integer getAcceptedApplications() {
        return acceptedApplications;
    }

    public void setAcceptedApplications(Integer acceptedApplications) {
        this.acceptedApplications = acceptedApplications;
    }

    public Integer getRejectedApplications() {
        return rejectedApplications;
    }

    public void setRejectedApplications(Integer rejectedApplications) {
        this.rejectedApplications = rejectedApplications;
    }
}
