package com.example.multiuserrealtimefragment.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ModelProject implements Parcelable {

// Status: Waiting, Taken, Paid, Done
String dataSize, dataAddress, dataCity, dataStyle, dataCustomerId, dataCustomerName, dataType,
        dataStatus, dataDetails, dataVendorId, dataVendorName, id, dataTransfer;
int dataPrice;

    public String getDataTransfer() {
        return dataTransfer;
    }

    public void setDataTransfer(String dataTransfer) {
        this.dataTransfer = dataTransfer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataStyle() {
        return dataStyle;
    }

    public void setDataStyle(String dataStyle) {
        this.dataStyle = dataStyle;
    }

    public String getDataCustomerId() {
        return dataCustomerId;
    }

    public void setDataCustomerId(String dataCustomerId) {
        this.dataCustomerId = dataCustomerId;
    }

    public String getDataCustomerName() {
        return dataCustomerName;
    }

    public void setDataCustomerName(String dataCustomerName) {
        this.dataCustomerName = dataCustomerName;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getDataDetails() {
        return dataDetails;
    }

    public void setDataDetails(String dataDetails) {
        this.dataDetails = dataDetails;
    }

    public String getDataVendorId() {
        return dataVendorId;
    }

    public void setDataVendorId(String dataVendorId) {
        this.dataVendorId = dataVendorId;
    }

    public String getDataVendorName() {
        return dataVendorName;
    }

    public void setDataVendorName(String dataVendorName) {
        this.dataVendorName = dataVendorName;
    }

    public ModelProject(){

    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public String getDataAddress() {
        return dataAddress;
    }

    public void setDataAddress(String dataAddress) {
        this.dataAddress = dataAddress;
    }

    public String getDataCity() {
        return dataCity;
    }

    public void setDataCity(String dataCity) {
        this.dataCity = dataCity;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getDataPrice() {
        return dataPrice;
    }

    public void setDataPrice(int dataPrice) {
        this.dataPrice = dataPrice;
    }

    public ModelProject(String dataSize, String dataAddress, String dataCity, String dataStyle, String dataCustomerId, String dataCustomerName, String dataType, String dataStatus, String dataDetails, String dataVendorId, String dataVendorName, int dataPrice, String dataTransfer) {
        this.dataSize = dataSize;
        this.dataAddress = dataAddress;
        this.dataCity = dataCity;
        this.dataStyle = dataStyle;
        this.dataCustomerId = dataCustomerId;
        this.dataCustomerName = dataCustomerName;
        this.dataType = dataType;
        this.dataStatus = dataStatus;
        this.dataDetails = dataDetails;
        this.dataVendorId = dataVendorId;
        this.dataVendorName = dataVendorName;
        this.dataPrice = dataPrice;
        this.dataTransfer = dataTransfer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.dataSize);
        dest.writeString(this.dataAddress);
        dest.writeString(this.dataCity);
        dest.writeString(this.dataStyle);
        dest.writeString(this.dataCustomerId);
        dest.writeString(this.dataCustomerName);
        dest.writeString(this.dataType);
        dest.writeString(this.dataStatus);
        dest.writeString(this.dataDetails);
        dest.writeString(this.dataVendorId);
        dest.writeString(this.dataVendorName);
        dest.writeString(this.dataTransfer);
        dest.writeInt(this.dataPrice);
        dest.writeString(this.id);
    }

    private ModelProject(Parcel in) {
        dataSize = in.readString();
        dataAddress = in.readString();
        dataCity = in.readString();
        dataStyle = in.readString();
        dataCustomerId = in.readString();
        dataCustomerName = in.readString();
        dataType = in.readString();
        dataStatus = in.readString();
        dataDetails = in.readString();
        dataVendorId = in.readString();
        dataVendorName = in.readString();
        dataTransfer = in.readString();
        dataPrice = in.readInt();
        id = in.readString();
    }

    public static final Creator<ModelProject> CREATOR = new Creator<ModelProject>() {
        @Override
        public ModelProject createFromParcel(Parcel in) {
            return new ModelProject(in);
        }

        @Override
        public ModelProject[] newArray(int size) {
            return new ModelProject[size];
        }
    };
}
