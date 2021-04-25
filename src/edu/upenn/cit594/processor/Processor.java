package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.DataStore;
import edu.upenn.cit594.datamanagement.Reader;

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
        System.out.println("sum: " + populationSum);
        return populationSum;
    }
    
    
    public void calculateTotalFinesPerCapita() {
        Map<String,Double> zipcodeToFineMap = new TreeMap<>();
        Map<String, Area> areaMap = getReaderData(areaReader);
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
        
        //TODO: move formatting to UI class - new DecimalFormat("#.####").format(finesPerCapita)
        
        System.out.println(zipcodeToFineMap);
        
    }

    public double calculateAverage(String zipcode, Calculator calculator) {
        List<Property> properties = getData(propertyReader);

        for (Property property : properties) {
            if (property.zipcode.equals(zipcode)) {
                calculator.sumAndCountMetric(property);
            }
        }

        System.out.println(calculator.average());
        return calculator.average();
    }

    public double calculateAverageMarketValue(String zipcode) {
        // Purely left in for testing purposes
        int countProperties = 0;
        double sumMarketValue = 0.0;
        List<Property> properties = getReaderData(propertyReader);
        for (Property property : properties) {
            if (property.zipcode.equals(zipcode)) {
                countProperties++;
                sumMarketValue += property.marketValue;
            }
        }
        System.out.println(sumMarketValue / countProperties);

        return sumMarketValue / countProperties;
    }

    public double calculateAverageLivableArea(String zipcode) {
        // Purely left in for testing purposes
        int countProperties = 0;
        double sumLivableArea = 0.0;
        List<Property> properties = getData(propertyReader);
        for (Property property : properties) {
            if (property.zipcode.equals(zipcode)) {
                countProperties++;
                sumLivableArea += property.totalLivableArea;
            }
        }
        System.out.println(sumLivableArea / countProperties);

        return sumLivableArea / countProperties;
    }
    
    /**
     * Gets data from provided reader.
     * Optimized so that if data has already been read from reader, it will simply return the data instead of re-reading.
     * @param reader the reader to get data from
     * @param <T> the type of data within the reader
     * @return
     */
    private <T> T getReaderData(Reader<T> reader) {
        /*
        * safe cast because only 1 of 2 things can happen:
        * data is coming from the readers' read method which guarantees that it's the same type as reader being passed in
        * OR the data is being pulled from the readerToDataStoreMap based on the provided reader
        * in which case the dataStore value being returned from map is the same type because this is the only method that updates the map
         */
        @SuppressWarnings("unchecked")
        T data = (T) readerToDataStoreMap.computeIfAbsent(reader, currentReader -> currentReader.read()).getData();
        return data;
    }
    
}
