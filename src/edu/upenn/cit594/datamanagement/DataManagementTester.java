package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataManagementTester {
    @Test
    void testAreaDelimitedFileReader() {
        AreaDelimitedFileReader areaDelimitedFileReader = new AreaDelimitedFileReader(
                "population.txt", false, " "
        );

        areaDelimitedFileReader.read();

        Map<String, Area> dataStore = areaDelimitedFileReader.dataStore;

        assertEquals(dataStore.size(), 48);
        assertEquals(dataStore.get("19102").population, 4705);
        assertEquals(dataStore.get("19102").zipcode, "19102");
        assertEquals(dataStore.get("19118").population, 9808);
        assertEquals(dataStore.get("19118").zipcode, "19118");
    }

    @Test
    void testParkingViolationDelimitedFileReader() {
        ParkingViolationDelimitedFileReader parkingViolationDelimitedFileReader = new ParkingViolationDelimitedFileReader(
                "parking.csv", false, ","
        );

        parkingViolationDelimitedFileReader.read();

        List<ParkingViolation> dataStore = parkingViolationDelimitedFileReader.dataStore;

        assertEquals(dataStore.size(), 25559);

        assertEquals(dataStore.get(0).date, Utils.getDateTime("2013-04-03T15:15:00Z"));
        assertEquals(dataStore.get(0).fine, 36);
        assertEquals(dataStore.get(0).violation, "METER EXPIRED CC");
        assertEquals(dataStore.get(0).plateId, "1322731");
        assertEquals(dataStore.get(0).state, "PA");
        assertEquals(dataStore.get(0).ticketNumber, "2905938");
        assertEquals(dataStore.get(0).zipcode, "19104");

        assertEquals(dataStore.get(25558).date, Utils.getDateTime("2013-12-09T10:10:00Z"));
        assertEquals(dataStore.get(25558).fine, 26);
        assertEquals(dataStore.get(25558).violation, "METER EXPIRED");
        assertEquals(dataStore.get(25558).plateId, "204335");
        assertEquals(dataStore.get(25558).state, "NJ");
        assertEquals(dataStore.get(25558).ticketNumber, "2931538");
        assertEquals(dataStore.get(25558).zipcode, "19147");
    }

    @Test
    void testPropertyDelimitedFileReader() {
        // THIS TESTS TO MAKE SURE WE CAN GET THROUGH THE ORIGINAL FILE
        PropertyDelimitedFileReader propertyDelimitedFileReader = new PropertyDelimitedFileReader(
                "properties.csv", true, ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"
        );

        propertyDelimitedFileReader.read();

        List<Property> dataStore = propertyDelimitedFileReader.dataStore;


        assertEquals(dataStore.get(0).zipcode, "191481303");
    }

    @Test
    void testPropertyDelimitedFileReaderLite() {
        // THIS TESTS TO MAKE SURE WE ARE PARSING THROUGH THE LISTS OK
        PropertyDelimitedFileReader propertyDelimitedFileReader = new PropertyDelimitedFileReader(
                "properties-test.csv", true, ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"
        );

        propertyDelimitedFileReader.read();

        List<Property> dataStore = propertyDelimitedFileReader.dataStore;

        assertEquals(dataStore.get(0).marketValue, 0.0);
        assertEquals(dataStore.get(0).zipcode, "191475901");
        assertEquals(dataStore.get(0).totalLivableArea, 0.0);

        assertEquals(dataStore.get(3).marketValue, Double.NaN);
        assertEquals(dataStore.get(3).zipcode, "191041636");
        assertEquals(dataStore.get(3).totalLivableArea, 0.0);

        assertEquals(dataStore.get(4).marketValue, 264800.0);
        assertEquals(dataStore.get(4).zipcode, "191481303");
        assertEquals(dataStore.get(4).totalLivableArea, 1800.0);
    }

    @Test
    void testParkingViolationJSONFileReader() {
        ParkingViolationJSONFileReader parkingViolationJSONFileReader = new ParkingViolationJSONFileReader("parking.json");

        parkingViolationJSONFileReader.read();

        List<ParkingViolation> dataStore = parkingViolationJSONFileReader.dataStore;

        assertEquals(dataStore.size(), 25559);

        assertEquals(dataStore.get(0).ticketNumber, "2905938");
        assertEquals(dataStore.get(0).plateId, "1322731");
        assertEquals(dataStore.get(0).date, Utils.getDateTime("2013-04-03T15:15:00Z"));
        assertEquals(dataStore.get(0).zipcode, "19104");
        assertEquals(dataStore.get(0).violation, "METER EXPIRED CC");
        assertEquals(dataStore.get(0).fine, 36);
        assertEquals(dataStore.get(0).state, "PA");

        assertEquals(dataStore.get(25558).ticketNumber, "2931538");
        assertEquals(dataStore.get(25558).plateId, "204335");
        assertEquals(dataStore.get(25558).date, Utils.getDateTime("2013-12-09T10:10:00Z"));
        assertEquals(dataStore.get(25558).zipcode, "19147");
        assertEquals(dataStore.get(25558).violation, "METER EXPIRED");
        assertEquals(dataStore.get(25558).fine, 26);
        assertEquals(dataStore.get(25558).state, "NJ");
    }
}
