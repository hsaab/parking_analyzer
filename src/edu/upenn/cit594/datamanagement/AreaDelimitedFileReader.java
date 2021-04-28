package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.utils.Utils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaDelimitedFileReader extends DelimitedFileReader<Map<String, Area>> {
    public AreaDelimitedFileReader(String fileName, boolean hasHeaders, String delimitedBy) throws FileNotFoundException {
        super(fileName, hasHeaders, delimitedBy);
    }
    
    public void initializeDataStore() {
        dataStore = new DataStore<>(new HashMap<>());
    }

    public void updateDataStore(List<String> dataList) {
        Area area = createArea(dataList);
        if (area == null) return;
        dataStore.getData().put(area.getZipcode(), area);
    }

    @Override
    public void setHeaderIndices() {
        // NO HEADERS FOR THE CSV FILE
    }

    public Area createArea(List<String> dataList) {
        String zipcode = Utils.extractZipcodeValueFromList(dataList, 0);
        double population = Utils.extractDoubleValueFromList(dataList, 1);
        if (zipcode.equals("") || Double.isNaN(population)) return null;
        return new Area(zipcode, population);
    }
}
