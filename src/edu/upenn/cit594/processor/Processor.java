package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.*;
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
    
    public Processor(Reader<List<ParkingViolation>> parkingViolationReader, Reader<List<Property>> propertyReader, Reader<Map<String,Area>> areaReader) {
        this.parkingViolationReader = parkingViolationReader;
        this.propertyReader = propertyReader;
        this.areaReader = areaReader;
    
        this.parkingViolationCalculator = new ParkingViolationCalculator();
        this.propertyCalculator = new PropertyCalculator();
        this.areaCalculator = new AreaCalculator();

        readerToDataStoreMap = new HashMap<>();
    }

    // AREA CALCULATIONS
    public int sumPopulations() {
        if(areaCalculator.getPopulationSum() != 0) {
            return areaCalculator.getPopulationSum();
        }

        Map<String,Area> areaMap = getReaderData(areaReader);

        return areaCalculator.sumPopulations(areaMap);
    }

    // PARKING VIOLATION CALCULATIONS
    public Map<String,Double> calculateTotalFinesPerCapita() {
        if(!parkingViolationCalculator.zipcodeToFineMap.isEmpty()) {
            return parkingViolationCalculator.zipcodeToFineMap;
        }

        Map<String,Area> areaMap = getReaderData(areaReader);
        List<ParkingViolation> violations = getReaderData(parkingViolationReader);

        return parkingViolationCalculator.calculateTotalFinesPerCapita(areaMap, violations);
    }

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

    // PROPERTY CALCULATIONS
    public double calculateAverageMarketValueByZipcode(String zipcode) {
        if(propertyCalculator.averageMarketValueByZipcode.containsKey(zipcode)) {
            return propertyCalculator.averageMarketValueByZipcode.get(zipcode);
        }

        List<Property> properties = getReaderData(propertyReader);

       return propertyCalculator.calculateAverageMarketValueByZipcode(zipcode, properties);
    }

    public double calculateAverageLivableAreaByZipcode(String zipcode) {
        if(propertyCalculator.averageLivableAreaByZipcode.containsKey(zipcode)) {
            return propertyCalculator.averageLivableAreaByZipcode.get(zipcode);
        }

        List<Property> properties = getReaderData(propertyReader);

        return propertyCalculator.calculateAverageLivableAreaByZipcode(zipcode, properties);
    }

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
