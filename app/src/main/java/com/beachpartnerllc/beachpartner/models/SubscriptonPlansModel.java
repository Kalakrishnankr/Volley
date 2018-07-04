package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by seq-kala on 1/6/18.
 */

public class SubscriptonPlansModel implements Parcelable {

    @SerializedName("planId")
    String planId;
    @SerializedName("planCode")
    String planCode;
    @SerializedName("planType")
    String planType;
    @SerializedName("planName")
    String planName;
    @SerializedName("planDescription")
    String planDescription;
    @SerializedName("regFee")
    String regFee;
    @SerializedName("monthlyCharge")
    String monthlyCharge;
    @SerializedName("benefitList")
    List<BenefitModel> benefitList;
    public boolean isExpanded = false;

    public String getPlanId() {
        return planId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public String getPlanType() {
        return planType;
    }

    public String getPlanName() {
        return planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public String getRegFee() {
        return regFee;
    }

    public String getMonthlyCharge() {
        return monthlyCharge;
    }

    public List<BenefitModel> getBenefitList() {
        return benefitList;
    }

    protected SubscriptonPlansModel(Parcel in) {
        planId = in.readString();
        planCode = in.readString();
        planType = in.readString();
        planName = in.readString();
        planDescription = in.readString();
        regFee = in.readString();
        monthlyCharge = in.readString();
        benefitList = in.createTypedArrayList(BenefitModel.CREATOR);
    }

    public static final Creator<SubscriptonPlansModel> CREATOR = new Creator<SubscriptonPlansModel>() {
        @Override
        public SubscriptonPlansModel createFromParcel(Parcel in) {
            return new SubscriptonPlansModel(in);
        }

        @Override
        public SubscriptonPlansModel[] newArray(int size) {
            return new SubscriptonPlansModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(planId);
        parcel.writeString(planCode);
        parcel.writeString(planType);
        parcel.writeString(planName);
        parcel.writeString(planDescription);
        parcel.writeString(regFee);
        parcel.writeString(monthlyCharge);
        parcel.writeTypedList(benefitList);
    }
}
