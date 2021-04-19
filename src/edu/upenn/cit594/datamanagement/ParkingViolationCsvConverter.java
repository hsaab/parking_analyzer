package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;

import java.util.List;

public class ParkingViolationCsvConverter extends DelimitedFileConverter<ParkingViolation>{
    
    public ParkingViolationCsvConverter(DelimitedFileReader reader) {
        super(reader, ",");
    }
    
    //TODO: dealing with a blank value at the end of a line that changes
    // the size of values (and thus something like .get(6) will cause ArrayIndexOutOfBounds)
    @Override
    public ParkingViolation convert(String toParse) {
        List<String> values = super.splitLine(toParse);
        String stringFine = values.get(1);
        String stringState = values.get(4);
        String stringZipcode = null;
        if (values.size() > 6) {
            stringZipcode = values.get(6);
        }
    
        Double fine = null;
        try {
            fine = Double.parseDouble(stringFine);
        } catch (NumberFormatException e) {
            // do nothing
        }
    
        String zipcode = stringZipcode;
        if (zipcode != null && zipcode.length() > 5) {
            zipcode = stringZipcode.substring(0, 5);
        }
        
       
        return new ParkingViolation(fine, stringState, zipcode);
    }
}
