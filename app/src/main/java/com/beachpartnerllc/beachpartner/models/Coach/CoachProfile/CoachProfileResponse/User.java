
package com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse;

import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("activated")
    private Boolean Activated;
    @SerializedName("age")
    private Long Age;
    @SerializedName("authToken")
    private Object AuthToken;
    @SerializedName("city")
    private String City;
    @SerializedName("deviceId")
    private String DeviceId;
    @SerializedName("dob")
    private Long Dob;
    @SerializedName("email")
    private String Email;
    @SerializedName("fcmToken")
    private String FcmToken;
    @SerializedName("firstName")
    private String FirstName;
    @SerializedName("gender")
    private String Gender;
    @SerializedName("id")
    private Long Id;
    @SerializedName("imageUrl")
    private String ImageUrl;
    @SerializedName("langKey")
    private String LangKey;
    @SerializedName("lastName")
    private String LastName;
    @SerializedName("location")
    private String Location;
    @SerializedName("login")
    private String Login;
    @SerializedName("loginType")
    private String LoginType;
    @SerializedName("phoneNumber")
    private String PhoneNumber;
    @SerializedName("resetDate")
    private Object ResetDate;
    @SerializedName("status")
    private String Status;
    @SerializedName("userProfile")
    private com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.UserProfile UserProfile;
    @SerializedName("userType")
    private String UserType;
    @SerializedName("videoUrl")
    private String VideoUrl;

    public Boolean getActivated() {
        return Activated;
    }

    public void setActivated(Boolean activated) {
        Activated = activated;
    }

    public Long getAge() {
        return Age;
    }

    public void setAge(Long age) {
        Age = age;
    }

    public Object getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(Object authToken) {
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

    public Long getDob() {
        return Dob;
    }

    public void setDob(Long dob) {
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

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getLoginType() {
        return LoginType;
    }

    public void setLoginType(String loginType) {
        LoginType = loginType;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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
