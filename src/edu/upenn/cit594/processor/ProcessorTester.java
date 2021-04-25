package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessorTester {
    Reader<List<Property>> propertyReader;
    Reader<Map<String, Area>> areaReader;
    Reader<List<ParkingViolation>> parkingViolationDelimitedFileReader;
    Reader<List<ParkingViolation>> parkingViolationJSONFileReader;
    Processor processorCsv;
    Processor processorJson;
    
    @BeforeAll
    static void setLog() {
        Logger.setFilename("processor_tests.txt");
    }
    
    @BeforeEach
    void setup() {
        this.propertyReader = new PropertyDelimitedFileReader("properties.csv",true,",");
        this.areaReader = new AreaDelimitedFileReader(
                "population.txt", false, " "
        );
        this.parkingViolationDelimitedFileReader = new ParkingViolationDelimitedFileReader(
                "parking.csv", false, ","
        );
        this.parkingViolationJSONFileReader = new ParkingViolationJSONFileReader(
                "parking.json"
        );
        processorCsv = new Processor(propertyReader, areaReader, parkingViolationDelimitedFileReader);
        processorJson = new Processor(propertyReader, areaReader, parkingViolationJSONFileReader);
    }
    
    @Test
    void testSumPopulations() {
        Logger.getLogger().log("testSumPopulations with csv properties file");
        int expected = 1526206;
        int result = processorCsv.sumPopulations();
        assertEquals(expected, result);
    
        Logger.getLogger().log("testSumPopulations with json properties file");
        result = processorJson.sumPopulations();
        assertEquals(expected, result);
    }

    public double calculateAverageLivableAreaByZipcodeTester(String zipcode, List<Property> propertyList) {
        // Purely left in for testing purposes
        int countProperties = 0;
        double sumLivableArea = 0.0;

        for (Property property : propertyList) {
            if (property.zipcode.equals(zipcode)) {
                countProperties++;
                sumLivableArea += property.totalLivableArea;
            }
        }
        System.out.println(sumLivableArea / countProperties);

        return sumLivableArea / countProperties;
    }

    public double calculateAverageMarketValueByZipcodeTester(String zipcode, List<Property> propertyList) {
        // Purely left in for testing purposes
        int countProperties = 0;
        double sumMarketValue = 0.0;

        for (Property property : propertyList) {
            if (property.zipcode.equals(zipcode)) {
                countProperties++;
                sumMarketValue += property.marketValue;
            }
        }
        System.out.println(sumMarketValue / countProperties);

        return sumMarketValue / countProperties;
    }

    @Test
    void testCalculateAverageCsv() {
        Logger.getLogger().log("testCalculateAverageCsv");
        List<Property> propertyList = processorCsv.getReaderData(propertyReader);

        // Checking strategy pattern and easier explicit method version tie for Market Value
        double strategyMethodAverage = processorCsv.calculateAverageMarketValueByZipcode("19148");
        double average = this.calculateAverageMarketValueByZipcodeTester("19148", propertyList);
    
        assertEquals(average, strategyMethodAverage);

        // Checking strategy pattern and easier explicit method version tie for Livable Area
        strategyMethodAverage = processorCsv.calculateAverageLivableAreaByZipcode("19148");
        average = this.calculateAverageLivableAreaByZipcodeTester("19148", propertyList);
    
        assertEquals(average, strategyMethodAverage);
    }
    
    @Test
    void testCalculateAverageJSON() {
        Logger.getLogger().log("testCalculateAverageJSON");
        List<Property> propertyList = processorJson.getReaderData(propertyReader);
        
        // Checking strategy pattern and easier explicit method version tie for Market Value
        double strategyMethodAverage = processorJson.calculateAverageMarketValueByZipcode("19148");
        double average = this.calculateAverageMarketValueByZipcodeTester("19148", propertyList);
        
        assertEquals(average, strategyMethodAverage);
        
        // Checking strategy pattern and easier explicit method version tie for Livable Area
        strategyMethodAverage = processorJson.calculateAverageLivableAreaByZipcode("19148");
        average = this.calculateAverageLivableAreaByZipcodeTester("19148", propertyList);
        
        assertEquals(average, strategyMethodAverage);
    }
}
