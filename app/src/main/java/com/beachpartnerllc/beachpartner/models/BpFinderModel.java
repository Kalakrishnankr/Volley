package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seq-kala on 3/4/18.
 */

public class BpFinderModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("activated")
    @Expose
    private String activated;
    @SerializedName("langKey")
    @Expose
    private String langKey;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("lastModifiedDate")
    @Expose
    private String lastModifiedDate;
    @SerializedName("authorities")
    @Expose
    private ArrayList<String> authorities = null;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("loginType")
    @Expose
    private String loginType;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("authToken")
    @Expose
    private String authToken;
    @SerializedName("userProfile")
    @Expose
    private UserProfileModel userProfile;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("videoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;
    @SerializedName("parent")
    @Expose
    private Object parent;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BpFinderModel() {
    }


    protected BpFinderModel(Parcel in) {
        id = in.readString();
        login = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        imageUrl = in.readString();
        activated = in.readString();
        langKey = in.readString();
        createdDate = in.readString();
        lastModifiedDate = in.readString();
        authorities = in.createStringArrayList();
        dob = in.readString();
        gender = in.readString();
        city = in.readString();
        phoneNumber = in.readString();
        loginType = in.readString();
        deviceId = in.readString();
        authToken = in.readString();
        userProfile = in.readParcelable(UserProfileModel.class.getClassLoader());
        userType = in.readString();
        videoUrl = in.readString();
        fcmToken = in.readString();
        age = in.readString();
        status =in.readString();
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

    public String getBpf_id() {
        return id;
    }

    public void setBpf_id(String id) {
        this.id = id;
    }

    public String getBpf_login() {
        return login;
    }

    public void setBpf_login(String login) {
        this.login = login;
    }

    public String getBpf_firstName() {
        return firstName;
    }

    public void setBpf_firstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBpf_lastName() {
        return lastName;
    }

    public void setBpf_lastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBpf_email() {
        return email;
    }

    public void setBpf_email(String email) {
        this.email = email;
    }

    public String getBpf_imageUrl() {
        return imageUrl;
    }

    public void setBpf_imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBpf_activated() {
        return activated;
    }

    public void setBpf_activated(String activated) {
        this.activated = activated;
    }

    public String getBpf_langKey() {
        return langKey;
    }

    public void setBpf_langKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<String> authorities) {
        this.authorities = authorities;
    }

    public String getBpf_dob() {
        return dob;
    }

    public void setBpf_dob(String dob) {
        this.dob = dob;
    }

    public String getBpf_gender() {
        return gender;
    }

    public void setBpf_gender(String gender) {
        this.gender = gender;
    }

    public Object getBpf_location() {
        return location;
    }

    public void setBpf_location(Object location) {
        this.location = location;
    }

    public String getBpf_city() {
        return city;
    }

    public void setBpf_city(String city) {
        this.city = city;
    }

    public String getBpf_phoneNumber() {
        return phoneNumber;
    }

    public void setBpf_phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBpf_loginType() {
        return loginType;
    }

    public void setBpf_loginType(String loginType) {
        this.loginType = loginType;
    }

    public String getBpf_deviceId() {
        return deviceId;
    }

    public void setBpf_deviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBpf_authToken() {
        return authToken;
    }

    public void setBpf_authToken(String authToken) {
        this.authToken = authToken;
    }

    public UserProfileModel getUserProfile() {
        if (userProfile == null) userProfile = new UserProfileModel();
        return userProfile;
    }

    public void setUserProfile(UserProfileModel userProfile) {
        this.userProfile = userProfile;
    }

    public String getBpf_userType() {
        return userType;
    }

    public void setBpf_userType(String userType) {
        this.userType = userType;
    }

    public String getBpf_videoUrl() {
        return videoUrl;
    }

    public void setBpf_videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getBpf_fcmToken() {
        return fcmToken;
    }

    public void setBpf_fcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }


    public void setBpf_age(String bpf_age) {
        this.age = age;
    }

    public String getBpf_age() {
        return age;
    }

    public String getBpf_topfinishes() {
        return getUserProfile().topFinishes;
    }

    public void setBpf_topfinishes(String bpf_topfinishes) {

        this.getUserProfile().topFinishes = bpf_topfinishes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(login);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(email);
        parcel.writeString(imageUrl);
        parcel.writeString(activated);
        parcel.writeString(langKey);
        parcel.writeString(createdDate);
        parcel.writeString(lastModifiedDate);
        parcel.writeStringList(authorities);
        parcel.writeString(dob);
        parcel.writeString(gender);
        parcel.writeString(city);
        parcel.writeString(phoneNumber);
        parcel.writeString(loginType);
        parcel.writeString(deviceId);
        parcel.writeString(authToken);
        parcel.writeParcelable(userProfile, i);
        parcel.writeString(userType);
        parcel.writeString(videoUrl);
        parcel.writeString(fcmToken);
        parcel.writeString(age);
        parcel.writeString(status);
    }
}
