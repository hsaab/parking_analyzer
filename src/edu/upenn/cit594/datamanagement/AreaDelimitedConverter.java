package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaDelimitedConverter extends DelimitedFileConverter<Area> {
    
    public AreaDelimitedConverter(DelimitedFileReader reader) {
        super(reader, " ");
    }
    
    @Override
    public Area convert(String toParse) {
        List<String> values = splitLine(toParse);
        String stringZipcode = values.get(0);
        String zipcode = stringZipcode;
        if (zipcode.length() > 5) {
            zipcode = zipcode.substring(0, 5);
        }
        
        String stringPopulation = values.get(1);
        Long population = null;
        try {
            population = Long.parseLong(stringPopulation);
        } catch (NumberFormatException e) {
            // do nothing
        }
        
        return new Area(zipcode, population);
    }
    
    /**
     * Converts this reader's contents into a map of zipcode of area to Area
     * @return map of zipcode of area to Area
     */
    @Override
    public Map<String,Area> convertToMap() {
        Map<String,Area> map = new HashMap<>();
        for (Area area: convertToList()) {
            map.put(area.getZipcode(), area);
        }
        
        return map;
    }
}
