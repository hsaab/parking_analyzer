package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.utils.Utils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Reads in a delimited file of area values and converts to a Map of the zipcodes of the Area to the Area itself
public class AreaDelimitedFileReader extends DelimitedFileReader<Map<String, Area>> {
    /**
     * Constructor
     * @param fileName the filename to read area data from
     * @param hasHeaders if the file has headers
     * @param delimitedBy the delimiter between values
     * @throws FileNotFoundException if the file cannot be found or opened
     */
    public AreaDelimitedFileReader(String fileName, boolean hasHeaders, String delimitedBy) throws FileNotFoundException {
        super(fileName, hasHeaders, delimitedBy);
    }
    
    @Override
    public void initializeDataStore() {
        dataStore = new DataStore<>(new HashMap<>());
    }
    
    /**
     * Updates the dataStore based on the provided data being passed in.
     * Ignores any null values.
     * @param dataList the data being passed in to convert to an Area.
     */
    public void updateDataStore(List<String> dataList) {
        Area area = createArea(dataList);
        if (area == null) return;
        dataStore.getData().put(area.getZipcode(), area);
    }

    @Override
    public void setHeaderIndices() {
        // no indices to set for this reader
    }
    
    /**
     * Parses the provided dataList into an Area.
     * Returns null if zipcode is blank or the population is not a number.
     * @param dataList the list of values with Area data
     * @return the Area being parsed from this data
     */
    public Area createArea(List<String> dataList) {
        String zipcode = Utils.extractZipcodeValueFromList(dataList, 0);
        double population = Utils.extractDoubleValueFromList(dataList, 1);
        if (zipcode.equals("") || Double.isNaN(population)) return null;
        return new Area(zipcode, population);
    }
}
