package edu.upenn.cit594.utils;

import org.json.simple.JSONObject;

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

    public static double extractDoubleValueFromList(List<String> dataList, int index) {
            try {
                if(Utils.isExistingValue(dataList, index)) {
                    return Double.parseDouble(dataList.get(index));
                } else {
                    return Double.NaN;
                }
            } catch(NumberFormatException e) {
                return Double.NaN;
            }
    }

    public static String extractStringValueFromList(List<String> dataList, int index) {
        if(Utils.isExistingValue(dataList, index)) {
            return dataList.get(index);
        } else {
            return "";
        }
    }

    public static String extractZipcodeValueFromList(List<String> dataList, int index) {
        if(Utils.isExistingValue(dataList, index)) {
            String zipcode = dataList.get(index);

            if(zipcode.length() > 5) {
                zipcode = zipcode.substring(0, 5);
            }

            return zipcode;
        } else {
            return "";
        }
    }

    public static String extractZipCodeValue(String zipcode) {
        if(zipcode.length() > 5) {
            zipcode = zipcode.substring(0, 5);
        }
            return zipcode;
    }
}
