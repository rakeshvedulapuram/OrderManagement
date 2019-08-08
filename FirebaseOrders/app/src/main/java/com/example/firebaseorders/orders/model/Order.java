package com.example.firebaseorders.orders.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Order implements Parcelable {

    private String orderId;
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerCity;
    private String customerCountry;
    private String dueDate;
    private String totalValue;

    public Order() {
    }

    protected Order(Parcel in) {
        orderId = in.readString();
        customerName = in.readString();
        customerPhoneNumber = in.readString();
        customerAddress = in.readString();
        customerCity = in.readString();
        customerCountry = in.readString();
        dueDate = in.readString();
        totalValue = in.readString();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    public Order(String orderId, String cutomerName, String cutomerPhoneNumber, String cutomerAddress, String cutomerCity, String cutomerCountry, String dueDate, String totalValue) {
        this.orderId = orderId;
        this.customerName = cutomerName;
        this.customerPhoneNumber = cutomerPhoneNumber;
        this.customerAddress = cutomerAddress;
        this.customerCity = cutomerCity;
        this.customerCountry = cutomerCountry;
        this.dueDate = dueDate;
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        System.out.println(this.orderId + "Order"+
                this.customerName + "customerName" +
                this.customerPhoneNumber + "customerPhoneNumber"+
                this.customerAddress + "customerAddress"+
                this.customerCity + "customerCity"+
                this.customerCountry + "customerCountry"+
                this.dueDate + "dueDate"+
                this.totalValue + "totalValue");
        return super.toString();
    }


    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("customerName", customerName);
        result.put("customerPhoneNumber", customerPhoneNumber);
        result.put("customerAddress", customerAddress);
        result.put("customerCity", customerCity);
        result.put("customerCountry", customerCountry);
        result.put("dueDate", dueDate);
        result.put("totalValue", totalValue);

        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.orderId);
        parcel.writeString(this.customerName);
        parcel.writeString(this.customerPhoneNumber);
        parcel.writeString(this.customerAddress);
        parcel.writeString(this.customerCity);
        parcel.writeString(this.customerCountry);
        parcel.writeString(this.dueDate);
        parcel.writeString(this.totalValue);

    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
