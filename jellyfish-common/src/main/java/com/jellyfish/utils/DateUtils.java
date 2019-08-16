package com.jellyfish.utils;


import org.joda.time.DateTime;

public class DateUtils {

    public static String getToday(){
        return DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }
    public static String getYesterday(){
        return DateTime.now().minusDays(1).toString("yyyy-MM-dd HH:mm:ss");
    }

    public static void main(String[] args) {
        System.out.println(getToday());
        System.out.println(getYesterday());
    }
}
