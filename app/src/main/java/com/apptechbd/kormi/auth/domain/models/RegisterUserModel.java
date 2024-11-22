package com.apptechbd.kormi.auth.domain.models;

import java.io.File;

public class RegisterUserModel {
    private String mobileNumber, fullName, role, nidNumber, drivingLicenseNumber, maxEducationDegreeName, divisionName, districtName;
    private int yearsOfExperience;
    private File nidImage, drivingLicenseImage, certificateImage;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNidNumber() {
        return nidNumber;
    }

    public void setNidNumber(String nidNumber) {
        this.nidNumber = nidNumber;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public String getMaxEducationDegreeName() {
        return maxEducationDegreeName;
    }

    public void setMaxEducationDegreeName(String maxEducationDegreeName) {
        this.maxEducationDegreeName = maxEducationDegreeName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public File getNidImage() {
        return nidImage;
    }

    public void setNidImage(File nidImage) {
        this.nidImage = nidImage;
    }

    public File getDrivingLicenseImage() {
        return drivingLicenseImage;
    }

    public void setDrivingLicenseImage(File drivingLicenseImage) {
        this.drivingLicenseImage = drivingLicenseImage;
    }

    public File getCertificateImage() {
        return certificateImage;
    }

    public void setCertificateImage(File certificateImage) {
        this.certificateImage = certificateImage;
    }
}
