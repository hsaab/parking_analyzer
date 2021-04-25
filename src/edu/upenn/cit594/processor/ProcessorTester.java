package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.AreaDelimitedFileReader;
import edu.upenn.cit594.datamanagement.ParkingViolationDelimitedFileReader;
import edu.upenn.cit594.datamanagement.PropertyDelimitedFileReader;

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
}
