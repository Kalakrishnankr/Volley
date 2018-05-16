package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seq-kala on 15/5/18.
 */

public class InvitationResponseModel implements Parcelable {
    @SerializedName("received")
    List<EventDetailsModel> invitationReceivedModels = new ArrayList<>();
    @SerializedName("send")
    List<EventDetailsModel>invitationSendModels = new ArrayList<>();

    public List<EventDetailsModel> getInvitationReceivedModels() {
        return invitationReceivedModels;
    }

    public List<EventDetailsModel> getInvitationSendModels() {
        return invitationSendModels;
    }

    protected InvitationResponseModel(Parcel in) {
    }

    public static final Creator<InvitationResponseModel> CREATOR = new Creator<InvitationResponseModel>() {
        @Override
        public InvitationResponseModel createFromParcel(Parcel in) {
            return new InvitationResponseModel(in);
        }

        @Override
        public InvitationResponseModel[] newArray(int size) {
            return new InvitationResponseModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
