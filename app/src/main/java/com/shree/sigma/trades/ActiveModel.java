package com.shree.sigma.trades;

public class ActiveModel {
    private String action;
    private String tag;
    private String name;
    private String date;
    private String time;
    private String rate;
    private String sb;
    private String margin;

    public ActiveModel() {
    }

    public ActiveModel(String action, String tag, String name, String date, String time, String rate, String sb, String margin) {
        this.action = action;
        this.tag = tag;
        this.name = name;
        this.date = date;
        this.time = time;
        this.rate = rate;
        this.sb = sb;
        this.margin = margin;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }
}
