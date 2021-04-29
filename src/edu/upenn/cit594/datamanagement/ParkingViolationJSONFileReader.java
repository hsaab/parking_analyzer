package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.utils.Utils;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Reads in a JSON file of parking violation values and converts to a List of ParkingViolation objects
public class ParkingViolationJSONFileReader extends JSONFileReader<List<ParkingViolation>> {
    public ParkingViolationJSONFileReader(String fileName) throws FileNotFoundException {
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
    
    /**
     * Parses the provided dataList into a ParkingViolation.
     * @param rawObject the jsonObject representing the ParkingViolation
     * @return the ParkingViolation being parsed from this data
     */
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
