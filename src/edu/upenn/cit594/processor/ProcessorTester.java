package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessorTester {
    Reader<List<ParkingViolation>> parkingViolationDelimitedFileReader;
    Reader<List<ParkingViolation>> parkingViolationJSONFileReader;
    Reader<List<Property>> propertyReader;
    Reader<Map<String, Area>> areaReader;
    
    Processor processorCsv;
    Processor processorJson;
    Processor processorTestCsv;
    PropertyDelimitedFileReader propertyTestReader;

    @BeforeAll
    static void setLog() {
        Logger.setFilename("processor_tests.txt");
    }
    
    @BeforeEach
    void setup() throws FileNotFoundException {
        this.parkingViolationDelimitedFileReader = new ParkingViolationDelimitedFileReader(
                "parking.csv", false, ","
        );
        this.parkingViolationJSONFileReader = new ParkingViolationJSONFileReader(
                "parking.json"
        );
        this.propertyReader = new PropertyDelimitedFileReader("properties.csv",true,",");
        this.propertyTestReader = new PropertyDelimitedFileReader("properties-test.csv",true,",");
        this.areaReader = new AreaDelimitedFileReader(
                "population.txt", false, " "
        );
        processorTestCsv = new Processor(parkingViolationDelimitedFileReader, propertyTestReader, areaReader);
        processorCsv = new Processor(parkingViolationDelimitedFileReader, propertyReader, areaReader);
        processorJson = new Processor(parkingViolationJSONFileReader, propertyReader, areaReader);
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
            if (property.getZipcode().equals(zipcode)) {
                countProperties++;
                sumLivableArea += property.getTotalLivableArea();
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
            if (property.getZipcode().equals(zipcode)) {
                countProperties++;
                sumMarketValue += property.getMarketValue();
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

    @Test
    void testCalculateResidentialMarketValuePerCapita() throws FileNotFoundException {
        double residentialMarketValuePerCapita = processorTestCsv.calculateResidentialMarketValuePerCapita("19148");

        // 264800 market value #1
        // 282900 market value #2
        // 49732 population
        assertEquals((264800 + 282900) / 49732, residentialMarketValuePerCapita, 0.5);
    }

    @Test
    void testTotalFinesPerCapita() throws FileNotFoundException {
        Map<String, Double> areaByMarketValue = processorCsv.calculateTotalFinesPerCapita();
        System.out.println(areaByMarketValue);
        assertEquals(42, areaByMarketValue.size());
    }

    @Test
    void testCalculateFineCountForHighestMarketValuePerCapitaAreas() throws FileNotFoundException {
        Set<Area> areaByMarketValue = processorCsv.calculateFineCountForHighestMarketValuePerCapitaAreas();

        //assertEquals(29, areaByMarketValue.size());

        Iterator<Area> iterator = areaByMarketValue.iterator();
        double prevMarketValuePerCapita = Double.POSITIVE_INFINITY;

        while (iterator.hasNext()) {
            Area nextArea = iterator.next();

            DecimalFormat formatter = new DecimalFormat("$#,###.00");
            System.out.println("Area Zip " + nextArea.getZipcode() + " market value: " + formatter.format(nextArea.getMarketValuePerCapita()) + " number of fines: " + nextArea.getFineCount());

            if(!Double.isNaN(nextArea.getMarketValuePerCapita())) {
                assertTrue(Double.compare(nextArea.getMarketValuePerCapita(), prevMarketValuePerCapita) <= 0);

                prevMarketValuePerCapita = nextArea.getMarketValuePerCapita();
            }
        }
    }
    
    @Test
    void testBadInputsForSumPopulation() throws FileNotFoundException {
        Reader<Map<String, Area>> areaReader = new AreaDelimitedFileReader("population-bad1.txt", true, " ");
        Processor badInputProcessor = new Processor(parkingViolationDelimitedFileReader, propertyReader, areaReader);
        assertEquals(970, badInputProcessor.sumPopulations());
    }
    
    @Test
    void testBadInputsForTotalFinesPerCapita() throws FileNotFoundException {
        Reader<Map<String, Area>> areaReader = new AreaDelimitedFileReader("population-bad1.txt", true, " ");
        Reader<List<ParkingViolation>> parkingReader = new ParkingViolationJSONFileReader("parking-bad-1.json");
        Processor badInputProcessor = new Processor(parkingReader, propertyReader, areaReader);
        Map<String, Double> finesPerCapita = badInputProcessor.calculateTotalFinesPerCapita();
        assertEquals(36.0 / 50.0,finesPerCapita.get("19102"));
        assertEquals(41.0 / 100.0,finesPerCapita.get("19107"));
        assertEquals(36.0 / 120.0,finesPerCapita.get("19113"));
        System.out.println(finesPerCapita);
    }
    
    @Test
    void testBadInputsForAverageMarketValue() throws FileNotFoundException {
        Reader<List<Property>> badPropertyReader = new PropertyDelimitedFileReader("properties-test-bad.csv",true,",");
        Processor badInputProcessor = new Processor(parkingViolationDelimitedFileReader, badPropertyReader, areaReader);
        //assertEquals(0.0, badInputProcessor.calculateAverageMarketValueByZipcode(""));
        assertEquals(282900 / 2.0, badInputProcessor.calculateAverageMarketValueByZipcode("19147"));
        assertEquals(0.0, badInputProcessor.calculateAverageMarketValueByZipcode("19104"));
        assertEquals(15000.0, badInputProcessor.calculateAverageMarketValueByZipcode("19133"));
        assertEquals(0.0, badInputProcessor.calculateAverageMarketValueByZipcode("345"));
        
    }
    
    @Test
    void testBadInputsForAverageLivableArea() throws FileNotFoundException {
        Reader<List<Property>> badPropertyReader = new PropertyDelimitedFileReader("properties-test-bad.csv",true,",");
        Processor badInputProcessor = new Processor(parkingViolationDelimitedFileReader, badPropertyReader, areaReader);
        //assertEquals(0.0, badInputProcessor.calculateAverageMarketValueByZipcode(""));
        assertEquals(2140 / 2.0, badInputProcessor.calculateAverageLivableAreaByZipcode("19147"));
        assertEquals(0.0, badInputProcessor.calculateAverageLivableAreaByZipcode("19104"));
        assertEquals(0.0, badInputProcessor.calculateAverageLivableAreaByZipcode("19133"));
        assertEquals(0.0, badInputProcessor.calculateAverageLivableAreaByZipcode("345"));
        
    }
    
    @Test
    void testBadInputsForMarketValuePerCapita() throws FileNotFoundException {
        Reader<Map<String, Area>> badAreaReader = new AreaDelimitedFileReader("population-bad1.txt", true, " ");
        Reader<List<Property>> badPropertyReader = new PropertyDelimitedFileReader("properties-test-bad.csv",true,",");
        Processor badInputProcessor = new Processor(parkingViolationDelimitedFileReader, badPropertyReader, badAreaReader);
        assertEquals(282900.0 / 100.0, badInputProcessor.calculateResidentialMarketValuePerCapita("19147"));
        assertEquals(0.0, badInputProcessor.calculateResidentialMarketValuePerCapita("19104"));
        assertEquals(0.0, badInputProcessor.calculateResidentialMarketValuePerCapita("19133"));
        assertEquals(0.0, badInputProcessor.calculateAverageLivableAreaByZipcode("345"));
    }
}
