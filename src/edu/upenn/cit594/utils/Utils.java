package edu.upenn.cit594.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Utils {
    public static Date getDateTime(String string) {
        Date dateTime = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            dateTime = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime;
    }

    public static boolean isExistingValue(List<String> dataList, int index) {
        return !(dataList.get(index).isEmpty()) && !(dataList.get(index) == null) && !(dataList.get(index).isBlank());
    }
}
