package com.beachpartnerllc.beachpartner.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allu on 29/4/18 12:19 PM.
 *
 * @author Ameen Maheen
 * Happy Coding
 */
public class AppConstants {


    public static final String POSITION = "cPosition";
    public static final String BLUE_BP_LIST = "bluebplist";
    public static final String BP_PROFILE = "bpProfile";
    public static final String HI_FI_LIST = "hifiList";
    public static final String NO_LIKES_LIST = "noLikeslist";
    public static final String USER_TYPE_ATHLETE = "Athlete";
    public static final String USER_TYPE_COACH = "Coach";
    public static final String CHAT_USER = "chatUser";
    public static final String ID = "id";
    public static final String EVENT_OBJECT = "eventObject";
    public static final String EVENT_DETAIL = "event_clicked";
    public static final String REQUEST_SEND_COUNT = "sendCount";
    public static String USER_DETAIL = "user_clicked";

    public static final String SUBSCIPTION_TYPE_ONE = "FREE";
    public static final String SUBSCIPTION_TYPE_TWO = "LITE";
    public static final String SUBSCIPTION_TYPE_THREE = "STANDARD";
    public static final String SUBSCIPTION_TYPE_FOUR = "RECRUITING";


    public static final String BENIFIT_CODE_B1 = "Swipes";
    public static final String BENIFIT_CODE_B2 = "Swipe visibility";
    public static final String BENIFIT_CODE_B3 = "High - fives";
    public static final String BENIFIT_CODE_B4 = "Connections";
    public static final String BENIFIT_CODE_B5 = "Chat";
    public static final String BENIFIT_CODE_B6 = "Master calendar";
    public static final String BENIFIT_CODE_B7 = "My calendar";
    public static final String BENIFIT_CODE_B8 = "My upcoming tournaments";
    public static final String BENIFIT_CODE_B9 = "Event invitation";
    public static final String BENIFIT_CODE_B10 = "Event search";
    public static final String BENIFIT_CODE_B11 = "Court notification";
    public static final String BENIFIT_CODE_B12 = "Partner requests";
    public static final String BENIFIT_CODE_B13 = "Blue BP profile boost";
    public static final String BENIFIT_CODE_B14 = "Visibility to players who “like” you";
    public static final String BENIFIT_CODE_B15 = "Visibility to coaches who “like” you";
    public static final String BENIFIT_CODE_B16 = "Passport search location change";
    public static final String BENIFIT_CODE_B17 = "Undo last swipe option";


    //StateLists
    public static List<String> getstatelist() {
        List<String> stateList = new ArrayList<>();
        stateList.add("Please Select");
        stateList.add("Alabama");
        stateList.add("Alaska");
        stateList.add("Arizona");
        stateList.add("Arkansas");
        stateList.add("California");
        stateList.add("Colorado");
        stateList.add("Connecticut");
        stateList.add("Delaware");
        stateList.add("Florida");
        stateList.add("Georgia");
        stateList.add("Hawaii");
        stateList.add("Idaho");
        stateList.add("Illinois");
        stateList.add("Indiana");
        stateList.add("Iowa");
        stateList.add("Kansas");
        stateList.add("Kentucky");
        stateList.add("Louisiana");
        stateList.add("Maine");
        stateList.add("Maryland");
        stateList.add("Massachusetts");
        stateList.add("Michigan");
        stateList.add("Minnesota");
        stateList.add("Mississippi");
        stateList.add("Missouri");
        stateList.add("Montana");
        stateList.add("Nebraska");
        stateList.add("Nevada");
        stateList.add("New Hampshire");
        stateList.add("New Jersey");
        stateList.add("New Mexico");
        stateList.add("New York");
        stateList.add("North Carolina");
        stateList.add("North Dakota");
        stateList.add("Ohio");
        stateList.add("Oklahoma");
        stateList.add("Oregon");
        stateList.add("Pennsylvania");
        stateList.add("Rhode Island");
        stateList.add("South Carolina");
        stateList.add("South Dakota");
        stateList.add("Tennessee");
        stateList.add("Texas");
        stateList.add("Utah");
        stateList.add("Vermont");
        stateList.add("Virginia");
        stateList.add("Washington");
        stateList.add("West Virginia");
        stateList.add("Wisconsin");
        stateList.add("Wyoming");
        return stateList;
    }

    //List regions
    public static List<String> getregionList() {
        List<String> regionList = new ArrayList<>();
        regionList.add("Please Select");
        regionList.add("Alaska Region");
        regionList.add("Aloha Region");
        regionList.add("Arizona Region");
        regionList.add("Badger Region");
        regionList.add("Bayou Region");
        regionList.add("Carolina Region");
        regionList.add("Chesapeake Region");
        regionList.add("Columbia Empire Region");
        regionList.add("Delta Region");
        regionList.add("Evergreen Region");
        regionList.add("Florida Region");
        regionList.add("Garden Empire Region");
        regionList.add("Gateway Region");
        regionList.add("Great Lakes Region");
        regionList.add("Great Plains Region");
        regionList.add("Gulf Coast Region");
        regionList.add("Heart of America Region");
        regionList.add("Hoosier Region");
        regionList.add("Intermountain Region");
        regionList.add("Iowa Region");
        regionList.add("Iroquois Empire Region");
        regionList.add("Keystone Region");
        regionList.add("Lakeshore Region");
        regionList.add("Lone Star Region");
        regionList.add("Moku O Keawe Region");
        regionList.add("New England Region");
        regionList.add("North Country Region");
        regionList.add("North Texas Region");
        regionList.add("Northern California Region");
        regionList.add("Ohio Valley Region");
        regionList.add("Oklahoma Region");
        regionList.add("Old Dominion Region");
        regionList.add("Palmetto Region");
        regionList.add("Pioneer Region");
        regionList.add("Puget Sound Region");
        regionList.add("Rocky Mountain Region");
        regionList.add("Southern Region");
        regionList.add("Southern California Region");
        regionList.add("Sun Country Region");
        regionList.add("Western Empire Region");
        return regionList;
    }

    //list experience levels
    public static List<String> getExperienceLevels() {
        List<String> experience = new ArrayList<>();
        experience.add("Please Select");
        experience.add("“Newbie” [New to the Game]");
        experience.add("1-2 years [Some Indoor/Beach Experience]");
        experience.add("2-3 years [Beach Club/Tournament Experience]");
        experience.add("3-4 years [Experienced Tournament Player]");
        experience.add("More than 4 years");
        return experience;
    }

    //Court side preference list
    public static List<String> getCourtSidePreference() {
        List<String> courtPref = new ArrayList<>();
        courtPref.add("Please Select");
        courtPref.add("Left Side");
        courtPref.add("Right Side");
        courtPref.add("No Preference");
        return courtPref;
    }

    //Player postionList
    public static List<String> getPlayerPosition() {
        List<String> position = new ArrayList<>();
        position.add("Please Select");
        position.add("Primary Blocker");
        position.add("Primary Defender");
        position.add("Split Block/Defense");
        return position;
    }

    //Tournament level interested
    public static List<String> getTournamentLevels() {
        List<String> tournamentInterest = new ArrayList<>();
        tournamentInterest.add("Please Select");
        tournamentInterest.add("Novice/Social");
        tournamentInterest.add("Unrated");
        tournamentInterest.add("B");
        tournamentInterest.add("A");
        tournamentInterest.add("AA");
        tournamentInterest.add("AAA");
        tournamentInterest.add("Pro");
        return tournamentInterest;
    }

    //Tour rating List
    public static List<String> getTournamentRating() {
        List<String> rating = new ArrayList<>();
        rating.add("Please Select");
        rating.add("PRO");
        rating.add("Open Or AAA");
        rating.add("AA");
        rating.add("A");
        rating.add("BB");
        rating.add("B");
        rating.add("C Or Novice");
        rating.add("Unrated");
        return rating;
    }

    //Willing to travel list
    public static List<String> getTravelList() {
        List<String> distance = new ArrayList<>();
        distance.add("Please Select");
        distance.add("Not Willing");
        distance.add("Up to 25 miles");
        distance.add("Up to 50 miles");
        distance.add("Up to 100 miles");
        distance.add("Up to 250 miles");
        distance.add("Up to 500 miles");
        distance.add("Nationwide");
        distance.add("International");
        return distance;
    }

    public static List<String> getHeightList() {
        List<String> height = new ArrayList<>();
        height.add("Please Select");
        height.add("4' 10\"");
        height.add("4' 11\"");
        height.add("5' 0\"");
        height.add("5' 1\"");
        height.add("5' 2\"");
        height.add("5' 3\"");
        height.add("5' 4\"");
        height.add("5' 5\"");
        height.add("5' 6\"");
        height.add("5' 7\"");
        height.add("5' 8\"");
        height.add("5' 9\"");
        height.add("5' 10\"");
        height.add("5' 11\"");
        height.add("6' 0\"");
        height.add("6' 1\"");
        height.add("6' 2\"");
        height.add("6' 3\"");
        height.add("6' 4\"");
        height.add("6' 5\"");
        height.add("6' 6\"");
        height.add("6' 7\"");
        height.add("6' 8\"");
        height.add("6' 9\"");
        height.add("6' 10\"");
        height.add("6' 11\"");
        height.add("7' 0\"");
        return height;
    }

    //
    public static List<String> getJuniorSubEvents() {
        List<String> juniorSubEvent = new ArrayList<>();
        juniorSubEvent.add("Please Select");
        juniorSubEvent.add("10U");
        juniorSubEvent.add("12U");
        juniorSubEvent.add("13U");
        juniorSubEvent.add("14U");
        juniorSubEvent.add("15U");
        juniorSubEvent.add("16U");
        juniorSubEvent.add("17U");
        juniorSubEvent.add("18U");
        return juniorSubEvent;
    }

    public static List<String> getAdultSubEvents() {
        List<String> adultSubEvent = new ArrayList<>();
        adultSubEvent.add("Please Select");
        adultSubEvent.add("Unrated");
        adultSubEvent.add("B");
        adultSubEvent.add("A");
        adultSubEvent.add("AA");
        adultSubEvent.add("AAA");
        adultSubEvent.add("Open");
        adultSubEvent.add("CoEd");
        adultSubEvent.add("CoEd Unrated");
        adultSubEvent.add("CoEd B");
        adultSubEvent.add("CoEd A");
        adultSubEvent.add("CoEd AA");
        adultSubEvent.add("CoEd AAA");
        adultSubEvent.add("CoEd Open");
        return adultSubEvent;
    }
    //Event Types
    public static List<String> getEventTypes(){
        List<String>eventTypes = new ArrayList<>();
        eventTypes.add("Please Select");
        eventTypes.add("Junior");
        eventTypes.add("College Showcase");
        eventTypes.add("College Clinic");
        eventTypes.add("National Tournament");
        eventTypes.add("Adult");
        return eventTypes;
    }

    //
}