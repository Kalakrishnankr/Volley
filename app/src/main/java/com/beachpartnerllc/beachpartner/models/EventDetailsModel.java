package com.beachpartnerllc.beachpartner.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by seq-kala on 15/5/18.
 */

public class EventDetailsModel {
    @SerializedName("eventId")
    String eventId;
    @SerializedName("eventEndDate")
    String eventEndDate;
    @SerializedName("teamSize")
    String teamSize;
    @SerializedName("eventLocation")
    String eventLocation;
    @SerializedName("eventName")
    String eventName;
    @SerializedName("sendCount")
    String sendCount;
    @SerializedName("eventStartDate")
    String eventStartDate;
}
