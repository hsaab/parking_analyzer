package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.utils.Utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Reads in a delimited file of parking violation values and converts to a List of ParkingViolation objects
public class ParkingViolationDelimitedFileReader extends DelimitedFileReader<List<ParkingViolation>> {
    /**
     * Constructor
     * @param fileName the filename to read area data from
     * @param hasHeaders if the file has headers
     * @param delimitedBy the delimiter between values
     * @throws FileNotFoundException if the file cannot be found or opened
     */
    public ParkingViolationDelimitedFileReader(String fileName, boolean hasHeaders, String delimitBy) throws FileNotFoundException {
        super(fileName, hasHeaders, delimitBy);
    }
    
    @Override
    public void initializeDataStore() {
        dataStore = new DataStore<>(new ArrayList<>());
    }

    @Override
    public void updateDataStore(List<String> dataList) {
        ParkingViolation parkingViolation = createParkingViolation(dataList);

        dataStore.getData().add(parkingViolation);
    }

    @Override
    public void setHeaderIndices() {
        // no indices to set for this reader
    }
    
    /**
     * Parses the provided dataList into a ParkingViolation.
     * @param dataList the list of values with ParkingViolation data
     * @return the ParkingViolation being parsed from this data
     */
    public ParkingViolation createParkingViolation(List<String> dataList) {
        Date date = Utils.getDateTime(dataList.get(0));
        double fine = Utils.extractDoubleValueFromList(dataList, 1);
        String violation = Utils.extractStringValueFromList(dataList, 2);
        String plateId = Utils.extractStringValueFromList(dataList, 3);
        String state = Utils.extractStringValueFromList(dataList, 4);
        String ticketNumber = Utils.extractStringValueFromList(dataList, 5);
        String zipcode = Utils.extractZipcodeValueFromList(dataList, 6);
        
        return new ParkingViolation(date, fine, violation, plateId, state, ticketNumber, zipcode);
    }
}
