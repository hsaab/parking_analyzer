package edu.upenn.cit594.utils;

import org.json.simple.JSONObject;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Utilities to extract values in various formats
public class Utils {
    /**
     * Parses the provided string into a Date object
     * @param string the date in string format
     * @return the Date object parsed from provided string
     */
    public static Date getDateTime(String string) {
        Date dateTime;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            dateTime = format.parse(string);
        } catch (ParseException e) {
            return null;
        }

        return dateTime;
    }
    
    /**
     * Given a list and index, determine if there is a non-empty, non-blank, non-null value at that index.
     * @param dataList the list to search through
     * @param index the index to look at the value
     * @return true if the value at the provided index is non-null, non-empty and non-blank
     */
    public static boolean isExistingValue(List<String> dataList, int index) {
        return !(dataList.get(index).isEmpty()) && !(dataList.get(index) == null) && !(dataList.get(index).isBlank());
    }
    
    /**
     * Extract a double value from provided list of strings at provided index
     * @param dataList the list to extract value from
     * @param index the index to extract value from
     * @return the double value at the provided index in the list, NaN if cannot be parsed or index is not in list
     */
    public static double extractDoubleValueFromList(List<String> dataList, int index) {
        try {
            if (Utils.isExistingValue(dataList, index)) {
                return Double.parseDouble(dataList.get(index));
            } else {
                return Double.NaN;
            }
        } catch(Exception e) {
            return Double.NaN;
        }
    }
    
    /**
     * Extract a string value from provided list of strings at provided index
     * @param dataList the list to extract value from
     * @param index the index to extract value from
     * @return the string value at the provided index in the list, blank if index is not in list
     */
    public static String extractStringValueFromList(List<String> dataList, int index) {
        try {
            if (Utils.isExistingValue(dataList, index)) {
                return dataList.get(index);
            } else {
                return "";
            }
        } catch(Exception e) {
            return "";
        }
    }
    
    /**
     * Extract a zipcode value from provided list of strings at provided index
     * @param dataList the list to extract value from
     * @param index the index to extract value from
     * @return the zipcode value at the provided index in the list, blank if index is not in list or if zipcode is not property formatted
     */
    public static String extractZipcodeValueFromList(List<String> dataList, int index) {
        if(Utils.isExistingValue(dataList, index)) {
            String zipcode = dataList.get(index);

            return extractZipCodeValue(zipcode);
        } else {
            return "";
        }
    }
    
    /**
     * Extract a zipcode value from string
     * @param zipcode the zipcode in raw string format
     * @return the zipcode as a string cleaned up, blank string if not property formatted
     */
    public static String extractZipCodeValue(String zipcode) {
        if(zipcode.length() >= 5) {
            zipcode = zipcode.substring(0, 5);
        } else {
            return "";
        }

        if(isOnlyDigits(zipcode)) {
            return zipcode;
        } else {
            return "";
        }
    }
    
    /**
     * Extract a zipcode value from provided object
     * @param object the object to extract zipcode from
     * @return the property formatted zipcode if valid, blank string if not
     */
    public static String extractZipcodeValueFromJSON(JSONObject object) {
        try {
            String zipcode = object.get("zip_code").toString();
            return extractZipCodeValue(zipcode);
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Extract a double value from object given propertyName
     * @param object the object to extract double from
     * @param propertyName the property name within the object to extract double from
     * @return the double value if propertyName is within object and parseable as double, NaN if either is not true
     */
    public static double extractDoubleFromJSON(JSONObject object, String propertyName) {
        try {
            return Double.parseDouble(object.get(propertyName).toString());
        } catch (Exception e) {
            return Double.NaN;
        }
    }
    
    /**
     * Extract a string value from provided object given propertyName
     * @param object the object to extract string from
     * @param propertyName the property name within the object to extract from
     * @return the string if the propertyName is within object, blank string if not
     */
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
