package com.apptechbd.nibay.home.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployerRatingResponseData {
    @SerializedName("averageRating")
    @Expose
    private Integer averageRating;
    @SerializedName("reviews")
    @Expose
    private List<EmployerRating> reviews;

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public List<EmployerRating> getReviews() {
        return reviews;
    }

    public void setReviews(List<EmployerRating> reviews) {
        this.reviews = reviews;
    }
}
