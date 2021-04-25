package edu.upenn.cit594.ui;

import edu.upenn.cit594.datamanagement.AreaDelimitedFileReader;
import edu.upenn.cit594.datamanagement.ParkingViolationDelimitedFileReader;
import edu.upenn.cit594.datamanagement.PropertyDelimitedFileReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

public class UITester {
    public static void main(String[] args) {
        Logger.setFilename("log_test.txt");
        PropertyDelimitedFileReader propertyDelimitedFileReader = new PropertyDelimitedFileReader(
                "properties.csv",true,","
        );
        AreaDelimitedFileReader areaDelimitedFileReader = new AreaDelimitedFileReader(
                "population.txt", false, " "
        );
        ParkingViolationDelimitedFileReader parkingViolationDelimitedFileReader = new ParkingViolationDelimitedFileReader(
                "parking.csv", false, ","
        );
        Processor processor = new Processor(propertyDelimitedFileReader, areaDelimitedFileReader, parkingViolationDelimitedFileReader);
        
        new CommandLineUserInterface(processor).start();
    }
}
