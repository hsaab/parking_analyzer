package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.DataStore;
import edu.upenn.cit594.datamanagement.Reader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Processor {
    private Reader<List<ParkingViolation>> parkingViolationReader;
    private Reader<List<Property>> propertyReader;
    private Reader<Map<String,Area>> areaReader;
    
    private ParkingViolationCalculator parkingViolationCalculator;
    private PropertyCalculator propertyCalculator;
    private AreaCalculator areaCalculator;

    private Map<Reader<?>, DataStore<?>> readerToDataStoreMap;

    /**
     * Constructor
     * @param parkingViolationReader reader that has parking violation data
     * @param propertyReader reader that has property data
     * @param areaReader reader that has zipcode and population data
     */
    public Processor(Reader<List<ParkingViolation>> parkingViolationReader, Reader<List<Property>> propertyReader, Reader<Map<String,Area>> areaReader) {
        this.parkingViolationReader = parkingViolationReader;
        this.propertyReader = propertyReader;
        this.areaReader = areaReader;
    
        this.parkingViolationCalculator = new ParkingViolationCalculator();
        this.propertyCalculator = new PropertyCalculator();
        this.areaCalculator = new AreaCalculator();

        readerToDataStoreMap = new HashMap<>();
    }

    /**
     * Sums all populations for all zipcodes
     * @return all populations as a sum
     */
    public int sumPopulations() {
        if(areaCalculator.getPopulationSum() != 0) {
            return areaCalculator.getPopulationSum();
        }

        Map<String,Area> areaMap = getReaderData(areaReader);

        return areaCalculator.sumPopulations(areaMap);
    }

    /**
     * calculates the total fines per capita across all zip codes
     * @return a map that has zipcode to fines per capita
     */
    public Map<String,Double> calculateTotalFinesPerCapita() {
        if(!parkingViolationCalculator.zipcodeToFineMap.isEmpty()) {
            return parkingViolationCalculator.zipcodeToFineMap;
        }

        Map<String,Area> areaMap = getReaderData(areaReader);
        List<ParkingViolation> violations = getReaderData(parkingViolationReader);

        return parkingViolationCalculator.calculateTotalFinesPerCapita(areaMap, violations);
    }

    /**
     * Sorts by highest market value per capita to lowest and presents the fine count
     * @return set of areas that have fine count sorted by market value per capita
     */
    public Set<Area> calculateFineCountForHighestMarketValuePerCapitaAreas() {
        if(!propertyCalculator.areaSetByMarketValue.isEmpty()) {
            return propertyCalculator.areaSetByMarketValue;
        }

        Map<String, Area> areaMap = getReaderData(areaReader);
        List<ParkingViolation> violations = getReaderData(parkingViolationReader);
        List<Property> properties = getReaderData(propertyReader);

        Map<String, ParkingViolationMetrics> zipcodeToParkingViolationMetricsMap = parkingViolationCalculator.buildParkingViolationMetricsByZipcode(areaMap, violations);

        return propertyCalculator.calculateFineCountForHighestMarketValuePerCapitaAreas(properties, areaMap, zipcodeToParkingViolationMetricsMap);
    }

    /**
     * Calculates the average market value for a given zipcode
     * @param zipcode zipcode entered by the user
     * @return average market value for the zipcode
     */
    public double calculateAverageMarketValueByZipcode(String zipcode) {
        if(propertyCalculator.averageMarketValueByZipcode.containsKey(zipcode)) {
            return propertyCalculator.averageMarketValueByZipcode.get(zipcode);
        }

        List<Property> properties = getReaderData(propertyReader);

       return propertyCalculator.calculateAverageMarketValueByZipcode(zipcode, properties);
    }

    /**
     * Calculates the average livable area for a given zipcode
     * @param zipcode zipcode entered by the user
     * @return average livable area for the zipcode
     */
    public double calculateAverageLivableAreaByZipcode(String zipcode) {
        if(propertyCalculator.averageLivableAreaByZipcode.containsKey(zipcode)) {
            return propertyCalculator.averageLivableAreaByZipcode.get(zipcode);
        }

        List<Property> properties = getReaderData(propertyReader);

        return propertyCalculator.calculateAverageLivableAreaByZipcode(zipcode, properties);
    }

    /**
     * Calculates the residential market value per capita for a given zipcode
     * @param zipcode zipcode entered by the user
     * @return residential market value per capita for a given zipcode
     */
    public double calculateResidentialMarketValuePerCapita(String zipcode) {
        if(propertyCalculator.marketValuePerCapitaByZipcode.containsKey(zipcode)) {
            return propertyCalculator.marketValuePerCapitaByZipcode.get(zipcode);
        }

        Map<String,Area> areaMap = getReaderData(areaReader);
        List<Property> properties = getReaderData(propertyReader);

        return propertyCalculator.calculateResidentialMarketValuePerCapita(zipcode, areaMap, properties);
    }

    /**
     * Gets data from provided reader.
     * Optimized so that if data has already been read from reader, it will simply return the data instead of re-reading.
     * @param reader the reader to get data from
     * @param <T> the type of data within the reader
     * @return
     */

    protected <T> T getReaderData(Reader<T> reader) {
        T data = (T) readerToDataStoreMap.computeIfAbsent(reader, currentReader -> currentReader.read()).getData();
        return data;
    }
}
