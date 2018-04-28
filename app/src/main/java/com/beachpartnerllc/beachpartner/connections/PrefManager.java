package com.beachpartnerllc.beachpartner.connections;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by seq-kala on 19/3/18.
 */

public class PrefManager {
    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(String email, String password,String id_token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.putString("Token",id_token);
        editor.commit();
    }

    //Save logged userid
    public void saveUserDetails(String user_id,String userType,String userName,String userPic,String userLocation,String subscriptions ){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId",user_id);
        editor.putString("userType",userType);
        editor.putString("subscriptions",subscriptions);
        editor.putString("userName",userName);
        editor.putString("userProfilePic",userPic);
        editor.putString("userLocation",userLocation);
        editor.commit();
    }

    public void saveRegistrationStatus(String status){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("registrationStatus",status);
        editor.commit();
    }
    public void saveTips(boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("WelcomeTips", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("tips",value);
        editor.commit();
    }

    public String getSubscriptionType(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("subscriptions", "");
    }
    public void saveSettingData(String location,String gender,Boolean isCoach, int minAge,int maxAge){
        SharedPreferences sharedPreferences = context.getSharedPreferences("settingsData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("location",location);
        editor.putInt("minAge",minAge);
        //editor.putString("subscriptions",subscriptions);
        editor.putInt("maxAge",maxAge);
        editor.putString("gender",gender);
        editor.putBoolean("isCoachActive",isCoach);
        editor.commit();
    }
    public void saveReverseCardId(String rvcId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("ReverseCardID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cardID",rvcId);
        editor.commit();
    }

    public String getReverseCardID(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("ReverseCardID", Context.MODE_PRIVATE);
        return sharedPreferences.getString("cardID", "");
    }

    //saveStripValue is "true" at first time bluebp strip loading //it remains false at filter procedure search
    public void saveStripsValue(Boolean isStatus){
        SharedPreferences sharedPreferences = context.getSharedPreferences("StripStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isStripStatus",isStatus);
        editor.commit();
    }

    public Boolean getStripValue(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("StripStatus", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isStripStatus",false);
    }


    public SharedPreferences getSettingsData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("settingsData", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public String getLocation(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userLocation", "");
    }


    public Boolean getTips(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("WelcomeTips", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("tips", false);
    }
    public String getProfilePic (){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userProfilePic", "");
    }

    public String getRegistrationStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("registrationStatus", "");
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }

    public String getPassword (){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Password", "");
    }
    public String getUserName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", "");
    }

    public String getUserId(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId", "");
    }

    public String getToken(){
        SharedPreferences  preferences = context.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        return preferences.getString("Token","");
    }
    public String getSubscription(){
        SharedPreferences  preferences = context.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        return preferences.getString("subscriptions","");
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }

    public void saveUserType(String userType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userType",userType);
        editor.commit();
    }
    public void savePageno(int pageno) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BlueBPpageNo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pageNo",pageno);
        editor.commit();
    }

    public int getPageno(){
        SharedPreferences  preferences = context.getSharedPreferences("BlueBPpageNo",Context.MODE_PRIVATE);
        return preferences.getInt("pageNo",0);
    }

    public String getUserType(){
        SharedPreferences  preferences = context.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        return preferences.getString("userType","");
    }



}
