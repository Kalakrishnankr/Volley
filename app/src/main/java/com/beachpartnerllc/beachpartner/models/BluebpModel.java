package com.beachpartnerllc.beachpartner.models;

import java.util.List;

/**
 * Created by user on 2/4/18.
 */

public class BluebpModel {
    private String bp_userId;
    private String bp_name;
    private String bp_age;
    private String bp_userType;
    private String bp_topFinishes;
    private String bp_imageUrl;
    private String bp_videoUrl;
    private String deviceId;
    private String bp_subscriptionType;
    private String bp_effectiveDate;
    private String bp_termDate;

    public String getBp_subscriptionType() {
        return bp_subscriptionType;
    }

    public void setBp_subscriptionType(String bp_subscriptionType) {
        this.bp_subscriptionType = bp_subscriptionType;
    }

    public String getBp_effectiveDate() {
        return bp_effectiveDate;
    }

    public void setBp_effectiveDate(String bp_effectiveDate) {
        this.bp_effectiveDate = bp_effectiveDate;
    }

    public String getBp_termDate() {
        return bp_termDate;
    }

    public void setBp_termDate(String bp_termDate) {
        this.bp_termDate = bp_termDate;
    }

    public String getBp_daysToExpireSubscription() {
        return bp_daysToExpireSubscription;
    }

    public void setBp_daysToExpireSubscription(String bp_daysToExpireSubscription) {
        this.bp_daysToExpireSubscription = bp_daysToExpireSubscription;
    }

    public String getBp_status() {
        return bp_status;
    }

    public void setBp_status(String bp_status) {
        this.bp_status = bp_status;
    }

    private String bp_daysToExpireSubscription;
    private String bp_status;


    public String getBp_userId() {
        return bp_userId;
    }

    public void setBp_userId(String bp_userId) {
        this.bp_userId = bp_userId;
    }

    public String getBp_name() {
        return bp_name;
    }

    public void setBp_name(String bp_name) {
        this.bp_name = bp_name;
    }

    public String getBp_age() {
        return bp_age;
    }

    public void setBp_age(String bp_age) {
        this.bp_age = bp_age;
    }

    public String getBp_userType() {
        return bp_userType;
    }

    public void setBp_userType(String bp_userType) {
        this.bp_userType = bp_userType;
    }

    public String getBp_topFinishes() {
        return bp_topFinishes;
    }

    public void setBp_topFinishes(String bp_topFinishes) {
        this.bp_topFinishes = bp_topFinishes;
    }

    public String getBp_imageUrl() {
        return bp_imageUrl;
    }

    public void setBp_imageUrl(String bp_imageUrl) {
        this.bp_imageUrl = bp_imageUrl;
    }

    public String getBp_videoUrl() {
        return bp_videoUrl;
    }

    public void setBp_videoUrl(String bp_videoUrl) {
        this.bp_videoUrl = bp_videoUrl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<EventModel> getBluebpList() {
        return bluebpList;
    }

    public void setBluebpList(List<EventModel> bluebpList) {
        this.bluebpList = bluebpList;
    }

    public List<EventModel>bluebpList;


}
