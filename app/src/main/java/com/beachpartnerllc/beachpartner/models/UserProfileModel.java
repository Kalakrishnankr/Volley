package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by allu on 1/5/18 12:07 PM.
 *
 * @author Ameen Maheen
 * Happy Coding
 */
public class UserProfileModel implements Parcelable{


    @SerializedName("topFinishes")
    String topFinishes ="";

    protected UserProfileModel(Parcel in) {
        topFinishes = in.readString();
    }

    public static final Creator<UserProfileModel> CREATOR = new Creator<UserProfileModel>() {
        @Override
        public UserProfileModel createFromParcel(Parcel in) {
            return new UserProfileModel(in);
        }

        @Override
        public UserProfileModel[] newArray(int size) {
            return new UserProfileModel[size];
        }
    };

    public UserProfileModel() {

    }

    public String getTopFinishes() {
        return topFinishes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(topFinishes);
    }
}
