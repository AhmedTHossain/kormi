package com.apptechbd.nibay.home.domain.models;

public class EmployerRating {
    private String employerName, comment, imageUrl;
    private int rating;

    public EmployerRating(String employerName, String comment, String imageUrl, int rating) {
        this.employerName = employerName;
        this.comment = comment;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
