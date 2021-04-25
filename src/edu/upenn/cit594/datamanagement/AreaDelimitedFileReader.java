package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaDelimitedFileReader extends DelimitedFileReader<Map<String, Area>> {
    public AreaDelimitedFileReader(String fileName, boolean hasHeaders, String delimitedBy) {
        super(fileName, hasHeaders, delimitedBy);
    }
    
    public void initializeDataStore() {
        dataStore = new DataStore<>(new HashMap<>());
    }

    public void updateDataStore(List<String> dataList) {
        Area area = createArea(dataList);

        dataStore.getData().put(area.zipcode, area);
    }

    @Override
    public void setHeaderIndices() {
        // NO HEADERS FOR THE CSV FILE
    }

    public Area createArea(List<String> dataList) {
        String zipcode = Utils.extractZipCodeValue(dataList.get(0));
        int population = Integer.parseInt(dataList.get(1));

        return new Area(zipcode, population);
    }
}
