package com.goldemo.beachpartner.models;

/**
 * Created by seq-kala on 19/3/18.
 */

/*This model for userDetails*/
public class UserDataModel {

    private String id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private String videoUrl;
    private String activated;
    private String langKey;
    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private String dob;
    private String gender;
    private String location;
    private String city;
    private String phoneNumber;
    private String loginType;
    private String deviceId;
    private String authToken;
    private String userType;
    private String age;

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

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getTermDate() {
        return termDate;
    }

    public void setTermDate(String termDate) {
        this.termDate = termDate;
    }

    public String getDaysToExpireSubscription() {
        return daysToExpireSubscription;
    }

    public void setDaysToExpireSubscription(String daysToExpireSubscription) {
        this.daysToExpireSubscription = daysToExpireSubscription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }



    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }



    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }






}
