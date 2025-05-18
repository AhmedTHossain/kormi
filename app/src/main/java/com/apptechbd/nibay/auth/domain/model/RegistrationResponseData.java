package com.apptechbd.nibay.auth.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationResponseData {
    @SerializedName("user")
    @Expose
    private RegistrationResponseUser user;

    public RegistrationResponseUser getUser() {
        return user;
    }

    public void setUser(RegistrationResponseUser user) {
        this.user = user;
    }
}
