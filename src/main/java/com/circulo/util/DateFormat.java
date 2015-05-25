package com.circulo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tfulton on 5/24/15.
 */
public class DateFormat {

    private static SimpleDateFormat iso8061;

    static {

        iso8061 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        iso8061.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static String getIso8061(Date date) {
        if(date == null) {
            return null;
        }
        return iso8061.format(date);
    }
}
