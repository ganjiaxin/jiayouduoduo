package com.hyk.code.common.utils;

import org.apache.fop.util.LogUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/9 14:24
 * @Description:
 */
public class VliateUser {


    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    public static boolean ValidatePassword(String password) {
       // String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        if (password.length() > 16 || password.length() < 8) {
            return false;
        } else {
//            Pattern p = Pattern.compile(regex);
//            Matcher m = p.matcher(password);
//            boolean isMatch = m.matches();
//            return isMatch;


            //String reg="^\\d+$"
             return true;
        }
    }


    public static boolean ValidateAllNum(String oilCard) {
        String regex = "^\\d+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(oilCard);
        boolean isMatch = m.matches();
        return isMatch;
    }

    public static void main(String args[]) {
        System.out.println(isPhone("17730209007"));
        System.out.println(ValidatePassword("123456"));
        String oilCard="1";
        System.out.println(ValidateAllNum(oilCard));
    }
}
