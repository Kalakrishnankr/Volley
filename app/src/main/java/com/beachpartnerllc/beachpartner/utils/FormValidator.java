package com.beachpartnerllc.beachpartner.utils;

import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

/**
 * Created by seqato on 22/04/18.
 */

public class FormValidator {


    private boolean validateAll(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                return false;
            }
        }
        return true;
    }
    private boolean valiadteEmail(EditText fields){
        return true;
    }

    public String validateEditText(EditText fields){
        String validationResponse = "failed";

        if(fields.getText().toString().trim().length() > 0){

            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(fields.getText().toString().trim());
            boolean check = m.find();

            if(check){
                validationResponse = "special character";
            }else {
                validationResponse = "valid";
            }

        }else{
            validationResponse = "failed";
        }

        return validationResponse;
    }


    public boolean emailValidator(EditText fields){

        boolean validEmail=false;
        if(fields.getText().toString().trim().length() > 0){

            // String regex = "^(.+)@(.+)$";
            String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher((CharSequence)fields.getText().toString().trim());
            boolean check = m.matches();

            if(check){
                validEmail = true;
            }else {
                validEmail = false;
            }

        }else{
            validEmail = false;
        }
        return validEmail;
    }
}
