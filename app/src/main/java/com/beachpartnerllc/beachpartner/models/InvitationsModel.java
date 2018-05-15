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
    @SerializedName("partnerList")
    List<PartnerResultModel>partnerList;

    protected InvitationsModel(Parcel in) {
        inviterUserId = in.readString();
        inviterImageUrl = in.readString();
        inviterName = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(inviterUserId);
        parcel.writeString(inviterImageUrl);
        parcel.writeString(inviterName);
    }
}
