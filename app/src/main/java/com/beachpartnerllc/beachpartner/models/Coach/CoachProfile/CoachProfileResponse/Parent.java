
package com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse;

import com.google.gson.annotations.SerializedName;


public class Parent {

    @SerializedName("activated")
    private Boolean Activated;
    @SerializedName("age")
    private Object Age;
    @SerializedName("authToken")
    private Object AuthToken;
    @SerializedName("city")
    private Object City;
    @SerializedName("deviceId")
    private Object DeviceId;
    @SerializedName("dob")
    private Object Dob;
    @SerializedName("email")
    private String Email;
    @SerializedName("fcmToken")
    private Object FcmToken;
    @SerializedName("firstName")
    private String FirstName;
    @SerializedName("gender")
    private Object Gender;
    @SerializedName("id")
    private Long Id;
    @SerializedName("imageUrl")
    private String ImageUrl;
    @SerializedName("langKey")
    private String LangKey;
    @SerializedName("lastName")
    private String LastName;
    @SerializedName("location")
    private Object Location;
    @SerializedName("login")
    private String Login;
    @SerializedName("loginType")
    private Object LoginType;
    @SerializedName("phoneNumber")
    private Object PhoneNumber;
    @SerializedName("resetDate")
    private Object ResetDate;
    @SerializedName("status")
    private String Status;
    @SerializedName("userProfile")
    private com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.UserProfile UserProfile;
    @SerializedName("userType")
    private Object UserType;
    @SerializedName("videoUrl")
    private Object VideoUrl;

    public Boolean getActivated() {
        return Activated;
    }

    public void setActivated(Boolean activated) {
        Activated = activated;
    }

    public Object getAge() {
        return Age;
    }

    public void setAge(Object age) {
        Age = age;
    }

    public Object getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(Object authToken) {
        AuthToken = authToken;
    }

    public Object getCity() {
        return City;
    }

    public void setCity(Object city) {
        City = city;
    }

    public Object getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(Object deviceId) {
        DeviceId = deviceId;
    }

    public Object getDob() {
        return Dob;
    }

    public void setDob(Object dob) {
        Dob = dob;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Object getFcmToken() {
        return FcmToken;
    }

    public void setFcmToken(Object fcmToken) {
        FcmToken = fcmToken;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public Object getGender() {
        return Gender;
    }

    public void setGender(Object gender) {
        Gender = gender;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public Object getLocation() {
        return Location;
    }

    public void setLocation(Object location) {
        Location = location;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public Object getLoginType() {
        return LoginType;
    }

    public void setLoginType(Object loginType) {
        LoginType = loginType;
    }

    public Object getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Object getResetDate() {
        return ResetDate;
    }

    public void setResetDate(Object resetDate) {
        ResetDate = resetDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.UserProfile getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.UserProfile userProfile) {
        UserProfile = userProfile;
    }

    public Object getUserType() {
        return UserType;
    }

    public void setUserType(Object userType) {
        UserType = userType;
    }

    public Object getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(Object videoUrl) {
        VideoUrl = videoUrl;
    }

}
