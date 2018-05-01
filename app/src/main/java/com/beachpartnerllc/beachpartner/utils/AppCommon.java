package com.beachpartnerllc.beachpartner.utils;

/**
 * Created by seq-kala on 28/4/18.
 */

public class AppCommon {
    public static String MOBILE_REGEX_PATTERN = "^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$";
    public static String VALID_STRING = "^[a-zA-Z]+$";
    public static boolean FORM_VALID = true;
    public static boolean FORM_INVALID = false;

    public static boolean isValidMobileNumber(String mobile_number){
        if (mobile_number.length() != 10){
            return false;
        }else {
            if (sumFromString(mobile_number) == 0){
                return false;
            }else {
                return true;
            }
        }
    }
    static int sumFromString(String s){
        int sum = 0;
        for(int i = 0; i < s.length() ; i++){
            if( Character.isDigit(s.charAt(i)) ){
                sum = sum + Character.getNumericValue(s.charAt(i));
            }
        }
        return sum;
    }
}
