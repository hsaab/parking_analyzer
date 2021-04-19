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
        String stringValue = values.get(headers.indexOf("market_value"));
        String stringLivableArea = values.get(headers.indexOf("total_livable_area"));
        String stringZipcode = values.get(headers.indexOf("zip_code"));
        
        Double value = null;
        try {
            value = Double.parseDouble(stringValue);
        } catch (NumberFormatException e) {
            // do nothing
        }
        
        Double area = null;
        try {
            area = Double.parseDouble(stringLivableArea);
        } catch (NumberFormatException e) {
            // do nothing
        }
    
        String zipcode = stringZipcode;
        if (zipcode.length() > 5) {
            zipcode = stringZipcode.substring(0, 5);
        }
        
        return new Property(value, area, zipcode);
    }
}
