import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

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
    
        // create logger
        Logger.setFilename(logFilename);
        
        // validate arguments
        if (!validFileFormat(parkingViolationsFileFormat)) {
            System.out.println(parkingViolationsFileFormat + " is not a valid format. Must be json or text.");
            return;
        }
    
        // set up readers
        Reader<List<Property>> propertyReader = new PropertyDelimitedFileReader(propertyValuesFilename,true,",");
        Reader<Map<String, Area>> areaReader = new AreaDelimitedFileReader(populationFilename, false, " ");
        Reader<List<ParkingViolation>> parkingViolationReader;
        if (parkingViolationsFileFormat.equalsIgnoreCase("csv")) {
            parkingViolationReader = new ParkingViolationDelimitedFileReader(parkingViolationsFilename, false, ",");
        } else { // has to be json
            parkingViolationReader = new ParkingViolationJSONFileReader(parkingViolationsFilename);
        }
    
        
        Processor processor = new Processor(propertyReader, areaReader, parkingViolationReader);
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
}
