package com.example.multiuserrealtimefragment.model;

import android.widget.CheckBox;
import android.widget.EditText;

public class ModelUser {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String isCustomerRadio;
    private String isVendorRadio;

    public ModelUser() {


    }

    public ModelUser(String fullName, String email, String phoneNumber, String isCustomerRadio, String isVendorRadio) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isCustomerRadio = isCustomerRadio;
        this.isVendorRadio = isVendorRadio;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIsCustomerRadio() {
        return isCustomerRadio;
    }

    public void setIsCustomerRadio(String isCustomerRadio) {
        this.isCustomerRadio = isCustomerRadio;
    }

    public String getIsVendorRadio() {
        return isVendorRadio;
    }

    public void setIsVendorRadio(String isVendorRadio) {
        this.isVendorRadio = isVendorRadio;
    }
}
