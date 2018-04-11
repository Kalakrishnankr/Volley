package com.goldemo.beachpartner.connections;

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
    public void saveUserDetails(String user_id,String userType,String subscriptions){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId",user_id);
        editor.putString("userType",userType);
        editor.putString("subscriptions",subscriptions);
        editor.commit();
    }


    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
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
