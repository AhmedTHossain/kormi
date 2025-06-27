package com.apptechbd.nibay.home.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobAd {
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
    @SerializedName("applicationStatus")
    @Expose
    private String applicationStatus;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("employerName")
    @Expose
    private String employerName;
    @SerializedName("employerPhoto")
    @Expose
    private String employerPhoto;
    @SerializedName("minEducationLevel")
    @Expose
    private Integer minEducationLevel;
    @SerializedName("minEducationLevelTxtEn")
    @Expose
    private String minEducationLevelTxtEn;
    @SerializedName("minEducationLevelTxtBn")
    @Expose
    private String minEducationLevelTxtBn;
    @SerializedName("isAcademicCertificateRequired")
    @Expose
    private Boolean isAcademicCertificateRequired;
    @SerializedName("jobRoleTxtEn")
    @Expose
    private String jobRoleTxtEn;
    @SerializedName("jobRoleTxtBn")
    @Expose
    private String jobRoleTxtBn;
    @SerializedName("isDrivingLicenseRequired")
    @Expose
    private Boolean isDrivingLicenseRequired;
    @SerializedName("jobStatus")
    @Expose
    private String jobStatus;

    private boolean isFollowing;

    public boolean getIsFollowing() { return isFollowing; }
    public void setIsFollowing(boolean following) { this.isFollowing = following; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getLongDescription() { return longDescription; }
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(String applicationDeadline) { this.applicationDeadline = applicationDeadline; }

    public Integer getSalary() { return salary; }
    public void setSalary(Integer salary) { this.salary = salary; }

    public Integer getJobRole() { return jobRole; }
    public void setJobRole(Integer jobRole) { this.jobRole = jobRole; }

    public Boolean getIsBirthCertificateRequired() { return isBirthCertificateRequired; }
    public void setIsBirthCertificateRequired(Boolean isBirthCertificateRequired) { this.isBirthCertificateRequired = isBirthCertificateRequired; }

    public Boolean getIsPortEntryPermitRequired() { return isPortEntryPermitRequired; }
    public void setIsPortEntryPermitRequired(Boolean isPortEntryPermitRequired) { this.isPortEntryPermitRequired = isPortEntryPermitRequired; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getApplicationStatus() { return applicationStatus; }
    public void setApplicationStatus(String applicationStatus) { this.applicationStatus = applicationStatus; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public Integer getV() { return v; }
    public void setV(Integer v) { this.v = v; }

    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }

    public String getEmployerPhoto() { return employerPhoto; }
    public void setEmployerPhoto(String employerPhoto) { this.employerPhoto = employerPhoto; }

    public Integer getMinEducationLevel() { return minEducationLevel; }
    public void setMinEducationLevel(Integer minEducationLevel) { this.minEducationLevel = minEducationLevel; }

    public String getMinEducationLevelTxtEn() { return minEducationLevelTxtEn; }
    public void setMinEducationLevelTxtEn(String minEducationLevelTxtEn) { this.minEducationLevelTxtEn = minEducationLevelTxtEn; }

    public String getMinEducationLevelTxtBn() { return minEducationLevelTxtBn; }
    public void setMinEducationLevelTxtBn(String minEducationLevelTxtBn) { this.minEducationLevelTxtBn = minEducationLevelTxtBn; }

    public Boolean getIsAcademicCertificateRequired() { return isAcademicCertificateRequired; }
    public void setIsAcademicCertificateRequired(Boolean isAcademicCertificateRequired) { this.isAcademicCertificateRequired = isAcademicCertificateRequired; }

    public String getJobRoleTxtEn() { return jobRoleTxtEn; }
    public void setJobRoleTxtEn(String jobRoleTxtEn) { this.jobRoleTxtEn = jobRoleTxtEn; }

    public String getJobRoleTxtBn() { return jobRoleTxtBn; }
    public void setJobRoleTxtBn(String jobRoleTxtBn) { this.jobRoleTxtBn = jobRoleTxtBn; }

    public Boolean getIsDrivingLicenseRequired() { return isDrivingLicenseRequired; }
    public void setIsDrivingLicenseRequired(Boolean isDrivingLicenseRequired) { this.isDrivingLicenseRequired = isDrivingLicenseRequired; }

    public String getJobStatus() { return jobStatus; }
    public void setJobStatus(String jobStatus) { this.jobStatus = jobStatus; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobAd jobAd = (JobAd) o;

        return id != null && id.equals(jobAd.id) &&
                safeEquals(title, jobAd.title) &&
                safeEquals(employerName, jobAd.employerName) &&
                safeEquals(jobRoleTxtEn, jobAd.jobRoleTxtEn) &&
                safeEquals(jobRoleTxtBn, jobAd.jobRoleTxtBn) &&
                safeEquals(division, jobAd.division) &&
                safeEquals(district, jobAd.district) &&
                safeEquals(applicationDeadline, jobAd.applicationDeadline) &&
                safeEquals(applicationStatus, jobAd.applicationStatus) &&
                safeEquals(jobStatus, jobAd.jobStatus) &&
                safeEquals(employerPhoto, jobAd.employerPhoto);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (employerName != null ? employerName.hashCode() : 0);
        result = 31 * result + (jobRoleTxtEn != null ? jobRoleTxtEn.hashCode() : 0);
        result = 31 * result + (jobRoleTxtBn != null ? jobRoleTxtBn.hashCode() : 0);
        result = 31 * result + (division != null ? division.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (applicationDeadline != null ? applicationDeadline.hashCode() : 0);
        result = 31 * result + (applicationStatus != null ? applicationStatus.hashCode() : 0);
        result = 31 * result + (jobStatus != null ? jobStatus.hashCode() : 0);
        result = 31 * result + (employerPhoto != null ? employerPhoto.hashCode() : 0);
        return result;
    }

    private boolean safeEquals(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }
}
