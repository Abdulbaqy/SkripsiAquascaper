package com.example.multiuserrealtimefragment.model;

public class ModelChat {
    String projectId, vendorId, customerId,
        projectType, projectSize, projectStyle, projectCity;

    public ModelChat() {

    }

    public ModelChat(String projectId, String projectType, String projectSize, String projectStyle, String projectCity) {
        this.projectId = projectId;
        this.projectType = projectType;
        this.projectSize = projectSize;
        this.projectStyle = projectStyle;
        this.projectCity = projectCity;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectSize() {
        return projectSize;
    }

    public void setProjectSize(String projectSize) {
        this.projectSize = projectSize;
    }

    public String getProjectStyle() {
        return projectStyle;
    }

    public void setProjectStyle(String projectStyle) {
        this.projectStyle = projectStyle;
    }

    public String getProjectCity() {
        return projectCity;
    }

    public void setProjectCity(String projectCity) {
        this.projectCity = projectCity;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
