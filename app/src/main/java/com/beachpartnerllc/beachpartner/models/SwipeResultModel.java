package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by allu on 1/5/18 11:26 AM.
 *
 * @author Ameen Maheen
 * Happy Coding
 */
public class SwipeResultModel implements Parcelable {

    @SerializedName("id")
    String id;
    @SerializedName("status")
    String status;
    @SerializedName("user")
    BpFinderModel  bpFinderModel;

    public SwipeResultModel() {
    }

    protected SwipeResultModel(Parcel in) {
        id = in.readString();
        status = in.readString();
        bpFinderModel = in.readParcelable(BpFinderModel.class.getClassLoader());
    }

    public static final Creator<SwipeResultModel> CREATOR = new Creator<SwipeResultModel>() {
        @Override
        public SwipeResultModel createFromParcel(Parcel in) {
            return new SwipeResultModel(in);
        }

        @Override
        public SwipeResultModel[] newArray(int size) {
            return new SwipeResultModel[size];
        }
    };

    public  Boolean isActive(){
        return  status.equals("Active");
    }

    public BpFinderModel getBpFinderModel() {
        return bpFinderModel;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setBpFinderModel(BpFinderModel bpFinderModel) {
        this.bpFinderModel = bpFinderModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(status);
        dest.writeParcelable(bpFinderModel, flags);
    }
}
