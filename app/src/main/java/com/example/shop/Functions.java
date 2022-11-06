package com.example.shop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    public static boolean isValidEmailAddress(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
