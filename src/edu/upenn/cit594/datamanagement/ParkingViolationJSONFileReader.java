package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.utils.Utils;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingViolationJSONFileReader extends JSONFileReader<List<ParkingViolation>> {
    public ParkingViolationJSONFileReader(String fileName) {
        super(fileName);
    }
    
    @Override
    public void initializeDataStore() {
        dataStore = new DataStore<>(new ArrayList<>());
    }

    @Override
    public void updateDataStore(JSONObject rawParkingViolation) {
        ParkingViolation convertedObject = createParkingViolation(rawParkingViolation);

        dataStore.getData().add(convertedObject);
    }

    public ParkingViolation createParkingViolation(JSONObject rawObject) {
        Date date = Utils.getDateTime(rawObject.get("date").toString());
        double fine = Utils.extractDoubleFromJSON(rawObject, "fine");
        String violation = Utils.extractStringFromJSON(rawObject, "violation");
        String plateId = Utils.extractStringFromJSON(rawObject, "plate_id");
        String state = Utils.extractStringFromJSON(rawObject, "state");
        String ticketNumber = Utils.extractStringFromJSON(rawObject, "ticket_number");
        String zipcode = Utils.extractZipcodeValueFromJSON(rawObject);

        return new ParkingViolation(date, fine, violation, plateId, state, ticketNumber, zipcode);
    }
}
