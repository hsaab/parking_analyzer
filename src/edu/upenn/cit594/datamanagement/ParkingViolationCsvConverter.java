package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;

import java.util.List;

public class ParkingViolationCsvConverter extends DelimitedFileConverter<ParkingViolation>{
    
    public ParkingViolationCsvConverter(DelimitedFileReader reader) {
        super(reader, ",");
    }
    
    //TODO: dealing with a parse integer that fails
    //TODO: dealing with a blank value at the end of a line that changes
    // the size of values (and thus something like .get(6) will cause ArrayIndexOutOfBounds)
    @Override
    public ParkingViolation convert(String toParse) {
        List<String> values = super.splitLine(toParse);
        String reason = values.get(2);
        if (values.size() < 7) return null; // temporary line to prevent ArrayIndexOutOfBounds
        String stringZipcode = values.get(6);
        if (stringZipcode.equals("")) return null;
        int zipcode = Integer.parseInt(stringZipcode);
        return new ParkingViolation(zipcode, reason);
    }
}
