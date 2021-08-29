package com.uc.appsinovatifguru;

import java.util.regex.Pattern;

public class GlobalValue {
    public static final String Onboarding_Complete = "COMPLETED_ONBOARDING_PREF_NAME";
//  public static final String serverURL = "https://guru-inovatif.com/api/";
    public static final String serverURL = "http://192.168.18.8/Laravel/webinovatifguru/public/api/";

    public static boolean ValidateEmail(String email){
        Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

        if(email.isEmpty()){
            return false;
        }else{
            if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                return false;
            }else{
                return true;
            }
        }
    }

    public static boolean ValidatePassword(String password){
        Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{0,20}");

        if (password.isEmpty()){
            return false;
        }else{
            if (password.length() < 8 || password.length() > 20){
                return false;
            }else if (!PASSWORD_PATTERN.matcher(password).matches()){
                return false;
            }else{
                return true;
            }
        }
    }
}
