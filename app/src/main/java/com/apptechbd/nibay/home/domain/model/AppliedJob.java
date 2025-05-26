package com.apptechbd.nibay.home.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppliedJob {
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
    @SerializedName("maxEducationLevel")
    @Expose
    private String maxEducationLevel;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("salary")
    @Expose
    private Integer salary;
    @SerializedName("applicationDeadline")
    @Expose
    private String applicationDeadline;
    @SerializedName("employerName")
    @Expose
    private String employerName;
    @SerializedName("employerPhoto")
    @Expose
    private String employerPhoto;
    @SerializedName("applicationStatus")
    @Expose
    private String applicationStatus;
    @SerializedName("jobSatus")
    @Expose
    private String jobSatus;

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

    public String getMaxEducationLevel() {
        return maxEducationLevel;
    }

    public void setMaxEducationLevel(String maxEducationLevel) {
        this.maxEducationLevel = maxEducationLevel;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(String applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerPhoto() {
        return employerPhoto;
    }

    public void setEmployerPhoto(String employerPhoto) {
        this.employerPhoto = employerPhoto;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getJobSatus() {
        return jobSatus;
    }

    public void setJobSatus(String jobSatus) {
        this.jobSatus = jobSatus;
    }
}
