package com.apptechbd.nibay.home.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobAdDetails {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("applicationDeadline")
    @Expose
    private String applicationDeadline;
    @SerializedName("salary")
    @Expose
    private Integer salary;
    @SerializedName("jobRole")
    @Expose
    private Integer jobRole;
    @SerializedName("isBirthCertificateRequired")
    @Expose
    private Boolean isBirthCertificateRequired;
    @SerializedName("isPortEntryPermitRequired")
    @Expose
    private Boolean isPortEntryPermitRequired;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("employerId")
    @Expose
    private String employerId;
    @SerializedName("isFollowing")
    @Expose
    private Boolean isFollowing;
    @SerializedName("jobStatus")
    @Expose
    private String jobStatus;
    @SerializedName("applicationStatus")
    @Expose
    private String applicationStatus;
    @SerializedName("minEducationLevel")
    @Expose
    private Integer minEducationLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(String applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getJobRole() {
        return jobRole;
    }

    public void setJobRole(Integer jobRole) {
        this.jobRole = jobRole;
    }

    public Boolean getIsBirthCertificateRequired() {
        return isBirthCertificateRequired;
    }

    public void setIsBirthCertificateRequired(Boolean isBirthCertificateRequired) {
        this.isBirthCertificateRequired = isBirthCertificateRequired;
    }

    public Boolean getIsPortEntryPermitRequired() {
        return isPortEntryPermitRequired;
    }

    public void setIsPortEntryPermitRequired(Boolean isPortEntryPermitRequired) {
        this.isPortEntryPermitRequired = isPortEntryPermitRequired;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public Boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(Boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Integer getMinEducationLevel() {
        return minEducationLevel;
    }

    public void setMinEducationLevel(Integer minEducationLevel) {
        this.minEducationLevel = minEducationLevel;
    }
}
