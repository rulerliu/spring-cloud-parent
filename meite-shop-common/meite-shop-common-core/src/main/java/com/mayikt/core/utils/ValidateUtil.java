package com.mayikt.core.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

public class ValidateUtil {

    public static boolean isMobile(String phoneNumber){
        if(StringUtils.isBlank(phoneNumber)){
            return false;
        }
        String regexp="^1[34578]\\d{9}$";
        return Pattern.compile(regexp).matcher(phoneNumber).matches();
    }
    public static boolean isEmail(String email){
        if(StringUtils.isBlank(email)){
            return false;
        }
        String regexp="^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        return Pattern.compile(regexp).matcher(email).matches();
    }

    public static boolean isQQ(String qq){
        if(StringUtils.isBlank(qq)){
            return false;
        }
        String regexp="^[1-9][0-9]{4,14}$";
        return Pattern.compile(regexp).matcher(qq).matches();
    }

    public static boolean validCommonZjhm(String zjhm){
        if(StringUtils.isBlank(zjhm)){
            return false;
        }
        String regexp="^\\w{5,18}$";
        return Pattern.compile(regexp).matcher(zjhm).matches();
    }

}
