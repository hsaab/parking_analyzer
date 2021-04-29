package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.utils.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataManagementTester {
    @BeforeAll
    static void setup() {
        Logger.setFilename("test.txt");
    }

    @Test
    void testAreaDelimitedFileReader() throws FileNotFoundException {
        AreaDelimitedFileReader areaDelimitedFileReader = new AreaDelimitedFileReader(
                "population.txt", false, " "
        );

        areaDelimitedFileReader.read();

        Map<String, Area> dataStore = areaDelimitedFileReader.dataStore.getData();

        assertEquals(dataStore.size(), 48);
        assertEquals(dataStore.get("19102").getPopulation(), 4705);
        assertEquals(dataStore.get("19102").getZipcode(), "19102");
        assertEquals(dataStore.get("19118").getPopulation(), 9808);
        assertEquals(dataStore.get("19118").getZipcode(), "19118");
    }

    @Test
    void testBadAreaDelimitedFileReader() throws FileNotFoundException {
        AreaDelimitedFileReader areaDelimitedFileReader = new AreaDelimitedFileReader(
                "population-bad1.txt", false, " "
        );

        areaDelimitedFileReader.read();

        Map<String, Area> dataStore = areaDelimitedFileReader.dataStore.getData();

        assertEquals(dataStore.size(), 48);
        assertEquals(dataStore.get("19102").getPopulation(), Double.NaN);
        assertEquals(dataStore.get("19102").getZipcode(), "19102");

        assertEquals(dataStore.get("19103").getPopulation(), Double.NaN);
        assertEquals(dataStore.get("19103").getZipcode(), "19103");
    }

    @Test
    void testBadPropertyDelimitedFileReader() throws FileNotFoundException {
        // Played with some of the values to make them bad
        PropertyDelimitedFileReader propertyDelimitedFileReader = new PropertyDelimitedFileReader(
                "properties-test-bad.csv", true, ","
        );

        propertyDelimitedFileReader.read();

        List<Property> dataStore = propertyDelimitedFileReader.dataStore.getData();

        assertEquals(dataStore.get(0).getMarketValue(), 0.0);
        assertEquals(dataStore.get(0).getZipcode(), "19147");
        assertEquals(dataStore.get(0).getTotalLivableArea(), 0.0);

        assertEquals(dataStore.get(3).getMarketValue(), Double.NaN);
        assertEquals(dataStore.get(3).getZipcode(), "19104");
        assertEquals(dataStore.get(3).getTotalLivableArea(), 0.0);

        assertEquals(dataStore.get(4).getMarketValue(), Double.NaN);
        assertEquals(dataStore.get(4).getZipcode(), "");
        assertEquals(dataStore.get(4).getTotalLivableArea(), Double.NaN);
    }

    @Test
    void testBadParkingViolationJSON() throws FileNotFoundException {
        ParkingViolationJSONFileReader parkingViolationJSONFileReader = new ParkingViolationJSONFileReader("parking-bad.json");

        parkingViolationJSONFileReader.read();

        List<ParkingViolation> dataStore = parkingViolationJSONFileReader.dataStore.getData();

        assertEquals(dataStore.get(0).getDate(), null);
        assertEquals(dataStore.get(0).getFine(), Double.NaN);
        assertEquals(dataStore.get(0).getViolation(), "345");
        assertEquals(dataStore.get(0).getPlateId(), "1322731");
        assertEquals(dataStore.get(0).getState(), "PA");
        assertEquals(dataStore.get(0).ticketNumber, "2905938");
        assertEquals(dataStore.get(0).zipcode, "19104");
    }

    @Test
    void testBadParkingViolationDelimitedFileReader() throws FileNotFoundException {
        ParkingViolationJSONFileReader parkingViolationReader = new ParkingViolationJSONFileReader(
                "parking-bad.json"
        );

        parkingViolationReader.read();

        List<ParkingViolation> dataStore = parkingViolationReader.dataStore.getData();

        assertEquals(dataStore.get(0).getDate(), null);
        assertEquals(dataStore.get(0).getFine(), Double.NaN);
        assertEquals(dataStore.get(0).getViolation(), "METER EXPIRED CC");
        assertEquals(dataStore.get(0).getPlateId(), "1322731");
        assertEquals(dataStore.get(0).getState(), "PA");
        assertEquals(dataStore.get(0).ticketNumber, "2905938");
        assertEquals(dataStore.get(0).zipcode, "19104");

        assertEquals(dataStore.get(1).zipcode, "");
    }

    @Test
    void testParkingViolationDelimitedFileReader() throws FileNotFoundException {
        ParkingViolationDelimitedFileReader parkingViolationDelimitedFileReader = new ParkingViolationDelimitedFileReader(
                "parking.csv", false, ","
        );

        parkingViolationDelimitedFileReader.read();

        List<ParkingViolation> dataStore = parkingViolationDelimitedFileReader.dataStore.getData();

        assertEquals(dataStore.size(), 25559);

        assertEquals(dataStore.get(0).getDate(), Utils.getDateTime("2013-04-03T15:15:00Z"));
        assertEquals(dataStore.get(0).getFine(), 36);
        assertEquals(dataStore.get(0).getViolation(), "METER EXPIRED CC");
        assertEquals(dataStore.get(0).getPlateId(), "1322731");
        assertEquals(dataStore.get(0).getState(), "PA");
        assertEquals(dataStore.get(0).ticketNumber, "2905938");
        assertEquals(dataStore.get(0).zipcode, "19104");

        assertEquals(dataStore.get(25558).getDate(), Utils.getDateTime("2013-12-09T10:10:00Z"));
        assertEquals(dataStore.get(25558).getFine(), 26);
        assertEquals(dataStore.get(25558).getViolation(), "METER EXPIRED");
        assertEquals(dataStore.get(25558).getPlateId(), "204335");
        assertEquals(dataStore.get(25558).getState(), "NJ");
        assertEquals(dataStore.get(25558).ticketNumber, "2931538");
        assertEquals(dataStore.get(25558).zipcode, "19147");
    }

    @Test
    void testPropertyDelimitedFileReader() throws FileNotFoundException {
        // THIS TESTS TO MAKE SURE WE CAN GET THROUGH THE ORIGINAL FILE
        PropertyDelimitedFileReader propertyDelimitedFileReader = new PropertyDelimitedFileReader(
                "properties.csv", true, ","
        );

        propertyDelimitedFileReader.read();

        List<Property> dataStore = propertyDelimitedFileReader.dataStore.getData();

        assertEquals(dataStore.get(0).getZipcode(), "19148");
    }

    @Test
    void testPropertyDelimitedFileReaderLite() throws FileNotFoundException {
        // THIS TESTS TO MAKE SURE WE ARE PARSING THROUGH THE LISTS OK
        PropertyDelimitedFileReader propertyDelimitedFileReader = new PropertyDelimitedFileReader(
                "properties-test.csv", true, ","
        );

        propertyDelimitedFileReader.read();

        List<Property> dataStore = propertyDelimitedFileReader.dataStore.getData();

        assertEquals(dataStore.get(0).getMarketValue(), 0.0);
        assertEquals(dataStore.get(0).getZipcode(), "19147");
        assertEquals(dataStore.get(0).getTotalLivableArea(), 0.0);

        assertEquals(dataStore.get(3).getMarketValue(), Double.NaN);
        assertEquals(dataStore.get(3).getZipcode(), "19104");
        assertEquals(dataStore.get(3).getTotalLivableArea(), 0.0);

        assertEquals(dataStore.get(4).getMarketValue(), 264800.0);
        assertEquals(dataStore.get(4).getZipcode(), "19148");
        assertEquals(dataStore.get(4).getTotalLivableArea(), 1800.0);
    }

    @Test
    void testParkingViolationJSONFileReader() throws FileNotFoundException {
        ParkingViolationJSONFileReader parkingViolationJSONFileReader = new ParkingViolationJSONFileReader("parking.json");

        parkingViolationJSONFileReader.read();

        List<ParkingViolation> dataStore = parkingViolationJSONFileReader.dataStore.getData();

        assertEquals(dataStore.size(), 25559);

        assertEquals(dataStore.get(0).ticketNumber, "2905938");
        assertEquals(dataStore.get(0).getPlateId(), "1322731");
        assertEquals(dataStore.get(0).getDate(), Utils.getDateTime("2013-04-03T15:15:00Z"));
        assertEquals(dataStore.get(0).zipcode, "19104");
        assertEquals(dataStore.get(0).getViolation(), "METER EXPIRED CC");
        assertEquals(dataStore.get(0).getFine(), 36);
        assertEquals(dataStore.get(0).getState(), "PA");

        assertEquals(dataStore.get(25558).ticketNumber, "2931538");
        assertEquals(dataStore.get(25558).getPlateId(), "204335");
        assertEquals(dataStore.get(25558).getDate(), Utils.getDateTime("2013-12-09T10:10:00Z"));
        assertEquals(dataStore.get(25558).zipcode, "19147");
        assertEquals(dataStore.get(25558).getViolation(), "METER EXPIRED");
        assertEquals(dataStore.get(25558).getFine(), 26);
        assertEquals(dataStore.get(25558).getState(), "NJ");
    }
}
