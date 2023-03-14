package com.example.trafficticketsystem;

public class summonModel {
    String plateNo, typeSummon, date, time, SummonAmount, location, imageURL, summonNo, paymentStatus;

    public summonModel() {
    }

    public summonModel(String plateNo, String typeSummon, String date, String time, String summonAmount, String location, String imageURL, String summonNo, String paymentStatus) {
        this.plateNo = plateNo;
        this.typeSummon = typeSummon;
        this.date = date;
        this.time = time;
        SummonAmount = summonAmount;
        this.location = location;
        this.imageURL = imageURL;
        this.summonNo = summonNo;
        this.paymentStatus = paymentStatus;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSummonAmount() {
        return SummonAmount;
    }

    public void setSummonAmount(String summonAmount) {
        SummonAmount = summonAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
}
