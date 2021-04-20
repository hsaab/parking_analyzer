package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.utils.Utils;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ParkingViolationJSONReader extends JSONReader<List> {

    public ParkingViolationJSONReader(String fileName) {
        super(fileName);
    }

    @Override
    public List<ParkingViolation> createDataStore(Iterator iterator) {
        List<ParkingViolation> parkingViolations = new ArrayList<>();

        while (iterator.hasNext()) {
            JSONObject rawParkingViolation = (JSONObject) iterator.next();

            ParkingViolation convertedObject = createParkingViolation(rawParkingViolation);

            parkingViolations.add(convertedObject);
        }

        return parkingViolations;
    }

    public ParkingViolation createParkingViolation(JSONObject rawObject) {

        Date date = Utils.getDateTime((String) rawObject.get("date"));
        Double fine = Double.parseDouble((String) rawObject.get("fine"));
        String plateId = (String) rawObject.get("plate_id");
        String violation = (String) rawObject.get("violation");
        String state = (String) rawObject.get("state");
        String ticketNumber = (String) rawObject.get("ticket_number");
        String zipcode = (String) rawObject.get("zip_code");

        return new ParkingViolation(date, fine, plateId, violation, state, ticketNumber, zipcode);
    }
}
