package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.utils.Utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

// Reads in a delimited file of property values and converts to a List of ParkingViolation objects
public class PropertyDelimitedFileReader extends DelimitedFileReader<List<Property>> {
    private int zipcodeIndex;
    private int totalLivableAreaIndex;
    private int marketValueIndex;
    
    /**
     * Constructor
     * @param fileName the filename to read area data from
     * @param hasHeaders if the file has headers
     * @param delimitedBy the delimiter between values
     * @throws FileNotFoundException if the file cannot be found or opened
     */
    public PropertyDelimitedFileReader(String fileName, boolean hasHeaders, String delimitBy) throws FileNotFoundException {
        super(fileName, hasHeaders, delimitBy);
    }
    
    
    @Override
    public void initializeDataStore() {
        dataStore = new DataStore<>(new ArrayList<>());
    }

    @Override
    public void updateDataStore(List<String> dataList) {
        Property property = createProperty(dataList);
        dataStore.getData().add(property);
    }

    @Override
    public void setHeaderIndices() {
        this.zipcodeIndex = this.headerList.indexOf("zip_code");
        this.totalLivableAreaIndex = this.headerList.indexOf("total_livable_area");
        this.marketValueIndex = this.headerList.indexOf("market_value");
    }
    
    /**
     * Parses the provided dataList into a Property.
     * @param dataList the list of values with Property data
     * @return the Property being parsed from this data
     */
    private Property createProperty(List<String> dataList) {
        double marketValue = Utils.extractDoubleValueFromList(dataList, this.marketValueIndex);
        double totalLivableArea = Utils.extractDoubleValueFromList(dataList, this.totalLivableAreaIndex);
        String zipcode = Utils.extractZipcodeValueFromList(dataList, this.zipcodeIndex);

        return new Property(
                marketValue,
                totalLivableArea,
                zipcode
        );
    }
}



