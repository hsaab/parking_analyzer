package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.AreaDelimitedFileReader;
import edu.upenn.cit594.datamanagement.ParkingViolationDelimitedFileReader;
import edu.upenn.cit594.datamanagement.PropertyDelimitedFileReader;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void testCalculateAverage() {
        PropertyDelimitedFileReader propertyDelimitedFileReader = new PropertyDelimitedFileReader("properties.csv",true,",");
        AreaDelimitedFileReader areaDelimitedFileReader = new AreaDelimitedFileReader(
                "population.txt", false, " "
        );
        ParkingViolationDelimitedFileReader parkingViolationDelimitedFileReader = new ParkingViolationDelimitedFileReader(
                "parking.csv", false, ","
        );
        Processor processor = new Processor(propertyDelimitedFileReader, areaDelimitedFileReader, parkingViolationDelimitedFileReader);

        List<Property> propertyList = processor.getReaderData(propertyDelimitedFileReader);

        // Checking strategy pattern and easier explicit method version tie for Market Value
        double strategyMethodAverage = processor.calculateAverageMarketValueByZipcode("19148");
        double average = this.calculateAverageMarketValueByZipcodeTester("19148", propertyList);

        assertEquals(strategyMethodAverage, average);

        // Checking strategy pattern and easier explicit method version tie for Livable Area
        strategyMethodAverage = processor.calculateAverageLivableAreaByZipcode("19148");
        average = this.calculateAverageLivableAreaByZipcodeTester("19148", propertyList);

        assertEquals(strategyMethodAverage, average);
    }
}
