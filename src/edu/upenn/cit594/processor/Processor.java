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
    Reader<List<Property>> propertyReader;
    Reader<Map<String,Area>> areaReader;
    Reader<List<ParkingViolation>> parkingViolationReader;

    Map<Reader<?>, DataStore<?>> readerToDataStoreMap;
    
    public Processor(Reader<List<Property>> propertyReader, Reader<Map<String,Area>> areaReader, Reader<List<ParkingViolation>> parkingViolationReader) {
        this.propertyReader = propertyReader;
        this.areaReader = areaReader;
        this.parkingViolationReader = parkingViolationReader;

        readerToDataStoreMap = new HashMap<>();
    }

    // AREA CALCULATIONS
    public int sumPopulations() {
        Map<String,Area> areaMap = getReaderData(areaReader);

        return AreaCalculator.sumPopulations(areaMap);
    }

    // PARKING VIOLATION CALCULATIONS
    public Map<String,Double> calculateTotalFinesPerCapita() {
        Map<String,Area> areaMap = getReaderData(areaReader);
        List<ParkingViolation> violations = getReaderData(parkingViolationReader);

        return ParkingViolationCalculator.calculateTotalFinesPerCapita(areaMap, violations);
    }

    public Set<Area> calculateFineCountForHighestMarketValuePerCapitaAreas() {
        Map<String, Area> areaMap = getReaderData(areaReader);
        List<ParkingViolation> violations = getReaderData(parkingViolationReader);
        List<Property> properties = getReaderData(propertyReader);

        Map<String, ParkingViolationMetrics> zipcodeToParkingViolationMetricsMap = ParkingViolationCalculator.buildParkingViolationMetricsByZipcode(areaMap, violations);

        return PropertyCalculator.calculateFineCountForHighestMarketValuePerCapitaAreas(properties, areaMap, zipcodeToParkingViolationMetricsMap);
    }

    // PROPERTY CALCULATIONS
    public double calculateAverageMarketValueByZipcode(String zipcode) {
        List<Property> properties = getReaderData(propertyReader);

       return PropertyCalculator.calculateAverageByZipcode(zipcode, new MarketValueMetrics(), properties);
    }

    public double calculateAverageLivableAreaByZipcode(String zipcode) {
        List<Property> properties = getReaderData(propertyReader);

        return PropertyCalculator.calculateAverageByZipcode(zipcode, new LivableAreaMetrics(), properties);
    }

    public double calculateResidentialMarketValuePerCapita(String zipcode) {
        Map<String,Area> areaMap = getReaderData(areaReader);
        List<Property> properties = getReaderData(propertyReader);

        return PropertyCalculator.calculateResidentialMarketValuePerCapita(zipcode, areaMap, properties);
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
