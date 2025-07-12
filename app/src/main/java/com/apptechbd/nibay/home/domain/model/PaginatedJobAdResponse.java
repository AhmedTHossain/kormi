package com.apptechbd.nibay.home.domain.model;

import java.util.List;

public class PaginatedJobAdResponse {
    private List<JobAd> jobAds;
    private int currentPage;
    private int totalPages;

    public PaginatedJobAdResponse(List<JobAd> jobAds, int currentPage, int totalPages) {
        this.jobAds = jobAds;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public List<JobAd> getJobAds() {
        return jobAds;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
