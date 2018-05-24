package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by seq-kala on 15/5/18.
 */

public class EventDetailsModel implements Parcelable {
    @SerializedName("eventId")
    String eventId;
    @SerializedName("eventEndDate")
    String eventEndDate;
    @SerializedName("eventurl")
    String eventurl;
    @SerializedName("teamSize")
    String teamSize;
    @SerializedName("acceptedCount")
    String acceptedCount;
    @SerializedName("eventLocation")
    String eventLocation;
    @SerializedName("eventName")
    String eventName;
    @SerializedName("invitationCount")
    String invitationCount;
    @SerializedName("pendingCount")
    String pendingCount;
    @SerializedName("rejectedCount")
    String rejectedCount;
    @SerializedName("sendCount")
    String sendCount;
    @SerializedName("eventStartDate")
    String eventStartDate;


    protected EventDetailsModel(Parcel in) {
        eventId = in.readString();
        eventEndDate = in.readString();
        eventurl = in.readString();
        teamSize = in.readString();
        acceptedCount = in.readString();
        eventLocation = in.readString();
        eventName = in.readString();
        invitationCount = in.readString();
        pendingCount = in.readString();
        rejectedCount = in.readString();
        sendCount = in.readString();
        eventStartDate = in.readString();
    }

    public static final Creator<EventDetailsModel> CREATOR = new Creator<EventDetailsModel>() {
        @Override
        public EventDetailsModel createFromParcel(Parcel in) {
            return new EventDetailsModel(in);
        }

        @Override
        public EventDetailsModel[] newArray(int size) {
            return new EventDetailsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eventId);
        parcel.writeString(eventEndDate);
        parcel.writeString(eventurl);
        parcel.writeString(teamSize);
        parcel.writeString(acceptedCount);
        parcel.writeString(eventLocation);
        parcel.writeString(eventName);
        parcel.writeString(invitationCount);
        parcel.writeString(pendingCount);
        parcel.writeString(rejectedCount);
        parcel.writeString(sendCount);
        parcel.writeString(eventStartDate);
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public String getEventurl() {
        return eventurl;
    }

    public String getTeamSize() {
        return teamSize;
    }

    public String getAcceptedCount() {
        return acceptedCount;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventName() {
        return eventName;
    }

    public String getInvitationCount() {
        return invitationCount;
    }

    public String getPendingCount() {
        return pendingCount;
    }

    public String getRejectedCount() {
        return rejectedCount;
    }

    public String getSendCount() {
        return sendCount;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }
}
