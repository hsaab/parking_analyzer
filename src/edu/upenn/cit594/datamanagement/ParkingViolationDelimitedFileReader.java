package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingViolationDelimitedFileReader extends DelimitedFileReader<List<ParkingViolation>> {
    public ParkingViolationDelimitedFileReader(String fileName, boolean hasHeaders, String delimitBy) {
        super(fileName, hasHeaders, delimitBy);

        dataStore = new ArrayList<ParkingViolation>();
    }

    @Override
    public void updateDataStore(List<String> dataList) {
        ParkingViolation parkingViolation = createParkingViolation(dataList);

        dataStore.add(parkingViolation);
    }

    @Override
    public void setHeaderIndices(List<String> headerList) {
        // NO HEADERS FOR THE CSV FILE
    }

    public ParkingViolation createParkingViolation(List<String> dataList) {
        Date date = Utils.getDateTime(dataList.get(0));
        Double fine = Double.parseDouble(dataList.get(1));
        String violation = dataList.get(2);
        String plateId = dataList.get(3);
        String state = dataList.get(4);
        String ticketNumber = dataList.get(5);
        String zipcode = dataList.size() == 7 ? dataList.get(6) : null;

        return new ParkingViolation(date, fine, violation, plateId, state, ticketNumber, zipcode);
    }
}
