package edu.upenn.cit594.utils;

import org.json.simple.JSONObject;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
        } catch(Exception e) {
            return Double.NaN;
        }
    }

    public static String extractStringValueFromList(List<String> dataList, int index) {
        try {
            if(Utils.isExistingValue(dataList, index)) {
                return dataList.get(index);
            } else {
                return "";
            }
        } catch(Exception e) {
            return "";
        }
    }

    public static String extractZipcodeValueFromList(List<String> dataList, int index) {
        if(Utils.isExistingValue(dataList, index)) {
            String zipcode = dataList.get(index);

            return extractZipCodeValue(zipcode);
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

    public static String extractZipcodeValueFromJSON(JSONObject object) {
        try {
            String zipcode = object.get("zip_code").toString();

            return extractZipCodeValue(zipcode);
        } catch(Exception e) {
            return "";
        }
    }

    public static double extractDoubleFromJSON(JSONObject object, String propertyName) {
        try {
            return Double.parseDouble(object.get(propertyName).toString());
        } catch(Exception e) {
            return Double.NaN;
        }
    }

    public static String extractStringFromJSON(JSONObject object, String propertyName) {
        try {
            return object.get(propertyName).toString();
        } catch(Exception e) {
            return "";
        }
    }
    
    /**
     * Truncates provided value to String representation with provided number of decimalPlaces.
     * Displays trailing 0s, even if not explicitly provided in value. Omits leading 0's.
     * @param value value to truncate
     * @param decimalPlaces number of decimal places to include
     * @return the string representation of value with truncated decimals
     */
    public static String truncateDecimalsInValue(double value, int decimalPlaces) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        if (decimalPlaces < 0) throw new IllegalArgumentException("decimalPlaces must be non-negative: " + decimalPlaces);
        StringBuilder pattern = new StringBuilder("0");
        if (decimalPlaces > 0)  pattern.append(".");
        for (int i = 1; i <= decimalPlaces; i++) {
            pattern.append("0");
        }
        
        decimalFormat.applyPattern(pattern.toString()); // update decimalFormat to use pattern that was just built
        return  decimalFormat.format(value);
    }
    
    /**
     * Determines if the input is only comprised of digits.
     * Spaces are considered non-digits.
     * @param input the input to test
     * @return true if all characters are digits, false if otherwise
     */
    public static boolean isOnlyDigits(String input) {
        for (int i = 0; i < input.toCharArray().length; i++) {
            char character = input.charAt(i);
            if (!Character.isDigit(character)) {
                return false;
            }
        }
        return true;
    }
}
