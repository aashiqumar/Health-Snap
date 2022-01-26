package com.store.healthsnap;

public class billModel {

    String pName, pDate, pTime, pFee, pNumber, pDescription;

    public billModel() {
    }

    public billModel(String pName, String pDate, String pTime, String pFee, String pNumber, String pDescription) {
        this.pName = pName;
        this.pDate = pDate;
        this.pTime = pTime;
        this.pFee = pFee;
        this.pNumber = pNumber;
        this.pDescription = pDescription;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getpFee() {
        return pFee;
    }

    public void setpFee(String pFee) {
        this.pFee = pFee;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }
}
