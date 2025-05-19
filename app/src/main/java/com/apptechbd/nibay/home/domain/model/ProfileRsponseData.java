package com.apptechbd.nibay.home.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileRsponseData {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("nidNumber")
    @Expose
    private String nidNumber;
    @SerializedName("nidCopy")
    @Expose
    private String nidCopy;
    @SerializedName("drivingLicense")
    @Expose
    private String drivingLicense;
    @SerializedName("drivingLicenseCopy")
    @Expose
    private Object drivingLicenseCopy;
    @SerializedName("yearsOfExperience")
    @Expose
    private String yearsOfExperience;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("profilePhoto")
    @Expose
    private String profilePhoto;
    @SerializedName("maxEducationLevel")
    @Expose
    private Integer maxEducationLevel;
    @SerializedName("deviceID")
    @Expose
    private String deviceID;
    @SerializedName("following")
    @Expose

    private List<Object> following;
    @SerializedName("birthCertificate")
    @Expose
    private Object birthCertificate;
    @SerializedName("portEntryPermit")
    @Expose
    private Object portEntryPermit;
    @SerializedName("chairmanCertificateCopy")
    @Expose
    private Object chairmanCertificateCopy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNidNumber() {
        return nidNumber;
    }

    public void setNidNumber(String nidNumber) {
        this.nidNumber = nidNumber;
    }

    public String getNidCopy() {
        return nidCopy;
    }

    public void setNidCopy(String nidCopy) {
        this.nidCopy = nidCopy;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public Object getDrivingLicenseCopy() {
        return drivingLicenseCopy;
    }

    public void setDrivingLicenseCopy(Object drivingLicenseCopy) {
        this.drivingLicenseCopy = drivingLicenseCopy;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Integer getMaxEducationLevel() {
        return maxEducationLevel;
    }

    public void setMaxEducationLevel(Integer maxEducationLevel) {
        this.maxEducationLevel = maxEducationLevel;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public List<Object> getFollowing() {
        return following;
    }

    public void setFollowing(List<Object> following) {
        this.following = following;
    }

    public Object getBirthCertificate() {
        return birthCertificate;
    }

    public void setBirthCertificate(Object birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public Object getPortEntryPermit() {
        return portEntryPermit;
    }

    public void setPortEntryPermit(Object portEntryPermit) {
        this.portEntryPermit = portEntryPermit;
    }

    public Object getChairmanCertificateCopy() {
        return chairmanCertificateCopy;
    }

    public void setChairmanCertificateCopy(Object chairmanCertificateCopy) {
        this.chairmanCertificateCopy = chairmanCertificateCopy;
    }
}
