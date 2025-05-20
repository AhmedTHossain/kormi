package com.apptechbd.nibay.home.domain.model;

public class ProfileDocument {
    private String documentTitle,documentImage;

    public ProfileDocument(String documentTitle, String documentImage) {
        this.documentTitle = documentTitle;
        this.documentImage = documentImage;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentImage() {
        return documentImage;
    }

    public void setDocumentImage(String documentImage) {
        this.documentImage = documentImage;
    }
}
