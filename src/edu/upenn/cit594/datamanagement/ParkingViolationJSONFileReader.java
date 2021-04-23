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
        Double fine = Double.parseDouble(rawObject.get("fine").toString());
        String violation = rawObject.get("violation").toString();
        String plateId = rawObject.get("plate_id").toString();
        String state = rawObject.get("state").toString();
        String ticketNumber = rawObject.get("ticket_number").toString();
        String zipcode = rawObject.get("zip_code").toString();

        return new ParkingViolation(date, fine, violation, plateId, state, ticketNumber, zipcode);
    }
}
