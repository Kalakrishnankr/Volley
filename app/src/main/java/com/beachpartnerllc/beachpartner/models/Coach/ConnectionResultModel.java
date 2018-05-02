package com.beachpartnerllc.beachpartner.models.Coach;

import android.os.Parcel;
import android.os.Parcelable;

import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by seq-kala on 2/5/18.
 */

public class ConnectionResultModel implements Parcelable {
    @SerializedName("id")
    String id;
    @SerializedName("status")
    String Status;
    @SerializedName("connectedUser")
    BpFinderModel bpFinderModel;

    public boolean isExpanded = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public BpFinderModel getBpFinderModel() {
        return bpFinderModel;
    }

    public void setBpFinderModel(BpFinderModel bpFinderModel) {
        this.bpFinderModel = bpFinderModel;
    }

    protected ConnectionResultModel(Parcel in) {
        id = in.readString();
        Status = in.readString();
        bpFinderModel = in.readParcelable(BpFinderModel.class.getClassLoader());
    }

    public static final Creator<ConnectionResultModel> CREATOR = new Creator<ConnectionResultModel>() {
        @Override
        public ConnectionResultModel createFromParcel(Parcel in) {
            return new ConnectionResultModel(in);
        }

        @Override
        public ConnectionResultModel[] newArray(int size) {
            return new ConnectionResultModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(Status);
        parcel.writeParcelable(bpFinderModel, i);
    }
}
