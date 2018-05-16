package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seq-kala on 15/5/18.
 */

public class EventResultModel implements Parcelable {

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
    List<InvitationsModel>invitationList= new ArrayList<>();


    protected EventResultModel(Parcel in) {
        eventId = in.readString();
        eventName = in.readString();
        eventDescription = in.readString();
        eventLocation = in.readString();
        eventVenue = in.readString();
        eventStartDate = in.readString();
        eventEndDate = in.readString();
        eventAdmin = in.readString();
        teamSize = in.readString();
        invitationList = in.createTypedArrayList(InvitationsModel.CREATOR);
    }

    public static final Creator<EventResultModel> CREATOR = new Creator<EventResultModel>() {
        @Override
        public EventResultModel createFromParcel(Parcel in) {
            return new EventResultModel(in);
        }

        @Override
        public EventResultModel[] newArray(int size) {
            return new EventResultModel[size];
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
        parcel.writeTypedList(invitationList);
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public String getEventAdmin() {
        return eventAdmin;
    }

    public String getTeamSize() {
        return teamSize;
    }

    public List<InvitationsModel> getInvitationList() {
        return invitationList;
    }
}
