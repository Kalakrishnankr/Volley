package com.beachpartnerllc.beachpartner.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by allu on 1/5/18 11:26 AM.
 *
 * @author Ameen Maheen
 * Happy Coding
 */
public class SwipeResultModel {

    @SerializedName("id")
    String id;
    @SerializedName("status")
    String status;
    @SerializedName("user")
    BpFinderModel  bpFinderModel;

    public BpFinderModel getBpFinderModel() {
        return bpFinderModel;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
