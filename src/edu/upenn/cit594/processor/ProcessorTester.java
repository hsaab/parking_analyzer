package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.AreaDelimitedFileReader;
import edu.upenn.cit594.datamanagement.ParkingViolationDelimitedFileReader;
import edu.upenn.cit594.datamanagement.PropertyDelimitedFileReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessorTester {
    public static void main(String[] args) {
        PropertyDelimitedFileReader propertyDelimitedFileReader = new PropertyDelimitedFileReader("properties.csv",true,",");
        AreaDelimitedFileReader areaDelimitedFileReader = new AreaDelimitedFileReader(
                "population.txt", false, " "
        );
        ParkingViolationDelimitedFileReader parkingViolationDelimitedFileReader = new ParkingViolationDelimitedFileReader(
                "parking.csv", false, ","
        );
        Processor processor = new Processor(propertyDelimitedFileReader, areaDelimitedFileReader, parkingViolationDelimitedFileReader);
        processor.sumPopulations();
        processor.calculateTotalFinesPerCapita();
        processor.calculateAverageMarketValue("19144");
    }

    @Test
    void testCalculateAverage() {
        PropertyDelimitedFileReader propertyDelimitedFileReader = new PropertyDelimitedFileReader("properties.csv",true,",");
        AreaDelimitedFileReader areaDelimitedFileReader = new AreaDelimitedFileReader(
                "population.txt", false, " "
        );
        ParkingViolationDelimitedFileReader parkingViolationDelimitedFileReader = new ParkingViolationDelimitedFileReader(
                "parking.csv", false, ","
        );
        Processor processor = new Processor(propertyDelimitedFileReader, areaDelimitedFileReader, parkingViolationDelimitedFileReader);

        // Checking strategy pattern and easier explicit method version tie for Market Value
        double strategyMethodAverage = processor.calculateAverage("19148", new PropertyValueCalculator());
        double average = processor.calculateAverageMarketValue("19148");

        assertEquals(strategyMethodAverage, average);

        // Checking strategy pattern and easier explicit method version tie for Livable Area
        strategyMethodAverage = processor.calculateAverage("19148", new LivableAreaCalculator());
        average = processor.calculateAverageLivableArea("19148");

        assertEquals(strategyMethodAverage, average);
    }
}
