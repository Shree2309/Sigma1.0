package com.shree.sigma.watchlist;

public class NSEModel {
    private String name;
    private String date;
    private String lot;
    private String num1;
    private String num2;

    public NSEModel() {
    }

    public NSEModel(String name, String date, String lot, String num1, String num2) {
        this.name = name;
        this.date = date;
        this.lot = lot;
        this.num1 = num1;
        this.num2 = num2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }
}
