
package com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData;

import com.google.gson.annotations.SerializedName;

public class CoachProfileUpdateInputData {

    @SerializedName("userInputDto")
    private com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData.UserInputDto UserInputDto;
    @SerializedName("userProfileDto")
    private com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData.UserProfileDto UserProfileDto;

    public com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData.UserInputDto getUserInputDto() {
        return UserInputDto;
    }

    public void setUserInputDto(com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData.UserInputDto userInputDto) {
        UserInputDto = userInputDto;
    }

    public com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData.UserProfileDto getUserProfileDto() {
        return UserProfileDto;
    }

    public void setUserProfileDto(com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData.UserProfileDto userProfileDto) {
        UserProfileDto = userProfileDto;
    }

}
