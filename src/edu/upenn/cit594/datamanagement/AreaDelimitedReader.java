package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Area;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AreaDelimitedReader extends DelimitedReader<Map> {
    AreaDelimitedReader(String fileName) {
        super(fileName);
    }

    public Map<String, Area> createDataStore(BufferedReader br) {
        String line = null;
        String splitBy = " ";
        Map<String, Area> areaMap = new HashMap<>();

        try {
            while((line = br.readLine()) != null) {
                Area area = createArea(line, splitBy);

                areaMap.put(area.zipcode, area);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return areaMap;
    }

    public Area createArea(String line, String splitBy) {
        String[] array = line.split(splitBy);

        String zipcode = array[0];
        int population = Integer.parseInt(array[1]);

        return new Area(zipcode, population);
    }
}
