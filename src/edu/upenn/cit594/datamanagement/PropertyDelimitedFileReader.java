package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PropertyDelimitedFileReader extends DelimitedFileReader<List<Property>> {
    private int zipcodeIndex;
    private int totalLivableAreaIndex;
    private int marketValueIndex;

    public PropertyDelimitedFileReader(String fileName, boolean hasHeaders, String delimitBy) {
        super(fileName, hasHeaders, delimitBy);

        dataStore = new ArrayList<Property>();
    }

    @Override
    public void updateDataStore(List<String> dataList) {
        Property property = createProperty(dataList);

        dataStore.add(property);
    }

    @Override
    public void setHeaderIndices() {
        this.zipcodeIndex = this.headerList.indexOf("zip_code");
        this.totalLivableAreaIndex = this.headerList.indexOf("total_livable_area");
        this.marketValueIndex = this.headerList.indexOf("market_value");
    }

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



