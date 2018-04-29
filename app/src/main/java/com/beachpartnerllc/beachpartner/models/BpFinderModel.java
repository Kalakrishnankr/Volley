package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by seq-kala on 3/4/18.
 */

public class BpFinderModel implements Parcelable {

    private String bpf_tableId;
    private String bpf_id;
    private String bpf_login;
    private String bpf_userProfile;
    private String bpf_subscriptions;
    private String bpf_firstName;
    private String bpf_lastName;
    private String bpf_email;
    private String bpf_activated;
    private String bpf_langKey;
    private String bpf_imageUrl;
    private String bpf_videoUrl;
    private String bpf_resetDate;
    private String bpf_dob;
    private String bpf_gender;
    private String bpf_loginType;
    private String bpf_city;
    private String bpf_phoneNumber;
    private String bpf_deviceId;
    private String bpf_authToken;
    private String bpf_location;
    private String bpf_userType;
    private String bpf_age;
    private String bpf_effectiveDate;
    private String bpf_termDate;
    private String bpf_fcmToken;


    public BpFinderModel(){}

    protected BpFinderModel(Parcel in) {
        bpf_tableId = in.readString();
        bpf_id = in.readString();
        bpf_login = in.readString();
        bpf_userProfile = in.readString();
        bpf_subscriptions = in.readString();
        bpf_firstName = in.readString();
        bpf_lastName = in.readString();
        bpf_email = in.readString();
        bpf_activated = in.readString();
        bpf_langKey = in.readString();
        bpf_imageUrl = in.readString();
        bpf_videoUrl = in.readString();
        bpf_resetDate = in.readString();
        bpf_dob = in.readString();
        bpf_gender = in.readString();
        bpf_loginType = in.readString();
        bpf_city = in.readString();
        bpf_phoneNumber = in.readString();
        bpf_deviceId = in.readString();
        bpf_authToken = in.readString();
        bpf_location = in.readString();
        bpf_userType = in.readString();
        bpf_age = in.readString();
        bpf_effectiveDate = in.readString();
        bpf_termDate = in.readString();
        bpf_fcmToken = in.readString();
        bpf_topfinishes = in.readString();
        bpf_subscriptionType = in.readString();
        bpf_daysToExpireSubscription = in.readString();
    }

    public static final Creator<BpFinderModel> CREATOR = new Creator<BpFinderModel>() {
        @Override
        public BpFinderModel createFromParcel(Parcel in) {
            return new BpFinderModel(in);
        }

        @Override
        public BpFinderModel[] newArray(int size) {
            return new BpFinderModel[size];
        }
    };

    public String getBpf_topfinishes() {
        return bpf_topfinishes;
    }

    public void setBpf_topfinishes(String bpf_topfinishes) {
        this.bpf_topfinishes = bpf_topfinishes;
    }

    private String bpf_topfinishes;

    public String getBpf_tableId() {
        return bpf_tableId;
    }

    public void setBpf_tableId(String bpf_tableId) {
        this.bpf_tableId = bpf_tableId;
    }


    public String getBpf_effectiveDate() {
        return bpf_effectiveDate;
    }

    public void setBpf_effectiveDate(String bpf_effectiveDate) {
        this.bpf_effectiveDate = bpf_effectiveDate;
    }

    public String getBpf_termDate() {
        return bpf_termDate;
    }

    public void setBpf_termDate(String bpf_termDate) {
        this.bpf_termDate = bpf_termDate;
    }

    public String getBpf_subscriptionType() {
        return bpf_subscriptionType;
    }

    public void setBpf_subscriptionType(String bpf_subscriptionType) {
        this.bpf_subscriptionType = bpf_subscriptionType;
    }

    public String bpf_subscriptionType;

    public String getBpf_daysToExpireSubscription() {
        return bpf_daysToExpireSubscription;
    }

    public void setBpf_daysToExpireSubscription(String bpf_daysToExpireSubscription) {
        this.bpf_daysToExpireSubscription = bpf_daysToExpireSubscription;
    }

    public String bpf_daysToExpireSubscription;

    public String getBpf_id() {
        return bpf_id;
    }

    public void setBpf_id(String bpf_id) {
        this.bpf_id = bpf_id;
    }

    public String getBpf_login() {
        return bpf_login;
    }

    public void setBpf_login(String bpf_login) {
        this.bpf_login = bpf_login;
    }

    public String getBpf_userProfile() {
        return bpf_userProfile;
    }

    public void setBpf_userProfile(String bpf_userProfile) {
        this.bpf_userProfile = bpf_userProfile;
    }

    public String getBpf_subscriptions() {
        return bpf_subscriptions;
    }

    public void setBpf_subscriptions(String bpf_subscriptions) {
        this.bpf_subscriptions = bpf_subscriptions;
    }

    public String getBpf_firstName() {
        return bpf_firstName;
    }

    public void setBpf_firstName(String bpf_firstName) {
        this.bpf_firstName = bpf_firstName;
    }

    public String getBpf_lastName() {
        return bpf_lastName;
    }

    public void setBpf_lastName(String bpf_lastName) {
        this.bpf_lastName = bpf_lastName;
    }

    public String getBpf_email() {
        return bpf_email;
    }

    public void setBpf_email(String bpf_email) {
        this.bpf_email = bpf_email;
    }

    public String getBpf_activated() {
        return bpf_activated;
    }

    public void setBpf_activated(String bpf_activated) {
        this.bpf_activated = bpf_activated;
    }

    public String getBpf_langKey() {
        return bpf_langKey;
    }

    public void setBpf_langKey(String bpf_langKey) {
        this.bpf_langKey = bpf_langKey;
    }

    public String getBpf_imageUrl() {
        return bpf_imageUrl;
    }

    public void setBpf_imageUrl(String bpf_imageUrl) {
        this.bpf_imageUrl = bpf_imageUrl;
    }

    public String getBpf_videoUrl() {
        return bpf_videoUrl;
    }

    public void setBpf_videoUrl(String bpf_videoUrl) {
        this.bpf_videoUrl = bpf_videoUrl;
    }

    public String getBpf_resetDate() {
        return bpf_resetDate;
    }

    public void setBpf_resetDate(String bpf_resetDate) {
        this.bpf_resetDate = bpf_resetDate;
    }

    public String getBpf_dob() {
        return bpf_dob;
    }

    public void setBpf_dob(String bpf_dob) {
        this.bpf_dob = bpf_dob;
    }

    public String getBpf_gender() {
        return bpf_gender;
    }

    public void setBpf_gender(String bpf_gender) {
        this.bpf_gender = bpf_gender;
    }

    public String getBpf_loginType() {
        return bpf_loginType;
    }

    public void setBpf_loginType(String bpf_loginType) {
        this.bpf_loginType = bpf_loginType;
    }

    public String getBpf_city() {
        return bpf_city;
    }

    public void setBpf_city(String bpf_city) {
        this.bpf_city = bpf_city;
    }

    public String getBpf_phoneNumber() {
        return bpf_phoneNumber;
    }

    public void setBpf_phoneNumber(String bpf_phoneNumber) {
        this.bpf_phoneNumber = bpf_phoneNumber;
    }

    public String getBpf_deviceId() {
        return bpf_deviceId;
    }

    public void setBpf_deviceId(String bpf_deviceId) {
        this.bpf_deviceId = bpf_deviceId;
    }

    public String getBpf_authToken() {
        return bpf_authToken;
    }

    public void setBpf_authToken(String bpf_authToken) {
        this.bpf_authToken = bpf_authToken;
    }

    public String getBpf_location() {
        return bpf_location;
    }

    public void setBpf_location(String bpf_location) {
        this.bpf_location = bpf_location;
    }

    public String getBpf_userType() {
        return bpf_userType;
    }

    public void setBpf_userType(String bpf_userType) {
        this.bpf_userType = bpf_userType;
    }

    public String getBpf_age() {
        return bpf_age;
    }

    public void setBpf_age(String bpf_age) {
        this.bpf_age = bpf_age;
    }

    public void setBpf_fcmToken(String bpf_fcmToken) {
        this.bpf_fcmToken = bpf_fcmToken;
    }
    public String  getBpf_fcmToken() {
        return bpf_fcmToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bpf_tableId);
        parcel.writeString(bpf_id);
        parcel.writeString(bpf_login);
        parcel.writeString(bpf_userProfile);
        parcel.writeString(bpf_subscriptions);
        parcel.writeString(bpf_firstName);
        parcel.writeString(bpf_lastName);
        parcel.writeString(bpf_email);
        parcel.writeString(bpf_activated);
        parcel.writeString(bpf_langKey);
        parcel.writeString(bpf_imageUrl);
        parcel.writeString(bpf_videoUrl);
        parcel.writeString(bpf_resetDate);
        parcel.writeString(bpf_dob);
        parcel.writeString(bpf_gender);
        parcel.writeString(bpf_loginType);
        parcel.writeString(bpf_city);
        parcel.writeString(bpf_phoneNumber);
        parcel.writeString(bpf_deviceId);
        parcel.writeString(bpf_authToken);
        parcel.writeString(bpf_location);
        parcel.writeString(bpf_userType);
        parcel.writeString(bpf_age);
        parcel.writeString(bpf_effectiveDate);
        parcel.writeString(bpf_termDate);
        parcel.writeString(bpf_fcmToken);
        parcel.writeString(bpf_topfinishes);
        parcel.writeString(bpf_subscriptionType);
        parcel.writeString(bpf_daysToExpireSubscription);
    }
}
