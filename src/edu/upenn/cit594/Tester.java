package edu.upenn.cit594;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;

import java.util.List;

public class Tester {
    public static void main(String[] args) {
        DelimitedFileReader reader = new DelimitedFileReader("properties.csv", 1);
        PropertyCsvConverter converter = new PropertyCsvConverter(reader);
        List<Property> properties = converter.convert();
        System.out.println(properties.get(32));
        
        DelimitedFileReader reader_edit = new DelimitedFileReader("edit_properties.csv", 1);
        PropertyCsvConverter converter_edit = new PropertyCsvConverter(reader_edit);
        List<Property> properties_edit = converter_edit.convert();
        System.out.println(properties_edit.get(32));
        
        DelimitedFileReader violationReader = new DelimitedFileReader("parking.csv", 0);
        ParkingViolationCsvConverter violationConverter = new ParkingViolationCsvConverter(violationReader);
        List<ParkingViolation> violations = violationConverter.convert();
        System.out.println(violations.get(0));
    }
}
