
package com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData;

import com.google.gson.annotations.SerializedName;

public class UserInputDto {

    @SerializedName("authToken")
    private String AuthToken;
    @SerializedName("city")
    private String City;
    @SerializedName("deviceId")
    private String DeviceId;
    @SerializedName("dob")
    private String Dob;
    @SerializedName("email")
    private String Email;
    @SerializedName("fcmToken")
    private String FcmToken;
    @SerializedName("firstName")
    private String FirstName;
    @SerializedName("gender")
    private String Gender;
    @SerializedName("imageUrl")
    private String ImageUrl;
    @SerializedName("langKey")
    private String LangKey;
    @SerializedName("lastName")
    private String LastName;
    @SerializedName("location")
    private String Location;
    @SerializedName("parentUserId")
    private String ParentUserId;
    @SerializedName("phoneNumber")
    private String PhoneNumber;
    @SerializedName("userType")
    private String UserType;
    @SerializedName("videoUrl")
    private String VideoUrl;

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFcmToken() {
        return FcmToken;
    }

    public void setFcmToken(String fcmToken) {
        FcmToken = fcmToken;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getLangKey() {
        return LangKey;
    }

    public void setLangKey(String langKey) {
        LangKey = langKey;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getParentUserId() {
        return ParentUserId;
    }

    public void setParentUserId(String parentUserId) {
        ParentUserId = parentUserId;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

}
