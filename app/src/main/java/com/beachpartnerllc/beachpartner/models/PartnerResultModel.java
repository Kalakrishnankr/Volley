package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by seq-kala on 15/5/18.
 */

public class PartnerResultModel extends ArrayList implements Parcelable {
    @SerializedName("partnerName")
    String partnerName;
    @SerializedName("partnerUserId")
    String partnerUserId;
    @SerializedName("partnerImageUrl")
    String partnerImageUrl;
    @SerializedName("invitationStatus")
    String invitationStatus;

    protected PartnerResultModel(Parcel in) {
        partnerName = in.readString();
        partnerUserId = in.readString();
        partnerImageUrl = in.readString();
        invitationStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(partnerName);
        dest.writeString(partnerUserId);
        dest.writeString(partnerImageUrl);
        dest.writeString(invitationStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PartnerResultModel> CREATOR = new Creator<PartnerResultModel>() {
        @Override
        public PartnerResultModel createFromParcel(Parcel in) {
            return new PartnerResultModel(in);
        }

        @Override
        public PartnerResultModel[] newArray(int size) {
            return new PartnerResultModel[size];
        }
    };

    public String getPartnerName() {
        return partnerName;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public String getPartnerImageUrl() {
        return partnerImageUrl;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }
}
