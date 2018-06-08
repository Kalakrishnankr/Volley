package com.beachpartnerllc.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by allu on 1/5/18 12:07 PM.
 *
 * @author Ameen Maheen
 * Happy Coding
 */
public class UserProfileModel implements Parcelable{
    @SerializedName("height")
    @Expose
    private String height;

    @SerializedName("cbvaPlayerNumber")
    @Expose
    private String cbvaPlayerNumber;

    @SerializedName("cbvaFirstName")
    @Expose
    private String cbvaFirstName;

    @SerializedName("cbvaLastName")
    @Expose
    private String cbvaLastName;

    @SerializedName("toursPlayedIn")
    @Expose
    private String toursPlayedIn;

    @SerializedName("totalPoints")
    @Expose
    private String totalPoints;

    @SerializedName("highSchoolAttended")
    @Expose
    private String highSchoolAttended;

    @SerializedName("collageClub")
    @Expose
    private String collageClub;

    @SerializedName("indoorClubPlayed")
    @Expose
    private String indoorClubPlayed;

    @SerializedName("collegeIndoor")
    @Expose
    private String collegeIndoor;

    @SerializedName("collegeBeach")
    @Expose
    private String collegeBeach;

    @SerializedName("tournamentLevelInterest")
    @Expose
    private String tournamentLevelInterest;

    @SerializedName("highestTourRatingEarned")
    @Expose
    private String highestTourRatingEarned;

    @SerializedName("experience")
    @Expose
    private String experience;

    @SerializedName("courtSidePreference")
    @Expose
    private String courtSidePreference;

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("willingToTravel")
    @Expose
    private String willingToTravel;

    @SerializedName("usaVolleyballRanking")
    @Expose
    private String usaVolleyballRanking;

    @SerializedName("topFinishes")
    String topFinishes ="";

    @SerializedName("collage")
    @Expose
    private String collage;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("yearsRunning")
    @Expose
    private String yearsRunning;

    @SerializedName("numOfAthlets")
    @Expose
    private String numOfAthlets;

    @SerializedName("programsOffered")
    @Expose
    private String programsOffered;

    @SerializedName("division")
    @Expose
    private String division;

    @SerializedName("fundingStatus")
    @Expose
    private String fundingStatus;

    @SerializedName("shareAthlets")
    @Expose
    private String shareAthlets;


    protected UserProfileModel(Parcel in) {
        height  = in.readString();
        cbvaPlayerNumber  = in.readString();
        cbvaFirstName =  in.readString();
        cbvaLastName   = in.readString();
        toursPlayedIn  =   in.readString();
        totalPoints  = in.readString();
        highSchoolAttended  = in.readString();
        collageClub  = in.readString();
        indoorClubPlayed  = in.readString();
        collegeIndoor  = in.readString();
        collegeBeach  = in.readString();
        tournamentLevelInterest   = in.readString();
        highestTourRatingEarned  = in.readString();
        experience  = in.readString();
        courtSidePreference  = in.readString();
        position  = in.readString();
        willingToTravel  = in.readString();
        usaVolleyballRanking  = in.readString();
        topFinishes  = in.readString();
        collage  = in.readString();
        description  = in.readString();
        yearsRunning  = in.readString();
        numOfAthlets  = in.readString();
        programsOffered  = in.readString();
        division  = in.readString();
        fundingStatus  = in.readString();
        shareAthlets  = in.readString();

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

    public String getUpf_height() {
        return height;
    }

    public String getUpf_cbvaPlayerNumber() {
        return cbvaPlayerNumber;
    }

    public String getUpf_cbvaFirstName() {
        return cbvaFirstName;
    }

    public String getUpf_cbvaLastName() {
        return cbvaLastName;
    }

    public String getUpf_toursPlayedIn() {
        return toursPlayedIn;
    }
    public String getUpf_totalPoints() {
        return totalPoints;
    }

    public String getUpf_highSchoolAttended() {
        return highSchoolAttended;
    }

    public String getUpf_collageClub() {
        return collageClub;
    }

    public String getUpf_indoorClubPlayed() {
        return indoorClubPlayed;
    }

    public String getUpf_collegeIndoor() {
        return collegeIndoor;
    }

    public String getUpf_collegeBeach() {
        return collegeBeach;
    }

    public String getUpf_tournamentLevelInterest() {
        return tournamentLevelInterest;
    }

    public String getUpf_highestTourRatingEarned() {
        return highestTourRatingEarned;
    }

    public String getUpf_experience() {
        return experience;
    }

    public String getUpf_courtSidePreference() {
        return courtSidePreference;
    }

    public String getUpf_position() {
        return position;
    }

    public String getUpf_willingToTravel() {
        return willingToTravel;
    }

    public String getUpf_usaVolleyballRanking() {
        return usaVolleyballRanking;
    }

    public String getUpf_topFinishes() {
        return topFinishes;
    }

    public String getUpf_collage() {
        return collage;
    }

    public String getUpf_description() {
        return description;
    }

    public String getUpf_yearsRunning() {
        return yearsRunning;
    }

    public String getUpf_numOfAthlets() {
        return numOfAthlets;
    }

    public String getUpf_programsOffered() {
        return programsOffered;
    }

    public String getUpf_division() {
        return division;
    }
    public String getUpf_fundingStatus() {
        return fundingStatus;
    }
    public String getUpf_shareAthlets() {
        return shareAthlets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(height);
        parcel.writeString(cbvaPlayerNumber);
        parcel.writeString(cbvaFirstName);
        parcel.writeString(cbvaLastName);
        parcel.writeString(toursPlayedIn);
        parcel.writeString(totalPoints);
        parcel.writeString(highSchoolAttended);
        parcel.writeString(collageClub);
        parcel.writeString(indoorClubPlayed);
        parcel.writeString(collegeIndoor);
        parcel.writeString(collegeBeach);
        parcel.writeString(tournamentLevelInterest);
        parcel.writeString(highestTourRatingEarned);
        parcel.writeString(experience);
        parcel.writeString(courtSidePreference);
        parcel.writeString(position);
        parcel.writeString(willingToTravel);
        parcel.writeString(usaVolleyballRanking);
        parcel.writeString(topFinishes);
        parcel.writeString(collage);
        parcel.writeString(description);
        parcel.writeString(yearsRunning);
        parcel.writeString(numOfAthlets);
        parcel.writeString(programsOffered);
        parcel.writeString(division);
        parcel.writeString(fundingStatus);
        parcel.writeString(shareAthlets);

    }
}
