import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // validate number arguments
        if (args.length != 5) {
            System.out.println("Wrong number of arguments. Should be : java Main [parkingViolationsFileFormat] [parkingViolationsFilename] [propertyValuesFilename] [populationFilename] [logFilename]");
            return;
        }
    
        // parse arguments
        String parkingViolationsFileFormat = args[0];
        String parkingViolationsFilename = args[1];
        String propertyValuesFilename = args[2];
        String populationFilename = args[3];
        String logFilename = args[4];
    
        // create logger and log arguments
        Logger.setFilename(logFilename);
        Logger.getLogger().log(args);
        
        // validate arguments
        if (!validFileFormat(parkingViolationsFileFormat)) {
            System.out.println(parkingViolationsFileFormat + " is not a valid format. Must be json or csv.");
            return;
        }
    
        // set up readers and ensure they can be read
        Reader<List<ParkingViolation>> parkingViolationReader;
        Reader<List<Property>> propertyReader;
        Reader<Map<String, Area>> areaReader;
        
        try {
            parkingViolationReader = createParkingViolationReader(parkingViolationsFileFormat, parkingViolationsFilename);
        } catch (FileNotFoundException e) {
            System.out.println (parkingViolationsFilename + " could not be opened or read.");
            return;
        }
        
        try {
            propertyReader = new PropertyDelimitedFileReader(propertyValuesFilename, true, ",");
        } catch (FileNotFoundException e) {
            System.out.println (propertyValuesFilename + " could not be opened or read.");
            return;
        }
        
        try {
            areaReader = new AreaDelimitedFileReader(populationFilename, false, " ");
        } catch (FileNotFoundException e) {
            System.out.println (populationFilename + " could not be opened or read.");
            return;
        }
    
        // set up processor and ui and run
        Processor processor = new Processor(parkingViolationReader, propertyReader, areaReader);
        CommandLineUserInterface ui = new CommandLineUserInterface(processor);
        
        ui.run();
    }
    
    /**
     * Determines if provided file format is valid
     * @param fileFormat file format to check
     * @return if file format is valid
     */
    private static boolean validFileFormat(String fileFormat) {
        fileFormat = fileFormat.toLowerCase();
        return fileFormat.equals("json") || fileFormat.equals("csv");
    }
    
    /**
     * Creates the necessary Reader of parking violation based on the provided fileFormat and filename.
     * @param fileFormat the format of the file being read
     * @param parkingViolationsFilename the name of the file being read
     * @return the reader of parking violations based on the provided fileFormat and filename
     * @throws FileNotFoundException if provided filename cannot be opened or read
     */
    private static Reader<List<ParkingViolation>> createParkingViolationReader(String fileFormat, String parkingViolationsFilename) throws FileNotFoundException {
        Reader<List<ParkingViolation>> parkingViolationReader;
        if (fileFormat.equalsIgnoreCase("csv")) {
            parkingViolationReader = new ParkingViolationDelimitedFileReader(parkingViolationsFilename, false, ",");
        } else if (fileFormat.equalsIgnoreCase("json")) {
            parkingViolationReader = new ParkingViolationJSONFileReader(parkingViolationsFilename);
        } else {
            throw new IllegalArgumentException(fileFormat + " must be csv or json");
        }
        
        return parkingViolationReader;
    }
}
