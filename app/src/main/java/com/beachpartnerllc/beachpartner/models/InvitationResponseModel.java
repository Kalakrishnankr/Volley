package com.beachpartnerllc.beachpartner.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by seq-kala on 15/5/18.
 */

public class InvitationResponseModel {
    @SerializedName("received")
    List<EventDetailsModel>invitationReceivedModels;
    @SerializedName("send")
    List<EventDetailsModel>invitationSendModels;
}
