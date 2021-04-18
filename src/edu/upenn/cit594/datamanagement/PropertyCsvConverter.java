package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Property;

import java.util.List;

public class PropertyCsvConverter extends DelimitedFileConverter<Property> {
    
    public PropertyCsvConverter(DelimitedFileReader reader) {
        super(reader, ",");
    }
    
    public PropertyCsvConverter(DelimitedFileReader reader, String delimiter) {
        super(reader, delimiter);
    }
    
    //TODO: reducing duplicate code by creating method for getting string value by passing in header
    //TODO: reducing duplicate code by creating method for getting string values of multiple values at once
    //TODO: testing if indexOf returns -1
    @Override
    public Property convert(String toParse) {
        List<String> values = super.splitLine(toParse);
        String location = values.get(headers.indexOf("location"));
        String stringNumberRooms = values.get(headers.indexOf("number_of_rooms"));
        if (stringNumberRooms.equals("")) return null;
        int numRooms = Integer.parseInt(stringNumberRooms);
        return new Property(location, numRooms);
    }
}
