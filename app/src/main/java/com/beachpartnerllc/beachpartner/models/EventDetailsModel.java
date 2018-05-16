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

    protected EventDetailsModel(Parcel in) {
        eventId = in.readString();
        eventEndDate = in.readString();
        teamSize = in.readString();
        eventLocation = in.readString();
        eventName = in.readString();
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
        parcel.writeString(teamSize);
        parcel.writeString(eventLocation);
        parcel.writeString(eventName);
        parcel.writeString(sendCount);
        parcel.writeString(eventStartDate);
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public String getTeamSize() {
        return teamSize;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventName() {
        return eventName;
    }

    public String getSendCount() {
        return sendCount;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }
}
