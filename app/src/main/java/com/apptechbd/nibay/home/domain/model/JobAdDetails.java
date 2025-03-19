package com.apptechbd.nibay.home.domain.model;

public class JobAdDetails {
    private int id;
    private String title, description, responsibilities, minAcademicQualification, experiece, role, deadline, division, district, salary, companyName, companyLogo, status;

    public JobAdDetails() {
    }

    public JobAdDetails(int id, String title, String description, String responsibilities, String minAcademicQualification, String experiece, String role, String deadline, String division, String district, String salary, String companyName, String companyLogo, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.responsibilities = responsibilities;
        this.minAcademicQualification = minAcademicQualification;
        this.experiece = experiece;
        this.role = role;
        this.deadline = deadline;
        this.division = division;
        this.district = district;
        this.salary = salary;
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getMinAcademicQualification() {
        return minAcademicQualification;
    }

    public void setMinAcademicQualification(String minAcademicQualification) {
        this.minAcademicQualification = minAcademicQualification;
    }

    public String getExperiece() {
        return experiece;
    }

    public void setExperiece(String experiece) {
        this.experiece = experiece;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
