package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by seq-kala on 15/5/18.
 */

public class PartnerResultModel implements Parcelable {
    @SerializedName("partnerName")
    String partnerName;
    @SerializedName("partnerUserId")
    String partnerUserId;
    @SerializedName("partnerImageUrl")
    String partnerImageUrl;

    protected PartnerResultModel(Parcel in) {
        partnerName = in.readString();
        partnerUserId = in.readString();
        partnerImageUrl = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(partnerName);
        parcel.writeString(partnerUserId);
        parcel.writeString(partnerImageUrl);
    }

    public String getPartnerName() {
        return partnerName;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public String getPartnerImageUrl() {
        return partnerImageUrl;
    }
}
