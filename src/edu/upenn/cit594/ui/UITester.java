package edu.upenn.cit594.ui;

import edu.upenn.cit594.datamanagement.AreaDelimitedFileReader;
import edu.upenn.cit594.datamanagement.ParkingViolationDelimitedFileReader;
import edu.upenn.cit594.datamanagement.PropertyDelimitedFileReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

import java.io.FileNotFoundException;

public class UITester {
    public static void main(String[] args) throws FileNotFoundException {
        Logger.setFilename("log_test.txt");
        ParkingViolationDelimitedFileReader parkingViolationDelimitedFileReader = new ParkingViolationDelimitedFileReader(
                "parking.csv", false, ","
        );
        PropertyDelimitedFileReader propertyDelimitedFileReader = new PropertyDelimitedFileReader(
                "properties.csv",true,","
        );
        AreaDelimitedFileReader areaDelimitedFileReader = new AreaDelimitedFileReader(
                "population.txt", false, " "
        );
        Processor processor = new Processor( parkingViolationDelimitedFileReader, propertyDelimitedFileReader, areaDelimitedFileReader);
        
        new CommandLineUserInterface(processor).run();
    }
}
