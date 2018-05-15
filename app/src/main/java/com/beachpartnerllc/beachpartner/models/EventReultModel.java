package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by seq-kala on 15/5/18.
 */

public class EventReultModel implements Parcelable {

    @SerializedName("eventId")
    String eventId;
    @SerializedName("eventName")
    String eventName;
    @SerializedName("eventDescription")
    String eventDescription;
    @SerializedName("eventLocation")
    String eventLocation;
    @SerializedName("eventVenue")
    String eventVenue;
    @SerializedName("eventStartDate")
    String eventStartDate;
    @SerializedName("eventEndDate")
    String eventEndDate;
    @SerializedName("eventAdmin")
    String eventAdmin;
    @SerializedName("teamSize")
    String teamSize;
    @SerializedName("invitationList")
    List<InvitationsModel>invitationList;

    protected EventReultModel(Parcel in) {
        eventId = in.readString();
        eventName = in.readString();
        eventDescription = in.readString();
        eventLocation = in.readString();
        eventVenue = in.readString();
        eventStartDate = in.readString();
        eventEndDate = in.readString();
        eventAdmin = in.readString();
        teamSize = in.readString();
    }

    public static final Creator<EventReultModel> CREATOR = new Creator<EventReultModel>() {
        @Override
        public EventReultModel createFromParcel(Parcel in) {
            return new EventReultModel(in);
        }

        @Override
        public EventReultModel[] newArray(int size) {
            return new EventReultModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eventId);
        parcel.writeString(eventName);
        parcel.writeString(eventDescription);
        parcel.writeString(eventLocation);
        parcel.writeString(eventVenue);
        parcel.writeString(eventStartDate);
        parcel.writeString(eventEndDate);
        parcel.writeString(eventAdmin);
        parcel.writeString(teamSize);
    }
}
