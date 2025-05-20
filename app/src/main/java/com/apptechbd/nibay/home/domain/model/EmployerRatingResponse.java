package com.apptechbd.nibay.home.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployerRatingResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private EmployerRatingResponseData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EmployerRatingResponseData getData() {
        return data;
    }

    public void setData(EmployerRatingResponseData data) {
        this.data = data;
    }
}
