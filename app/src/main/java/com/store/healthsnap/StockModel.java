package com.store.healthsnap;

public class StockModel {

    String BP, BPtotal, Edate, Id, Mdate, Name, Profit, Quantity, SP, SPtotal;

    public StockModel() {
    }

    public StockModel(String BP, String BPtotal, String edate, String id, String mdate, String name, String profit, String quantity, String SP, String SPtotal) {
        this.BP = BP;
        this.BPtotal = BPtotal;
        this.Edate = edate;
        this.Id = id;
        this.Mdate = mdate;
        this.Name = name;
        this.Profit = profit;
        this.Quantity = quantity;
        this.SP = SP;
        this.SPtotal = SPtotal;
    }

    public String getBP() {
        return BP;
    }

    public void setBP(String BP) {
        this.BP = BP;
    }

    public String getBPtotal() {
        return BPtotal;
    }

    public void setBPtotal(String BPtotal) {
        this.BPtotal = BPtotal;
    }

    public String getEdate() {
        return Edate;
    }

    public void setEdate(String edate) {
        Edate = edate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMdate() {
        return Mdate;
    }

    public void setMdate(String mdate) {
        Mdate = mdate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProfit() {
        return Profit;
    }

    public void setProfit(String profit) {
        Profit = profit;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getSP() {
        return SP;
    }

    public void setSP(String SP) {
        this.SP = SP;
    }

    public String getSPtotal() {
        return SPtotal;
    }

    public void setSPtotal(String SPtotal) {
        this.SPtotal = SPtotal;
    }
}
