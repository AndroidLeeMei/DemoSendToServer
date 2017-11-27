package com.example.auser.demosendtoserver;

/**
 * Created by auser on 2017/11/27.
 */

public class Data {
    String address;
    String phoneNumber;

    public Data() {
        this("台北市中正區民三路9號","45698723500");//內建測試資料
    }

    public Data(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {

        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
