package com.apptechbd.nibay.home.domain.model;

public class JobAd {
    private String jobTitle, companyName, location, expireDate, applicationStatus;
    private int companyLogo;

    public JobAd(String jobTitle, String companyName, String location, String expireDate, String applicationStatus, int companyLogo) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.expireDate = expireDate;
        this.applicationStatus = applicationStatus;
        this.companyLogo = companyLogo;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public int getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(int companyLogo) {
        this.companyLogo = companyLogo;
    }
}
