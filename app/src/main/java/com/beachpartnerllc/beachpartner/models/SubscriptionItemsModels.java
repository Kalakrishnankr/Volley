package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by seq-kala on 4/6/18.
 */

public class SubscriptionItemsModels implements Parcelable {

    @SerializedName("remainingDays")
    String remainingDays;
    @SerializedName("planName")
    String planName;


    public String getRemainingDays() {
        return remainingDays;
    }

    public String getPlanName() {
        return planName;
    }

    protected SubscriptionItemsModels(Parcel in) {
        remainingDays = in.readString();
        planName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(remainingDays);
        dest.writeString(planName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubscriptionItemsModels> CREATOR = new Creator<SubscriptionItemsModels>() {
        @Override
        public SubscriptionItemsModels createFromParcel(Parcel in) {
            return new SubscriptionItemsModels(in);
        }

        @Override
        public SubscriptionItemsModels[] newArray(int size) {
            return new SubscriptionItemsModels[size];
        }
    };
}
