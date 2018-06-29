package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by seq-kala on 1/6/18.
 */

public class BenefitModel implements Parcelable {
    @SerializedName("benefitCode")
    String benefitCode;
    @SerializedName("benefitName")
    String benefitName;
    @SerializedName("benefitStatus")
    String benefitStatus;
    @SerializedName("limitType")
    String limitType;
    @SerializedName("limitNumber")
    String limitNumber;
    @SerializedName("userNote")
    String userNote;

    public String getBenefitCode() {
        return benefitCode;
    }

    public String getBenefitName() {
        return benefitName;
    }

    public String getBenefitStatus() {
        return benefitStatus;
    }

    public String getLimitType() {
        return limitType;
    }

    public String getLimitNumber() {
        return limitNumber;
    }

    public String getUserNote() {
        return userNote;
    }

    public static Creator<BenefitModel> getCREATOR() {
        return CREATOR;
    }



    protected BenefitModel(Parcel in) {
        benefitCode = in.readString();
        benefitName = in.readString();
        benefitStatus = in.readString();
        limitType = in.readString();
        limitNumber = in.readString();
        userNote = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(benefitCode);
        dest.writeString(benefitName);
        dest.writeString(benefitStatus);
        dest.writeString(limitType);
        dest.writeString(limitNumber);
        dest.writeString(userNote);
    }

    public Boolean isAvailable(){
        return benefitStatus.equals("Limited") || benefitStatus.equals("Available");
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BenefitModel> CREATOR = new Creator<BenefitModel>() {
        @Override
        public BenefitModel createFromParcel(Parcel in) {
            return new BenefitModel(in);
        }

        @Override
        public BenefitModel[] newArray(int size) {
            return new BenefitModel[size];
        }
    };
}
