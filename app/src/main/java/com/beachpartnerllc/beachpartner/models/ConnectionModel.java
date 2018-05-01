package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by seq-kala on 26/3/18.
 */

public class ConnectionModel implements Parcelable {
    public String Connected_uId;
    public String Connected_login;
    public String Connected_firstName;
    public String Connected_lastName;
    public String Connected_email;
    public String Connected_activated;
    public String Connected_phoneNumber;
    public String Connected_deviceId;
    public String Connected_authToken;
    public String Connected_location;

    public String getConnected_fcmToken() {
        return Connected_fcmToken;
    }

    public void setConnected_fcmToken(String connected_fcmToken) {
        Connected_fcmToken = connected_fcmToken;
    }

    public String Connected_fcmToken;

    public String getConnected_status() {
        return Connected_status;
    }

    public void setConnected_status(String connected_status) {
        Connected_status = connected_status;
    }

    public boolean isConnected_isAvailable_ondate() {
        return Connected_isAvailable_ondate;
    }

    public String Connected_status;
    public boolean Connected_isAvailable_ondate;

    public String getConnected_userType() {
        return Connected_userType;
    }

    public void setConnected_userType(String connected_userType) {
        Connected_userType = connected_userType;
    }

    public String Connected_userType;

    public String getConnected_imageUrl() {
        return Connected_imageUrl;
    }

    public void setConnected_imageUrl(String connected_imageUrl) {
        Connected_imageUrl = connected_imageUrl;
    }

    public String Connected_imageUrl;
    public boolean isExpanded = false;


    public String getConnected_uId() {
        return Connected_uId;
    }

    public void setConnected_uId(String connected_uId) {
        Connected_uId = connected_uId;
    }

    public String getConnected_login() {
        return Connected_login;
    }

    public void setConnected_login(String connected_login) {
        Connected_login = connected_login;
    }

    public String getConnected_firstName() {
        return Connected_firstName;
    }

    public void setConnected_firstName(String connected_firstName) {
        Connected_firstName = connected_firstName;
    }

    public String getConnected_lastName() {
        return Connected_lastName;
    }

    public void setConnected_lastName(String connected_lastName) {
        Connected_lastName = connected_lastName;
    }

    public String getConnected_email() {
        return Connected_email;
    }

    public void setConnected_email(String connected_email) {
        Connected_email = connected_email;
    }

    public String getConnected_activated() {
        return Connected_activated;
    }

    public void setConnected_activated(String connected_activated) {
        Connected_activated = connected_activated;
    }

    public String getConnected_phoneNumber() {
        return Connected_phoneNumber;
    }

    public void setConnected_phoneNumber(String connected_phoneNumber) {
        Connected_phoneNumber = connected_phoneNumber;
    }

    public String getConnected_deviceId() {
        return Connected_deviceId;
    }

    public void setConnected_deviceId(String connected_deviceId) {
        Connected_deviceId = connected_deviceId;
    }

    public String getConnected_authToken() {
        return Connected_authToken;
    }

    public void setConnected_authToken(String connected_authToken) {
        Connected_authToken = connected_authToken;
    }

    public String getConnected_location() {
        return Connected_location;
    }

    public void setConnected_location(String connected_location) {
        Connected_location = connected_location;
    }

    public boolean getConnected_isAvailable_ondate() {
        return Connected_isAvailable_ondate;
    }

    public void setConnected_isAvailable_ondate(boolean connected_isAvailable_ondate) {
        Connected_isAvailable_ondate = connected_isAvailable_ondate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
