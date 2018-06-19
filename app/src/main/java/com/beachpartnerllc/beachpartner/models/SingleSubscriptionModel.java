package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seq-kala on 4/6/18.
 */

public class SingleSubscriptionModel implements Parcelable {
    @SerializedName("subscriptions")
    List<SubscriptionItemsModels> subItemModel = new ArrayList<>();
    @SerializedName("addons")
    List<SubscriptionItemsModels> addonsItemModel = new ArrayList<>();
    @SerializedName("regFeePaid")
    String regFeePaid;

    protected SingleSubscriptionModel(Parcel in) {
        regFeePaid = in.readString();
    }

    public static final Creator<SingleSubscriptionModel> CREATOR = new Creator<SingleSubscriptionModel>() {
        @Override
        public SingleSubscriptionModel createFromParcel(Parcel in) {
            return new SingleSubscriptionModel(in);
        }

        @Override
        public SingleSubscriptionModel[] newArray(int size) {
            return new SingleSubscriptionModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(regFeePaid);
    }

    public List<SubscriptionItemsModels> getSubItemModel() {
        return subItemModel;
    }

    public List<SubscriptionItemsModels> getAddonsItemModel() {
        return addonsItemModel;
    }

    public String getRegFeePaid() {
        return regFeePaid;
    }
}
