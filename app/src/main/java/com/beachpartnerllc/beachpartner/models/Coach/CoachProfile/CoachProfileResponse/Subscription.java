
package com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse;

import com.google.gson.annotations.SerializedName;


public class Subscription {

    @SerializedName("daysToExpireSubscription")
    private Long DaysToExpireSubscription;
    @SerializedName("effectiveDate")
    private Long EffectiveDate;
    @SerializedName("id")
    private Long Id;
    @SerializedName("status")
    private String Status;
    @SerializedName("subscriptionType")
    private String SubscriptionType;
    @SerializedName("termDate")
    private Long TermDate;
    @SerializedName("user")
    private com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.User User;

    public Long getDaysToExpireSubscription() {
        return DaysToExpireSubscription;
    }

    public void setDaysToExpireSubscription(Long daysToExpireSubscription) {
        DaysToExpireSubscription = daysToExpireSubscription;
    }

    public Long getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(Long effectiveDate) {
        EffectiveDate = effectiveDate;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSubscriptionType() {
        return SubscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        SubscriptionType = subscriptionType;
    }

    public Long getTermDate() {
        return TermDate;
    }

    public void setTermDate(Long termDate) {
        TermDate = termDate;
    }

    public com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.User getUser() {
        return User;
    }

    public void setUser(com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.User user) {
        User = user;
    }

}
