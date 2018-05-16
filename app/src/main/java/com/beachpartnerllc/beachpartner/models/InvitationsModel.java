package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by seq-kala on 15/5/18.
 */

public class InvitationsModel implements Parcelable {
    @SerializedName("inviterUserId")
    String inviterUserId;
    @SerializedName("inviterImageUrl")
    String inviterImageUrl;
    @SerializedName("inviterName")
    String inviterName;
    @SerializedName("eventStatus")
    String eventStatus;
    @SerializedName("partnerList")
    List<PartnerResultModel>partnerList;

    protected InvitationsModel(Parcel in) {
        inviterUserId = in.readString();
        inviterImageUrl = in.readString();
        inviterName = in.readString();
        eventStatus = in.readString();
        partnerList = in.createTypedArrayList(PartnerResultModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(inviterUserId);
        dest.writeString(inviterImageUrl);
        dest.writeString(inviterName);
        dest.writeString(eventStatus);
        dest.writeTypedList(partnerList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InvitationsModel> CREATOR = new Creator<InvitationsModel>() {
        @Override
        public InvitationsModel createFromParcel(Parcel in) {
            return new InvitationsModel(in);
        }

        @Override
        public InvitationsModel[] newArray(int size) {
            return new InvitationsModel[size];
        }
    };

    public String getInviterUserId() {
        return inviterUserId;
    }

    public String getInviterImageUrl() {
        return inviterImageUrl;
    }

    public String getInviterName() {
        return inviterName;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public List<PartnerResultModel> getPartnerList() {
        return partnerList;
    }
}
