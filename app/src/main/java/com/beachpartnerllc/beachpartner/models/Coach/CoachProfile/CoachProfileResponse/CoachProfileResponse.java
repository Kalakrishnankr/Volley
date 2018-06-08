
package com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse;

import com.beachpartnerllc.beachpartner.models.UserProfileModel;
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
    private String Dob;
    @SerializedName("email")
    private String Email;
    @SerializedName("fcmToken")
    private String FcmToken;
    @SerializedName("firstName")
    private String FirstName;
    @SerializedName("gender")
    private String Gender;
    @SerializedName("id")
    private String Id;
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
    private UserProfileModel UserProfile;
    @SerializedName("userType")
    private String UserType;
    @SerializedName("videoUrl")
    private String VideoUrl;


    private String height;
    private String cbvaPlayerNumber;
    private String cbvaFirstName;
    private String cbvaLastName;
    private String toursPlayedIn;
    private String totalPoints;
    private String highSchoolAttended;
    private String collageClub;
    private String indoorClubPlayed;
    private String collegeIndoor;
    private String collegeBeach;
    private String tournamentLevelInterest;
    private String highestTourRatingEarned;
    private String experience;
    private String courtSidePreference;
    private String position;
    private String willingToTravel;
    private String usaVolleyballRanking;
    private String topFinishes;
    private String collage;
    private String description;
    private String yearsRunning;
    private String numOfAthlets;
    private String programsOffered;
    private String division;
    private String fundingStatus;
    private String subscriptionType;
    private String effectiveDate;
    private String termDate;
    private String daysToExpireSubscription;

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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
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

    public UserProfileModel getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(UserProfileModel userProfile) {
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCbvaPlayerNumber() {
        return cbvaPlayerNumber;
    }

    public void setCbvaPlayerNumber(String cbvaPlayerNumber) {
        this.cbvaPlayerNumber = cbvaPlayerNumber;
    }

    public String getCbvaFirstName() {
        return cbvaFirstName;
    }

    public void setCbvaFirstName(String cbvaFirstName) {
        this.cbvaFirstName = cbvaFirstName;
    }

    public String getCbvaLastName() {
        return cbvaLastName;
    }

    public void setCbvaLastName(String cbvaLastName) {
        this.cbvaLastName = cbvaLastName;
    }

    public String getToursPlayedIn() {
        return toursPlayedIn;
    }

    public void setToursPlayedIn(String toursPlayedIn) {
        this.toursPlayedIn = toursPlayedIn;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getHighSchoolAttended() {
        return highSchoolAttended;
    }

    public void setHighSchoolAttended(String highSchoolAttended) {
        this.highSchoolAttended = highSchoolAttended;
    }

    public String getCollageClub() {
        return collageClub;
    }

    public void setCollageClub(String collageClub) {
        this.collageClub = collageClub;
    }

    public String getIndoorClubPlayed() {
        return indoorClubPlayed;
    }

    public void setIndoorClubPlayed(String indoorClubPlayed) {
        this.indoorClubPlayed = indoorClubPlayed;
    }

    public String getCollegeIndoor() {
        return collegeIndoor;
    }

    public void setCollegeIndoor(String collegeIndoor) {
        this.collegeIndoor = collegeIndoor;
    }

    public String getCollegeBeach() {
        return collegeBeach;
    }

    public void setCollegeBeach(String collegeBeach) {
        this.collegeBeach = collegeBeach;
    }

    public String getTournamentLevelInterest() {
        return tournamentLevelInterest;
    }

    public void setTournamentLevelInterest(String tournamentLevelInterest) {
        this.tournamentLevelInterest = tournamentLevelInterest;
    }

    public String getHighestTourRatingEarned() {
        return highestTourRatingEarned;
    }

    public void setHighestTourRatingEarned(String highestTourRatingEarned) {
        this.highestTourRatingEarned = highestTourRatingEarned;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCourtSidePreference() {
        return courtSidePreference;
    }

    public void setCourtSidePreference(String courtSidePreference) {
        this.courtSidePreference = courtSidePreference;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWillingToTravel() {
        return willingToTravel;
    }

    public void setWillingToTravel(String willingToTravel) {
        this.willingToTravel = willingToTravel;
    }

    public String getUsaVolleyballRanking() {
        return usaVolleyballRanking;
    }

    public void setUsaVolleyballRanking(String usaVolleyballRanking) {
        this.usaVolleyballRanking = usaVolleyballRanking;
    }

    public String getTopFinishes() {
        return topFinishes;
    }

    public void setTopFinishes(String topFinishes) {
        this.topFinishes = topFinishes;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYearsRunning() {
        return yearsRunning;
    }

    public void setYearsRunning(String yearsRunning) {
        this.yearsRunning = yearsRunning;
    }

    public String getNumOfAthlets() {
        return numOfAthlets;
    }

    public void setNumOfAthlets(String numOfAthlets) {
        this.numOfAthlets = numOfAthlets;
    }

    public String getProgramsOffered() {
        return programsOffered;
    }

    public void setProgramsOffered(String programsOffered) {
        this.programsOffered = programsOffered;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getFundingStatus() {
        return fundingStatus;
    }

    public void setFundingStatus(String fundingStatus) {
        this.fundingStatus = fundingStatus;
    }

    public String getShareAthlets() {
        return shareAthlets;
    }

    public void setShareAthlets(String shareAthlets) {
        this.shareAthlets = shareAthlets;
    }

    private String shareAthlets;



}
