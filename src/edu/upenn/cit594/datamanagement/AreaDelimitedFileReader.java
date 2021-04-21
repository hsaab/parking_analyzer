package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaDelimitedFileReader extends DelimitedFileReader<Map<String, Area>> {
    AreaDelimitedFileReader(String fileName, boolean hasHeaders, String delimitedBy) {
        super(fileName, hasHeaders, delimitedBy);

        dataStore = new HashMap<String, Area>();
    }

    public void updateDataStore(List<String> dataList) {
        Area area = createArea(dataList);

        dataStore.put(area.zipcode, area);
    }

    @Override
    public void setHeaderIndices(List<String> headerList) {
        // NO HEADERS FOR THE CSV FILE
    }

    public Area createArea(List<String> dataList) {
        String zipcode = dataList.get(0);
        int population = Integer.parseInt(dataList.get(1));

        return new Area(zipcode, population);
    }
}
