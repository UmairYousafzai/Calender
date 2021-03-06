package com.example.calender.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GetSupplierModel implements Parcelable {

    private int Supplier_Id;
    private String Supplier_Name;
    private String Address;


    protected GetSupplierModel(Parcel in) {
        Supplier_Id = in.readInt();
        Supplier_Name = in.readString();
        Address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Supplier_Id);
        dest.writeString(Supplier_Name);
        dest.writeString(Address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GetSupplierModel> CREATOR = new Creator<GetSupplierModel>() {
        @Override
        public GetSupplierModel createFromParcel(Parcel in) {
            return new GetSupplierModel(in);
        }

        @Override
        public GetSupplierModel[] newArray(int size) {
            return new GetSupplierModel[size];
        }
    };

    public int getSupplier_Id() {
        return Supplier_Id;
    }

    public void setSupplier_Id(int supplier_Id) {
        Supplier_Id = supplier_Id;
    }

    public String getSupplier_Name() {
        return Supplier_Name;
    }

    public void setSupplier_Name(String supplier_Name) {
        Supplier_Name = supplier_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
