package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    
    public int sumPopulations() {
        int populationSum = 0;
        Map<String, Area> areaMap = getReaderData(areaReader);
        
        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {
            populationSum += entry.getValue().population;
        }
        return populationSum;
    }
    
    
    public Map<String,Double> calculateTotalFinesPerCapita() {
        Map<String,Double> zipcodeToFineMap = new TreeMap<>();
        Map<String,Area> areaMap = getReaderData(areaReader);
        List<ParkingViolation> violations = getReaderData(parkingViolationReader);
        for (ParkingViolation violation : violations) {
            if (areaMap.containsKey((violation.zipcode))) {
                if (zipcodeToFineMap.containsKey(violation.zipcode)) {
                    double sumFine = zipcodeToFineMap.get(violation.zipcode) + violation.fine;
                    zipcodeToFineMap.put(violation.zipcode, sumFine);
                } else {
                    zipcodeToFineMap.put(violation.zipcode, violation.fine);
                }
            }
        }
       
        for (Map.Entry<String, Double> entry : zipcodeToFineMap.entrySet()) {
            double finesPerCapita = entry.getValue() / areaMap.get(entry.getKey()).population;
            zipcodeToFineMap.put(entry.getKey(), finesPerCapita);
        }
        
        return zipcodeToFineMap;
    }

    public double calculateAverageByZipcode(String zipcode, PropertyCalculator propertyCalculator) {
        List<Property> properties = getReaderData(propertyReader);

        for (Property property : properties) {
            if (property.zipcode.equals(zipcode)) {
                propertyCalculator.sumAndCountMetric(property);
            }
        }

        System.out.println(propertyCalculator.average());
        return propertyCalculator.average();
    }

    public double calculateAverageMarketValueByZipcode(String zipcode) {
       return this.calculateAverageByZipcode(zipcode, new MarketValuePropertyCalculator());
    }

    public double calculateAverageLivableAreaByZipcode(String zipcode) {
        return this.calculateAverageByZipcode(zipcode, new LivableAreaPropertyCalculator());
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
