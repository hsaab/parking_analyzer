package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.DataStore;
import edu.upenn.cit594.datamanagement.Reader;

import java.text.DecimalFormat;
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
        Map<String, Area> areaMap = getData(areaReader);
        
        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {
            populationSum += entry.getValue().population;
        }
        System.out.println("sum: " + populationSum);
        return populationSum;
    }
    
    
    public void calculateTotalFinesPerCapita() {
        Map<String,Double> zipcodeToFineMap = new TreeMap<>();
        Map<String, Area> areaMap = getData(areaReader);
        List<ParkingViolation> violations = getData(parkingViolationReader);
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
    
    public double calculateAverageMarketValue(String zipcode) {
        int countProperties = 0;
        double sumMarketValue = 0.0;
        List<Property> properties = getData(propertyReader);
        for (Property property : properties) {
            if (property.zipcode.equals(zipcode)) {
                countProperties++;
                sumMarketValue += property.marketValue;
            }
        }
        System.out.println(sumMarketValue / countProperties);
        
        return sumMarketValue / countProperties;
    }
    
    
    /**
     * Gets data from readerToDataStoreMap based on provided reader.
     * Puts data from reader in readerToDataStoreMap if not already there.
     * @param reader the reader to get data from
     * @param <T>
     * @return
     */
    private <T> T getData(Reader<T> reader) {
        @SuppressWarnings("unchecked")  // safe cast because the data in dataStore can only be the same type as reader
        DataStore<T> dataStore = (DataStore<T>) readerToDataStoreMap.computeIfAbsent(reader, currentReader -> reader.read());
        return dataStore.getData();
    }
    
}
