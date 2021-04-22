package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.Reader;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Processor {
    Reader<? extends List<Property>> propertyReader;
    Reader<? extends Map<String,Area>> areaReader;
    Reader<? extends List<ParkingViolation>> parkingViolationReader;
    
    public Processor(Reader<? extends List<Property>> propertyReader, Reader<? extends Map<String,Area>> areaReader, Reader<? extends List<ParkingViolation>> parkingViolationReader) {
        this.propertyReader = propertyReader;
        this.areaReader = areaReader;
        this.parkingViolationReader = parkingViolationReader;
    }
    
    public int sumPopulations() {
        int populationSum = 0;
        Map<String,Area> areaMap = this.areaReader.read();
        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {
            populationSum += entry.getValue().population;
        }
        System.out.println("sum: " + populationSum);
        return populationSum;
    }
    
    public void calculateTotalFines() {
    
    }
    
    public void calculateTotalFinesPerCapita() {
        Map<String,Double> zipcodeToFineMap = new TreeMap<>();
        Map<String,Area> areaMap = this.areaReader.read();
        // sum all fines for each zipcode
        List<ParkingViolation> violations = this.parkingViolationReader.read();
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
            zipcodeToFineMap.put(entry.getKey(), Double.parseDouble(new DecimalFormat("#.####").format(finesPerCapita)));
        }
        
        
        
        System.out.println(zipcodeToFineMap);
        
    }
    
    public double calculateAverageMarketValue(String zipcode) {
        int countProperties = 0;
        double sumMarketValue = 0.0;
        List<Property> properties = propertyReader.read();
        for (Property property : properties) {
            if (property.zipcode.equals(zipcode)) {
                countProperties++;
                sumMarketValue += property.marketValue;
            }
        }
        System.out.println(sumMarketValue / countProperties);
        
        return sumMarketValue / countProperties;
    }
}
