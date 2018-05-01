
package com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CoachProfileResponse {

    @SerializedName("activated")
    private Boolean Activated;
    @SerializedName("authToken")
    private Object AuthToken;
    @SerializedName("authorities")
    private List<String> Authorities;
    @SerializedName("city")
    private String City;
    @SerializedName("createdDate")
    private Long CreatedDate;
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
    @SerializedName("lastModifiedDate")
    private Long LastModifiedDate;
    @SerializedName("lastName")
    private String LastName;
    @SerializedName("location")
    private String Location;
    @SerializedName("login")
    private String Login;
    @SerializedName("loginType")
    private String LoginType;
    @SerializedName("parent")
    private com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.Parent Parent;
    @SerializedName("phoneNumber")
    private String PhoneNumber;
    @SerializedName("subscriptions")
    private List<Subscription> Subscriptions;
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

    public Object getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(Object authToken) {
        AuthToken = authToken;
    }

    public List<String> getAuthorities() {
        return Authorities;
    }

    public void setAuthorities(List<String> authorities) {
        Authorities = authorities;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Long getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Long createdDate) {
        CreatedDate = createdDate;
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

    public Long getLastModifiedDate() {
        return LastModifiedDate;
    }

    public void setLastModifiedDate(Long lastModifiedDate) {
        LastModifiedDate = lastModifiedDate;
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

    public com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.Parent getParent() {
        return Parent;
    }

    public void setParent(com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.Parent parent) {
        Parent = parent;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public List<Subscription> getSubscriptions() {
        return Subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        Subscriptions = subscriptions;
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
