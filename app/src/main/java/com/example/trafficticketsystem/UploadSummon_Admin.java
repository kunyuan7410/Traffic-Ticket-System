package com.example.trafficticketsystem;

public class UploadSummon_Admin {
    String imageURL, plateNo, typeSummon, Location, Time, Date, summonAmount, summonNo, paymentStatus, policeName;

    public UploadSummon_Admin() {
    }

    public UploadSummon_Admin(String imageURL, String plateNo, String typeSummon, String location, String time, String date, String summonAmount, String summonNo, String paymentStatus, String policeName) {
        this.imageURL = imageURL;
        this.plateNo = plateNo;
        this.typeSummon = typeSummon;
        Location = location;
        Time = time;
        Date = date;
        this.summonAmount = summonAmount;
        this.summonNo = summonNo;
        this.paymentStatus = paymentStatus;
        this.policeName = policeName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getTypeSummon() {
        return typeSummon;
    }

    public void setTypeSummon(String typeSummon) {
        this.typeSummon = typeSummon;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSummonAmount() {
        return summonAmount;
    }

    public void setSummonAmount(String summonAmount) {
        this.summonAmount = summonAmount;
    }

    public String getSummonNo() {
        return summonNo;
    }

    public void setSummonNo(String summonNo) {
        this.summonNo = summonNo;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }
}
