package com.apptechbd.nibay.home.domain.model;

public class FollowedEmployer {
    private String name, logoUrl;
    private boolean isFollowed;
    private int id;

    public FollowedEmployer(String name, String logoUrl, boolean isFollowed, int id) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.isFollowed = isFollowed;
        this.id = id;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
