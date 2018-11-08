package com.ycgrp.cloudticket.bean;

/**
 * Created by Administrator on 2017/10/26.
 */

public class VisitBean {
    private long saveTime;
    private String visitToken;
    private String visitorToken;

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public String getVisitToken() {
        return visitToken;
    }

    public void setVisitToken(String visitToken) {
        this.visitToken = visitToken;
    }

    public String getVisitorToken() {
        return visitorToken;
    }

    public void setVisitorToken(String visitorToken) {
        this.visitorToken = visitorToken;
    }
}
