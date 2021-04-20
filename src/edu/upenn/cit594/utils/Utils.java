package edu.upenn.cit594.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static Date getDateTime(String string) {
        Date dateTime = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        try {
            dateTime = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime;
    }
}
