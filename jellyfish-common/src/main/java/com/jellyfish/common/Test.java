package com.jellyfish.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        BigDecimal decimal=new BigDecimal(0);
        String effectiveDate="2019-09-11";
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
        DateTime dateTime= DateTime.parse(effectiveDate,format);

        for (int i=0;i<10;i++) {
           decimal=decimal.add(new BigDecimal(10));
            dateTime=dateTime.plusMonths(1);
            System.out.println(dateTime.toString());
        }
        System.out.println(decimal);
        System.out.println(dateTime);
    }
}
