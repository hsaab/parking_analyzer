package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;

public class ParkingViolationCsvConverter extends StringConverter<ParkingViolation>{
    public ParkingViolationCsvConverter() {
        super(null, null);
    }
    
    @Override
    public ParkingViolation convert(String toParse) {
        return null;
    }
}
