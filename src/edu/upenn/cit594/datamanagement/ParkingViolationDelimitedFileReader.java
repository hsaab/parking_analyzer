package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingViolationDelimitedFileReader extends DelimitedFileReader<List<ParkingViolation>> {
    public ParkingViolationDelimitedFileReader(String fileName, boolean hasHeaders, String delimitBy) {
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
        // NO HEADERS FOR THE CSV FILE
    }

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
